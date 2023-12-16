package com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.ui.common

sealed class UiState<out T : Any?> {
    object Loading : UiState<Nothing>()
    data class Success<out T : Any>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
