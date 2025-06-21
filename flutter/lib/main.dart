import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

import 'screens/login/login_screen.dart';
import 'screens/home/turmas_screen.dart';
import 'theme/app_theme.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  Future<Widget> _startApp() async {
    final prefs = await SharedPreferences.getInstance();
    final isLoggedIn = prefs.getBool('isLoggedIn') ?? false;

    // Aqui você pode também validar perfil do usuário, se quiser

    if (isLoggedIn) {
      return const TurmasScreen();
    } else {
      return const LoginScreen();
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Correção de Gabaritos',
      debugShowCheckedModeBanner: false,
      theme: AppTheme.lightTheme,
      home: FutureBuilder<Widget>(
        future: _startApp(),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.done) {
            if (snapshot.hasData) {
              return snapshot.data!;
            } else {
              // Se algo der errado, volta para LoginScreen
              return const LoginScreen();
            }
          }
          // Enquanto carrega o SharedPreferences, mostra loading
          return const Scaffold(
            body: Center(child: CircularProgressIndicator()),
          );
        },
      ),
    );
  }
}
