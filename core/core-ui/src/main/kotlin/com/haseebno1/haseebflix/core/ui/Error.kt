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

package com.haseebno1.haseebflix.core.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import com.haseebno1.haseebflix.core.designsystem.component.HaseebFlixCenteredBox
import com.haseebno1.haseebflix.core.designsystem.component.HaseebFlixOutlinedButton
import com.haseebno1.haseebflix.core.designsystem.theme.HaseebFlixTheme
import com.haseebno1.haseebflix.core.model.UserMessage
import com.haseebno1.haseebflix.core.ui.mapper.asString

@Composable
fun HaseebFlixError(
    errorMessage: UserMessage,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = HaseebFlixTheme.shapes.medium,
    containerColor: Color = HaseebFlixTheme.colors.primarySoft,
    errorTextColor: Color = HaseebFlixTheme.colors.secondaryRed,
    actionButtonColor: Color = HaseebFlixTheme.colors.primaryBlue,
    errorTextStyle: TextStyle = HaseebFlixTheme.typography.regular.h4,
    @StringRes actionButtonTextResourceId: Int = R.string.retry,
    shouldShowOfflineMode: Boolean = false,
    onOfflineModeClick: () -> Unit = {},
    offlineModeButtonColor: Color = HaseebFlixTheme.colors.secondaryGreen,
    @StringRes offlineModeButtonTextResourceId: Int = R.string.offline_mode
) {
    Column(
        modifier = modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(HaseebFlixTheme.spacing.small)
    ) {
        Card(
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = containerColor)
        ) {
            Column(
                modifier = Modifier.padding(HaseebFlixTheme.spacing.extraMedium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(HaseebFlixTheme.spacing.small)
            ) {
                Text(
                    text = errorMessage.asString(),
                    style = errorTextStyle,
                    color = errorTextColor
                )
                HaseebFlixOutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    textResourceId = actionButtonTextResourceId,
                    onClick = onRetry,
                    containerColor = HaseebFlixTheme.colors.primarySoft,
                    contentColor = actionButtonColor
                )
            }
        }
        if (shouldShowOfflineMode) {
            HaseebFlixOutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                textResourceId = offlineModeButtonTextResourceId,
                onClick = onOfflineModeClick,
                containerColor = HaseebFlixTheme.colors.primaryDark,
                contentColor = offlineModeButtonColor
            )
        }
    }
}

@Composable
fun HaseebFlixCenteredError(
    errorMessage: UserMessage,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = HaseebFlixTheme.shapes.medium,
    containerColor: Color = HaseebFlixTheme.colors.primarySoft,
    errorTextColor: Color = HaseebFlixTheme.colors.secondaryRed,
    actionButtonColor: Color = HaseebFlixTheme.colors.primaryBlue,
    @StringRes actionButtonTextResourceId: Int = R.string.retry,
    shouldShowOfflineMode: Boolean = false,
    onOfflineModeClick: () -> Unit = {},
    offlineModeButtonColor: Color = HaseebFlixTheme.colors.secondaryGreen,
    @StringRes offlineModeButtonTextResourceId: Int = R.string.offline_mode
) {
    HaseebFlixCenteredBox(
        modifier = modifier
            .padding(horizontal = HaseebFlixTheme.spacing.extraMedium)
            .fillMaxSize()
    ) {
        HaseebFlixError(
            errorMessage = errorMessage,
            onRetry = onRetry,
            shape = shape,
            containerColor = containerColor,
            errorTextColor = errorTextColor,
            actionButtonColor = actionButtonColor,
            actionButtonTextResourceId = actionButtonTextResourceId,
            shouldShowOfflineMode = shouldShowOfflineMode,
            onOfflineModeClick = onOfflineModeClick,
            offlineModeButtonColor = offlineModeButtonColor,
            offlineModeButtonTextResourceId = offlineModeButtonTextResourceId
        )
    }
}
