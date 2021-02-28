package com.adriano.spotifytag.presentation.createplaylist

sealed class CreatePlayListViewEvent {
    data class TagClicked(val index: Int) : CreatePlayListViewEvent()
    object CreatePlaylistClicked : CreatePlayListViewEvent()
    data class TagsLoaded(val tags: List<String>) : CreatePlayListViewEvent()
}
