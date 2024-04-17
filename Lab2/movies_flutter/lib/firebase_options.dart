// File generated by FlutterFire CLI.
// ignore_for_file: type=lint
import 'package:firebase_core/firebase_core.dart' show FirebaseOptions;
import 'package:flutter/foundation.dart'
    show defaultTargetPlatform, kIsWeb, TargetPlatform;
import 'package:firebase_core/firebase_core.dart';
import 'firebase_options.dart';

/// Default [FirebaseOptions] for use with your Firebase apps.
///
/// Example:
/// ```dart
/// import 'firebase_options.dart';
/// // ...
/// await Firebase.initializeApp(
///   options: DefaultFirebaseOptions.currentPlatform,
/// );
/// ```
class DefaultFirebaseOptions {
  static FirebaseOptions get currentPlatform {

    if (kIsWeb) {
      throw UnsupportedError(
        'DefaultFirebaseOptions have not been configured for web - '
        'you can reconfigure this by running the FlutterFire CLI again.',
      );
    }
    switch (defaultTargetPlatform) {
      case TargetPlatform.android:
        return android;
      case TargetPlatform.iOS:
        return ios;
      case TargetPlatform.macOS:
        throw UnsupportedError(
          'DefaultFirebaseOptions have not been configured for macos - '
          'you can reconfigure this by running the FlutterFire CLI again.',
        );
      case TargetPlatform.windows:
        throw UnsupportedError(
          'DefaultFirebaseOptions have not been configured for windows - '
          'you can reconfigure this by running the FlutterFire CLI again.',
        );
      case TargetPlatform.linux:
        throw UnsupportedError(
          'DefaultFirebaseOptions have not been configured for linux - '
          'you can reconfigure this by running the FlutterFire CLI again.',
        );
      default:
        throw UnsupportedError(
          'DefaultFirebaseOptions are not supported for this platform.',
        );
    }
  }

  static const FirebaseOptions android = FirebaseOptions(
    apiKey: 'AIzaSyBK1nnUseTYuzGoNpM02hWtKvRMOMwQ6B8',
    appId: '1:613617637520:android:aaebba23d52f623bdde015',
    messagingSenderId: '613617637520',
    projectId: 'moviesapplication-973c9',
    databaseURL: 'https://moviesapplication-973c9-default-rtdb.europe-west1.firebasedatabase.app',
    storageBucket: 'moviesapplication-973c9.appspot.com',
  );

  static const FirebaseOptions ios = FirebaseOptions(
    apiKey: 'AIzaSyBqIrNJi46J1haDQV_XV_uoiwxxpv2Jelc',
    appId: '1:613617637520:ios:878f031fb2a9868adde015',
    messagingSenderId: '613617637520',
    projectId: 'moviesapplication-973c9',
    databaseURL: 'https://moviesapplication-973c9-default-rtdb.europe-west1.firebasedatabase.app',
    storageBucket: 'moviesapplication-973c9.appspot.com',
    iosBundleId: 'by.bsuir.ilya.moviesFlutter',
  );
}

// ...

