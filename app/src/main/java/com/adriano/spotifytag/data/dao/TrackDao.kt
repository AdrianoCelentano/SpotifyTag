package com.adriano.spotifytag.data.dao

import androidx.room.*
import com.adriano.spotifytag.data.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TrackDao {

    @Query("SELECT * FROM tracks WHERE uri = :uri")
    abstract fun trackWithUriFlow(uri: String): Flow<TrackEntity>

    @Query("SELECT * FROM tracks WHERE uri = :uri")
    abstract suspend fun trackWithUri(uri: String): TrackEntity?

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