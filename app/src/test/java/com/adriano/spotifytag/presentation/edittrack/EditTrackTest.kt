package com.adriano.spotifytag.presentation.edittrack

import com.spotify.protocol.types.ImageUri
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class EditTrackTest {

    @Before
    fun before() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `text changed`() {
        val editTrackVM = EditTrackViewModel(mockk(relaxed = true), mockk(relaxed = true))
        val expectedTextInput = "text"
        val event = EditTrackViewEvent.TagTextChanged(value = expectedTextInput)

        editTrackVM.event(event)

        assertThat(editTrackVM.state.currentTextInput).isEqualTo(expectedTextInput)
    }

    @Test
    fun `track changed`() {
        val editTrackVM = EditTrackViewModel(mockk(relaxed = true), mockk(relaxed = true))
        val expectedTrack = createTrackViewState()
        val event = EditTrackViewEvent.TrackChanged(track = expectedTrack)

        editTrackVM.event(event)

        assertThat(editTrackVM.state.currentTrack).isEqualTo(expectedTrack)
    }

    private fun createTrackViewState(
        uri: String = "uri",
        name: String = "name",
        artist: String = "artist",
        imageUri: ImageUri = ImageUri("uri"),
        album: String = "album"
    ): TrackViewState {
        return TrackViewState(
            uri = uri,
            name = name,
            artist = artist,
            imageUri = imageUri,
            album = album,
        )
    }
}