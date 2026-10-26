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

package com.haseebno1.haseebflix.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.haseebno1.haseebflix.core.navigation.HaseebFlixNavigationDestination
import com.haseebno1.haseebflix.feature.details.navigation.DetailsDestination
import com.haseebno1.haseebflix.feature.details.navigation.detailsGraph
import com.haseebno1.haseebflix.feature.home.navigation.homeGraph
import com.haseebno1.haseebflix.feature.list.navigation.ListDestination
import com.haseebno1.haseebflix.feature.list.navigation.listGraph
import com.haseebno1.haseebflix.feature.player.navigation.PlayerDestination
import com.haseebno1.haseebflix.feature.player.navigation.playerGraph
import com.haseebno1.haseebflix.feature.search.navigation.searchGraph
import com.haseebno1.haseebflix.feature.settings.navigation.settingsGraph
import com.haseebno1.haseebflix.feature.wishlist.navigation.wishlistGraph

@Composable
fun HaseebFlixNavHost(
    navController: NavHostController,
    startDestination: HaseebFlixNavigationDestination,
    onNavigateToDestination: (HaseebFlixNavigationDestination, String) -> Unit,
    onBackClick: () -> Unit,
    onShowMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination.route
    ) {
        homeGraph(
            onNavigateToListDestination = {
                onNavigateToDestination(ListDestination, ListDestination.createNavigationRoute(it))
            },
            onNavigateToDetailsDestination = {
                onNavigateToDestination(
                    DetailsDestination,
                    DetailsDestination.createNavigationRoute(it)
                )
            }
        )
        searchGraph(
            onNavigateToListDestination = {
                onNavigateToDestination(ListDestination, ListDestination.createNavigationRoute(it))
            },
            onNavigateToDetailsDestination = {
                onNavigateToDestination(
                    DetailsDestination,
                    DetailsDestination.createNavigationRoute(it)
                )
            }
        )
        wishlistGraph(
            onNavigateToDetailsDestination = {
                onNavigateToDestination(
                    DetailsDestination,
                    DetailsDestination.createNavigationRoute(it)
                )
            }
        )
        settingsGraph()
        listGraph(
            onBackButtonClick = onBackClick,
            onNavigateToDetailsDestination = {
                onNavigateToDestination(
                    DetailsDestination,
                    DetailsDestination.createNavigationRoute(it)
                )
            }
        )
        detailsGraph(
            onBackButtonClick = onBackClick,
            onShowMessage = onShowMessage,
            onPlayMovie = { id, title ->
                onNavigateToDestination(
                    PlayerDestination,
                    PlayerDestination.createMovieRoute(id, title)
                )
            },
            onPlayTvShow = { id, title, season, episode ->
                onNavigateToDestination(
                    PlayerDestination,
                    PlayerDestination.createTvShowRoute(id, title, season, episode)
                )
            }
        )
        playerGraph(onBackClick = onBackClick)
    }
}
