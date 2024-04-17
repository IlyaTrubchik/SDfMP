import 'package:flutter/material.dart';
import 'package:firebase_database/firebase_database.dart';
import 'package:firebase_auth/firebase_auth.dart';


class MovieListItem {
  String id;
  String name;
  String country;
  String genre;
  String description;
  bool bookmarked;

  MovieListItem({
    required this.id,
    required this.name,
    required this.country,
    required this.genre,
    required this.description,
    this.bookmarked = false,
  });
}

class MoviesPage extends StatefulWidget {
  @override
  _MoviesPageState createState() => _MoviesPageState();
}

class _MoviesPageState extends State<MoviesPage> {

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
        if(favoritesData.containsKey(key)) movie.bookmarked = true;
        movies.add(movie);
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






class MovieView extends StatefulWidget {
  final MovieListItem movie;
  MovieView({required this.movie});

  @override
  _MovieViewState createState() => _MovieViewState();
}
class _MovieViewState extends State<MovieView> {
  FirebaseAuth auth = FirebaseAuth.instance;
  _onBookmark(String movieId,bool bookmarked){
    User? user = auth.currentUser;
    String userId="";
    if (user != null) {
      userId = user.uid;
    }
    else{
      Navigator.pushNamed(context, "/login");
    }
    //userId = "GRbuBKIUO9gpUVlRIJAfYnCr86f1";
    DatabaseReference favRef =
    FirebaseDatabase.instance.ref('users/${userId}/favorites/');
    if(!bookmarked) {
      favRef.child(movieId).remove();
    }
    else
    {
      favRef.update({
        movieId: true,
      }).then((_) {
        print('Data added to Firebase');
      }).catchError((error) {
        print('Error adding data to Firebase: $error');
      });
    }
  }


@override
Widget build(BuildContext context) {
  return Card(
    child: Column(
      children: [
        const Divider(
          color: Colors.grey,
          thickness: 0.5,
        ),
        Row(
          children: [
            Expanded(
              flex: 8,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 4.0),
                    child: Text(
                      widget.movie.name,
                      style: const TextStyle(
                        fontSize: 25.0,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 4.0),
                    child: Text(
                      'Страна: ${widget.movie.country}',
                      style: const TextStyle(
                        fontSize: 20.0,
                        color: Colors.grey,
                      ),
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 4.0),
                    child: Text(
                      'Жанр: ${widget.movie.genre}',
                      style: const TextStyle(
                        fontSize: 20.0,
                        color: Colors.grey,
                      ),
                    ),
                  ),
                ],
              ),
            ),
            Expanded(
              flex: 2,
              child: IconButton(
                icon: Icon(
                  widget.movie.bookmarked ? Icons.favorite : Icons.favorite_border,
                ),
                onPressed: () {
                  setState((){
                    widget.movie.bookmarked = !widget.movie.bookmarked;
                    _onBookmark(widget.movie.id,widget.movie.bookmarked);
                  });
                },
              ),
            )
          ],
        ),
        const Divider(
          color: Colors.grey,
          thickness: 0.5,
        ),
      ],
    ),
  );
}
}
