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

package com.haseebno1.haseebflix.feature.list.util

import androidx.annotation.StringRes
import com.haseebno1.haseebflix.core.model.MediaType
import com.haseebno1.haseebflix.core.model.MediaType.Common.Discover
import com.haseebno1.haseebflix.core.model.MediaType.Common.NowPlaying
import com.haseebno1.haseebflix.core.model.MediaType.Common.Popular
import com.haseebno1.haseebflix.core.model.MediaType.Common.TopRated
import com.haseebno1.haseebflix.core.model.MediaType.Common.Trending
import com.haseebno1.haseebflix.core.model.MediaType.Common.Upcoming
import com.haseebno1.haseebflix.core.ui.R

@StringRes
internal fun MediaType.Common.asTitleResourceId() = mediaTypesTitleResources.getValue(this)

private val mediaTypesTitleResources = mapOf(
    Upcoming to R.string.upcoming_movies,
    TopRated to R.string.top_rated,
    Popular to R.string.most_popular,
    NowPlaying to R.string.now_playing,
    Discover to R.string.discover,
    Trending to R.string.trending
)
