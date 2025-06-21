import 'package:flutter/material.dart';
import '../../services/api_service.dart';
import '../responder_prova/responder_screen.dart';

class AlunosScreen extends StatefulWidget {
  final int turmaId;
  final int provaId;

  const AlunosScreen({super.key, required this.turmaId, required this.provaId});

  @override
  State<AlunosScreen> createState() => _AlunosScreenState();
}

class _AlunosScreenState extends State<AlunosScreen> {
  List<Map<String, dynamic>> alunos = [];

  @override
  void initState() {
    super.initState();
    carregarAlunos();
  }

  Future<void> carregarAlunos() async {
    final resultado = await ApiService.getAlunos(widget.turmaId);
    setState(() {
      alunos = resultado;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Alunos')),
      body: ListView.builder(
        itemCount: alunos.length,
        itemBuilder: (context, index) {
          final aluno = alunos[index];
          return ListTile(
            title: Text(aluno['nome']),
            trailing: const Icon(Icons.edit_note),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (_) => ResponderScreen(
                    alunoId: aluno['id'],
                    provaId: widget.provaId,
                  ),
                ),
              );
            },
          );
        },
      ),
    );
  }
}
