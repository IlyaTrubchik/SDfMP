package com.example.movies.ui.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.MovieRepository
import kotlinx.coroutines.launch

data class MovieUIState(
    val name:String = "",
    val description:String = "",
    val genre:String = "",
    val country:String = "",
    val images:List<ByteArray> ?= null,
)

class MovieViewModel(): ViewModel(){
    var uiState by mutableStateOf(MovieUIState())
        private set
    val repository:MovieRepository = MovieRepository()

    fun getAllImages(movieId:String){
        viewModelScope.launch{
            repository.getImagesByMovieId(movieId){images->
                uiState = uiState.copy(images = images)
            }
            repository.getMovieByMovieId(movieId){movie->
                uiState = uiState.copy(name = movie.name!!, description = movie.description!!,genre = movie.genre!!,country = movie.country!!)
            }
        }

    }

}