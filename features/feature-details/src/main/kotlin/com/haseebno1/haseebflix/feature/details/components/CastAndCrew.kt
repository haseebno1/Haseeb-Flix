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

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.haseebno1.haseebflix.core.designsystem.component.HaseebFlixCardNetworkImage
import com.haseebno1.haseebflix.core.designsystem.component.HaseebFlixImagePlaceholder
import com.haseebno1.haseebflix.core.designsystem.component.haseebflixPlaceholder
import com.haseebno1.haseebflix.core.designsystem.theme.HaseebFlixTheme
import com.haseebno1.haseebflix.core.model.Credits
import com.haseebno1.haseebflix.core.ui.R

@Composable
internal fun CastAndCrew(
    credits: Credits,
    modifier: Modifier = Modifier,
    isPlaceholder: Boolean = false
) {
    val cast = credits.cast
    val crew = credits.crew

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(HaseebFlixTheme.spacing.extraMedium)
    ) {
        if (isPlaceholder) {
            val placeholderContent: LazyListScope.() -> Unit = {
                items(PlaceholderCount) {
                    CastAndCrewItemPlaceholder()
                }
            }
            CastAndCrewContainer(titleResourceId = R.string.cast, content = placeholderContent)
            CastAndCrewContainer(titleResourceId = R.string.crew, content = placeholderContent)
        } else {
            if (cast.isNotEmpty()) {
                CastAndCrewContainer(titleResourceId = R.string.cast) {
                    items(cast) { castItem ->
                        with(castItem) {
                            CastAndCrewItem(
                                profilePath = profilePath,
                                name = name,
                                description = character
                            )
                        }
                    }
                }
            }
            if (crew.isNotEmpty()) {
                CastAndCrewContainer(titleResourceId = R.string.crew) {
                    items(crew) { crewItem ->
                        with(crewItem) {
                            CastAndCrewItem(
                                profilePath = profilePath,
                                name = name,
                                description = job
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun CastAndCrewPlaceholder(modifier: Modifier = Modifier) {
    CastAndCrew(
        modifier = modifier,
        credits = Credits(cast = emptyList(), crew = emptyList()),
        isPlaceholder = true
    )
}

@Suppress("ModifierParameterPosition")
@Composable
private fun CastAndCrewContainer(
    @StringRes titleResourceId: Int,
    modifier: Modifier = Modifier,
    content: LazyListScope.() -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(HaseebFlixTheme.spacing.medium)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = HaseebFlixTheme.spacing.extraMedium),
            text = stringResource(id = titleResourceId),
            style = HaseebFlixTheme.typography.semiBold.h4,
            color = HaseebFlixTheme.colors.white
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(HaseebFlixTheme.spacing.smallMedium),
            contentPadding = PaddingValues(horizontal = HaseebFlixTheme.spacing.extraMedium),
            content = content
        )
    }
}

@Composable
private fun CastAndCrewItem(
    profilePath: String?,
    name: String,
    description: String,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    isPlaceholder: Boolean = false
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(HaseebFlixTheme.spacing.small)
    ) {
        if (isPlaceholder) {
            HaseebFlixImagePlaceholder(
                modifier = Modifier
                    .size(CastAndCrewItemImageSize)
                    .clip(shape)
            )
        } else {
            HaseebFlixCardNetworkImage(
                modifier = Modifier.size(CastAndCrewItemImageSize),
                model = profilePath,
                contentDescription = name,
                shape = shape
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(HaseebFlixTheme.spacing.extraSmall)) {
            Text(
                modifier = if (isPlaceholder) {
                    Modifier
                        .width(PlaceholderTextWidth)
                        .haseebflixPlaceholder(color = HaseebFlixTheme.colors.grey)
                } else {
                    Modifier
                },
                text = name,
                style = HaseebFlixTheme.typography.semiBold.h5,
                color = HaseebFlixTheme.colors.white
            )
            Text(
                modifier = if (isPlaceholder) {
                    Modifier
                        .width(PlaceholderTextWidth)
                        .haseebflixPlaceholder(color = HaseebFlixTheme.colors.grey)
                } else {
                    Modifier
                },
                text = description,
                style = HaseebFlixTheme.typography.medium.h7,
                color = HaseebFlixTheme.colors.grey
            )
        }
    }
}

@Composable
private fun CastAndCrewItemPlaceholder(modifier: Modifier = Modifier) {
    CastAndCrewItem(
        modifier = modifier,
        profilePath = null,
        name = PlaceholderText,
        description = PlaceholderText,
        isPlaceholder = true
    )
}

private val CastAndCrewItemImageSize = 40.dp
private val PlaceholderTextWidth = 50.dp
private const val PlaceholderText = ""
private const val PlaceholderCount = 20
