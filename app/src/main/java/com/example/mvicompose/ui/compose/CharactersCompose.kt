package com.example.mvicompose.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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
    ScrollableColumn {
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
fun VStack(child: @Composable () -> Unit) =
    ScrollableColumn {
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
                modifier = Modifier.padding(top = 4.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }


@Composable
fun CharacterImage(urlImage: String) {
    CoilImage(
        data = urlImage,
        requestBuilder = {
            transformations(CircleCropTransformation())
        },
    )
}

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