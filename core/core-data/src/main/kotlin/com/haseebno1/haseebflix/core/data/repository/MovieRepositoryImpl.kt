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

package com.haseebno1.haseebflix.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import com.haseebno1.haseebflix.core.common.result.HaseebFlixResult
import com.haseebno1.haseebflix.core.data.mapper.asMediaType
import com.haseebno1.haseebflix.core.data.mapper.asMovieEntity
import com.haseebno1.haseebflix.core.data.mapper.asMovieModel
import com.haseebno1.haseebflix.core.data.mapper.asNetworkMediaType
import com.haseebno1.haseebflix.core.data.mapper.listMap
import com.haseebno1.haseebflix.core.data.mapper.pagingMap
import com.haseebno1.haseebflix.core.data.paging.MovieRemoteMediator
import com.haseebno1.haseebflix.core.data.paging.SearchMoviePagingSource
import com.haseebno1.haseebflix.core.data.util.defaultPagingConfig
import com.haseebno1.haseebflix.core.database.model.movie.MovieEntity
import com.haseebno1.haseebflix.core.database.source.MovieDatabaseDataSource
import com.haseebno1.haseebflix.core.domain.model.MediaTypeModel
import com.haseebno1.haseebflix.core.domain.model.MovieModel
import com.haseebno1.haseebflix.core.domain.repository.MovieRepository
import com.haseebno1.haseebflix.core.network.common.networkBoundResource
import com.haseebno1.haseebflix.core.network.source.MovieNetworkDataSource
import com.haseebno1.haseebflix.core.network.util.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val databaseDataSource: MovieDatabaseDataSource,
    private val networkDataSource: MovieNetworkDataSource
) : MovieRepository {
    override fun getByMediaType(
        mediaTypeModel: MediaTypeModel.Movie
    ): Flow<HaseebFlixResult<List<MovieModel>>> {
        val mediaType = mediaTypeModel.asMediaType()
        return networkBoundResource(
            query = {
                databaseDataSource.getByMediaType(
                    mediaType = mediaType,
                    pageSize = PAGE_SIZE
                ).listMap(MovieEntity::asMovieModel)
            },
            fetch = { networkDataSource.getByMediaType(mediaType.asNetworkMediaType()) },
            saveFetchResult = { response ->
                databaseDataSource.deleteByMediaTypeAndInsertAll(
                    mediaType = mediaType,
                    movies = response.results.map { it.asMovieEntity(mediaType) }
                )
            }
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagingByMediaType(
        mediaTypeModel: MediaTypeModel.Movie
    ): Flow<PagingData<MovieModel>> {
        val mediaType = mediaTypeModel.asMediaType()
        return Pager(
            config = defaultPagingConfig,
            remoteMediator = MovieRemoteMediator(databaseDataSource, networkDataSource, mediaType),
            pagingSourceFactory = { databaseDataSource.getPagingByMediaType(mediaType) }
        ).flow.pagingMap(MovieEntity::asMovieModel)
    }

    override fun search(query: String): Flow<PagingData<MovieModel>> = Pager(
        config = defaultPagingConfig,
        pagingSourceFactory = { SearchMoviePagingSource(query, networkDataSource) }
    ).flow
}
