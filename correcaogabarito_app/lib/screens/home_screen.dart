import 'package:flutter/material.dart';
import '../widgets/breadcrumb.dart';
import '../services/auth_service.dart';

class HomeScreen extends StatelessWidget {
  HomeScreen({super.key});

  final AuthService _authService = AuthService();

  Future<void> _logout(BuildContext context) async {
    await _authService.logout();

    // Mostra mensagem para o usuário
    ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(content: Text('Logout realizado, token removido')),
    );

    // Navega para login removendo histórico de rotas anteriores
    Navigator.pushNamedAndRemoveUntil(context, '/login', (route) => false);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Home'),
        actions: [
          IconButton(
            icon: const Icon(Icons.logout),
            tooltip: 'Sair',
            onPressed: () => _logout(context),
          ),
        ],
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // seu breadcrumb e conteúdo
            const SizedBox(height: 24),
            const Center(
              child: Text('Bem-vindo ao aplicativo!'),
            ),
          ],
        ),
      ),
    );
  }
}
