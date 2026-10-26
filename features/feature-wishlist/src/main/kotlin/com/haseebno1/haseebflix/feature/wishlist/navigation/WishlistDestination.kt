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

package com.haseebno1.haseebflix.feature.wishlist.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.haseebno1.haseebflix.core.model.MediaType
import com.haseebno1.haseebflix.core.navigation.HaseebFlixNavigationDestination
import com.haseebno1.haseebflix.feature.wishlist.WishlistRoute

object WishlistDestination : HaseebFlixNavigationDestination {
    override val route = "wishlist_route"
    override val destination = "wishlist_destination"
}

fun NavGraphBuilder.wishlistGraph(
    onNavigateToDetailsDestination: (MediaType.Details) -> Unit
) = composable(route = WishlistDestination.route) {
    WishlistRoute(
        onMovieClick = { onNavigateToDetailsDestination(MediaType.Details.Movie(it)) },
        onTvShowClick = { onNavigateToDetailsDestination(MediaType.Details.TvShow(it)) }
    )
}
