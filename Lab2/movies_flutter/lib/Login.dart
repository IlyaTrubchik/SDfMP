
import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';

import 'main.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  //final String title;

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  String _email="";
  String _password="";

  final FirebaseAuth _auth = FirebaseAuth.instance;
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
  _loginUser(String email,String password) async{
    try {
      UserCredential userCredential = await _auth.signInWithEmailAndPassword(
        email: email,
        password: password,
      );
      Navigator.pushNamed(context, '/profile');
    } on FirebaseAuthException catch (e) {
      if (e.code == 'user-not-found') {
        MyApp.showMyDialog(
            context, 'The email address is not registered. Please sign up.');
      } else if (e.code == 'wrong-password') {
        MyApp.showMyDialog(context,
            'The password you entered is incorrect. Please try again.');
      } else if (e.code == 'invalid-email') {
        MyApp.showMyDialog(context,
            'The email address you entered is not valid. Please check and try again.');
      }
      else{
        MyApp.showMyDialog(context,
            'Fill empty fields');
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar:AppBar(

      ),
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
                ElevatedButton(
                    onPressed:(){ _loginUser(_email,_password);},
                    child: const Text(
                        "Login"
                    )),
                    ElevatedButton(
                      onPressed: () {
                        Navigator.pushNamed(context, '/register');
                      },
                      child: const Text("Register"),
                    ),
              ],
            ),
          )
      )
    );
  }
}
