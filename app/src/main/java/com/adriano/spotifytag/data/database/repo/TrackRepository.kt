package com.adriano.spotifytag.data.database.repo

import com.adriano.spotifytag.data.database.dao.TrackDao
import com.adriano.spotifytag.data.database.entity.TrackEntity
import javax.inject.Inject

class TrackRepository @Inject constructor(
    private val trackDao: TrackDao
) {

    suspend fun getTracksWithTags(tags: List<String>): List<String> {
        return trackDao.trackWithTags(tags)
    }

    suspend fun getOrCreateTrack(newTrackEntity: TrackEntity): String {
        val maybeTrackEntity = trackDao.trackWithUri(newTrackEntity.uri)
        if (maybeTrackEntity != null) return maybeTrackEntity.uri
        createTrack(newTrackEntity)
        return newTrackEntity.uri
    }

    suspend fun createTrack(newTrackEntity: TrackEntity) {
        trackDao.insert(newTrackEntity)
    }
}