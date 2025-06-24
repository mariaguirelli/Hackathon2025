import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import '../services/auth_service.dart';
import '../models/turmas_params.dart';
import './turmas_screen.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  final AuthService _authService = AuthService();

  String? _userName = '';
  String? _userEmail = '';
  int? _selectedYear;
  late List<int> _years;
  bool _isLoading = false;

  @override
  void initState() {
    super.initState();
    _loadUserInfo();
    _generateYearList();
  }

  Future<void> _loadUserInfo() async {
    final prefs = await SharedPreferences.getInstance();
    setState(() {
      _userName = prefs.getString('userName') ?? 'Usuário';
      _userEmail = prefs.getString('email') ?? '';
    });
  }

  void _generateYearList() {
    final currentYear = DateTime.now().year;
    _years = List.generate(30, (index) => currentYear - index);
    _selectedYear = _years.first;
  }

  Future<void> _logout(BuildContext context) async {
    await _authService.logout();

    ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(content: Text('Logout realizado, token removido')),
    );

    Navigator.pushNamedAndRemoveUntil(context, '/login', (route) => false);
  }

  Future<void> _listTurmas() async {
    final prefs = await SharedPreferences.getInstance();
    final token = prefs.getString('userToken') ?? '';

    if (token.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Usuário não autenticado')),
      );
      return;
    }

    if (_selectedYear == null || _userEmail == null || _userEmail!.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Selecione um ano letivo válido.')),
      );
      return;
    }

    setState(() {
      _isLoading = true;
    });

    final url = Uri.parse(
      'http://localhost:8080/api/turmas/por-professor?email=$_userEmail&anoLetivo=$_selectedYear',
    );

    try {
      final response = await http.get(
        url,
        headers: {
          'Authorization': 'Bearer $token',
          'Content-Type': 'application/json',
        },
      );

      if (response.statusCode == 200) {
        final turmas = jsonDecode(response.body);
        print('Turmas encontradas: $turmas');
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Turmas carregadas com sucesso.')),
        );

        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) => TurmasScreen(
              email: _userEmail!,
              anoLetivo: _selectedYear!,
            ),
          ),
        );

      } else {
        print('Erro: ${response.body}');
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Erro ao buscar turmas: ${response.body}')),
        );
      }
    } catch (e) {
      print('Erro na requisição: $e');
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Erro ao conectar ao servidor: $e')),
      );
    } finally {
      setState(() {
        _isLoading = false;
      });
    }
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
      body: Center(
        child: Padding(
          padding: const EdgeInsets.all(16),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              Text(
                'Bem-vindo, $_userName!',
                style: const TextStyle(
                  fontSize: 20,
                  fontWeight: FontWeight.w500,
                ),
              ),
              const SizedBox(height: 24),
              SizedBox(
                width: 200,
                child: DropdownButton<int>(
                  value: _selectedYear,
                  items: _years
                      .map((year) => DropdownMenuItem(
                            value: year,
                            child: Text(year.toString()),
                          ))
                      .toList(),
                  onChanged: (year) {
                    setState(() {
                      _selectedYear = year;
                    });
                  },
                  isExpanded: true,
                ),
              ),
              const SizedBox(height: 16),
              ElevatedButton.icon(
                icon: const Icon(Icons.list),
                label: _isLoading
                    ? const SizedBox(
                        width: 16,
                        height: 16,
                        child: CircularProgressIndicator(
                          strokeWidth: 2,
                          color: Colors.white,
                        ),
                      )
                    : const Text('Listar Turmas'),
                onPressed: _isLoading ? null : _listTurmas,
              ),
            ],
          ),
        ),
      ),
    );
  }
}
