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

package com.haseebno1.haseebflix.core.data.mapper

import com.haseebno1.haseebflix.core.database.model.common.Genre
import com.haseebno1.haseebflix.core.domain.model.GenreModel
import com.haseebno1.haseebflix.core.network.model.common.NetworkGenre
import com.haseebno1.haseebflix.core.network.model.common.NetworkGenreWithId

internal fun NetworkGenre.asGenre() = id.asGenre()
internal fun List<NetworkGenre>.asGenres() = map(NetworkGenre::asGenre)

@JvmName("intListAsGenres")
internal fun List<Int>.asGenres() = map(Int::asGenre)

@JvmName("intListAsGenreModels")
internal fun List<Int>.asGenreModels() = map(Int::asGenreModel)

internal fun Genre.asGenreModel() = GenreModel[genreName]
internal fun List<Genre>.asGenreModels() = map(Genre::asGenreModel)

private fun Int.asGenre() = Genre[NetworkGenreWithId[this].genreName]
private fun Int.asGenreModel() = GenreModel[NetworkGenreWithId[this].genreName]
