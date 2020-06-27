package com.example.mvicompose.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.ui.core.setContent
import androidx.ui.foundation.isSystemInDarkTheme
import androidx.ui.livedata.observeAsState
import androidx.ui.material.MaterialTheme
import com.example.mvicompose.mvibase.MviView
import com.example.mvicompose.presentation.CharactersIntent
import com.example.mvicompose.presentation.CharactersIntent.*
import com.example.mvicompose.presentation.CharactersViewModel
import com.example.mvicompose.presentation.CharactersViewModelFactory
import com.example.mvicompose.presentation.CharactersViewState
import com.example.mvicompose.presentation.CharactersViewState.*
import com.example.mvicompose.ui.compose.*
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MviView<CharactersIntent, CharactersViewState> {

    private val retryLoadPublish = PublishSubject.create<RetryLoadAllIntent>()
    private val refreshCharactersPublish = PublishSubject.create<RefreshAllIntent>()

    private val viewModel: CharactersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        processIntents()

        setContent {
            val themeColors = if (isSystemInDarkTheme()) darkThemeColors else lightThemeColors

            MaterialTheme(colors = themeColors, typography = appTypography) {
                viewModel.liveData().observeAsState().value?.let { state: CharactersViewState ->
                    Render(state)
                }
            }
        }
    }

    private fun processIntents(): Unit = viewModel.processIntents(intents())

    override fun intents(): Observable<CharactersIntent> {
        return Observable.merge(
            Observable.just(LoadAllIntent),
            retryLoadPublish,
            refreshCharactersPublish
        )
    }

    @Composable
    override fun Render(state: CharactersViewState) {
        when (state) {
            is DefaultState         -> Default()
            is LoadingState         -> Loading()
            is NoneCharactersState  -> ComposeEmptyCharacters { emitRefreshIntent() }
            is CharactersListState  -> ComposeCharacters(state.characters) { emitRefreshIntent() }
            is FailureState         -> Failure(state.error) { emitRetryIntent() }
        }
    }

    private fun emitRefreshIntent(): Unit = refreshCharactersPublish.onNext(RefreshAllIntent)

    private fun emitRetryIntent(): Unit = retryLoadPublish.onNext(RetryLoadAllIntent)

}