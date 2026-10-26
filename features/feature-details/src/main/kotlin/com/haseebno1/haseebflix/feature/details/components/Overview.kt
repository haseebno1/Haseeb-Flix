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

package com.haseebno1.haseebflix.feature.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.haseebno1.haseebflix.core.designsystem.component.haseebflixPlaceholder
import com.haseebno1.haseebflix.core.designsystem.theme.HaseebFlixTheme
import com.haseebno1.haseebflix.core.ui.R

@Composable
internal fun Overview(
    overview: String,
    modifier: Modifier = Modifier,
    isPlaceholder: Boolean = false
) {
    Column(
        modifier = modifier
            .padding(horizontal = HaseebFlixTheme.spacing.extraMedium)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(HaseebFlixTheme.spacing.small)
    ) {
        Text(
            text = stringResource(id = R.string.overview),
            style = HaseebFlixTheme.typography.semiBold.h4,
            color = HaseebFlixTheme.colors.white
        )
        Text(
            modifier = if (isPlaceholder) {
                Modifier
                    .fillMaxWidth()
                    .haseebflixPlaceholder(color = HaseebFlixTheme.colors.grey)
            } else {
                Modifier
            },
            text = overview.ifEmpty { stringResource(id = R.string.no_overview) },
            style = HaseebFlixTheme.typography.regular.h5,
            color = HaseebFlixTheme.colors.whiteGrey
        )
    }
}

@Composable
internal fun OverviewPlaceholder(modifier: Modifier = Modifier) {
    Overview(modifier = modifier, overview = PlaceholderText, isPlaceholder = true)
}

private const val PlaceholderText = ""
