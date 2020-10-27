package com.example.mvicompose.ui.compose

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Default() {
    Column(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
        Text(
            text = "Rick and Morty",
            modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)
                .padding(16.dp),
            style = MaterialTheme.typography.body1
        )
    }
}