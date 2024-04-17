import 'package:flutter/material.dart';
import 'package:firebase_database/firebase_database.dart';
import 'package:firebase_auth/firebase_auth.dart';

import 'Movies.dart';

class FavoritesPage extends StatefulWidget {
  const FavoritesPage({super.key});

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".


  @override
  State<FavoritesPage> createState() => _FavoritesPageState();
}

class _FavoritesPageState extends State<FavoritesPage> {


  Future<List<MovieListItem>> fetchMoviesFromFirebase() async {
    DatabaseReference moviesRef =
    FirebaseDatabase.instance.ref('movies/');
    FirebaseAuth auth = FirebaseAuth.instance;
    User? user = auth.currentUser;
    String userId="";
    if (user != null) {
      userId = user.uid;
    }
    else{
      Navigator.pushNamed(context, "/login");
    }
    //userId = "GRbuBKIUO9gpUVlRIJAfYnCr86f1";
    DatabaseReference refToFavorites =
    FirebaseDatabase.instance.ref('users/${userId}/favorites/');

    DataSnapshot snapshot = await moviesRef.get();
    DataSnapshot favSnapshot = await refToFavorites.get();
    List<MovieListItem> movies = [];
    if (snapshot.value != null && snapshot.value is Map<dynamic, dynamic>) {
      Map<dynamic, dynamic> data = snapshot.value as Map<dynamic, dynamic>;
      Map<dynamic, dynamic> favoritesData =  new Map();
      if (favSnapshot.value != null) {
        favoritesData = favSnapshot.value as Map<
            dynamic,
            dynamic>;
      }
      data.forEach((key, value) {
        MovieListItem movie = MovieListItem(
          id: key,
          name: value['name'],
          country: value['country'],
          genre: value['genre'],
          description: value['description'],
          bookmarked: false,
          // Другие поля фильма
        );
        if(favoritesData.containsKey(key))
          {
            movie.bookmarked = true;
            movies.add(movie);
          }
      });

    }

    return movies;
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<List<MovieListItem>>(
        future: fetchMoviesFromFirebase(),
        builder:(context,snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            // Display a loading indicator while the data is being fetched
            return CircularProgressIndicator();
          } else if (snapshot.hasError) {
            // Display an error message if there was an error in fetching the data
            return Text('Error: ${snapshot.error}');
          } else {
            List<MovieListItem> movies = snapshot.data!;
            return Scaffold(
              appBar: AppBar(
                actions: [
                  TextButton(
                    onPressed: () {
                      Navigator.pushNamed(context,'/profile');
                    },
                    child: Text('Профиль'),
                  ),
                  TextButton(
                    onPressed: () {
                      Navigator.pushNamed(context,'/movies');
                    },
                    child: Text('Фильмы'),
                  ),
                  TextButton(
                    onPressed: () {
                      Navigator.pushNamed(context,'/favorites');
                    },
                    child: Text('Любимое'),
                  ),
                ],
              ),
              body: Column(
                children: [
                  Expanded(
                    child: ListView.builder(
                      itemCount: movies.toList()
                          .length,
                      itemBuilder: (context, index) {
                        var movie = movies.toList()[index];
                        return
                          GestureDetector(
                              onTap:(){Navigator.pushNamed(context, '/movie', arguments: {'id': movie.id});} ,
                              child:
                              MovieView(
                                movie: movie,
                              )
                          );
                      },
                    ),
                  ),
                ],
              ),
            );
          }
        }
    );
  }
}