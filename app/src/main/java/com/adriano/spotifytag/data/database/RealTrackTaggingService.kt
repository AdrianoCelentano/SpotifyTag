package com.adriano.spotifytag.data.database

import com.adriano.spotifytag.data.database.dao.TrackTagEntryDao
import com.adriano.spotifytag.data.database.entity.TrackEntity
import com.adriano.spotifytag.data.database.entity.TrackTagEntryEntity
import com.adriano.spotifytag.data.database.repo.TagRepository
import com.adriano.spotifytag.data.database.repo.TrackRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RealTrackTaggingService @Inject constructor(
    private val trackRepository: TrackRepository,
    private val trackTagEntryDao: TrackTagEntryDao,
    private val tagRepository: TagRepository,
) : TrackTaggingService {

    override suspend fun deleteTagForTrack(tag: String, trackUri: String) {
        val tagId = tagRepository.getTagWithName(tag) ?: return
        val trackTagEntry = trackTagEntryDao.getTrackTagEntry(trackUri, tagId.id)
        if (trackTagEntry != null) trackTagEntryDao.delete(trackTagEntry)
    }

    override suspend fun createTagForTrack(trackEntity: TrackEntity, tag: String) {
        val tagId = tagRepository.getOrCreateTag(tag)
        val trackUri = trackRepository.getOrCreateTrack(trackEntity)
        trackTagEntryDao.insert(
            TrackTagEntryEntity(
                trackUri = trackUri,
                tagId = tagId
            )
        )
    }

    override fun getAllTagsForTrack(trackUri: String): Flow<List<String>> {
        return tagRepository.getAllTagsForTrack(trackUri)
    }
}