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

package com.haseebno1.haseebflix.di

import com.haseebno1.haseebflix.BuildConfig
import com.haseebno1.haseebflix.core.network.api.HaseebFlixApi
import com.haseebno1.haseebflix.core.network.api.HaseebFlixApiKeyProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideHaseebFlixApi(apiKeyProvider: HaseebFlixApiKeyProvider) = HaseebFlixApi(apiKeyProvider)

    @Provides
    fun provideHaseebFlixApiKeyProvider() = object : HaseebFlixApiKeyProvider {
        override val apiKey: String = BuildConfig.HASEEBFLIX_API_KEY
    }

    @Provides
    fun provideMovieService(api: HaseebFlixApi) = api.movieService

    @Provides
    fun provideTvShowService(api: HaseebFlixApi) = api.tvShowService
}
