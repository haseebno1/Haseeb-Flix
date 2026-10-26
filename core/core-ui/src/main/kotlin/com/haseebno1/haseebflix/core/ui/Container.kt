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
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.LazyPagingItems
import com.haseebno1.haseebflix.core.designsystem.component.HaseebFlixCenteredBox
import com.haseebno1.haseebflix.core.designsystem.component.HaseebFlixCircularProgressIndicator
import com.haseebno1.haseebflix.core.designsystem.component.HaseebFlixMessage
import com.haseebno1.haseebflix.core.designsystem.component.HaseebFlixSwipeRefresh
import com.haseebno1.haseebflix.core.designsystem.theme.HaseebFlixTheme
import com.haseebno1.haseebflix.core.model.Movie
import com.haseebno1.haseebflix.core.model.TvShow
import com.haseebno1.haseebflix.core.model.UserMessage
import com.haseebno1.haseebflix.core.ui.mapper.asUserMessage
import com.haseebno1.haseebflix.core.ui.util.error
import com.haseebno1.haseebflix.core.ui.util.isEmpty
import com.haseebno1.haseebflix.core.ui.util.isError
import com.haseebno1.haseebflix.core.ui.util.isFinished
import com.haseebno1.haseebflix.core.ui.util.isLoading
import com.haseebno1.haseebflix.core.ui.util.isNotEmpty

@Composable
fun MoviesAndTvShowsContainer(
    @StringRes titleResourceId: Int,
    onSeeAllClick: () -> Unit,
    movies: List<Movie>,
    tvShows: List<TvShow>,
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ContainerTitleWithButton(titleResourceId = titleResourceId, onSeeAllClick = onSeeAllClick)

        Text(
            modifier = Modifier.padding(horizontal = HaseebFlixTheme.spacing.extraMedium),
            text = stringResource(id = R.string.movies),
            style = HaseebFlixTheme.typography.semiBold.h5,
            color = HaseebFlixTheme.colors.whiteGrey
        )

        Spacer(modifier = Modifier.height(HaseebFlixTheme.spacing.small))

        MoviesContainer(movies = movies, onClick = onMovieClick)

        Spacer(modifier = Modifier.height(HaseebFlixTheme.spacing.smallMedium))

        Text(
            modifier = Modifier.padding(horizontal = HaseebFlixTheme.spacing.extraMedium),
            text = stringResource(id = R.string.tv_shows),
            style = HaseebFlixTheme.typography.semiBold.h5,
            color = HaseebFlixTheme.colors.whiteGrey
        )

        Spacer(modifier = Modifier.height(HaseebFlixTheme.spacing.small))

        TvShowsContainer(tvShows = tvShows, onClick = onTvShowClick)
    }
}

@Composable
fun MoviesContainer(
    @StringRes titleResourceId: Int,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier) {
        ContainerTitleWithButton(titleResourceId = titleResourceId, onSeeAllClick = onSeeAllClick)

        Spacer(modifier = Modifier.height(HaseebFlixTheme.spacing.extraSmall))

        content()
    }
}

@Composable
fun MoviesContainer(
    movies: LazyPagingItems<Movie>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = movies.loadState.refresh.isLoading,
    emptyContent: @Composable LazyItemScope.() -> Unit = {
        HaseebFlixMessage(
            modifier = Modifier.fillParentMaxSize(),
            messageResourceId = R.string.no_movie_results,
            imageResourceId = R.drawable.no_search_results
        )
    },
    loadingContent: @Composable LazyItemScope.() -> Unit = { HaseebFlixCircularProgressIndicator() },
    errorContent: @Composable LazyItemScope.(errorMessage: UserMessage) -> Unit = { errorMessage ->
        HaseebFlixError(
            modifier = Modifier.fillMaxWidth(),
            errorMessage = errorMessage,
            onRetry = movies::retry
        )
    }
) {
    HaseebFlixSwipeRefresh(
        modifier = modifier,
        isRefreshing = isLoading,
        onRefresh = movies::refresh
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(HaseebFlixTheme.spacing.medium),
            contentPadding = PaddingValues(HaseebFlixTheme.spacing.extraMedium)
        ) {
            when {
                movies.isNotEmpty() -> {
                    items(count = movies.itemCount) { index ->
                        val movie = movies[index]
                        if (movie == null) {
                            VerticalMovieItemPlaceholder()
                        } else {
                            VerticalMovieItem(movie = movie, onClick = onClick)
                        }
                    }

                    if (movies.loadState.refresh.isError) {
                        item { errorContent(movies.loadState.refresh.error.asUserMessage()) }
                    }
                }

                movies.loadState.refresh.isLoading -> {
                    items(PlaceholderCount) { VerticalMovieItemPlaceholder() }
                }

                movies.loadState.refresh.isFinished -> {
                    if (movies.isEmpty()) {
                        item(content = emptyContent)
                    }
                }

                movies.loadState.refresh.isError -> {
                    item {
                        HaseebFlixCenteredBox(modifier = Modifier.fillParentMaxSize()) {
                            errorContent(movies.loadState.refresh.error.asUserMessage())
                        }
                    }
                }
            }
            if (movies.loadState.append.isLoading) {
                item { HaseebFlixCenteredBox(modifier = Modifier.fillMaxWidth()) { loadingContent() } }
            }
            if (movies.loadState.append.isError) {
                item { errorContent(movies.loadState.append.error.asUserMessage()) }
            }
        }
    }
}

