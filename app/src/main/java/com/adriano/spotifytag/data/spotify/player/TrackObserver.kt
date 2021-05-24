package com.adriano.spotifytag.data.spotify.player

import com.adriano.spotifytag.presentation.edittrack.TrackViewState
import kotlinx.coroutines.flow.Flow

interface TrackObserver {
    fun disconnect()
    fun currentTrackFlow(): Flow<TrackViewState>
}
