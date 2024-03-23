package com.example.movies.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movies.ui.viewModels.MovieListItem
import com.example.movies.ui.viewModels.MoviesViewModel

@Composable
fun FavoritesScreen(
    panelOnClicks: panelOnClicks,
    moviesViewModel: MoviesViewModel = viewModel(),
    navController: NavController,
){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        Column(){
            FavoriteMoviesList(
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
fun FavoriteMoviesList(modifier:Modifier,movies:List<MovieListItem>?, navController: NavController) {
    LazyColumn (modifier =  modifier){
        if(movies!=null) {
            items(movies) { movie ->
                if (movie.bookmarked == true) {
                    MovieView(movie = movie) { currMovie ->
                        navigateToMovie(currMovie.movieId, navController)
                    }
                }
            }
        }
    }
}
