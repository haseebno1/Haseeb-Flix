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

package com.haseebno1.haseebflix.core.designsystem.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun HaseebFlixTheme(
    colorScheme: ColorScheme = DarkColorScheme,
    shapes: Shapes = Shapes,
    typography: Typography = Typography,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = colorScheme,
        shapes = shapes,
        typography = typography
    ) { ProvideHaseebFlixThemeDependencies(content = content) }
}

@Composable
private fun ProvideHaseebFlixThemeDependencies(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalHaseebFlixColors provides HaseebFlixColors(),
        LocalHaseebFlixShapes provides HaseebFlixShapes(),
        LocalHaseebFlixTypography provides HaseebFlixTypography(),
        LocalHaseebFlixSpacing provides HaseebFlixSpacing(),
        LocalIndication provides haseebflixRipple()
    ) {
        ProvideTextStyle(value = HaseebFlixTheme.typography.regular.h4, content = content)
    }
}

object HaseebFlixTheme {
    val colors: HaseebFlixColors
        @Composable
        @ReadOnlyComposable
        get() = LocalHaseebFlixColors.current

    val shapes: HaseebFlixShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalHaseebFlixShapes.current

    val typography: HaseebFlixTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalHaseebFlixTypography.current

    val spacing: HaseebFlixSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalHaseebFlixSpacing.current
}
