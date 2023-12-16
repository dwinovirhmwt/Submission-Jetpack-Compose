package com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.data.AnimeGhibliRepository
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.model.AnimeGhibli
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: AnimeGhibliRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<AnimeGhibli>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<AnimeGhibli>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        repository.searchAnimeGhibli(_query.value)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updateAnimeGhibli(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateAnimeGhibli(id, newState)
            .collect { isUpdated ->
                if (isUpdated) search(_query.value)
            }
    }
}