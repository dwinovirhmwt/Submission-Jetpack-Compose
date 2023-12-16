package com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.R
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.di.Injection
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.ui.common.UiState
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.viewmodel.DetailViewModel
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.viewmodel.ViewModelFactory


@Composable
fun DetailScreen(
    animeGhibliId: Int,
    navigateBack: () -> Unit,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAnimeGhibliById(animeGhibliId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailInformation(
                    id = data.id,
                    title = data.title,
                    poster = data.poster,
                    year = data.year,
                    genre = data.genre,
                    producer = data.producer,
                    score = data.score,
                    synopsis = data.synopsis,
                    isFavorite = data.isFavorite,
                    navigateBack = navigateBack,
                    onFavoriteButtonClicked = { id, state ->
                        viewModel.updateAnimeGhibli(id, state)
                    }
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailInformation(
    id: Int,
    title: String,
    @DrawableRes poster: Int,
    year: String,
    genre: String,
    producer: String,
    score: String,
    synopsis: String,
    isFavorite: Boolean,
    navigateBack: () -> Unit,
    onFavoriteButtonClicked: (id: Int, state: Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            Image(
                painter = painterResource(poster),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("scrollToBottom")
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                    )
                    Text(
                        text = year,
                        modifier = Modifier
                            .padding(start = 2.dp, end = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                    )
                    Text(
                        text = genre,
                        modifier = Modifier
                            .padding(start = 2.dp, end = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                    )
                    Text(
                        text = producer,
                        overflow = TextOverflow.Visible,
                        modifier = Modifier
                            .padding(start = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                    )
                    Text(
                        text = score,
                        overflow = TextOverflow.Visible,
                        modifier = Modifier
                            .padding(start = 4.dp)
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp))
            Text(
                text = synopsis,
                fontSize = 16.sp,
                lineHeight = 28.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        IconButton(
            onClick = navigateBack,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .align(Alignment.TopStart)
                .clip(CircleShape)
                .size(40.dp)
                .testTag("back_home")
                .background(Color.White)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back),
            )
        }
        IconButton(
            onClick = {
                onFavoriteButtonClicked(id, isFavorite)
            },
            modifier = Modifier
                .padding(end = 16.dp, top = 16.dp)
                .align(Alignment.TopEnd)
                .clip(CircleShape)
                .size(40.dp)
                .background(Color.White)
                .testTag("favorite_detail_button")
        ) {
            Icon(
                imageVector = if (!isFavorite) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                contentDescription = if (!isFavorite) stringResource(R.string.add_favorite) else stringResource(
                    R.string.delete_favorite
                ),
                tint = if (!isFavorite) Color.Black else Color.Red
            )
        }
    }
}