package com.adriano.spotifytag.data.database.dao

import androidx.room.*
import com.adriano.spotifytag.data.database.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TrackDao {

    @Query("SELECT track_tag_entries.track_uri FROM tags JOIN track_tag_entries ON tags.id = track_tag_entries.tag_id AND tags.name in (:tags)")
    abstract fun trackWithTags(tags: List<String>): List<String>

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