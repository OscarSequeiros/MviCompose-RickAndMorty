package com.example.mvicompose.ui

import android.os.Bundle
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
            Observable.just(LoadAllIntent),
            retryLoadPublish,
            refreshCharactersPublish
        )
    }

    private fun automaticIntent() = Observable.just(LoadAllIntent)

    private fun retryLoadIntent() = retryLoadPublish

    private fun refreshLoadIntent() = refreshCharactersPublish

    @Composable
    override fun Render(state: CharactersViewState) {
        when (state) {
            is DefaultState         -> Default()

            is LoadingState         -> Loading()

            is NoneCharactersState  -> ComposeEmptyCharacters {
                refreshCharactersPublish.onNext(RefreshAllIntent)
            }
            is CharactersListState  -> ComposeCharacters(state.characters) {
                refreshCharactersPublish.onNext(RefreshAllIntent)
            }
            is FailureState         -> Failure(state.error) {
                retryLoadPublish.onNext(RetryLoadAllIntent)
            }
        }
    }
}