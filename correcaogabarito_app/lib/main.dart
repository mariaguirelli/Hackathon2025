import 'package:flutter/material.dart';
import 'screens/login_screen.dart';
import 'screens/home_screen.dart';
import 'screens/turmas_screen.dart';
import 'widgets/auth_guard.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'App Correção Gabarito',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      initialRoute: '/login',
      routes: {
        '/login': (context) => const LoginScreen(),

        '/home': (context) => AuthGuard(
              child: HomeScreen(),
            ),

        '/turmas': (context) {
          final args = ModalRoute.of(context)!.settings.arguments as Map<String, dynamic>?;

          if (args == null || !args.containsKey('email') || !args.containsKey('anoLetivo')) {
            WidgetsBinding.instance.addPostFrameCallback((_) {
              Navigator.of(context).pushReplacementNamed('/login');
            });

            return const Scaffold(
              body: Center(
                child: CircularProgressIndicator(),
              ),
            );
          }

          final String email = args['email'];
          final int anoLetivo = args['anoLetivo'];

          return AuthGuard(
            child: TurmasScreen(email: email, anoLetivo: anoLetivo),
          );
        },
      },
    );
  }
}
