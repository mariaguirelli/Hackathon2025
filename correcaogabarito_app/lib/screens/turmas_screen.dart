import 'package:flutter/material.dart';

import 'package:shared_preferences/shared_preferences.dart';
import '../models/turma.dart';
import '../services/api_service.dart';
import '../widgets/breadcrumb.dart';  // importe o breadcrumb
import 'alunos_screen.dart';

class TurmasScreen extends StatefulWidget {
  const TurmasScreen({Key? key}) : super(key: key);

  @override
  State<TurmasScreen> createState() => _TurmasScreenState();
}

class _TurmasScreenState extends State<TurmasScreen> {
  final ApiService apiService = ApiService();
  Future<List<Turma>>? futureTurmas;

  @override
  void initState() {
    super.initState();
    futureTurmas = _loadTurmas();
  }

  Future<List<Turma>> _loadTurmas() async {
    final prefs = await SharedPreferences.getInstance();
    final email = prefs.getString('email') ?? '';
    return apiService.fetchTurmas(email);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Turmas')),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Breadcrumb(
              items: [
                BreadcrumbItem(
                  label: 'Home',
                  onTap: () {
                    Navigator.pushNamedAndRemoveUntil(context, '/home', (route) => false);
                  },
                ),
                BreadcrumbItem(label: 'Turmas'),
              ],
            ),
            const SizedBox(height: 16),
              Expanded(
                child: futureTurmas == null
                    ? const Center(child: CircularProgressIndicator())
                    : FutureBuilder<List<Turma>>(
                        future: futureTurmas,
                        builder: (context, snapshot) {
                          if (snapshot.connectionState == ConnectionState.waiting) {
                            return const Center(child: CircularProgressIndicator());
                          } else if (snapshot.hasError) {
                            return Center(child: Text('Erro: ${snapshot.error}'));
                          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
                            return const Center(child: Text('Nenhuma turma encontrada'));
                          } else {
                            final turmas = snapshot.data!;
                            return ListView.builder(
                              itemCount: turmas.length,
                              itemBuilder: (context, index) {
                                final turma = turmas[index];
                                return Card(
                                  margin: const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
                                  child: ListTile(
                                    title: Text(turma.nome),
                                    leading: const Icon(Icons.class_),
                                    onTap: () {
                                      Navigator.push(
                                        context,
                                        MaterialPageRoute(
                                          builder: (context) => AlunosScreen(turma: turma),
                                        ),
                                      );
                                    },
                                  ),
                                );
                              },
                            );
                          }
                        },
                      ),
              ),
          ],
        ),
      ),
    );
  }
}

