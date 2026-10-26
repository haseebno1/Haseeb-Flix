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

package com.haseebno1.haseebflix.core.data.mapper

import com.haseebno1.haseebflix.core.database.model.common.MediaType
import com.haseebno1.haseebflix.core.database.model.tvshow.TvShowEntity
import com.haseebno1.haseebflix.core.domain.model.TvShowModel
import com.haseebno1.haseebflix.core.network.model.tvshow.NetworkTvShow

internal fun NetworkTvShow.asTvShowEntity(mediaType: MediaType.TvShow) = TvShowEntity(
    mediaType = mediaType,
    networkId = id,
    name = name,
    overview = overview,
    popularity = popularity,
    firstAirDate = firstAirDate,
    genres = genreIds.asGenres(),
    originalName = originalName,
    originalLanguage = originalLanguage,
    originCountry = originCountry,
    voteAverage = voteAverage,
    voteCount = voteCount,
    posterPath = posterPath?.asImageUrl(),
    backdropPath = backdropPath?.asImageUrl()
)

internal fun TvShowEntity.asTvShowModel() = TvShowModel(
    id = networkId,
    name = name,
    overview = overview,
    popularity = popularity,
    firstAirDate = firstAirDate,
    genres = genres.asGenreModels(),
    originalName = originalName,
    originalLanguage = originalLanguage,
    originCountry = originCountry,
    voteAverage = voteAverage,
    voteCount = voteCount,
    posterPath = posterPath,
    backdropPath = backdropPath
)

internal fun NetworkTvShow.asTvShowModel() = TvShowModel(
    id = id,
    name = name,
    overview = overview,
    popularity = popularity,
    firstAirDate = firstAirDate,
    genres = genreIds.asGenreModels(),
    originalName = originalName,
    originalLanguage = originalLanguage,
    originCountry = originCountry,
    voteAverage = voteAverage,
    voteCount = voteCount,
    posterPath = posterPath?.asImageUrl(),
    backdropPath = backdropPath?.asImageUrl()
)
