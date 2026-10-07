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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maximillianleonov.cinemax.core.designsystem.theme.CinemaxTheme
import com.maximillianleonov.cinemax.core.model.UserMessage
import com.maximillianleonov.cinemax.core.ui.CinemaxBackButton
import com.maximillianleonov.cinemax.core.ui.CinemaxCenteredError
import com.maximillianleonov.cinemax.core.ui.R

@Composable
internal fun PlayerRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PlayerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PlayerScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onRetry = { viewModel.onEvent(PlayerEvent.Retry) },
        onPlaybackFailed = { viewModel.onEvent(PlayerEvent.PlaybackFailed) },
        modifier = modifier
    )
}

@Composable
private fun PlayerScreen(
    uiState: PlayerUiState,
    onBackClick: () -> Unit,
    onRetry: () -> Unit,
    onPlaybackFailed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier.fillMaxSize(), color = Color.Black) {
        if (uiState.hasError) {
            Box(modifier = Modifier.fillMaxSize()) {
                CinemaxCenteredError(
                    errorMessage = UserMessage(messageResourceId = R.string.player_error),
                    onRetry = onRetry
                )
                CinemaxBackButton(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .windowInsetsPadding(WindowInsets.safeDrawing)
                        .padding(CinemaxTheme.spacing.medium),
                    onClick = onBackClick
                )
            }
        } else {
            PlayerWebView(
                embedUrl = uiState.embedUrl,
                embedOrigin = uiState.embedOrigin,
                hostPageOrigin = uiState.hostPageOrigin,
                reloadKey = uiState.reloadKey,
                onExit = onBackClick,
                onError = onPlaybackFailed,
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.safeDrawing)
            )
        }
    }
}
