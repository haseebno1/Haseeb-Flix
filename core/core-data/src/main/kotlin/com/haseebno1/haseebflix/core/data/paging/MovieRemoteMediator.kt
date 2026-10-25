/*
 * Copyright 2022 Afig Aliyev
 * Modifications Copyright 2026 Abdul Haseeb
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haseebno1.haseebflix.core.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.haseebno1.haseebflix.core.common.result.HttpException
import com.haseebno1.haseebflix.core.common.result.isFailure
import com.haseebno1.haseebflix.core.common.result.isSuccess
import com.haseebno1.haseebflix.core.data.mapper.asMovieEntity
import com.haseebno1.haseebflix.core.data.mapper.asNetworkMediaType
import com.haseebno1.haseebflix.core.data.util.Constants
import com.haseebno1.haseebflix.core.data.util.PagingUtils.getRemoteKeyClosestToCurrentPosition
import com.haseebno1.haseebflix.core.data.util.PagingUtils.getRemoteKeyForFirstItem
import com.haseebno1.haseebflix.core.data.util.PagingUtils.getRemoteKeyForLastItem
import com.haseebno1.haseebflix.core.database.model.common.MediaType
import com.haseebno1.haseebflix.core.database.model.movie.MovieEntity
import com.haseebno1.haseebflix.core.database.model.movie.MovieRemoteKeyEntity
import com.haseebno1.haseebflix.core.database.source.MovieDatabaseDataSource
import com.haseebno1.haseebflix.core.network.source.MovieNetworkDataSource
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val databaseDataSource: MovieDatabaseDataSource,
    private val networkDataSource: MovieNetworkDataSource,
    private val mediaType: MediaType.Movie
) : RemoteMediator<Int, MovieEntity>() {
    @Suppress("ReturnCount")
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKey?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKey = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKey?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKey != null
                    )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKey = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKey?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKey != null
                    )
                    nextPage
                }
            }

            val response = networkDataSource.getByMediaType(
                mediaType = mediaType.asNetworkMediaType(),
                page = currentPage
            )

            when {
                response.isSuccess() -> {
                    val movies = response.value.results.map { it.asMovieEntity(mediaType) }
                    val endOfPaginationReached = movies.isEmpty()

                    val prevPage = if (currentPage == 1) null else currentPage - 1
                    val nextPage = if (endOfPaginationReached) null else currentPage + 1

                    val remoteKeys = movies.map { entity ->
                        MovieRemoteKeyEntity(
                            id = entity.networkId,
                            mediaType = mediaType,
                            prevPage = prevPage,
                            nextPage = nextPage
                        )
                    }

                    databaseDataSource.handlePaging(
                        mediaType = mediaType,
                        movies = movies,
                        remoteKeys = remoteKeys,
                        shouldDeleteMoviesAndRemoteKeys = loadType == LoadType.REFRESH
                    )

                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }
                response.isFailure() -> return MediatorResult.Error(response.error)
                else -> error("${Constants.Messages.UNHANDLED_STATE} $response")
            }
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieEntity>
    ): MovieRemoteKeyEntity? = getRemoteKeyClosestToCurrentPosition(state) { entity ->
        databaseDataSource.getRemoteKeyByIdAndMediaType(
            id = entity.networkId,
            mediaType = mediaType
        )
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieEntity>
    ): MovieRemoteKeyEntity? = getRemoteKeyForFirstItem(state) { entity ->
        databaseDataSource.getRemoteKeyByIdAndMediaType(
            id = entity.networkId,
            mediaType = mediaType
        )
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieEntity>
    ): MovieRemoteKeyEntity? = getRemoteKeyForLastItem(state) { entity ->
        databaseDataSource.getRemoteKeyByIdAndMediaType(
            id = entity.networkId,
            mediaType = mediaType
        )
    }
}
