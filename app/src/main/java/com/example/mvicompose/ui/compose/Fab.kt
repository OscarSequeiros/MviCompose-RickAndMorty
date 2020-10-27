package com.example.mvicompose.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.mvicompose.R

@Composable
fun Fab(action: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
        val icon = vectorResource(id = R.drawable.ic_refresh)
        FloatingActionButton(
            onClick = action,
            modifier = Modifier.fillMaxHeight()
                .wrapContentHeight(Alignment.Bottom)
                .padding(36.dp),
            backgroundColor = MaterialTheme.colors.secondary
        ) {
            Image(asset = icon, modifier = Modifier.preferredSize(24.dp))
        }
    }
}