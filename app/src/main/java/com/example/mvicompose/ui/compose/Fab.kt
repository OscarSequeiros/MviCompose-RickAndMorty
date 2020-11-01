package com.example.mvicompose.ui.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.mvicompose.R

@Composable
fun Fab(action: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
        FloatingActionButton(
            onClick = action,
            icon = { Icon(Icons.Filled.Refresh) },
            modifier = Modifier.fillMaxHeight()
                .wrapContentHeight(Alignment.Bottom)
                .padding(36.dp)
        )
    }
}