package com.adriano.spotifytag.data.database.repo

import com.adriano.spotifytag.data.database.dao.TagsDao
import com.adriano.spotifytag.data.database.entity.TagEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TagRepository @Inject constructor(
    private val tagsDao: TagsDao,
) {

    fun getAllTags(): Flow<List<String>> {
        return tagsDao.getAllTags()
            .mapTagEntitiesToTagNames()
    }

    fun getAllTagsForTrack(trackUri: String): Flow<List<String>> {
        return tagsDao.getTagsForTrackFlow(trackUri)
            .mapTagEntitiesToTagNames()
    }

    suspend fun getTagWithName(name: String) = tagsDao.getTagWithName(name)

    suspend fun getOrCreateTag(tag: String): Long {
        val maybeTagEntity = tagsDao.getTagWithName(tag)
        if (maybeTagEntity != null) return maybeTagEntity.id
        return tagsDao.insert(TagEntity(name = tag))
    }

    private fun Flow<List<TagEntity>>.mapTagEntitiesToTagNames(): Flow<List<String>> {
        return map { tags ->
            tags.map { tag -> tag.name }
        }
    }
}