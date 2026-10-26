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

import android.content.Context
import androidx.room.Room
import com.haseebno1.haseebflix.BuildConfig
import com.haseebno1.haseebflix.core.database.HaseebFlixDatabase
import com.haseebno1.haseebflix.core.database.util.HaseebFlixVersionProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideHaseebFlixDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        HaseebFlixDatabase::class.java,
        HaseebFlixDatabase.HASEEBFLIX_DATABASE
    ).build()

    @Provides
    fun provideHaseebFlixVersionProvider() = object : HaseebFlixVersionProvider {
        override val version: String = BuildConfig.VERSION_NAME
    }

    @Provides
    fun provideMovieDao(database: HaseebFlixDatabase) = database.movieDao

    @Provides
    fun provideTvShowDao(database: HaseebFlixDatabase) = database.tvShowDao

    @Provides
    fun provideMovieRemoteKeyDao(database: HaseebFlixDatabase) = database.movieRemoteKeyDao

    @Provides
    fun provideTvShowRemoteKeyDao(database: HaseebFlixDatabase) = database.tvShowRemoteKeyDao

    @Provides
    fun provideMovieDetailsDao(database: HaseebFlixDatabase) = database.movieDetailsDao

    @Provides
    fun provideTvShowDetailsDao(database: HaseebFlixDatabase) = database.tvShowDetailsDao

    @Provides
    fun provideWishlistDao(database: HaseebFlixDatabase) = database.wishlistDao
}
