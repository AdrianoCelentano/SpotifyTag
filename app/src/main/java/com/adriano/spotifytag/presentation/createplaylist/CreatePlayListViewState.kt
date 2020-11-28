package com.adriano.spotifytag.presentation.createplaylist

data class CreatePlayListViewState(
    val tags: List<TagViewState>,
) {

    companion object {
        fun init(): CreatePlayListViewState = CreatePlayListViewState(listOf())
    }
}

data class TagViewState(
    val checked: Boolean,
    val name: String,
)