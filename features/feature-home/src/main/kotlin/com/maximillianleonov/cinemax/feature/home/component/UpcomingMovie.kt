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

package com.maximillianleonov.cinemax.feature.home.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxImagePlaceholder
import com.maximillianleonov.cinemax.core.designsystem.component.CinemaxNetworkImage
import com.maximillianleonov.cinemax.core.designsystem.theme.CinemaxTheme
import com.maximillianleonov.cinemax.core.model.Movie
import com.maximillianleonov.cinemax.core.ui.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun UpcomingMoviesContainer(
    movies: List<Movie>,
    onSeeAllClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val shouldShowPlaceholder = movies.isEmpty()
    val pageCount = if (shouldShowPlaceholder) PlaceholderCount else movies.size
    val pagerState = rememberPagerState(pageCount = { pageCount })

    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = CinemaxTheme.spacing.medium)
        ) { page ->
            if (shouldShowPlaceholder) {
                UpcomingMovieItemPlaceholder(
                    modifier = Modifier.pagerTransition(pagerState = pagerState, page = page)
                )
            } else {
                with(movies[page]) {
                    UpcomingMovieItem(
                        modifier = Modifier.pagerTransition(
                            pagerState = pagerState,
                            page = page
                        ),
                        title = title,
                        backdropPath = backdropPath ?: posterPath,
                        onClick = { onMovieClick(id) }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(CinemaxTheme.spacing.smallMedium))
        DefaultHorizontalPagerIndicator(
            modifier = Modifier
                .padding(horizontal = CinemaxTheme.spacing.extraMedium)
                .align(Alignment.CenterHorizontally),
            pagerState = pagerState,
            pageCount = pageCount
        )
    }
}

@Composable
private fun UpcomingMovieItem(
    title: String,
    backdropPath: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    isPlaceholder: Boolean = false
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp)
            .clip(shape)
            .then(if (isPlaceholder) Modifier else Modifier.clickable(onClick = onClick)),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = CinemaxTheme.colors.surfaceContainerLow)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (isPlaceholder) {
                CinemaxImagePlaceholder()
            } else {
                CinemaxNetworkImage(
                    modifier = Modifier.fillMaxSize(),
                    model = backdropPath,
                    contentDescription = title,
                    contentScale = ContentScale.Crop
                )
            }

            // Scrim Gradients for legibility
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                CinemaxTheme.colors.black.copy(alpha = 0.5f),
                                CinemaxTheme.colors.black.copy(alpha = 0.95f)
                            )
                        )
                    )
            )

            // Content Overlays
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top 10 Ribbon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .background(
                            color = CinemaxTheme.colors.surfaceContainerHighest.copy(alpha = 0.85f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .background(
                                color = CinemaxTheme.colors.primaryRed,
                                shape = RoundedCornerShape(2.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "H",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "#1 in Movies Today",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = title,
                    style = CinemaxTheme.typography.semiBold.h2,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Action Stack Buttons: Play & My List
                Row(
                    modifier = Modifier.fillMaxWidth(0.85f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onClick,
                        modifier = Modifier.weight(1f).height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = stringResource(id = R.string.play),
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(id = R.string.play),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    Button(
                        onClick = onClick,
                        modifier = Modifier.weight(1f).height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CinemaxTheme.colors.surfaceContainerHigh.copy(alpha = 0.85f),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.wishlist),
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(id = R.string.wishlist),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun UpcomingMovieItemPlaceholder(modifier: Modifier = Modifier) {
    UpcomingMovieItem(
        modifier = modifier,
        title = "",
        backdropPath = null,
        onClick = {},
        isPlaceholder = true
    )
}

private const val PlaceholderCount = 10
