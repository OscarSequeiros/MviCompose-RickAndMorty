package com.example.mvicompose.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity(), MviView<CharactersIntent, CharactersViewState> {

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
                val state = viewModel.state.collectAsState()
                Render(state.value)
            }
        }
    }

    private fun processIntents() = viewModel.processIntent(LoadAllIntent)

    private fun emitRefreshIntent() = viewModel.processIntent(RefreshAllIntent)

    private fun emitRetryIntent() = viewModel.processIntent(RetryLoadAllIntent)

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
}