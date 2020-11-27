package com.adriano.spotifytag.data.repo

import com.adriano.spotifytag.data.dao.TagsDao
import com.adriano.spotifytag.data.dao.TrackTagEntryDao
import com.adriano.spotifytag.data.entity.TagEntity
import com.adriano.spotifytag.data.entity.TrackEntity
import com.adriano.spotifytag.data.entity.TrackTagEntryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TagRepository @Inject constructor(
    private val tagsDao: TagsDao,
    private val trackRepository: TrackRepository,
    private val trackTagEntryDao: TrackTagEntryDao,
) {

    fun getAllTags() {
        tagsDao.getAllTags()
    }

    suspend fun deleteTagForTrack(tag: String, trackUri: String) {
        val tagId = tagsDao.getTagWithName(tag) ?: return
        val trackTagEntry = trackTagEntryDao.getTrackTagEntry(trackUri, tagId.id)
        if (trackTagEntry != null) trackTagEntryDao.delete(trackTagEntry)
    }

    fun getAllTagsForTrack(trackUri: String): Flow<List<String>> {
        return tagsDao.getTagsForTrackFlow(trackUri)
            .map { tags -> tags.map { it.name } }
    }

    suspend fun createTagForTrack(trackEntity: TrackEntity, tag: String) {
        val tagId = getOrCreateTag(tag)
        val trackUri = trackRepository.getOrCreateTrack(trackEntity)
        trackTagEntryDao.insert(
            TrackTagEntryEntity(
                trackUri = trackUri,
                tagId = tagId
            )
        )
    }

    private suspend fun getOrCreateTag(tag: String): Long {
        val maybeTagEntity = tagsDao.getTagWithName(tag)
        if (maybeTagEntity != null) return maybeTagEntity.id
        return tagsDao.insert(TagEntity(name = tag))
    }
}