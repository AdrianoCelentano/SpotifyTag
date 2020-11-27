package com.adriano.spotifytag.data.dao

import androidx.room.*
import com.adriano.spotifytag.data.entity.TrackTagEntryEntity

@Dao
abstract class TrackTagEntryDao {

    @Query("SELECT * FROM track_tag_entries WHERE track_uri = :trackUri AND tag_id = :tagId")
    abstract suspend fun getTrackTagEntry(trackUri: String, tagId: Long): TrackTagEntryEntity?

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