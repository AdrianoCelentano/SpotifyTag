package com.adriano.spotifytag

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adriano.spotifytag.data.database.SpotifyTagDatabase
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class SpotifyTagApplication: Application() {

    private lateinit var database: RoomDatabase

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        database = Room.databaseBuilder(this, SpotifyTagDatabase::class.java, "data.db")
            .fallbackToDestructiveMigration()
            .build()


    }
}