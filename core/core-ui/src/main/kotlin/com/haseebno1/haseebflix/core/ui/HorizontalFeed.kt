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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.haseebno1.haseebflix.core.designsystem.component.HaseebFlixImagePlaceholder
import com.haseebno1.haseebflix.core.designsystem.component.HaseebFlixNetworkImage
import com.haseebno1.haseebflix.core.designsystem.component.haseebflixPlaceholder
import com.haseebno1.haseebflix.core.designsystem.theme.HaseebFlixTheme

@Composable
internal fun HorizontalFeedItem(
    title: String,
    posterPath: String?,
    genres: List<String>,
    voteAverage: Double,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = HaseebFlixTheme.colors.primarySoft,
    shape: Shape = HaseebFlixTheme.shapes.smallMedium,
    isPlaceholder: Boolean = false
) {
    Card(
        modifier = modifier
            .width(HorizontalFeedItemWidth)
            .clip(shape)
            .then(if (isPlaceholder) Modifier else Modifier.clickable(onClick = onClick)),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = shape
    ) {
        Column {
            Box(modifier = Modifier.height(HorizontalFeedItemPosterHeight)) {
                if (isPlaceholder) {
                    HaseebFlixImagePlaceholder()
                } else {
                    HaseebFlixNetworkImage(
                        modifier = Modifier.fillMaxSize(),
                        model = posterPath,
                        contentDescription = title
                    )
                }
                RatingItem(
                    rating = voteAverage,
                    modifier = Modifier
                        .padding(
                            top = HaseebFlixTheme.spacing.small,
                            end = HaseebFlixTheme.spacing.small
                        )
                        .align(Alignment.TopEnd)
                        .then(
                            if (isPlaceholder) {
                                Modifier.haseebflixPlaceholder(color = HaseebFlixTheme.colors.secondaryOrange)
                            } else {
                                Modifier
                            }
                        )
                )
            }
            Spacer(modifier = Modifier.height(HaseebFlixTheme.spacing.smallMedium))
            Text(
                modifier = Modifier
                    .padding(horizontal = HaseebFlixTheme.spacing.small)
                    .then(
                        if (isPlaceholder) {
                            Modifier
                                .fillMaxWidth()
                                .haseebflixPlaceholder(
                                    color = HaseebFlixTheme.colors.white
                                )
                        } else {
                            Modifier
                        }
                    ),
                text = title,
                style = HaseebFlixTheme.typography.semiBold.h5,
                color = HaseebFlixTheme.colors.white,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(HaseebFlixTheme.spacing.extraSmall))
            Text(
                modifier = Modifier
                    .padding(
                        start = HaseebFlixTheme.spacing.small,
                        end = HaseebFlixTheme.spacing.small,
                        bottom = HaseebFlixTheme.spacing.small
                    )
                    .then(
                        if (isPlaceholder) {
                            Modifier
                                .fillMaxWidth(
                                    HorizontalFeedItemPlaceholderSecondTextMaxWidthFraction
                                )
                                .haseebflixPlaceholder(color = HaseebFlixTheme.colors.grey)
                        } else {
                            Modifier
                        }
                    ),
                text = genres.joinToString(separator = FeedItemGenreSeparator)
                    .ifEmpty { stringResource(id = R.string.no_genre) },
                style = HaseebFlixTheme.typography.medium.h7,
                color = HaseebFlixTheme.colors.grey,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
internal fun HorizontalFeedItemPlaceholder(modifier: Modifier = Modifier) {
    HorizontalFeedItem(
        modifier = modifier,
        title = FeedItemPlaceholderText,
        posterPath = FeedItemPlaceholderText,
        genres = emptyList(),
        voteAverage = FeedItemPlaceholderRating,
        onClick = {},
        isPlaceholder = true
    )
}

private val HorizontalFeedItemWidth = 135.dp
private val HorizontalFeedItemPosterHeight = 178.dp
private const val HorizontalFeedItemPlaceholderSecondTextMaxWidthFraction = 0.5f
