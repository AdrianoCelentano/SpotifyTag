package com.adriano.spotifytag.presentation.edittrack

import com.adriano.spotifytag.data.database.TrackTaggingService
import com.adriano.spotifytag.data.database.entity.TrackEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeTrackTaggingService(
    tags: List<Pair<TrackEntity, List<String>>> = TestDataFactory.createTrackTagData()
) : TrackTaggingService {

    private val data = mutableListOf<Pair<TrackEntity, List<String>>>()
    private var selectedTrack = ""
    private val tagsStateFlow: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())

    override suspend fun deleteTagForTrack(tag: String, trackUri: String) {
        val existingTrack = data.find { it.first.uri == trackUri } ?: return
        val newTags = existingTrack.second.toMutableList().apply { remove(tag) }
        data.remove(existingTrack)
        data.add(existingTrack.copy(second = newTags))
        emitNewTags()
    }

    override suspend fun createTagForTrack(trackEntity: TrackEntity, tag: String) {
        val existingTrack = data.find { it.first == trackEntity }
        if (existingTrack == null) {
            data.add(trackEntity to listOf(tag))
        } else {
            val newTags = existingTrack.second.toMutableList().apply { add(tag) }
            data.remove(existingTrack)
            data.add(existingTrack.copy(second = newTags))
        }
        emitNewTags()
    }

    override fun getAllTagsForTrack(trackUri: String): Flow<List<String>> {
        selectedTrack = trackUri
        return tagsStateFlow
    }

    private fun emitNewTags() {
        tagsStateFlow.value = data.find { it.first.uri == selectedTrack }?.second ?: emptyList()
    }
}