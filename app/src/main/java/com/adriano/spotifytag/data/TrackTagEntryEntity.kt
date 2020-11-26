package com.adriano.spotifytag.data

import androidx.compose.runtime.Immutable
import androidx.room.*

@Entity(
    tableName = "track_tag_entries",
    foreignKeys = [
        ForeignKey(
            entity = TagEntity::class,
            parentColumns = ["id"],
            childColumns = ["tag_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TrackEntity::class,
            parentColumns = ["uri"],
            childColumns = ["track_uri"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("track_uri", "tag_id", unique = true),
        Index("tag_id"),
        Index("track_uri")
    ]
)
@Immutable
data class TrackTagEntryEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "track_uri") val trackUri: String,
    @ColumnInfo(name = "tag_id") val tagId: Long
)