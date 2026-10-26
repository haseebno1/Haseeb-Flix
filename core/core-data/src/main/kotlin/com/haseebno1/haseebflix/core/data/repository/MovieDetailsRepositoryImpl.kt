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

import com.haseebno1.haseebflix.core.common.result.HaseebFlixResult
import com.haseebno1.haseebflix.core.data.mapper.asMovieDetailsEntity
import com.haseebno1.haseebflix.core.data.mapper.asMovieDetailsModel
import com.haseebno1.haseebflix.core.data.mapper.listMap
import com.haseebno1.haseebflix.core.database.source.MovieDetailsDatabaseDataSource
import com.haseebno1.haseebflix.core.database.source.WishlistDatabaseDataSource
import com.haseebno1.haseebflix.core.domain.model.MovieDetailsModel
import com.haseebno1.haseebflix.core.domain.repository.MovieDetailsRepository
import com.haseebno1.haseebflix.core.network.common.networkBoundResource
import com.haseebno1.haseebflix.core.network.model.movie.NetworkMovieDetails
import com.haseebno1.haseebflix.core.network.source.MovieDetailsNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val databaseDataSource: MovieDetailsDatabaseDataSource,
    private val networkDataSource: MovieDetailsNetworkDataSource,
    private val wishlistDatabaseDataSource: WishlistDatabaseDataSource
) : MovieDetailsRepository {
    override fun getById(id: Int): Flow<HaseebFlixResult<MovieDetailsModel?>> = networkBoundResource(
        query = {
            databaseDataSource.getById(id).map {
                it?.asMovieDetailsModel(
                    isWishlisted = wishlistDatabaseDataSource.isMovieWishlisted(it.id)
                )
            }
        },
        fetch = { networkDataSource.getById(id) },
        saveFetchResult = { response ->
            databaseDataSource.deleteAndInsert(response.asMovieDetailsEntity())
        }
    )

    override fun getByIds(ids: List<Int>): Flow<HaseebFlixResult<List<MovieDetailsModel>>> =
        networkBoundResource(
            query = {
                databaseDataSource.getByIds(ids).listMap {
                    it.asMovieDetailsModel(
                        isWishlisted = wishlistDatabaseDataSource.isMovieWishlisted(it.id)
                    )
                }
            },
            fetch = { networkDataSource.getByIds(ids) },
            saveFetchResult = { response ->
                databaseDataSource.deleteAndInsertAll(
                    response.map(NetworkMovieDetails::asMovieDetailsEntity)
                )
            }
        )
}
