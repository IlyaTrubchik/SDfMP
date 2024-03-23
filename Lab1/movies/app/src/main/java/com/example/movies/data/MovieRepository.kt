package com.example.movies.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.concurrent.atomic.AtomicInteger


class MovieRepository {
    private val database = FirebaseDatabase.getInstance("https://moviesapplication-973c9-default-rtdb.europe-west1.firebasedatabase.app/")
    private val referenceToMovies = database.getReference("movies/")


    fun getAllMoviesWithFavorites(userId: String,callback:(List<Movie>)->Unit){
               val referenceToFavoriteMovies =
                   database.getReference("users").child(userId).child("favorites")
               referenceToMovies.addListenerForSingleValueEvent(object : ValueEventListener {
                   override fun onDataChange(snapshot: DataSnapshot) {
                       val movies: MutableList<Movie> = mutableListOf()
                       for (childSnapshot in snapshot.children) {
                           val movie = childSnapshot.getValue(Movie::class.java)
                           movie?.movieId = childSnapshot.key
                           referenceToFavoriteMovies.child(movie?.movieId.toString()).get()
                               .addOnCompleteListener { task ->
                                   if (task.isSuccessful) {
                                       val taskSnapshot = task.result
                                       if (taskSnapshot != null && taskSnapshot.exists()) {
                                           movie?.isBookmarked = true
                                       } else {
                                           movie?.isBookmarked = false
                                       }
                                       if (movie != null) {
                                           movies.add(movie)
                                           if(movies.size == snapshot.children.count())
                                           {
                                               callback(movies)
                                           }
                                       }

                                   } else {
                                       val error = 0   // Обработка ошибки при чтении данных
                                   }
                               }
                       }

                   }

                   override fun onCancelled(error: DatabaseError) {
                       throw error.toException()
                   }
               })

       }

       fun getMovieByMovieId(movieId:String?,callback: (Movie) -> Unit)
       {
           val referenceToMovie = database.getReference("movies/${movieId}")
           referenceToMovie.get().addOnSuccessListener {
               task->
               val movie = task.getValue(Movie::class.java)
               if(movie!=null) {
                   callback(movie)
               }
           }
       }

       fun bookMarkMovie(userId: String, movieId: String?, isBookmarked: Boolean?) {
           if (movieId != null) {
               if (isBookmarked == true) {
                   val referenceToFavoriteMovies =
                       database.getReference("users").child(userId).child("favorites")
                   referenceToFavoriteMovies.child(movieId).setValue(true)
               } else {
                   val referenceToFavoriteMovies =
                       database.getReference("users").child(userId).child("favorites")
                   referenceToFavoriteMovies.child(movieId).removeValue();
               }
           }
       }

   fun getImagesByMovieId(movieId: String?,callback: (List<ByteArray>) -> Unit) {
        val storage = Firebase.storage
        val storageReference = storage.getReference("movies/${movieId}")
        val images:MutableList<ByteArray> = mutableListOf<ByteArray>()
        var referencesList:List<StorageReference> = emptyList();
        val maxDownloadSizeBytes: Long = 1024 * 1024;
        storageReference.listAll().addOnSuccessListener { task ->
              referencesList = task.items;
              for(reference in referencesList)
              {
                  try {
                      reference.getBytes(maxDownloadSizeBytes).addOnCompleteListener{task->
                          images.add(task.result)
                          if(images.size == referencesList.size) {
                              callback(images)
                          }
                      }
                  }catch(e:Exception)
                  {
                      println(e.toString());
                  }
              }

            }
        }

}

