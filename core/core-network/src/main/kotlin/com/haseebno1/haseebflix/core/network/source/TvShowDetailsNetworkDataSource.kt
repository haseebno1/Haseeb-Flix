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

package com.haseebno1.haseebflix.core.network.source

import com.haseebno1.haseebflix.core.common.result.HaseebFlixResult
import com.haseebno1.haseebflix.core.common.result.isFailure
import com.haseebno1.haseebflix.core.common.result.isSuccess
import com.haseebno1.haseebflix.core.network.api.service.TvShowService
import com.haseebno1.haseebflix.core.network.model.tvshow.NetworkTvShowDetails
import com.haseebno1.haseebflix.core.network.util.Constants
import com.haseebno1.haseebflix.core.network.util.MESSAGE_UNHANDLED_STATE
import javax.inject.Inject

class TvShowDetailsNetworkDataSource @Inject constructor(private val tvShowService: TvShowService) {
    suspend fun getById(
        id: Int,
        appendToResponse: String = Constants.Fields.DETAILS_APPEND_TO_RESPONSE
    ): HaseebFlixResult<NetworkTvShowDetails> = tvShowService.getDetailsById(id, appendToResponse)

    suspend fun getByIds(
        ids: List<Int>,
        appendToResponse: String = Constants.Fields.DETAILS_APPEND_TO_RESPONSE
    ): HaseebFlixResult<List<NetworkTvShowDetails>> {
        val tvShows = ids.map { id ->
            val response = tvShowService.getDetailsById(id, appendToResponse)

            when {
                response.isSuccess() -> response.value
                response.isFailure() -> return HaseebFlixResult.failure(response.error)
                else -> error("$MESSAGE_UNHANDLED_STATE $response")
            }
        }

        return HaseebFlixResult.success(tvShows)
    }
}
