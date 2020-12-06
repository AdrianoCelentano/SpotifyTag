package com.adriano.spotifytag.presentation.createplaylist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adriano.spotifytag.data.database.repo.TagRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


class CreatePlaylistViewModel @ViewModelInject constructor(
    private val tagRepository: TagRepository,
) : ViewModel() {

    var state by mutableStateOf(CreatePlayListViewState.init())

    init {
        viewModelScope.launch {
            tagRepository.getAllTags()
                .collect { event(CreatePlayListViewEvent.TagsLoaded(it)) }
        }
    }

    fun event(event: CreatePlayListViewEvent) {
        Timber.d("Event: $event")
        when (event) {
            is CreatePlayListViewEvent.TagClicked -> handlTagClick(event.index)
            CreatePlayListViewEvent.CreatePlaylistClicked -> handleCreatePLaylistClicked()
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

    private fun handlTagClick(clickedIndex: Int) {
        val updatedTags = state.tags.mapIndexed { index, tag ->
            if (index == clickedIndex) tag.copy(checked = tag.checked.not())
            else tag
        }
        updateState(state.copy(tags = updatedTags))
    }

    private fun handleCreatePLaylistClicked() {

    }
}