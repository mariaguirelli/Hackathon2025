import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

import 'package:correcao_gabaritos/models/usuario.dart';
import 'package:correcao_gabaritos/services/api_service.dart';
import 'package:correcao_gabaritos/screens/home/turmas_screen.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final _emailController = TextEditingController();
  final _senhaController = TextEditingController();

  Future<void> _login() async {
    String email = _emailController.text.trim();
    String senha = _senhaController.text.trim();

    final Usuario? usuario = await ApiService.login(email, senha);

    if (usuario != null && usuario.perfil == 'PROFESSOR') {
      final prefs = await SharedPreferences.getInstance();
      await prefs.setBool('isLoggedIn', true);
      await prefs.setString('professor_id', usuario.id.toString());
      await prefs.setString('nome', usuario.nome);
      await prefs.setString('userEmail', usuario.email);

      if (!mounted) return;
      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (_) => const TurmasScreen()),
      );
    } else {
      if (!mounted) return;
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Apenas professores podem acessar')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Login')),
      body: Padding(
        padding: const EdgeInsets.all(20),
        child: Column(
          children: [
            TextField(
              controller: _emailController,
              decoration: const InputDecoration(labelText: 'Email'),
            ),
            TextField(
              controller: _senhaController,
              obscureText: true,
              decoration: const InputDecoration(labelText: 'Senha'),
            ),
            const SizedBox(height: 20),
            ElevatedButton(onPressed: _login, child: const Text('Entrar')),
          ],
        ),
      ),
    );
  }
}
