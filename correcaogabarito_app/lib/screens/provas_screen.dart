import 'package:flutter/material.dart';

import '../models/aluno.dart';
import '../models/turma.dart';
import '../models/prova.dart';
import '../services/api_service.dart';
import './respostas_screen.dart';
import '../widgets/breadcrumb.dart';

class ProvasScreen extends StatefulWidget {
  final Aluno aluno;
  final Turma turma;

  const ProvasScreen({super.key, required this.aluno, required this.turma});

  @override
  State<ProvasScreen> createState() => _ProvasScreenState();
}

class _ProvasScreenState extends State<ProvasScreen> {
  final ApiService apiService = ApiService();
  late Future<List<Prova>> futureProvas;

  int? selectedBimestre; // bimestre selecionado, inicializa como null (nenhum selecionado)

  @override
  void initState() {
    super.initState();
    // Buscar provas do aluno sem bimestre inicialmente
    futureProvas = apiService.fetchProvasDaTurma(widget.aluno.id);
  }

  void _onBimestreChanged(int? bimestre) {
    setState(() {
      selectedBimestre = bimestre;
      // Atualiza futureProvas com o bimestre selecionado
      futureProvas = apiService.fetchProvasDaTurma(widget.aluno.id, bimestre: bimestre);
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
            Breadcrumb(
              items: [
                BreadcrumbItem(
                  label: 'Home',
                  onTap: () {
                    Navigator.popUntil(context, (route) => route.isFirst);
                  },
                ),
                BreadcrumbItem(
                  label: 'Turmas',
                  onTap: () {
                    Navigator.popUntil(context, (route) => route.settings.name == '/turmas');
                  },
                ),
                BreadcrumbItem(
                  label: 'Alunos',
                  onTap: () {
                    Navigator.pop(context);
                  },
                ),
                BreadcrumbItem(label: 'Provas'),
              ],
            ),

            const SizedBox(height: 16),

            // Dropdown para seleção do bimestre
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
                // Botão para limpar o filtro e mostrar todas as provas
                if (selectedBimestre != null)
                  TextButton(
                    onPressed: () => _onBimestreChanged(null),
                    child: const Text('Limpar filtro'),
                  ),
              ],
            ),

            const SizedBox(height: 16),

            Expanded(
              child: FutureBuilder<List<Prova>>(
                future: futureProvas,
                builder: (context, snapshot) {
                  if (snapshot.connectionState == ConnectionState.waiting) {
                    return const Center(child: CircularProgressIndicator());
                  } else if (snapshot.hasError) {
                    return Center(child: Text('Erro ao carregar provas: ${snapshot.error}'));
                  } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
                    return const Center(child: Text('Nenhuma prova encontrada'));
                  } else {
                    final provas = snapshot.data!;
                    return ListView.builder(
                      itemCount: provas.length,
                      itemBuilder: (context, index) {
                        final prova = provas[index];
                        return Card(
                          margin: const EdgeInsets.symmetric(vertical: 8, horizontal: 0),
                          child: ListTile(
                            title: Text(prova.titulo),
                            subtitle: Text('Data: ${prova.dataAplicacao}'),
                            leading: const Icon(Icons.assignment),
                            onTap: () {
                              Navigator.push(
                                context,
                                MaterialPageRoute(
                                  builder: (context) => RespostasScreen(
                                    prova: prova,
                                    aluno: widget.aluno,
                                    turma: widget.turma,
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

