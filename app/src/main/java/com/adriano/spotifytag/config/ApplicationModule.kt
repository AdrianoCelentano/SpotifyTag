package com.adriano.spotifytag.config

import com.adriano.spotifytag.spotify.Spotify
import com.adriano.spotifytag.spotify.SpotifyImageLoader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {


}

@Module
@InstallIn(ApplicationComponent::class)
interface ApplicationBindsModule {

    @Binds
    fun bindSpotifyImageLoader(spotify: Spotify): SpotifyImageLoader
}