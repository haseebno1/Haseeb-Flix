/*
 * Copyright 2022 Afig Aliyev
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

package com.maximillianleonov.cinemax.feature.player.navigation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.maximillianleonov.cinemax.core.navigation.CinemaxNavigationDestination
import com.maximillianleonov.cinemax.feature.player.PlayerRoute

internal sealed interface PlayerRequest {
    val id: Int
    val title: String?

    data class Movie(override val id: Int, override val title: String?) : PlayerRequest
    data class TvShow(
        override val id: Int,
        override val title: String?,
        val season: Int,
        val episode: Int
    ) : PlayerRequest
}

object PlayerDestination : CinemaxNavigationDestination {
    override val route = "player_route"
    override val destination = "player_destination"

    const val idArgument = "id"
    const val mediaTypeArgument = "mediaType"
    const val seasonArgument = "season"
    const val episodeArgument = "episode"
    const val titleArgument = "title"

    val routeWithArguments = "$route/{$idArgument}/{$mediaTypeArgument}" +
        "?$seasonArgument={$seasonArgument}" +
        "&$episodeArgument={$episodeArgument}" +
        "&$titleArgument={$titleArgument}"

    fun createMovieRoute(id: Int, title: String?) =
        createRoute(id, MovieMediaType, DefaultSeason, DefaultEpisode, title)

    fun createTvShowRoute(id: Int, title: String?, season: Int, episode: Int) =
        createRoute(id, TvShowMediaType, season, episode, title)

    private fun createRoute(
        id: Int,
        mediaType: String,
        season: Int,
        episode: Int,
        title: String?
    ) = buildString {
        append("$route/$id/$mediaType")
        append("?$seasonArgument=$season&$episodeArgument=$episode")
        if (!title.isNullOrBlank()) append("&$titleArgument=${Uri.encode(title)}")
    }

    internal fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): PlayerRequest {
        val id = checkNotNull(savedStateHandle.get<Int>(idArgument)) { MediaIdNullMessage }
        val title = savedStateHandle.get<String>(titleArgument)
        return when (val mediaType = savedStateHandle.get<String>(mediaTypeArgument)) {
            MovieMediaType -> PlayerRequest.Movie(id = id, title = title)
            TvShowMediaType -> PlayerRequest.TvShow(
                id = id,
                title = title,
                season = savedStateHandle.get<Int>(seasonArgument) ?: DefaultSeason,
                episode = savedStateHandle.get<Int>(episodeArgument) ?: DefaultEpisode
            )

            else -> error("$InvalidMediaTypeMessage $mediaType")
        }
    }
}

fun NavGraphBuilder.playerGraph(onBackClick: () -> Unit) = composable(
    route = PlayerDestination.routeWithArguments,
    arguments = listOf(
        navArgument(PlayerDestination.idArgument) { type = NavType.IntType },
        navArgument(PlayerDestination.mediaTypeArgument) { type = NavType.StringType },
        navArgument(PlayerDestination.seasonArgument) {
            type = NavType.IntType
            defaultValue = DefaultSeason
        },
        navArgument(PlayerDestination.episodeArgument) {
            type = NavType.IntType
            defaultValue = DefaultEpisode
        },
        navArgument(PlayerDestination.titleArgument) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        }
    )
) {
    PlayerRoute(onBackClick = onBackClick)
}

// Path segment values of the embed provider (`/embed/movie/..`, `/embed/tv/..`).
private const val MovieMediaType = "movie"
private const val TvShowMediaType = "tv"
private const val DefaultSeason = 1
private const val DefaultEpisode = 1

private const val MediaIdNullMessage = "Media id is null."
private const val InvalidMediaTypeMessage = "Invalid media type."
