import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_database/firebase_database.dart';
import 'Profile.dart' as profile;
import 'main.dart';
class RegisterPage extends StatefulWidget {
  const RegisterPage({super.key});

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  @override
  State<RegisterPage> createState() => _RegisterPageState();
}

class _RegisterPageState extends State<RegisterPage> {

  String _email="";
  String _password="";
  profile.UserInfo _userInfo = profile.UserInfo();

  _changeEmail(String text)
  {
    setState(() {
      _email = text;
    });
  }

  _changePassword(String password)
  {
    setState(() {
      _password = password;
    });
  }
  _changeSurname(String text)
  {
    setState(() {
       _userInfo.surname = text;
    });
  }
  _changeName(String text)
  {
    setState(() {
       _userInfo.name = text;
    });
  }

  final FirebaseAuth _auth = FirebaseAuth.instance;

  _registerUser(String email, String password) async {
    try {
      UserCredential userCredential = await _auth
          .createUserWithEmailAndPassword(
        email: email,
        password: password,
      );
      String? userId = userCredential.user?.uid;
      DatabaseReference userRef =
      FirebaseDatabase.instance.ref('users/${userId}/userInfo/');
      Map<String, dynamic> user = {
        'name': _userInfo.name,
        'surname': _userInfo.surname,
        'email': _email,
      };
      userRef.set(user).then((_) {
        print('Данные успешно добавлены');
      }).catchError((error) {
        print('Ошибка при добавлении данных: $error');
      });

      Navigator.pushNamed(context, '/login');
    } on FirebaseAuthException catch (e) {
      if (e.code == 'weak-password') {
        MyApp.showMyDialog(context,
            'Your password is too weak. Please choose a stronger one.');
      } else if (e.code == 'email-already-in-use') {
        MyApp.showMyDialog(context,
            'An account with this email already exists. Please log in or use a different email.');
      } else if (e.code == 'invalid-email') {
        MyApp.showMyDialog(context,
            'The email address you entered is not valid. Please check and try again.');
      }  else{
        MyApp.showMyDialog(context,
            'Fill empty fields');
      }
    } catch (e) {
      MyApp.showMyDialog(
          context, 'An unexpected error occurred. Please try again later.');
    }


  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar:AppBar(),
        body:Container(
            child:Center(
              child:  Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[

                  Padding(
                      padding: const EdgeInsets.only(top:10,bottom: 10),
                      child:TextField(
                        decoration: const InputDecoration(
                            border: OutlineInputBorder(),
                            hintText: "Введите email"
                        ),
                        onChanged: _changeEmail,
                      )
                  ),

                  Padding(
                    padding: const EdgeInsets.only(top:10,bottom: 10),
                    child: TextField(
                      decoration: const InputDecoration(
                        border: OutlineInputBorder(),
                        hintText: "Введите пароль",
                      ),
                      onChanged: _changePassword,
                    ),
                  ),
                  Padding(
                      padding: const EdgeInsets.only(top:10,bottom: 10),
                      child:TextField(
                        decoration: const InputDecoration(
                            border: OutlineInputBorder(),
                            hintText: "Введите имя"
                        ),
                        onChanged: _changeName,
                      )
                  ),
                  Padding(
                      padding: const EdgeInsets.only(top:10,bottom: 10),
                      child:TextField(
                        decoration: const InputDecoration(
                            border: OutlineInputBorder(),
                            hintText: "Введите фамилию"
                        ),
                        onChanged: _changeSurname,
                      )
                  ),
                  ElevatedButton(
                      onPressed:() {_registerUser(_email,_password);},
                      child: const Text(
                        "Register"
                      )),
                  ElevatedButton(
                    onPressed: () {
                      Navigator.pushNamed(context, '/login');
                    },
                    child: const Text("Login"),
                  ),
                ],
              ),
            )
        )
    );
  }
}
