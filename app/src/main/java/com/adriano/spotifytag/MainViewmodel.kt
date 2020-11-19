package com.adriano.spotifytag

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adriano.spotifytag.spotify.Spotify
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch

class MainViewmodel @ViewModelInject constructor(
    private val spotify: Spotify,
    @ApplicationContext context: Context
) : ViewModel() {

    val currentTrackFlow = spotify.currentTrackFlow()

    init {
        viewModelScope.launch {
            spotify.connect(context)
        }
    }

    override fun onCleared() {
        super.onCleared()
        spotify.disconnect()
    }
}