package com.example.mvicompose.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.ui.core.Alignment
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.*
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.livedata.observeAsState
import androidx.ui.material.*
import androidx.ui.res.imageResource
import androidx.ui.res.vectorResource
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import coil.request.GetRequest
import coil.transform.CircleCropTransformation
import com.example.mvicompose.mvibase.MviView
import com.example.mvicompose.presentation.CharactersIntent
import com.example.mvicompose.presentation.CharactersIntent.LoadAllIntent
import com.example.mvicompose.presentation.CharactersIntent.RefreshAllIntent
import com.example.mvicompose.presentation.CharactersViewModel
import com.example.mvicompose.presentation.CharactersViewModelFactory
import com.example.mvicompose.presentation.CharactersViewState
import com.example.mvicompose.presentation.CharactersViewState.*
import com.example.mvicompose.presentation.model.UiCharacter
import dev.chrisbanes.accompanist.coil.CoilImage
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import com.example.mvicompose.R

class MainActivity : AppCompatActivity(), MviView<CharactersIntent, CharactersViewState> {

    private val retryLoadPublish = PublishSubject.create<CharactersIntent>()
    private val refreshCharactersPublish = PublishSubject.create<RefreshAllIntent>()

    private val viewModel: CharactersViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, CharactersViewModelFactory())
            .get(CharactersViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        processIntents()


        setContent {
            val themeColors = if (isSystemInDarkTheme()) darkThemeColors else lightThemeColors

            MaterialTheme(colors = themeColors) {
                viewModel.liveData().observeAsState().value?.let { state: CharactersViewState ->
                    Render(state)
                }
            }
        }
    }

    private fun processIntents() {
        viewModel.processIntents(intents())
    }

    override fun intents(): Observable<CharactersIntent> {
        return Observable.merge(
            automaticIntent(),
            retryLoadIntent(),
            refreshLoadIntent()
        )
    }

    private fun automaticIntent() = Observable.just(LoadAllIntent)

    private fun retryLoadIntent() = retryLoadPublish

    private fun refreshLoadIntent() = refreshCharactersPublish

    @Composable
    override fun Render(state: CharactersViewState) {
        when (state) {
            is DefaultState -> Default()
            is LoadingState -> Loading()
            is NoneCharactersState -> {
            }
            is CharactersListState -> ComposeCharacters(state.characters)
            is FailureState -> Failure(e = state.error)
        }
    }

    @Composable
    private fun Default() {
        Column(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
            Image(
                asset = imageResource(id = R.drawable.notification_bg_normal_pressed),
                modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)
            )
            Text(
                text = "message",
                modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)
                    .padding(16.dp),
                style = MaterialTheme.typography.body1
            )
        }
    }

    @Composable
    private fun Failure(e: Throwable) {
        e.printStackTrace()
        Text(
            text = "Error",
            modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)
                .padding(16.dp)
        )
    }

    @Composable
    fun Loading() {
        Box(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
            CircularProgressIndicator(color = Color.Green)
        }
    }

    @Composable
    fun ComposeCharacters(characters: List<UiCharacter>) {
        VStack {
            characters.map {
                CharacterView(it)
                CharacterDivider()
            }
        }
        Fab { refreshCharactersPublish.onNext(RefreshAllIntent) }
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
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    fontSize = 18.sp
                )
                Text(
                    text = character.gender,
                    color = MaterialTheme.colors.onPrimary,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = character.specieStatus,
                    color = MaterialTheme.colors.onPrimary,
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
    fun Fab(action:() -> Unit) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalGravity = Alignment.End) {
            val icon = vectorResource(id = R.drawable.ic_refresh)
            FloatingActionButton(
                onClick = action,
                modifier = Modifier.fillMaxHeight().wrapContentHeight(Alignment.Bottom).padding(36.dp),
                backgroundColor = MaterialTheme.colors.secondary
            ) {
                Image(asset = icon, modifier = Modifier.preferredSize(48.dp))
            }
        }
    }

}