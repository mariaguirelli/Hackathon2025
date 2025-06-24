import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../models/turma.dart';
import '../services/api_service.dart';
import '../widgets/breadcrumb.dart';  // importe o breadcrumb
import 'alunos_screen.dart';
import '../models/turmas_params.dart';

class TurmasScreen extends StatefulWidget {
  final String email;
  final int anoLetivo;

  const TurmasScreen({
    Key? key,
    required this.email,
    required this.anoLetivo,
  }) : super(key: key);

  @override
  State<TurmasScreen> createState() => _TurmasScreenState();
}

class _TurmasScreenState extends State<TurmasScreen> {
  final ApiService apiService = ApiService();
  Future<List<Turma>>? futureTurmas;

  late int _anoLetivo;

  @override
  void initState() {
    super.initState();
    _anoLetivo = widget.anoLetivo;
    _loadTurmasComParametros();
  }

  Future<void> _loadTurmasComParametros() async {
    setState(() {
      futureTurmas = apiService.fetchTurmas(widget.email, _anoLetivo);
    });
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
                                        builder: (context) => AlunosScreen(
                                          turma: turma,
                                          params: TurmasParams(email: widget.email, anoLetivo: _anoLetivo),
                                        ),
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

