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

package com.maximillianleonov.cinemax.core.designsystem.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val Dark = Color(0xFF131313)
private val Soft = Color(0xFF1C1B1B)
private val PrimaryRed = Color(0xFFE50914)
private val MatchGreen = Color(0xFF46D369)
private val RatingGold = Color(0xFFFFB800)
private val Black = Color(0xFF000000)
private val Grey = Color(0xFFB3B3B3)
private val DarkGrey = Color(0xFF353534)
private val White = Color(0xFFFFFFFF)
private val WhiteGrey = Color(0xFFE5E2E1)
private val LineDark = Color(0xFF333333)
private val SurfaceContainer = Color(0xFF201F1F)
private val SurfaceContainerHigh = Color(0xFF2A2A2A)

internal val DarkColorScheme = darkColorScheme(
    primary = PrimaryRed,
    onPrimary = White,
    primaryContainer = PrimaryRed,
    onPrimaryContainer = White,
    secondary = Soft,
    onSecondary = White,
    secondaryContainer = SurfaceContainerHigh,
    onSecondaryContainer = Grey,
    background = Dark,
    onBackground = White,
    surface = Dark,
    onSurface = White,
    surfaceVariant = DarkGrey,
    onSurfaceVariant = Grey,
    outline = LineDark
)

@Immutable
data class CinemaxColors(
    val default: Color = Color.Unspecified,
    val primaryDark: Color = Dark,
    val primarySoft: Color = Soft,
    val primaryBlue: Color = PrimaryRed,
    val primaryRed: Color = PrimaryRed,
    val secondaryGreen: Color = MatchGreen,
    val secondaryOrange: Color = RatingGold,
    val secondaryRed: Color = PrimaryRed,
    val ratingGold: Color = RatingGold,
    val white: Color = White,
    val whiteGrey: Color = WhiteGrey,
    val black: Color = Black,
    val grey: Color = Grey,
    val darkGrey: Color = DarkGrey,
    val lineDark: Color = LineDark,
    val surfaceContainerLow: Color = Soft,
    val surfaceContainer: Color = SurfaceContainer,
    val surfaceContainerHigh: Color = SurfaceContainerHigh,
    val surfaceContainerHighest: Color = DarkGrey
)

internal val LocalCinemaxColors = staticCompositionLocalOf { CinemaxColors() }
