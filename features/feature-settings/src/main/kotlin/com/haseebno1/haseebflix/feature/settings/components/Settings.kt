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

package com.haseebno1.haseebflix.feature.settings.components

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.haseebno1.haseebflix.core.designsystem.component.HaseebFlixCenteredBox
import com.haseebno1.haseebflix.core.designsystem.component.HaseebFlixIcon
import com.haseebno1.haseebflix.core.designsystem.theme.HaseebFlixTheme
import com.haseebno1.haseebflix.core.ui.R
import com.haseebno1.haseebflix.feature.settings.model.Settings

@Composable
internal fun SettingsItem(
    settings: Settings,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
    Row(
        modifier = modifier
            .then(
                when (settings) {
                    is Settings.Action -> Modifier.clickable(onClick = settings.onClick)
                    is Settings.IntentAction -> Modifier.clickable { context.startActivity(settings.intent) }
                    is Settings.Info -> Modifier
                }
            )
            .padding(HaseebFlixTheme.spacing.medium)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(HaseebFlixTheme.spacing.medium)
        ) {
            IconBox(
                iconResourceId = settings.iconResourceId,
                contentDescription = stringResource(id = settings.titleResourceId)
            )
            TitleText(titleResourceId = settings.titleResourceId)
        }
        when (settings) {
            is Settings.Info -> ValueText(value = settings.value)
            is Settings.Action, is Settings.IntentAction -> ForwardButton()
        }
    }
}

@Composable
private fun IconBox(
    @DrawableRes iconResourceId: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    HaseebFlixCenteredBox(
        modifier = modifier
            .size(IconShapeSize)
            .background(color = HaseebFlixTheme.colors.primarySoft, shape = CircleShape)
    ) {
        HaseebFlixIcon(
            iconResourceId = iconResourceId,
            contentDescription = contentDescription,
            tint = HaseebFlixTheme.colors.grey
        )
    }
}

@Composable
private fun TitleText(
    @StringRes titleResourceId: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = HaseebFlixTheme.typography.medium.h5,
    color: Color = HaseebFlixTheme.colors.white
) {
    ValueText(
        modifier = modifier,
        value = stringResource(id = titleResourceId),
        style = style,
        color = color
    )
}

@Composable
private fun ValueText(
    value: String,
    modifier: Modifier = Modifier,
    style: TextStyle = HaseebFlixTheme.typography.medium.h5,
    color: Color = HaseebFlixTheme.colors.white
) {
    Text(
        modifier = modifier,
        text = value,
        style = style,
        color = color
    )
}

@Composable
private fun ForwardButton(
    modifier: Modifier = Modifier,
    color: Color = HaseebFlixTheme.colors.primaryBlue
) {
    HaseebFlixIcon(
        modifier = modifier.size(IconShapeSize),
        iconResourceId = R.drawable.ic_arrow_forward,
        contentDescription = stringResource(id = R.string.forward),
        tint = color
    )
}

private val IconShapeSize = 32.dp
