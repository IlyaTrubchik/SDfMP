package com.example.movies.data

data class Movie (
    var movieId: String?=null,
    val name: String?=null,
    val description:String?=null,
    val pathToImages:String?=null,
    val genre:String? = null,
    val country:String? = null,
    var isBookmarked:Boolean?=false
)
