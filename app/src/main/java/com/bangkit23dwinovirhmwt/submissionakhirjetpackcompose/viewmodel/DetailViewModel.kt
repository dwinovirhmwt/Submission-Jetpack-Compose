package com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.data.AnimeGhibliRepository
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.model.AnimeGhibli
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.ui.common.UiState

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: AnimeGhibliRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<AnimeGhibli>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<AnimeGhibli>>
        get() = _uiState

    fun getAnimeGhibliById(id: Int) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(repository.getAnimeGhibliById(id))
    }

    fun updateAnimeGhibli(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateAnimeGhibli(id, !newState)
            .collect { isUpdated ->
                if (isUpdated) getAnimeGhibliById(id)
            }
    }

}