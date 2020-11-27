package com.adriano.spotifytag.data.database.dao

import androidx.room.*
import com.adriano.spotifytag.data.database.entity.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TagsDao {

    @Query("SELECT tags.* FROM tags JOIN track_tag_entries ON tags.id = track_tag_entries.tag_id AND track_tag_entries.track_uri = :trackUri")
    abstract suspend fun getTagsForTrack(trackUri: String): List<TagEntity>

    @Query("SELECT tags.* FROM tags JOIN track_tag_entries ON tags.id = track_tag_entries.tag_id AND track_tag_entries.track_uri = :trackUri")
    abstract fun getTagsForTrackFlow(trackUri: String): Flow<List<TagEntity>>

    @Query("SELECT * FROM tags WHERE name = :name")
    abstract suspend fun getTagWithName(name: String): TagEntity?

    @Query("SELECT * FROM tags WHERE id = :id")
    abstract suspend fun getTagWithId(id: Long): TagEntity?

    @Query("SELECT * FROM tags")
    abstract fun getAllTags(): Flow<List<TagEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: TagEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(vararg entity: TagEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(entities: Collection<TagEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: TagEntity)

    @Delete
    abstract suspend fun delete(entity: TagEntity): Int
}