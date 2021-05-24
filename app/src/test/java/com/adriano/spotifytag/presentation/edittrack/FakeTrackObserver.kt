package com.adriano.spotifytag.presentation.edittrack

import com.adriano.spotifytag.data.spotify.player.TrackObserver
import com.spotify.protocol.types.ImageUri
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTrackObserver(
    private val tracks: List<String>
) : TrackObserver {

    override fun disconnect() {

    }

    override fun currentTrackFlow(): Flow<TrackViewState> {
        return flow {
            tracks.forEach {
                emit(
                    TrackViewState(
                        uri = it,
                        name = "name",
                        album = "album",
                        imageUri = ImageUri("uri"),
                        artist = "artist",
                    )
                )
            }
        }
    }
}