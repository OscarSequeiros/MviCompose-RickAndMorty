package com.example.mvicompose.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import com.example.mvicompose.R

@Composable
fun ComposeEmptyCharacters(action: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
        val icon = vectorResource(id = R.drawable.ic_empty_state)
        Image(
            imageVector = icon,
            modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)
        )
    }
    Fab(action)
}