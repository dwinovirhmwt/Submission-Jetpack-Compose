package com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.ui.screen


import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.R
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.di.Injection
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.model.AnimeGhibli
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.ui.common.UiState
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.ui.items.AnimeGhibliItem
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.ui.items.EmptyList
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.ui.items.SearchButton
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.viewmodel.HomeViewModel
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.viewmodel.ViewModelFactory

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Int) -> Unit,
) {
    val query by viewModel.query
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.search(query)
            }

            is UiState.Success -> {
                HomeContent(
                    query = query,
                    onQueryChange = viewModel::search,
                    listAnime = uiState.data,
                    onFavoriteIconClicked = { id, newState ->
                        viewModel.updateAnimeGhibli(id, newState)
                    },
                    navigateToDetail = navigateToDetail
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    query: String,
    onQueryChange: (String) -> Unit,
    listAnime: List<AnimeGhibli>,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    navigateToDetail: (Int) -> Unit,
) {
    Column {
        SearchButton(
            query = query,
            onQueryChange = onQueryChange
        )
        if (listAnime.isNotEmpty()) {
            ListAnime(
                listAnime = listAnime,
                onFavoriteIconClicked = onFavoriteIconClicked,
                navigateToDetail = navigateToDetail
            )
        } else {
            EmptyList(
                Warning = stringResource(R.string.empty_data),
                modifier = Modifier
                    .testTag("emptyList")
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListAnime(
    listAnime: List<AnimeGhibli>,
    onFavoriteIconClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
    contentPaddingTop: Dp = 0.dp,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp,
            top = contentPaddingTop
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .testTag("lazy_list")
    ) {
        items(listAnime, key = { it.id }) { item ->
            AnimeGhibliItem(
                id = item.id,
                title = item.title,
                poster = item.poster,
                year = item.year,
                genre = item.genre,
                producer = item.producer,
                score = item.score,
                synopsis = item.synopsis,
                isFavorite = item.isFavorite,
                onFavoriteIconClicked = onFavoriteIconClicked,
                modifier = Modifier
                    .animateItemPlacement(tween(durationMillis = 300))
                    .clickable { navigateToDetail(item.id) }
            )
        }
    }
}
