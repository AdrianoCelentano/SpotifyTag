package com.adriano.spotifytag.presentation.createplaylist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adriano.spotifytag.data.database.repo.TagRepository
import com.adriano.spotifytag.data.spotify.playlist.SpotifyPlaylistCreator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


class CreatePlaylistViewModel @ViewModelInject constructor(
    private val tagRepository: TagRepository,
    private val playlistCreator: SpotifyPlaylistCreator,
) : ViewModel() {

    var state by mutableStateOf(CreatePlayListViewState.init())
    val effectFlow = MutableSharedFlow<CreatePlaylistEffect>()

    init {
        viewModelScope.launch {
            tagRepository.getAllTags()
                .collect { event(CreatePlayListViewEvent.TagsLoaded(it)) }
        }
    }

    fun event(event: CreatePlayListViewEvent) {
        Timber.d("Event: $event")
        when (event) {
            is CreatePlayListViewEvent.TagClicked -> handleTagClick(event.index)
            CreatePlayListViewEvent.CreatePlaylistClicked -> handleCreatePlaylistClicked()
            is CreatePlayListViewEvent.TagsLoaded -> handleTagsLoaded(event.tags)
        }
    }

    private fun updateState(newState: CreatePlayListViewState) {
        Timber.d("State: $newState")
        state = newState
    }

    private fun handleTagsLoaded(tags: List<String>) {
        val newTags = tags.map { TagViewState(checked = false, name = it) }
        updateState(state.copy(tags = newTags))
    }

    private fun handleTagClick(clickedIndex: Int) {
        val updatedTags = state.tags.mapIndexed { index, tag ->
            if (index == clickedIndex) tag.copy(checked = tag.checked.not())
            else tag
        }
        updateState(state.copy(tags = updatedTags))
    }

    private fun handleCreatePlaylistClicked() {
        viewModelScope.launch {
            playlistCreator.createPlaylist(checkedTags())
        }
    }

    private fun checkedTags() = state.tags
        .filter { it.checked }
        .map { it.name }

}