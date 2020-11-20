package com.adriano.spotifytag.edittrack.view.tags

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adriano.spotifytag.theme.typography

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.DarkGray
    ) {
        Row(
            modifier = Modifier.padding(start = 12.dp, top = 6.dp, end = 12.dp, bottom = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = text,
                style = typography.body1.copy(color = Color.White)
            )
            Spacer(Modifier.preferredWidth(12.dp))
            Icon(
                modifier = Modifier.preferredSize(20.dp, 20.dp),
                asset = Icons.Outlined.Close,
                tint = Color.White
            )
        }
    }
}