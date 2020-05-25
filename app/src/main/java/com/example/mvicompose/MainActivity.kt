package com.example.mvicompose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Box
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.layout.*
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.res.imageResource
import androidx.ui.unit.dp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            /*Column(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
                Image(
                        asset = imageResource(id = R.mipmap.jetpack_hero_arrow),
                        modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)
                )
                Text(
                        text = "message",
                        modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center).padding(16.dp)
                )
            }*/
            Box(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
                CircularProgressIndicator()
            }
        }
    }
}