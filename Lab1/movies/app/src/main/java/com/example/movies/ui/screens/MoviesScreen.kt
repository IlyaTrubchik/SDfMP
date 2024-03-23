package com.example.movies.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movies.ui.viewModels.MovieListItem
import com.example.movies.ui.viewModels.MoviesViewModel
import kotlinx.coroutines.flow.filter


@Composable
fun MoviesScreen(
    panelOnClicks: panelOnClicks,
    moviesViewModel:MoviesViewModel = viewModel(),
    navController: NavController,
){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){

            Column() {
                MovieList(
                    movies = moviesViewModel.uiState.moviesList,
                    navController = navController,
                    modifier = Modifier.weight(0.9f)
                )
                NavigationPanel(
                    panelOnClicks = panelOnClicks,
                    modifier = Modifier
                        .background(Color.LightGray)
                        .fillMaxWidth()
                        .weight(0.1f)

                )
            }
    }
}
@Composable
fun MovieList(modifier: Modifier,movies:List<MovieListItem>?,navController: NavController) {
    LazyColumn(modifier = modifier) {
        if(movies!=null) {
            items(movies) { movie ->
                MovieView(movie = movie) { currMovie ->
                    navigateToMovie(currMovie.movieId,navController)
                }

            }
        }

    }
}

@Composable
fun MovieView(movie:MovieListItem,onItemClick: (MovieListItem) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .clickable { onItemClick(movie) }

    ) {
        Divider(
            modifier = Modifier
                .padding(start = 0.dp, end = 0.dp)
                .fillMaxWidth()
                .align(CenterHorizontally),
            color = Color.LightGray,
            thickness = 2.dp
        )
        Row {
            val bookmarkState = remember{ mutableStateOf(movie.bookmarked) };
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp)
                    .weight(0.8f)
            ) {
                Text(
                    text = movie.name.toString(),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 4.dp)
                )
                Text(
                    text = "Страна:"+movie.country.toString(),
                    fontSize = 20.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 4.dp)
                )
                Text(
                    text = "Жанр:"+movie.genre.toString(),
                    fontSize = 20.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 4.dp)
                )
            }
            IconButton(
                modifier = Modifier.weight(0.2f),
                onClick = {
                    movie.bookmarked= movie.bookmarked?.not()
                    movie.onBookmark(movie.bookmarked!!);
                    bookmarkState.value = movie.bookmarked;
                }
            ) {
                Icon(
                    imageVector = if (bookmarkState.value == true) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Добавить в избранное"
                )
            }
        }
        Divider(
            modifier = Modifier
                .padding(start = 0.dp, end = 0.dp)
                .fillMaxWidth()
                .align(CenterHorizontally),
            color = Color.LightGray,
            thickness = 2.dp
        )
    }
}
fun navigateToMovie(movieId:String?,navController: NavController)
{
    if(movieId!=null)
    {
        navController.navigate("movie/${movieId}")
    }
}

