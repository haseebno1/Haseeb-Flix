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

package com.maximillianleonov.cinemax.feature.player

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.maximillianleonov.cinemax.core.ui.common.EventHandler
import com.maximillianleonov.cinemax.feature.player.navigation.PlayerDestination
import com.maximillianleonov.cinemax.feature.player.navigation.PlayerRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    config: PlayerConfig,
    savedStateHandle: SavedStateHandle
) : ViewModel(), EventHandler<PlayerEvent> {
    private val _uiState = MutableStateFlow(
        createInitialUiState(
            config = config,
            request = PlayerDestination.fromSavedStateHandle(savedStateHandle)
        )
    )
    val uiState = _uiState.asStateFlow()

    override fun onEvent(event: PlayerEvent) = when (event) {
        PlayerEvent.Retry -> onRetry()
        PlayerEvent.PlaybackFailed -> onPlaybackFailed()
    }

    private fun onRetry() = _uiState.update {
        it.copy(hasError = false, reloadKey = it.reloadKey + 1)
    }

    private fun onPlaybackFailed() = _uiState.update { it.copy(hasError = true) }

    private fun createInitialUiState(config: PlayerConfig, request: PlayerRequest): PlayerUiState {
        val urlBuilder = PlayerUrlBuilder(config.baseUrl)
        val embedUrl = when (request) {
            is PlayerRequest.Movie -> urlBuilder.movie(request.id, request.title, AppPlayerOptions)
            is PlayerRequest.TvShow -> urlBuilder.tvShow(
                tmdbId = request.id,
                season = request.season,
                episode = request.episode,
                title = request.title,
                options = AppPlayerOptions
            )
        }
        return PlayerUiState(
            embedUrl = embedUrl,
            embedOrigin = urlBuilder.origin,
            hostPageOrigin = config.hostPageOrigin
        )
    }
}

private val AppPlayerOptions = PlayerOptions(
    brand = "HaseebFlix",
    brandColorHex = "E50914",
    showExit = true
)
