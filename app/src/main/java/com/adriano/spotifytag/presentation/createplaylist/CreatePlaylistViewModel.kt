package com.adriano.spotifytag.presentation.createplaylist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.adriano.spotifytag.data.database.repo.TagRepository

class CreatePlaylistViewModel @ViewModelInject constructor(
    private val tagRepository: TagRepository,
) : ViewModel() {

    val tags = tagRepository.getAllTags()
}