package com.example.mvicompose.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.ViewModelProvider

import com.example.mvicompose.mvibase.MviView
import com.example.mvicompose.presentation.CharactersIntent
import com.example.mvicompose.presentation.CharactersIntent.*
import com.example.mvicompose.presentation.CharactersViewModel
import com.example.mvicompose.presentation.CharactersViewModelFactory
import com.example.mvicompose.presentation.CharactersViewState
import com.example.mvicompose.presentation.CharactersViewState.*
import com.example.mvicompose.ui.compose.*
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class MainActivity : AppCompatActivity(), MviView<CharactersIntent, CharactersViewState> {

    private val retryLoadPublish = PublishSubject.create<RetryLoadAllIntent>()
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