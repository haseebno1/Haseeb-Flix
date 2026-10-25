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

package com.haseebno1.haseebflix.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.haseebno1.haseebflix.core.designsystem.component.HaseebFlixSwipeRefresh
import com.haseebno1.haseebflix.core.designsystem.theme.HaseebFlixTheme
import com.haseebno1.haseebflix.core.model.MediaType
import com.haseebno1.haseebflix.core.model.Movie
import com.haseebno1.haseebflix.core.model.TvShow
import com.haseebno1.haseebflix.core.ui.HaseebFlixCenteredError
import com.haseebno1.haseebflix.core.ui.MoviesAndTvShowsContainer
import com.haseebno1.haseebflix.core.ui.R
import com.haseebno1.haseebflix.core.ui.mapper.asUserMessage
import com.haseebno1.haseebflix.feature.home.component.UpcomingMoviesContainer

@Composable
internal fun HomeRoute(
    onSeeAllClick: (MediaType.Common) -> Unit,
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        uiState = uiState,
        onSeeAllClick = onSeeAllClick,
        onMovieClick = onMovieClick,
        onTvShowClick = onTvShowClick,
        onRefresh = { viewModel.onEvent(HomeEvent.Refresh) },
        onRetry = { viewModel.onEvent(HomeEvent.Retry) },
        onOfflineModeClick = { viewModel.onEvent(HomeEvent.ClearError) },
        modifier = modifier
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    onSeeAllClick: (MediaType.Common) -> Unit,
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
    onOfflineModeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    HaseebFlixSwipeRefresh(
        modifier = modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        isRefreshing = uiState.isLoading,
        onRefresh = onRefresh
    ) {
        if (uiState.error != null) {
            HaseebFlixCenteredError(
                errorMessage = uiState.error.asUserMessage(),
                onRetry = onRetry,
                shouldShowOfflineMode = uiState.isOfflineModeAvailable,
                onOfflineModeClick = onOfflineModeClick
            )
        } else {
            HomeContent(
                movies = uiState.movies,
                tvShows = uiState.tvShows,
                onSeeAllClick = onSeeAllClick,
                onMovieClick = onMovieClick,
                onTvShowClick = onTvShowClick
            )
        }
    }
}

@Composable
private fun HomeContent(
    movies: Map<MediaType.Movie, List<Movie>>,
    tvShows: Map<MediaType.TvShow, List<TvShow>>,
    onSeeAllClick: (MediaType.Common) -> Unit,
    onMovieClick: (Int) -> Unit,
    onTvShowClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(HaseebFlixTheme.spacing.extraMedium),
        contentPadding = PaddingValues(vertical = HaseebFlixTheme.spacing.extraMedium)
    ) {
        item {
            UpcomingMoviesContainer(
                movies = movies[MediaType.Movie.Upcoming].orEmpty(),
                onSeeAllClick = { onSeeAllClick(MediaType.Common.Upcoming) },
                onMovieClick = onMovieClick
            )
        }
        item {
            MoviesAndTvShowsContainer(
                titleResourceId = R.string.top_rated,
                onSeeAllClick = { onSeeAllClick(MediaType.Common.TopRated) },
                movies = movies[MediaType.Movie.TopRated].orEmpty(),
                tvShows = tvShows[MediaType.TvShow.TopRated].orEmpty(),
                onMovieClick = onMovieClick,
                onTvShowClick = onTvShowClick
            )
        }
        item {
            MoviesAndTvShowsContainer(
                titleResourceId = R.string.most_popular,
                onSeeAllClick = { onSeeAllClick(MediaType.Common.Popular) },
                movies = movies[MediaType.Movie.Popular].orEmpty(),
                tvShows = tvShows[MediaType.TvShow.Popular].orEmpty(),
                onMovieClick = onMovieClick,
                onTvShowClick = onTvShowClick
            )
        }
        item {
            MoviesAndTvShowsContainer(
                titleResourceId = R.string.now_playing,
                onSeeAllClick = { onSeeAllClick(MediaType.Common.NowPlaying) },
                movies = movies[MediaType.Movie.NowPlaying].orEmpty(),
                tvShows = tvShows[MediaType.TvShow.NowPlaying].orEmpty(),
                onMovieClick = onMovieClick,
                onTvShowClick = onTvShowClick
            )
        }
    }
}
