import 'package:flutter/material.dart';
import 'package:firebase_database/firebase_database.dart';
import 'package:firebase_auth/firebase_auth.dart';

class UserInfo{
  String name;
  String surname;
  String age;
  String sex;
  String country;
  String city;
  String address;
  String postcode;
  String email;
  String telephone;

  UserInfo({
    this.name="",
    this.surname="",
    this.age="",
    this.sex="",
    this.country="",
    this.city="",
    this.address="",
    this.postcode="",
    this.email="",
    this.telephone=""
  });
}


class ProfilePage extends StatefulWidget {
  const ProfilePage({super.key});

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".


  @override
  State<ProfilePage> createState() => _ProfilePageState();
}

class _ProfilePageState extends State<ProfilePage> {

  _logOut() async {
    FirebaseAuth auth = FirebaseAuth.instance;
    await auth.signOut();
    Navigator.pushNamed(context, '/login');
  }
  void _deleteAccount() async{
    FirebaseAuth auth = FirebaseAuth.instance;
    User? user = auth.currentUser;
    String userId="";
    if (user != null) {
      userId = user.uid;
    }
    else{
      Navigator.pushNamed(context, "/login");
    }
    DatabaseReference userRef =
    FirebaseDatabase.instance.ref('users/${userId}');
    userRef.remove();
    await auth.signOut();
    await auth.currentUser?.delete();
    Navigator.pushNamed(context, "/register");
  }
  Future<UserInfo> _fetchUserFromFirebase() async {
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
    DatabaseReference userRef =
    FirebaseDatabase.instance.ref('users/${userId}/userInfo/');
    DataSnapshot snapshot = await userRef.get();
    UserInfo userInfo = UserInfo();
    if (snapshot.value != null && snapshot.value is Map<dynamic,dynamic>) {
      Map<dynamic, dynamic> movieData = snapshot.value as Map<dynamic,dynamic>;
      userInfo = UserInfo(
          name: movieData['name'],
          surname: movieData['surname'],
        //  country: movieData['Country'],
        //  city: movieData['City'],
        //  sex: movieData['sex'],
        //  postcode: movieData['postcode'],
        //  telephone: movieData['telephone'],
          email: movieData['email'],
       //   age: movieData['age'],
       //   address: movieData['address']
      );
    };
    return userInfo;
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<UserInfo>(
        future:_fetchUserFromFirebase(),
        builder:(context,snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            // Display a loading indicator while the data is being fetched
            return CircularProgressIndicator();
          } else if (snapshot.hasError) {
            // Display an error message if there was an error in fetching the data
            return Text('Error: ${snapshot.error}');
          } else {
            UserInfo user = snapshot.data!;
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
              body:
                Column(
                  children: [
                    Container(
                      width: double.infinity,
                      margin: const EdgeInsets.all(20.0),
                      padding: const EdgeInsets.all(20.0),
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(10.0),
                        color: Colors.grey[200],
                      ),
                      child: ListView(
                        physics: BouncingScrollPhysics(),
                        shrinkWrap: true,
                        children: [
                          Text(
                            'Имя: ${user.name}',
                            style: const TextStyle(
                              fontSize: 20.0,
                              color: Colors.grey,
                            ),
                          ),
                          Text(
                            'Фамилия: ${user.surname}',
                            style: const TextStyle(
                              fontSize: 20.0,
                              color: Colors.grey,
                            ),
                          ),
                          Text(
                            'Возраст: ${user.age}',
                            style: const TextStyle(
                              fontSize: 20.0,
                              color: Colors.grey,
                            ),
                          ),
                          Text(
                            'Пол: ${user.sex}',
                            style: const TextStyle(
                              fontSize: 20.0,
                              color: Colors.grey,
                            ),
                          ),
                          Text(
                            'Страна: ${user.country}',
                            style: const TextStyle(
                              fontSize: 20.0,
                              color: Colors.grey,
                            ),
                          ),
                          Text(
                            'Город: ${user.city}',
                            style: const TextStyle(
                              fontSize: 20.0,
                              color: Colors.grey,
                            ),
                          ),
                          Text(
                            'Адрес: ${user.address}',
                            style: const TextStyle(
                              fontSize: 20.0,
                              color: Colors.grey,
                            ),
                          ),
                          Text(
                            'Почтовый индекс: ${user.postcode}',
                            style: const TextStyle(
                              fontSize: 20.0,
                              color: Colors.grey,
                            ),
                          ),
                          Text(
                            'Email: ${user.email}',
                            style: const TextStyle(
                              fontSize: 20.0,
                              color: Colors.grey,
                            ),
                          ),
                          Text(
                            'Телефон: ${user.telephone}',
                            style: const TextStyle(
                              fontSize: 20.0,
                              color: Colors.grey,
                            ),
                          ),
                        ],
                      ),
                    ),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        ElevatedButton(
                          onPressed: () {
                            _deleteAccount();
                          },
                          child: const Text("Delete account"),
                        ),
                        ElevatedButton(
                          onPressed: () {
                            _logOut();
                          },
                          child: const Text("Log out"),
                        ),
                      ],
                    ),
                  ],
                ),
              );
          }
        }
      );
    }
  }
