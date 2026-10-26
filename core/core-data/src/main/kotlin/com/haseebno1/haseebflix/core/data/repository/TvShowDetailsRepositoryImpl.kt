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
import com.haseebno1.haseebflix.core.data.mapper.asTvShowDetailsEntity
import com.haseebno1.haseebflix.core.data.mapper.asTvShowDetailsModel
import com.haseebno1.haseebflix.core.data.mapper.listMap
import com.haseebno1.haseebflix.core.database.source.TvShowDetailsDatabaseDataSource
import com.haseebno1.haseebflix.core.database.source.WishlistDatabaseDataSource
import com.haseebno1.haseebflix.core.domain.model.TvShowDetailsModel
import com.haseebno1.haseebflix.core.domain.repository.TvShowDetailsRepository
import com.haseebno1.haseebflix.core.network.common.networkBoundResource
import com.haseebno1.haseebflix.core.network.model.tvshow.NetworkTvShowDetails
import com.haseebno1.haseebflix.core.network.source.TvShowDetailsNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TvShowDetailsRepositoryImpl @Inject constructor(
    private val databaseDataSource: TvShowDetailsDatabaseDataSource,
    private val networkDataSource: TvShowDetailsNetworkDataSource,
    private val wishlistDatabaseDataSource: WishlistDatabaseDataSource
) : TvShowDetailsRepository {
    override fun getById(id: Int): Flow<HaseebFlixResult<TvShowDetailsModel?>> = networkBoundResource(
        query = {
            databaseDataSource.getById(id).map {
                it?.asTvShowDetailsModel(
                    isWishlisted = wishlistDatabaseDataSource.isTvShowWishlisted(it.id)
                )
            }
        },
        fetch = { networkDataSource.getById(id) },
        saveFetchResult = { response ->
            databaseDataSource.deleteAndInsert(response.asTvShowDetailsEntity())
        }
    )

    override fun getByIds(ids: List<Int>): Flow<HaseebFlixResult<List<TvShowDetailsModel>>> =
        networkBoundResource(
            query = {
                databaseDataSource.getByIds(ids).listMap {
                    it.asTvShowDetailsModel(
                        isWishlisted = wishlistDatabaseDataSource.isTvShowWishlisted(it.id)
                    )
                }
            },
            fetch = { networkDataSource.getByIds(ids) },
            saveFetchResult = { response ->
                databaseDataSource.deleteAndInsertAll(
                    response.map(NetworkTvShowDetails::asTvShowDetailsEntity)
                )
            }
        )
}
