import 'package:flutter/material.dart';
import '../../services/api_service.dart';
import '../../models/aluno.dart';

class AlunosScreen extends StatefulWidget {
  final int turmaId;
  final int provaId;

  const AlunosScreen({super.key, required this.turmaId, required this.provaId});

  @override
  State<AlunosScreen> createState() => _AlunosScreenState();
}

class _AlunosScreenState extends State<AlunosScreen> {
  List<Aluno> alunos = [];
  bool carregando = true;
  bool erro = false;

  @override
  void initState() {
    super.initState();
    carregarAlunos();
  }

  Future<void> carregarAlunos() async {
    try {
      final resultado = await ApiService.getAlunos(widget.turmaId);
      if (!mounted) return;
      setState(() {
        alunos = resultado;
        carregando = false;
        erro = false;
      });
    } catch (e) {
      if (!mounted) return;
      setState(() {
        erro = true;
        carregando = false;
      });
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text('Erro ao carregar alunos: $e')));
    }
  }

  @override
  Widget build(BuildContext context) {
    Widget body;

    if (carregando) {
      body = const Center(child: CircularProgressIndicator());
    } else if (erro) {
      body = const Center(child: Text('Erro ao carregar os alunos.'));
    } else if (alunos.isEmpty) {
      body = const Center(child: Text('Nenhum aluno encontrado.'));
    } else {
      body = ListView.builder(
        itemCount: alunos.length,
        itemBuilder: (context, index) {
          final aluno = alunos[index];
          return ListTile(
            title: Text(aluno.nome),
            trailing: const Icon(Icons.edit_note),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (_) => ResponderScreen(
                    alunoId: aluno.id,
                    provaId: widget.provaId,
                  ),
                ),
              );
            },
          );
        },
      );
    }

    return Scaffold(
      appBar: AppBar(title: const Text('Alunos')),
      body: body,
    );
  }
}
