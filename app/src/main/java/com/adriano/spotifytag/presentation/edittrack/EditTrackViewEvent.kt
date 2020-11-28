package com.adriano.spotifytag.presentation.edittrack

sealed class EditTrackViewEvent {
    object FabClicked : EditTrackViewEvent()
    data class TagTextChanged(val value: String) : EditTrackViewEvent()
    data class TagClicked(val index: Int) : EditTrackViewEvent()
    data class TrackChanged(val track: TrackViewState) : EditTrackViewEvent()
    data class TagsChanged(val tags: List<String>) : EditTrackViewEvent()
}