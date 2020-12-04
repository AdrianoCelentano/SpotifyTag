package com.adriano.spotifytag.presentation.util

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Stable
fun Modifier.bottomNavigationPadding() = this.then(
    padding(bottom = 56.dp)
)