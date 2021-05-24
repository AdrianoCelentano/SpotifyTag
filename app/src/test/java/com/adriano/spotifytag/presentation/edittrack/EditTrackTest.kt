package com.adriano.spotifytag.presentation.edittrack

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

@ExperimentalCoroutinesApi
class EditTrackTest {

    @Test
    fun `text changed`() = runBlockingTest {
        val editTrackVM = EditTrackViewModel(
            FakeTrackObserver(listOf()),
            FakeTrackTaggingService(emptyList())
        )
        val expectedTextInput = "text"

        editTrackVM.event(EditTrackViewEvent.FabClicked)
        editTrackVM.event(EditTrackViewEvent.TagTextChanged(expectedTextInput))

        assertThat(editTrackVM.state.currentTextInput).isEqualTo(expectedTextInput)
    }

    @Test
    fun `text changed without edit mode`() = runBlockingTest {
        val editTrackVM = EditTrackViewModel(
            FakeTrackObserver(listOf()),
            FakeTrackTaggingService(emptyList())
        )

        editTrackVM.event(EditTrackViewEvent.TagTextChanged(""))

        Assertions.assertThatExceptionOfType(IllegalStateException::class.java)
    }

    @Test
    fun `track changed`() = runBlockingTest {
        val editTrackVM = EditTrackViewModel(
            FakeTrackObserver(listOf()),
            FakeTrackTaggingService(emptyList())
        )
        val expectedTrack = TestDataFactory.createTrackViewState()

        editTrackVM.event(EditTrackViewEvent.TrackChanged(expectedTrack))

        assertThat(editTrackVM.state.currentTrack).isEqualTo(expectedTrack)
    }

    @Test
    fun `tag read`() = runBlockingTest {
        val expectedUri = "uri"
        val editTrackVM = EditTrackViewModel(
            FakeTrackObserver(listOf(expectedUri)),
            FakeTrackTaggingService(TestDataFactory.createTrackTagData(uri = expectedUri))
        )

        assertThat(editTrackVM.state.currentTrack?.uri).isEqualTo(expectedUri)
    }

    @Test
    fun `tag added`() = runBlockingTest {
        val expectedTag = "tag"
        val editTrackVM = EditTrackViewModel(
            FakeTrackObserver(tracks = listOf("track")),
            FakeTrackTaggingService(tags = emptyList())
        )

        editTrackVM.event(EditTrackViewEvent.FabClicked)
        editTrackVM.event(EditTrackViewEvent.TagTextChanged(expectedTag))
        editTrackVM.event(EditTrackViewEvent.FabClicked)

        assertThat(editTrackVM.state.tags).isEqualTo(listOf(expectedTag))
    }

    @Test
    fun `no tag added when text is empty`() = runBlockingTest {
        val editTrackVM = EditTrackViewModel(
            FakeTrackObserver(tracks = listOf("track")),
            FakeTrackTaggingService(tags = emptyList())
        )

        editTrackVM.event(EditTrackViewEvent.FabClicked)
        editTrackVM.event(EditTrackViewEvent.TagTextChanged(""))
        editTrackVM.event(EditTrackViewEvent.FabClicked)

        assertThat(editTrackVM.state.tags).isEmpty()
    }

    @Test
    fun `tag deleted`() = runBlockingTest {
        val editTrackVM = EditTrackViewModel(
            FakeTrackObserver(listOf("track")),
            FakeTrackTaggingService(emptyList())
        )

        editTrackVM.event(EditTrackViewEvent.FabClicked)
        editTrackVM.event(EditTrackViewEvent.TagTextChanged("tag"))
        editTrackVM.event(EditTrackViewEvent.FabClicked)
        editTrackVM.event(EditTrackViewEvent.TagClicked(0))

        assertThat(editTrackVM.state.tags).isEmpty()
    }

    @Test
    fun `edit mode enter`() = runBlockingTest {
        val editTrackVM = EditTrackViewModel(
            FakeTrackObserver(tracks = emptyList()),
            FakeTrackTaggingService(tags = emptyList())
        )

        editTrackVM.event(EditTrackViewEvent.FabClicked)

        assertThat(editTrackVM.state.editMode).isEqualTo(true)
    }

    @Test
    fun `edit mode exit`() = runBlockingTest {
        val editTrackVM = EditTrackViewModel(
            FakeTrackObserver(tracks = emptyList()),
            FakeTrackTaggingService(tags = emptyList())
        )

        editTrackVM.event(EditTrackViewEvent.FabClicked)
        editTrackVM.event(EditTrackViewEvent.FabClicked)

        assertThat(editTrackVM.state.editMode).isEqualTo(false)
    }
}