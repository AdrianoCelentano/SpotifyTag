package com.adriano.spotifytag.data.database.entity

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tracks",
    indices = [
        Index("uri", unique = true)
    ]
)
@Immutable
data class TrackEntity(
    @PrimaryKey @ColumnInfo(name = "uri") val uri: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "artist") val artist: String? = null,
    @ColumnInfo(name = "album") val album: String? = null,
    @ColumnInfo(name = "image_url") val spotifyImageUrl: String? = null,
) {
}