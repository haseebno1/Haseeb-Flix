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

package com.haseebno1.haseebflix.core.database.dao.tvshow

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.haseebno1.haseebflix.core.database.model.common.MediaType
import com.haseebno1.haseebflix.core.database.model.tvshow.TvShowEntity
import com.haseebno1.haseebflix.core.database.util.Constants.Tables.TV_SHOWS
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {
    @Query("SELECT * FROM $TV_SHOWS WHERE media_type = :mediaType LIMIT :pageSize")
    fun getByMediaType(mediaType: MediaType.TvShow, pageSize: Int): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM $TV_SHOWS WHERE media_type = :mediaType")
    fun getPagingByMediaType(mediaType: MediaType.TvShow): PagingSource<Int, TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tvShows: List<TvShowEntity>)

    @Query("DELETE FROM $TV_SHOWS WHERE media_type = :mediaType")
    suspend fun deleteByMediaType(mediaType: MediaType.TvShow)
}
