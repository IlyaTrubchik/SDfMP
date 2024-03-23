package com.example.movies.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movies.ui.screens.FavoritesScreen
import com.example.movies.ui.screens.LoginScreen
import com.example.movies.ui.screens.MovieScreen
import com.example.movies.ui.screens.MoviesScreen
import com.example.movies.ui.screens.RegisterScreen
import com.example.movies.ui.screens.UserScreen
import com.example.movies.ui.screens.panelOnClicks
import com.example.movies.ui.viewModels.logOut
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope

@Composable
fun MoviesNavGraph(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {
    var startDist = "movies";
    val auth: FirebaseAuth = Firebase.auth
    val user = auth.currentUser
    if(user == null)
    {
      startDist = "register";
    }
    NavHost(
        navController = navController,
        startDestination = startDist
        ){
        composable("login"){ LoginScreen { navController.navigate("userScreen") } }
        composable("userScreen"){ UserScreen (onLogOut = { logOut(); navController.navigate("register")},
            onDelete =  {navController.navigate("login")},
            panelOnClicks = panelOnClicks(
                onFavoritesClick = {navController.navigate("favorites")},
                onMovieClick = {navController.navigate("movies")},
                onProfileClick = {navController.navigate("userScreen")}
            )
        )}
        composable("register"){ RegisterScreen(onRegister = {navController.navigate("login")})}
        composable("movies"){
            MoviesScreen(
                navController = navController,
                panelOnClicks = panelOnClicks(
                    onFavoritesClick = {navController.navigate("favorites")},
                    onMovieClick = {navController.navigate("movies")},
                    onProfileClick = {navController.navigate("userScreen")}
                )
            )
        }
        composable("favorites"){
            FavoritesScreen(
                navController = navController,
                panelOnClicks = panelOnClicks(
                    onFavoritesClick = {navController.navigate("favorites")},
                    onMovieClick = {navController.navigate("movies")},
                    onProfileClick = {navController.navigate("userScreen")}
                )
            )
        }
        composable("movie/{movieId}",arguments = listOf(navArgument("movieId") { type = NavType.StringType }))
        {
                entry->
            var movieId:String? = null;
            try {
                movieId = entry.arguments?.getString("movieId");

            }catch (e:Exception)
            {
                println(e.toString())
            }
            MovieScreen(
                movieId = movieId!!,
                panelOnClicks = panelOnClicks(
                    onFavoritesClick = {navController.navigate("favorites")},
                    onMovieClick = {navController.navigate("movies")},
                    onProfileClick = {navController.navigate("userScreen")}
                )
            )
        }

    }
}