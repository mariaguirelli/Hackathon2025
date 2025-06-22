import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

class AuthService {
  // Mock para testar login
  Future<bool> login(String username, String password) async {
    await Future.delayed(const Duration(seconds: 2));
    if (username == 'professor' && password == '1234') {
      // Simula sucesso
      final prefs = await SharedPreferences.getInstance();
      await prefs.setString('userToken', 'token_mock_123');
      await prefs.setString('username', username);
      return true;
    }
    return false;
  }

  // Login real depois que o backend estiver pronto
  /*
  Future<bool> login(String username, String password) async {
    final response = await http.post(
      Uri.parse('http://SEU_BACKEND/api/login'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'username': username, 'senha': password}),
    );

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      final prefs = await SharedPreferences.getInstance();
      await prefs.setString('userToken', data['token']);
      await prefs.setString('username', username);
      return true;
    } else {
      return false;
    }
  }
  */

  // Verificar se usuário está logado
  Future<bool> isLoggedIn() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.containsKey('userToken');
  }

  // Logout (apagar dados)
  Future<void> logout() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove('userToken');
    await prefs.remove('username');
  }
}
