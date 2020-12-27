package com.example.mvicompose.ui.compose

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.transform.CircleCropTransformation
import com.example.mvicompose.presentation.model.UiCharacter
import dev.chrisbanes.accompanist.coil.CoilImage

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
        fadeIn = true
    )
}