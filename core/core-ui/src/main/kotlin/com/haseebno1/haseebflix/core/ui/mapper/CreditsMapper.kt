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

package com.haseebno1.haseebflix.core.ui.mapper

import com.haseebno1.haseebflix.core.domain.model.CastModel
import com.haseebno1.haseebflix.core.domain.model.CreditsModel
import com.haseebno1.haseebflix.core.domain.model.CrewModel
import com.haseebno1.haseebflix.core.model.Cast
import com.haseebno1.haseebflix.core.model.Credits
import com.haseebno1.haseebflix.core.model.Crew

internal fun CreditsModel.asCredits() = Credits(
    cast = cast.map(CastModel::asCast),
    crew = crew.map(CrewModel::asCrew)
)

private fun CastModel.asCast() = Cast(
    id = id,
    name = name,
    character = character,
    profilePath = profilePath
)

private fun CrewModel.asCrew() = Crew(
    id = id,
    name = name,
    job = job,
    profilePath = profilePath
)
