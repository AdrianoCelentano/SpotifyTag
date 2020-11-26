package com.adriano.spotifytag.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TagsDao {

    @Query("SELECT * FROM tags WHERE name = :name")
    abstract suspend fun getTagWithName(name: String): TagEntity?

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