package com.adriano.spotifytag.data

import androidx.room.Database
import androidx.room.RoomDatabase

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