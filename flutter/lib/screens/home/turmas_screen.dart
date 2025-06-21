import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../../services/api_service.dart';
import '../../models/turma.dart';
import '../select_student/provas_screen.dart';

class TurmasScreen extends StatefulWidget {
  const TurmasScreen({super.key});

  @override
  State<TurmasScreen> createState() => _TurmasScreenState();
}

class _TurmasScreenState extends State<TurmasScreen> {
  List<Turma> turmas = [];
  bool carregando = true;
  bool erro = false;

  @override
  void initState() {
    super.initState();
    carregarTurmas();
  }

  Future<void> carregarTurmas() async {
    try {
      final prefs = await SharedPreferences.getInstance();
      final professorId = int.parse(prefs.getString('professor_id') ?? '0');
      final resultado = await ApiService.getTurmas(professorId);

      if (!mounted) return;
      setState(() {
        turmas = resultado;
        carregando = false;
      });
    } catch (e) {
      if (!mounted) return;
      setState(() {
        erro = true;
        carregando = false;
      });
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text('Erro ao carregar turmas: $e')));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Turmas')),
      body: carregando
          ? const Center(child: CircularProgressIndicator())
          : erro
          ? const Center(child: Text('Erro ao carregar as turmas.'))
          : turmas.isEmpty
          ? const Center(child: Text('Nenhuma turma cadastrada.'))
          : ListView.builder(
              itemCount: turmas.length,
              itemBuilder: (context, index) {
                final turma = turmas[index];
                return ListTile(
                  title: Text(turma.nome),
                  trailing: const Icon(Icons.arrow_forward_ios),
                  onTap: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (_) => ProvasScreen(turmaId: turma.id),
                      ),
                    );
                  },
                );
              },
            ),
    );
  }
}
