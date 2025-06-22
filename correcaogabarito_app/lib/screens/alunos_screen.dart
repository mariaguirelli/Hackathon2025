import 'package:flutter/material.dart';

import '../models/turma.dart';
import '../models/aluno.dart';
import '../services/api_service.dart';
import 'provas_screen.dart';

class AlunosScreen extends StatefulWidget {
  final Turma turma;

  const AlunosScreen({super.key, required this.turma});

  @override
  State<AlunosScreen> createState() => _AlunosScreenState();
}

class _AlunosScreenState extends State<AlunosScreen> {
  final ApiService apiService = ApiService();
  late Future<List<Aluno>> futureAlunos;

  @override
  void initState() {
    super.initState();
    futureAlunos = apiService.fetchAlunos(widget.turma.id);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Alunos - ${widget.turma.nome}'),
      ),
      body: FutureBuilder<List<Aluno>>(
        future: futureAlunos,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Erro: ${snapshot.error}'));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center(child: Text('Nenhum aluno encontrado'));
          } else {
            final alunos = snapshot.data!;
            return ListView.builder(
              itemCount: alunos.length,
              itemBuilder: (context, index) {
                final aluno = alunos[index];
                return Card(
                  margin: const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
                  child: ListTile(
                    title: Text(aluno.nome),
                    leading: const Icon(Icons.person),
                    onTap: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => ProvasScreen(aluno: aluno),
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
    );
  }
}
