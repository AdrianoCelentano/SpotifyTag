package com.adriano.spotifytag.config

import android.content.Context
import androidx.room.Room
import com.adriano.spotifytag.data.SpotifyTagDatabase
import com.adriano.spotifytag.data.dao.TagsDao
import com.adriano.spotifytag.data.dao.TrackDao
import com.adriano.spotifytag.data.dao.TrackTagEntryDao
import com.adriano.spotifytag.spotify.Spotify
import com.adriano.spotifytag.spotify.SpotifyImageLoader
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SpotifyTagDatabase {
        return Room.databaseBuilder(context, SpotifyTagDatabase::class.java, "data.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @JvmStatic
    @Provides
    fun provideTrackDao(spotifyTagDatabase: SpotifyTagDatabase): TrackDao {
        return spotifyTagDatabase.trackDao()
    }

    @JvmStatic
    @Provides
    fun provideTagDao(spotifyTagDatabase: SpotifyTagDatabase): TagsDao {
        return spotifyTagDatabase.tagDao()
    }

    @JvmStatic
    @Provides
    fun provideTrackTagEntryDao(spotifyTagDatabase: SpotifyTagDatabase): TrackTagEntryDao {
        return spotifyTagDatabase.trackTagEntryDao()
    }

}

@Module
@InstallIn(ApplicationComponent::class)
interface ApplicationBindsModule {

    @Binds
    fun bindSpotifyImageLoader(spotify: Spotify): SpotifyImageLoader
}