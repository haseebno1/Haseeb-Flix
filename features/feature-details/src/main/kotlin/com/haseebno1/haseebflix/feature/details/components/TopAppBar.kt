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

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.haseebno1.haseebflix.core.designsystem.component.HaseebFlixTopAppBar
import com.haseebno1.haseebflix.core.designsystem.component.haseebflixPlaceholder
import com.haseebno1.haseebflix.core.designsystem.theme.HaseebFlixTheme
import com.haseebno1.haseebflix.core.ui.HaseebFlixBackButton
import com.haseebno1.haseebflix.core.ui.HaseebFlixWishlistButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopAppBar(
    title: String,
    isWishlisted: Boolean,
    onBackButtonClick: () -> Unit,
    onWishlistButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    titleColor: Color = HaseebFlixTheme.colors.white,
    isPlaceholder: Boolean = false
) {
    HaseebFlixTopAppBar(
        modifier = modifier,
        title = {
            Text(
                modifier = Modifier
                    .padding(horizontal = HaseebFlixTheme.spacing.small)
                    .then(
                        if (isPlaceholder) {
                            Modifier
                                .fillMaxWidth()
                                .haseebflixPlaceholder(color = titleColor)
                        } else {
                            Modifier
                        }
                    ),
                text = title,
                style = HaseebFlixTheme.typography.semiBold.h4,
                color = titleColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = { HaseebFlixBackButton(onClick = onBackButtonClick) },
        actions = {
            HaseebFlixWishlistButton(isWishlisted = isWishlisted, onClick = onWishlistButtonClick)
        },
        windowInsets = windowInsets,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = HaseebFlixTheme.colors.white,
            navigationIconContentColor = HaseebFlixTheme.colors.white
        )
    )
}

@Composable
internal fun TopAppBarPlaceholder(
    isWishlisted: Boolean,
    onBackButtonClick: () -> Unit,
    onWishlistButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = PlaceholderText,
        isWishlisted = isWishlisted,
        onBackButtonClick = onBackButtonClick,
        onWishlistButtonClick = onWishlistButtonClick,
        isPlaceholder = true
    )
}

private const val PlaceholderText = ""
