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

package com.haseebno1.haseebflix.core.designsystem.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.haseebno1.haseebflix.core.designsystem.theme.HaseebFlixTheme

@Composable
fun HaseebFlixOutlinedButton(
    @StringRes textResourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = HaseebFlixTheme.shapes.medium,
    containerColor: Color = HaseebFlixTheme.colors.primarySoft,
    contentColor: Color = HaseebFlixTheme.colors.primaryBlue,
    borderColor: Color = contentColor,
    textStyle: TextStyle = HaseebFlixTheme.typography.medium.h5
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(brush = SolidColor(borderColor))
    ) {
        Text(text = stringResource(id = textResourceId), style = textStyle)
    }
}

@Composable
fun HaseebFlixIconButton(
    @DrawableRes iconResourceId: Int,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current
) {
    HaseebFlixIconButtonContent(
        modifier = modifier,
        onClick = onClick
    ) {
        HaseebFlixIcon(
            iconResourceId = iconResourceId,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

@Composable
fun HaseebFlixIconButton(
    imageVector: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current
) {
    HaseebFlixIconButtonContent(
        modifier = modifier,
        onClick = onClick
    ) {
        HaseebFlixIcon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

@Suppress("ReusedModifierInstance")
@Composable
private fun HaseebFlixIconButtonContent(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalMinimumInteractiveComponentSize provides 0.dp
    ) {
        IconButton(modifier = modifier, onClick = onClick, content = content)
    }
}
