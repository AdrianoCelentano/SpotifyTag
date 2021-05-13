package com.adriano.spotifytag.config

import android.content.Context
import androidx.room.Room
import com.adriano.spotifytag.data.database.SpotifyTagDatabase
import com.adriano.spotifytag.data.database.dao.TagsDao
import com.adriano.spotifytag.data.database.dao.TrackDao
import com.adriano.spotifytag.data.database.dao.TrackTagEntryDao
import com.adriano.spotifytag.data.spotify.player.SpotifyImageLoader
import com.adriano.spotifytag.data.spotify.player.SpotifyPlayerObserver
import com.adriano.spotifytag.data.spotify.playlist.SpotifyRetrofitService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SpotifyTagDatabase {
        return Room.databaseBuilder(context, SpotifyTagDatabase::class.java, "data.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideTrackDao(spotifyTagDatabase: SpotifyTagDatabase): TrackDao {
        return spotifyTagDatabase.trackDao()
    }

    @Provides
    fun provideTagDao(spotifyTagDatabase: SpotifyTagDatabase): TagsDao {
        return spotifyTagDatabase.tagDao()
    }

    @Provides
    fun provideTrackTagEntryDao(spotifyTagDatabase: SpotifyTagDatabase): TrackTagEntryDao {
        return spotifyTagDatabase.trackTagEntryDao()
    }

    @Provides
    fun provideSpotifyRetrofitWebService(): SpotifyRetrofitService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spotify.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(SpotifyRetrofitService::class.java)
    }

}

@Module
@InstallIn(SingletonComponent::class)
interface ApplicationBindsModule {

    @Binds
    fun bindSpotifyImageLoader(spotify: SpotifyPlayerObserver): SpotifyImageLoader
}