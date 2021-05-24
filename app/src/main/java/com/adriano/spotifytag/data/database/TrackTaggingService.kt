package com.adriano.spotifytag.data.database

import com.adriano.spotifytag.data.database.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

interface TrackTaggingService {
    suspend fun deleteTagForTrack(tag: String, trackUri: String)
    suspend fun createTagForTrack(trackEntity: TrackEntity, tag: String)
    fun getAllTagsForTrack(trackUri: String): Flow<List<String>>
}