@Composable
fun TvShowsContainer(
    tvShows: LazyPagingItems<TvShow>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = tvShows.loadState.refresh.isLoading,
    emptyContent: @Composable LazyItemScope.() -> Unit = {
        HaseebFlixMessage(
            modifier = Modifier.fillParentMaxSize(),
            messageResourceId = R.string.no_tv_show_results,
            imageResourceId = R.drawable.no_search_results
        )
    },
    loadingContent: @Composable LazyItemScope.() -> Unit = { HaseebFlixCircularProgressIndicator() },
    errorContent: @Composable LazyItemScope.(errorMessage: UserMessage) -> Unit = { errorMessage ->
        HaseebFlixError(
            modifier = Modifier.fillMaxWidth(),
            errorMessage = errorMessage,
            onRetry = tvShows::retry
        )
    }
) {
    HaseebFlixSwipeRefresh(
        modifier = modifier,
        isRefreshing = isLoading,
        onRefresh = tvShows::refresh
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(HaseebFlixTheme.spacing.medium),
            contentPadding = PaddingValues(HaseebFlixTheme.spacing.extraMedium)
        ) {
            when {
                tvShows.isNotEmpty() -> {
                    items(count = tvShows.itemCount) { index ->
                        val tvShow = tvShows[index]
                        if (tvShow == null) {
                            VerticalTvShowItemPlaceholder()
                        } else {
                            VerticalTvShowItem(tvShow = tvShow, onClick = onClick)
                        }
                    }

                    if (tvShows.loadState.refresh.isError) {
                        item { errorContent(tvShows.loadState.refresh.error.asUserMessage()) }
                    }
                }

                tvShows.loadState.refresh.isLoading -> {
                    items(PlaceholderCount) { VerticalTvShowItemPlaceholder() }
                }

                tvShows.loadState.refresh.isFinished -> {
                    if (tvShows.isEmpty()) {
                        item(content = emptyContent)
                    }
                }

                tvShows.loadState.refresh.isError -> {
                    item {
                        HaseebFlixCenteredBox(modifier = Modifier.fillParentMaxSize()) {
                            errorContent(tvShows.loadState.refresh.error.asUserMessage())
                        }
                    }
                }
            }
            if (tvShows.loadState.append.isLoading) {
                item { HaseebFlixCenteredBox(modifier = Modifier.fillMaxWidth()) { loadingContent() } }
            }
            if (tvShows.loadState.append.isError) {
                item { errorContent(tvShows.loadState.append.error.asUserMessage()) }
            }
        }
    }
}

@Composable
private fun MoviesContainer(
    movies: List<Movie>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    ContainerContent(
        modifier = modifier,
        items = movies,
        itemContent = { movie -> HorizontalMovieItem(movie = movie, onClick = onClick) },
        placeholderContent = { HorizontalMovieItemPlaceholder() }
    )
}

@Composable
private fun TvShowsContainer(
    tvShows: List<TvShow>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    ContainerContent(
        modifier = modifier,
        items = tvShows,
        itemContent = { tvShow -> HorizontalTvShowItem(tvShow = tvShow, onClick = onClick) },
        placeholderContent = { HorizontalTvShowItemPlaceholder() }
    )
}

@Composable
private fun <T> ContainerContent(
    items: List<T>,
    itemContent: @Composable LazyItemScope.(T) -> Unit,
    placeholderContent: @Composable LazyItemScope.(Int) -> Unit,
    modifier: Modifier = Modifier,
    shouldShowPlaceholder: Boolean = items.isEmpty()
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(HaseebFlixTheme.spacing.smallMedium),
        contentPadding = PaddingValues(horizontal = HaseebFlixTheme.spacing.smallMedium)
    ) {
        if (shouldShowPlaceholder) {
            items(count = PlaceholderCount, itemContent = placeholderContent)
        } else {
            items(items = items, itemContent = itemContent)
        }
    }
}

@Composable
private fun ContainerTitleWithButton(
    @StringRes titleResourceId: Int,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = HaseebFlixTheme.spacing.extraMedium)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = titleResourceId),
            style = HaseebFlixTheme.typography.semiBold.h4,
            color = HaseebFlixTheme.colors.white
        )
        TextButton(onClick = onSeeAllClick) {
            Text(
                text = stringResource(id = R.string.see_all),
                style = HaseebFlixTheme.typography.medium.h5,
                color = HaseebFlixTheme.colors.primaryBlue
            )
        }
    }
}

private const val PlaceholderCount = 20
