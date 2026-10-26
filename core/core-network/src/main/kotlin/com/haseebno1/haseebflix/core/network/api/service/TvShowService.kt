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

package com.haseebno1.haseebflix.core.network.api.service

import com.haseebno1.haseebflix.core.common.result.HaseebFlixResult
import com.haseebno1.haseebflix.core.network.model.response.TvShowResponseDto
import com.haseebno1.haseebflix.core.network.model.tvshow.NetworkTvShowDetails
import com.haseebno1.haseebflix.core.network.util.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowService {
    @GET(Constants.Path.TOP_RATED_TV_SHOW)
    suspend fun getTopRated(
        @Query(Constants.Fields.PAGE) page: Int = Constants.DEFAULT_PAGE
    ): HaseebFlixResult<TvShowResponseDto>

    @GET(Constants.Path.POPULAR_TV_SHOW)
    suspend fun getPopular(
        @Query(Constants.Fields.PAGE) page: Int = Constants.DEFAULT_PAGE
    ): HaseebFlixResult<TvShowResponseDto>

    @GET(Constants.Path.ON_THE_AIR_TV_SHOW)
    suspend fun getOnTheAir(
        @Query(Constants.Fields.PAGE) page: Int = Constants.DEFAULT_PAGE
    ): HaseebFlixResult<TvShowResponseDto>

    @GET(Constants.Path.DISCOVER_TV_SHOW)
    suspend fun getDiscover(
        @Query(Constants.Fields.PAGE) page: Int = Constants.DEFAULT_PAGE
    ): HaseebFlixResult<TvShowResponseDto>

    @GET(Constants.Path.TRENDING_TV_SHOW)
    suspend fun getTrending(
        @Query(Constants.Fields.PAGE) page: Int = Constants.DEFAULT_PAGE
    ): HaseebFlixResult<TvShowResponseDto>

    @GET(Constants.Path.DETAILS_TV_SHOW)
    suspend fun getDetailsById(
        @Path(Constants.Fields.ID)
        id: Int,
        @Query(Constants.Fields.APPEND_TO_RESPONSE)
        appendToResponse: String = Constants.Fields.DETAILS_APPEND_TO_RESPONSE
    ): HaseebFlixResult<NetworkTvShowDetails>

    @GET(Constants.Path.SEARCH_TV_SHOW)
    suspend fun search(
        @Query(Constants.Fields.QUERY) query: String,
        @Query(Constants.Fields.PAGE) page: Int = Constants.DEFAULT_PAGE
    ): HaseebFlixResult<TvShowResponseDto>
}
