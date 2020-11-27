package com.adriano.spotifytag.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.adriano.spotifytag.data.dao.TagsDao
import com.adriano.spotifytag.data.dao.TrackDao
import com.adriano.spotifytag.data.dao.TrackTagEntryDao
import com.adriano.spotifytag.data.entity.TagEntity
import com.adriano.spotifytag.data.entity.TrackEntity
import com.adriano.spotifytag.data.entity.TrackTagEntryEntity

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