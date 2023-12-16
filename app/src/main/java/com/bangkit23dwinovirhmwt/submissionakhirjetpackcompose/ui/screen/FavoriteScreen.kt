package com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.R
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.di.Injection
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.model.AnimeGhibli
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.ui.common.UiState
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.ui.items.EmptyList
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.viewmodel.FavoriteViewModel
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.viewmodel.ViewModelFactory

@Composable
fun FavoriteScreen(
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getFavoriteAnimeGhibli()
            }

            is UiState.Success -> {
                FavoriteInformation(
                    listAnime = uiState.data,
                    navigateToDetail = navigateToDetail,
                    onFavoriteIconClicked = { id, newState ->
                        viewModel.updateAnimeGhibli(id, newState)
                    }
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun FavoriteInformation(
    listAnime: List<AnimeGhibli>,
    navigateToDetail: (Int) -> Unit,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        if (listAnime.isNotEmpty()) {
            ListAnime(
                listAnime = listAnime,
                onFavoriteIconClicked = onFavoriteIconClicked,
                contentPaddingTop = 16.dp,
                navigateToDetail = navigateToDetail
            )
        } else {
            EmptyList(
                Warning = stringResource(R.string.empty_favorite)
            )
        }
    }
}