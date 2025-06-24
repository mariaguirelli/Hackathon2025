import 'package:flutter/material.dart';
import '../models/aluno.dart';
import '../models/turma.dart';
import '../models/prova.dart';
import '../services/api_service.dart';
import './respostas_screen.dart';
import '../widgets/breadcrumb.dart';
import '../models/turmas_params.dart';

class ProvasScreen extends StatefulWidget {
  final Aluno aluno;
  final Turma turma;
  final TurmasParams params;

  const ProvasScreen({
    super.key,
    required this.aluno,
    required this.turma,
    required this.params,
  });

  @override
  State<ProvasScreen> createState() => _ProvasScreenState();
}

class _ProvasScreenState extends State<ProvasScreen> {
  final ApiService apiService = ApiService();
  Future<List<Prova>>? futureProvas;
  int? selectedBimestre;

  void _onBimestreChanged(int? bimestre) {
    setState(() {
      selectedBimestre = bimestre;
      if (bimestre != null) {
        futureProvas = apiService.fetchProvasDaTurma(widget.aluno.id, bimestre: bimestre);
      } else {
        futureProvas = null;
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Provas de ${widget.aluno.nome}'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                const Text('Bimestre: ', style: TextStyle(fontWeight: FontWeight.bold)),
                DropdownButton<int>(
                  value: selectedBimestre,
                  hint: const Text('Selecione'),
                  items: const [
                    DropdownMenuItem(value: 1, child: Text('1º Bimestre')),
                    DropdownMenuItem(value: 2, child: Text('2º Bimestre')),
                    DropdownMenuItem(value: 3, child: Text('3º Bimestre')),
                    DropdownMenuItem(value: 4, child: Text('4º Bimestre')),
                  ],
                  onChanged: _onBimestreChanged,
                ),
                if (selectedBimestre != null)
                  TextButton(
                    onPressed: () => _onBimestreChanged(null),
                    child: const Text('Limpar filtro'),
                  ),
              ],
            ),

            const SizedBox(height: 16),

            Expanded(
              child: selectedBimestre == null
                  ? const Center(child: Text('Selecione o bimestre para visualizar as provas.'))
                  : FutureBuilder<List<Prova>>(
                      future: futureProvas,
                      builder: (context, snapshot) {
                        if (snapshot.connectionState == ConnectionState.waiting) {
                          return const Center(child: CircularProgressIndicator());
                        } else if (snapshot.hasError) {
                          return Center(child: Text('Erro ao carregar provas: ${snapshot.error}'));
                        } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
                          return const Center(child: Text('Nenhuma prova encontrada para o bimestre selecionado.'));
                        } else {
                          final provas = snapshot.data!;
                          return ListView.builder(
                            itemCount: provas.length,
                            itemBuilder: (context, index) {
                              final prova = provas[index];
                              return Card(
                                margin: const EdgeInsets.symmetric(vertical: 8),
                                child: ListTile(
                                  title: Text(prova.titulo),
                                  subtitle: Text('Data: ${prova.dataAplicacao}'),
                                  leading: const Icon(Icons.assignment),
                                  onTap: () async {
                                    await Navigator.push(
                                      context,
                                      MaterialPageRoute(
                                        builder: (context) => RespostasScreen(
                                          prova: prova,
                                          aluno: widget.aluno,
                                          turma: widget.turma,
                                          params: widget.params,
                                        ),
                                      ),
                                    );

                                    // Após retornar da tela de Respostas, atualiza a lista de provas
                                    if (selectedBimestre != null) {
                                      setState(() {
                                        futureProvas = apiService.fetchProvasDaTurma(widget.aluno.id, bimestre: selectedBimestre!);
                                      });
                                    }
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
