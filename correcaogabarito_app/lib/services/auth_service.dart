import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import 'package:flutter/material.dart'; // Para widgets, ScaffoldMessenger, Navigator, etc
import '../services/auth_service.dart';
import 'package:jwt_decode/jwt_decode.dart';


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
      await prefs.setString('userName', data['nome']);
      return null; // Login OK
    } else {
      // Lê o corpo JSON retornado pela API (ex: {"code": "NOT_PROFESSOR", "message": "Você não tem permissão."})
      final data = jsonDecode(response.body);
      return data['message'] ?? 'Erro desconhecido';
    }
  }

  Future<bool> isLoggedIn() async {
    final prefs = await SharedPreferences.getInstance();
    final token = prefs.getString('userToken');

    if (token == null || Jwt.isExpired(token)) {
      await logout(); // remove o token expirado
      return false;
    }

    return true;
  }

  Future<void> logout() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove('userToken');
    await prefs.remove('email');
  }

}

