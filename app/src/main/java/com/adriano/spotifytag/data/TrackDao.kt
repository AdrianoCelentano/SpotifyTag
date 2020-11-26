package com.adriano.spotifytag.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TrackDao {
    @Query("SELECT * FROM tracks WHERE uri = :uri")
    abstract fun podcastWithUri(uri: String): Flow<TrackEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: TrackEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(vararg entity: TrackEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(entities: Collection<TrackEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: TrackEntity)

    @Delete
    abstract suspend fun delete(entity: TrackEntity): Int
}