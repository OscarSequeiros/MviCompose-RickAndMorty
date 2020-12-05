package com.example.mvicompose.ui.compose

import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.mvicompose.R

@Composable
fun Failure(e: Throwable, action: () -> Unit) {
    e.printStackTrace()
    Column(
        modifier = Modifier.fillMaxSize()
            .wrapContentHeight(Alignment.CenterVertically)
            .padding(start = 96.dp, end = 96.dp)
    ) {
        val icon = vectorResource(id = R.drawable.ic_not_found)
        Image(
            imageVector = icon,
            modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)
        )
        Button(
            modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)
                .padding(top = 24.dp),
            onClick = action
        ) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}