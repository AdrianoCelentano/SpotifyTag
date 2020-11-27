package com.adriano.spotifytag.data.repo

import com.adriano.spotifytag.data.dao.TrackDao
import com.adriano.spotifytag.data.entity.TrackEntity
import javax.inject.Inject

class TrackRepository @Inject constructor(
    private val trackDao: TrackDao
) {

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