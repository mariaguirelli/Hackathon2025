import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import 'package:flutter/material.dart'; // Para widgets, ScaffoldMessenger, Navigator, etc
import '../services/auth_service.dart'; // Seu serviço de autenticação (ajuste o caminho conforme seu projeto)


class AuthService {
  Future<String?> login(String email, String senha) async {
    final response = await http.post(
      Uri.parse('http://localhost:8080/api/login'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'email': email, 'senha': senha}),
    );

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      final prefs = await SharedPreferences.getInstance();
      await prefs.setString('userToken', data['token']);
      await prefs.setString('email', email);
      return null; // Login OK
    } else {
      // Lê o corpo JSON retornado pela API (ex: {"code": "NOT_PROFESSOR", "message": "Você não tem permissão."})
      final data = jsonDecode(response.body);
      return data['message'] ?? 'Erro desconhecido';
    }
  }

   Future<bool> isLoggedIn() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.containsKey('userToken');
  }

  Future<void> logout() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove('userToken');
    await prefs.remove('email');
  }

}

