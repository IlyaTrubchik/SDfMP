package com.example.movies.ui.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.MovieRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class MovieListItem(
    val movieId:String?,
    val name:String?,
    val country:String?,
    val genre:String?,
    var bookmarked: Boolean? = false,
    val onBookmark:(isBookMarked:Boolean)->Unit,

    )
 data class MoviesListState(
    val isLoading: Boolean = false,
    val moviesList: List<MovieListItem> ?= null,
    val isDataLoaded:Boolean = false
)


class MoviesViewModel: ViewModel() {
    var uiState by mutableStateOf(MoviesListState())
        private set
    private val moviesRepository  = MovieRepository()
    private  var auth: FirebaseAuth = Firebase.auth
    private val user = auth.currentUser
    private val userId = user?.uid


    init{
        loadMovies()
    }
    fun loadMovies(){
        var moviesList:List<MovieListItem>
        viewModelScope.launch {

                moviesRepository.getAllMoviesWithFavorites(userId.toString()) { movies ->
                    uiState = uiState.copy(
                        isLoading = false,
                        moviesList = movies.map { movie ->
                            MovieListItem(
                                movieId = movie.movieId,
                                country = movie.country,
                                genre = movie.genre,
                                name = movie.name,
                                bookmarked = movie.isBookmarked,
                                onBookmark = {isBookmarked ->  moviesRepository.bookMarkMovie(userId!!,movie.movieId,isBookmarked)}
                            )
                        },
                        isDataLoaded = true
                    )
                }
        }
    }
}

