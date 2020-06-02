package com.example.mvicompose.ui.compose

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.res.vectorResource
import androidx.ui.text.font.FontStyle
import androidx.ui.unit.dp
import coil.request.GetRequest
import coil.transform.CircleCropTransformation
import com.example.mvicompose.R
import com.example.mvicompose.presentation.model.UiCharacter
import dev.chrisbanes.accompanist.coil.CoilImage

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

@Composable
fun Failure(e: Throwable, action: () -> Unit) {
    e.printStackTrace()
    VerticalScroller {
        Column(
            modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
                .padding(start = 96.dp, end = 96.dp)
        ) {
            val icon = vectorResource(id = R.drawable.ic_not_found)
            Image(
                asset = icon,
                modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)
            )
            Button(
                modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center),
                onClick = action
            ) {
                Text(text = "Retry")
            }
        }
    }
}

@Composable
fun Loading() {
    Box(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
        CircularProgressIndicator(color = MaterialTheme.colors.secondary)
    }
}


@Composable
fun ComposeEmptyCharacters(action: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
        val icon = vectorResource(id = R.drawable.ic_empty_state)
        Image(
            asset = icon,
            modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)
        )
    }
    Fab(action)
}

@Composable
fun ComposeCharacters(characters: List<UiCharacter>, action: () -> Unit) {
    VStack {
        characters.map {
            CharacterView(it)
            CharacterDivider()
        }
    }
    Fab(action)
}

@Composable
fun VStack(child: @Composable() () -> Unit) =
    VerticalScroller {
        Column(modifier = Modifier.fillMaxWidth()) {
            child()
        }
    }


@Composable
fun CharacterDivider() =
    Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f))

@Composable
fun CharacterView(character: UiCharacter) =
    Row(
        modifier = Modifier.padding(16.dp).fillMaxWidth().wrapContentSize(Alignment.CenterStart)
    ) {
        CharacterImage(character.urlImage)
        Column(
            modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.CenterStart)
                .padding(12.dp)
        ) {
            Text(
                text = character.name,
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h6
            )
            Text(
                text = character.gender,
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.body1,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = character.specieStatus,
                color = MaterialTheme.colors.onPrimary,
                fontStyle = FontStyle.Normal,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }


@Composable
fun CharacterImage(urlImage: String) {
    CoilImage(
        request = GetRequest.Builder(ContextAmbient.current)
            .data(urlImage)
            .transformations(CircleCropTransformation())
            .build(),
        modifier = Modifier.preferredSize(96.dp, 96.dp)
    )
}

@Composable
fun Fab(action: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalGravity = Alignment.End) {
        val icon = vectorResource(id = R.drawable.ic_refresh)
        FloatingActionButton(
            onClick = action,
            modifier = Modifier.fillMaxHeight().wrapContentHeight(Alignment.Bottom)
                .padding(36.dp),
            backgroundColor = MaterialTheme.colors.secondary
        ) {
            Image(asset = icon, modifier = Modifier.preferredSize(48.dp))
        }
    }
}