import 'dart:typed_data';
import 'package:flutter/material.dart';
import 'package:firebase_database/firebase_database.dart';
import 'package:firebase_storage/firebase_storage.dart';
import 'package:flutter/material.dart';
class Movie {
  String name;
  String country;
  String genre;
  String description;
  bool bookmarked;

  Movie({
    this.name = "",
    this.country = "",
    this.genre = "",
    this.description = "",
    this.bookmarked = false,
  });
}

class MoviePage extends StatefulWidget {
  String movieId = "";

  @override
  _MoviePageState createState() => _MoviePageState();
}

class _MoviePageState extends State<MoviePage> {

  Future<Movie> _fetchMovieFromFirebase(String id) async{
    DatabaseReference moviesRef =
    FirebaseDatabase.instance.ref('movies/${id}/');
    DataSnapshot snapshot = await moviesRef.get();
    Movie movie = Movie();
    if (snapshot.value != null && snapshot.value is Map<dynamic,dynamic>) {
      Map<dynamic, dynamic> movieData = snapshot.value as Map<dynamic,dynamic>;
      movie = Movie(
        name: movieData['name'],
        description: movieData['description'],
        genre: movieData['genre'],
        country: movieData['country']
      );
    };
    return movie;
  }

  @override
  Widget build(BuildContext context) {
    final Map<String, dynamic>? arguments = ModalRoute.of(context)?.settings.arguments as Map<String, dynamic>?;
    widget.movieId = arguments?['id'] as String;
    return FutureBuilder<Movie>(
          future: _fetchMovieFromFirebase(widget.movieId),
          builder:(context,snapshot) {
            if (snapshot.connectionState == ConnectionState.waiting) {
              // Display a loading indicator while the data is being fetched
              return CircularProgressIndicator();
            } else if (snapshot.hasError) {
              // Display an error message if there was an error in fetching the data
              return Text('Error: ${snapshot.error}');
            } else {
              Movie movie = snapshot.data!;
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
                body: Container(
                  child: Column(
                    children: [
                      Expanded(
                        child: ListView(
                          physics: BouncingScrollPhysics(),
                          children: [
                            MovieImageSlider(
                              movieId: widget.movieId,
                            ),
                            Text(
                              'Название: ${movie.name}',
                              style: const TextStyle(
                                fontSize: 20.0,
                                color: Colors.grey,
                              ),
                            ),
                            Text(
                              'Страна: ${movie.country}',
                              style: const TextStyle(
                                fontSize: 20.0,
                                color: Colors.grey,
                              ),
                            ),
                            Text(
                              'Жанр: ${movie.genre}',
                              style: const TextStyle(
                                fontSize: 20.0,
                                color: Colors.grey,
                              ),
                            ),
                            Text(
                              'Описание: ${movie.description}',
                              style: const TextStyle(
                                fontSize: 20.0,
                                color: Colors.grey,
                              ),
                            ),
                          ],
                        ),
                      ),
                    ],
                  ),
                ),
              );
            }
          }
      );
    }
}

class MovieImageSlider extends StatefulWidget{
  const MovieImageSlider({super.key,required this.movieId});

  final String movieId;

  @override
  _MovieImageSliderState createState() => _MovieImageSliderState();
}

class _MovieImageSliderState extends State<MovieImageSlider> {
  Future<List<Uint8List>>? imageList;
  final FirebaseStorage storage = FirebaseStorage.instanceFor(bucket:"gs://moviesapplication-973c9.appspot.com");

  Future<List<Uint8List>> _fetchMovieImagesFromFirebase(String id) async
  {
    List<Uint8List> imageList = [];

    // Replace 'gs://your-bucket-name/images/' with the path to your images in Firebase Storage
    String storagePath = '/movies/${id}/';
    Reference storageRef = storage.ref(storagePath);

    // List the files in the specified storage path
    ListResult result = await storageRef.listAll();

    // Iterate through the files and download them
    for (Reference ref in result.items) {
      // Download the image data as bytes
      final data = await ref.getData();
      if(data!=null) {
        // Add the image data to the list
        imageList.add(data);
      }
    }
    return imageList;
  }

  @override
  Widget build(BuildContext context) {
      return FutureBuilder<List<Uint8List>>(
          future: _fetchMovieImagesFromFirebase(widget.movieId),
          builder:(context,snapshot) {
            if (snapshot.connectionState == ConnectionState.waiting) {
              // Display a loading indicator while the data is being fetched
              return CircularProgressIndicator();
            } else if (snapshot.hasError) {
              // Display an error message if there was an error in fetching the data
              return Text('Error: ${snapshot.error}');
            } else {
              List<Uint8List> imageList = snapshot.data!;
              return SingleChildScrollView(
                    scrollDirection: Axis.horizontal,
                    child: Row(
                      children: [
                        for (var imageBytes in imageList)
                        Container(
                          margin: const EdgeInsets.all(8.0), // Расстояние между изображениями
                          decoration: BoxDecoration(
                            borderRadius: BorderRadius.circular(10.0), // Закругленные границы
                          ),
                          child: Image.memory(
                            imageBytes,
                            fit: BoxFit.fill,
                            width: 400,
                            height: 400,
                            gaplessPlayback: true,
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
