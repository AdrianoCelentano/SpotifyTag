package com.adriano.spotifytag.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.adriano.spotifytag.data.database.dao.TagsDao
import com.adriano.spotifytag.data.database.dao.TrackDao
import com.adriano.spotifytag.data.database.dao.TrackTagEntryDao
import com.adriano.spotifytag.data.database.entity.TagEntity
import com.adriano.spotifytag.data.database.entity.TrackEntity
import com.adriano.spotifytag.data.database.entity.TrackTagEntryEntity

@Database(
    entities = [
        TrackEntity::class,
        TagEntity::class,
        TrackTagEntryEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class SpotifyTagDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
    abstract fun tagDao(): TagsDao
    abstract fun trackTagEntryDao(): TrackTagEntryDao
}