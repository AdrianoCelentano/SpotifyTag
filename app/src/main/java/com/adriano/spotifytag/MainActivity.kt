package com.adriano.spotifytag

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Providers
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import com.adriano.spotifytag.spotify.SpotifyImageLoader
import com.adriano.spotifytag.ui.SpotifyTagTheme
import com.adriano.spotifytag.view.Chip
import com.adriano.spotifytag.view.FlowLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewmodel: MainViewmodel by viewModels()

    @Inject
    lateinit var spotifyImageLoader: SpotifyImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.Black.toArgb()

        setContent {

            SpotifyTagTheme {

                Surface(color = MaterialTheme.colors.background) {

                    Box(modifier = Modifier.fillMaxSize()) {

                        Column(
                            modifier = Modifier.fillMaxSize()
                        )
                        {
                            Providers(SpotifyImageLoaderAmbient provides spotifyImageLoader) {
                                SpotifyTrackState(
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(24.dp),
                                )
                            }

                            ScrollableColumn(
                                modifier = Modifier.fillMaxSize()
                                    .padding(bottom = 80.dp)
                            ) {
                                FlowLayout(
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(start = 24.dp, end = 24.dp),
                                    horizontalSpacing = 12.dp,
                                    verticalSpacing = 4.dp
                                ) {
                                    mainViewmodel.state.tags.forEachIndexed { index, text ->
                                        Chip(
                                            onClick = {
                                                mainViewmodel.event(TrackViewEvent.TagClicked(index))
                                            },
                                            modifier = Modifier,
                                            text = text
                                        )
                                    }
                                }
                            }
                        }

                        AddTextFab(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                        )
                    }
                }
            }
        }
    }
}