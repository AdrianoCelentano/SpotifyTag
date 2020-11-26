package com.adriano.spotifytag.data

import androidx.room.*

@Dao
abstract class TrackTagEntryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: TrackTagEntryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(vararg entity: TrackTagEntryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(entities: Collection<TrackTagEntryEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: TrackTagEntryEntity)

    @Delete
    abstract suspend fun delete(entity: TrackTagEntryEntity): Int
}