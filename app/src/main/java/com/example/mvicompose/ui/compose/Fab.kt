package com.example.mvicompose.ui.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Fab(action: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
        FloatingActionButton(
            onClick = action,
            icon = {
                Icon(
                    asset = Icons.Filled.Refresh,
                    tint = Color.White
                )
            },
            modifier = Modifier.fillMaxHeight()
                .wrapContentHeight(Alignment.Bottom)
                .padding(36.dp)
        )
    }
}