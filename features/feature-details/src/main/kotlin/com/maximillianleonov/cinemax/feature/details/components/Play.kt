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

package com.maximillianleonov.cinemax.feature.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.maximillianleonov.cinemax.core.designsystem.theme.CinemaxTheme
import com.maximillianleonov.cinemax.core.ui.R

@Composable
internal fun PlayButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = CinemaxTheme.spacing.extraMedium)
            .height(PlayButtonHeight),
        onClick = onClick,
        shape = CinemaxTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = CinemaxTheme.colors.primaryBlue,
            contentColor = CinemaxTheme.colors.white
        )
    ) {
        Text(text = text, style = CinemaxTheme.typography.semiBold.h5)
    }
}

@Composable
internal fun MoviePlayContent(onPlayClick: () -> Unit, modifier: Modifier = Modifier) {
    PlayButton(
        modifier = modifier,
        text = stringResource(id = R.string.play),
        onClick = onPlayClick
    )
}

@Suppress("LongParameterList")
@Composable
internal fun TvShowPlayContent(
    numberOfSeasons: Int,
    selectedSeason: Int,
    selectedEpisode: Int,
    onSeasonChange: (Int) -> Unit,
    onEpisodeChange: (Int) -> Unit,
    onPlayClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.small)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(CinemaxTheme.spacing.medium)) {
            Stepper(
                text = stringResource(id = R.string.season_number, selectedSeason),
                decrementDescription = stringResource(id = R.string.previous_season),
                incrementDescription = stringResource(id = R.string.next_season),
                canDecrement = selectedSeason > 1,
                canIncrement = selectedSeason < numberOfSeasons,
                onDecrement = { onSeasonChange(selectedSeason - 1) },
                onIncrement = { onSeasonChange(selectedSeason + 1) }
            )
            Stepper(
                text = stringResource(id = R.string.episode_number, selectedEpisode),
                decrementDescription = stringResource(id = R.string.previous_episode),
                incrementDescription = stringResource(id = R.string.next_episode),
                canDecrement = selectedEpisode > 1,
                canIncrement = true,
                onDecrement = { onEpisodeChange(selectedEpisode - 1) },
                onIncrement = { onEpisodeChange(selectedEpisode + 1) }
            )
        }
        PlayButton(
            text = stringResource(id = R.string.play_episode, selectedSeason, selectedEpisode),
            onClick = onPlayClick
        )
    }
}

@Suppress("LongParameterList")
@Composable
private fun Stepper(
    text: String,
    decrementDescription: String,
    incrementDescription: String,
    canDecrement: Boolean,
    canIncrement: Boolean,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        TextButton(
            modifier = Modifier.semantics { contentDescription = decrementDescription },
            onClick = onDecrement,
            enabled = canDecrement
        ) {
            Text(text = "\u2212", style = CinemaxTheme.typography.semiBold.h4)
        }
        Text(
            text = text,
            style = CinemaxTheme.typography.medium.h5,
            color = CinemaxTheme.colors.white
        )
        TextButton(
            modifier = Modifier.semantics { contentDescription = incrementDescription },
            onClick = onIncrement,
            enabled = canIncrement
        ) {
            Text(text = "+", style = CinemaxTheme.typography.semiBold.h4)
        }
    }
}

private val PlayButtonHeight = 48.dp
