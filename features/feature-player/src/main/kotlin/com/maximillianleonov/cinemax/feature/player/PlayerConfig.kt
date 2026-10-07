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

package com.maximillianleonov.cinemax.feature.player

/**
 * Runtime configuration of the embedded player.
 *
 * @param baseUrl Base URL of the embed provider, e.g. `https://embed.vidrift.net`.
 * Must be an `https` URL. Kept configurable so a provider domain change doesn't need code changes.
 * @param hostPageOrigin Origin used for the small local page that frames the player.
 */
data class PlayerConfig(
    val baseUrl: String,
    val hostPageOrigin: String = DefaultHostPageOrigin
)

const val DefaultHostPageOrigin = "https://cinemax.local"
