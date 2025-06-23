import 'package:flutter/material.dart';

import '../models/prova.dart';
import '../models/questao.dart';
import '../models/aluno.dart';
import '../models/turma.dart';
import '../services/api_service.dart';
import '../widgets/breadcrumb.dart';
import '../models/RespostaAlunoDTO.dart';

class RespostasScreen extends StatefulWidget {
  final Prova prova;
  final Aluno aluno;
  final Turma turma;

  const RespostasScreen({
    super.key,
    required this.prova,
    required this.aluno,
    required this.turma,
  });

  @override
  State<RespostasScreen> createState() => _RespostasScreenState();
}

class _RespostasScreenState extends State<RespostasScreen> {
  final ApiService apiService = ApiService();

  late Future<List<Questao>> _futureQuestoes;
  List<Questao> _questoes = [];
  final Map<int, String> respostasSelecionadas = {};

  @override
  void initState() {
    super.initState();
    _futureQuestoes = apiService.fetchQuestoesDaProva(widget.prova.id);
  }

  void _enviarRespostas() async {
    final respostasDTO = respostasSelecionadas.entries.map((entry) {
      return RespostaAlunoDTO(
        alunoId: widget.aluno.id,
        questaoId: entry.key,
        respostaMarcada: entry.value,
      );
    }).toList();

    final sucesso = await apiService.enviarRespostas(respostasDTO);

    if (sucesso) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Gabarito enviado com sucesso!')),
      );
      Navigator.pop(context);
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Erro ao enviar gabarito')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Gabarito: ${widget.prova.titulo}'),
      ),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: Breadcrumb(
              items: [
                BreadcrumbItem(label: 'Home', onTap: () => Navigator.popUntil(context, (route) => route.isFirst)),
                BreadcrumbItem(label: 'Turmas', onTap: () => Navigator.popUntil(context, (route) => route.settings.name == '/turmas' || route.isFirst)),
                BreadcrumbItem(label: 'Alunos', onTap: () => Navigator.pop(context)),
                BreadcrumbItem(label: 'Provas', onTap: () => Navigator.pop(context)),
                BreadcrumbItem(label: widget.prova.titulo),
              ],
            ),
          ),
          Expanded(
            child: FutureBuilder<List<Questao>>(
              future: _futureQuestoes,
              builder: (context, snapshot) {
                if (snapshot.connectionState == ConnectionState.waiting) {
                  return const Center(child: CircularProgressIndicator());
                } else if (snapshot.hasError) {
                  return Center(child: Text('Erro ao carregar questões: ${snapshot.error}'));
                } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
                  return const Center(child: Text('Nenhuma questão encontrada'));
                } else {
                  WidgetsBinding.instance.addPostFrameCallback((_) {
                    if (_questoes.isEmpty) {
                      setState(() {
                        _questoes = snapshot.data!;
                      });
                    }
                  });

                  return ListView(
                    padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
                    children: [
                      ..._questoes.asMap().entries.map((entry) {
                        final index = entry.key;
                        final questao = entry.value;

                        // Apenas as letras A a E como opções
                        final List<String> letras = ['A', 'B', 'C', 'D', 'E'];

                        return Card(
                          margin: const EdgeInsets.only(bottom: 16),
                          child: Padding(
                            padding: const EdgeInsets.all(12),
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  'Questão ${index + 1}',
                                  style: const TextStyle(
                                    fontWeight: FontWeight.bold,
                                    fontSize: 16,
                                  ),
                                ),
                                const SizedBox(height: 8),
                                ...letras.map((letra) {
                                  return RadioListTile<String>(
                                    title: Text(letra),
                                    value: letra,
                                    groupValue: respostasSelecionadas[questao.id],
                                    onChanged: (valor) {
                                      setState(() {
                                        respostasSelecionadas[questao.id] = valor!;
                                      });
                                    },
                                  );
                                }).toList(),
                              ],
                            ),
                          ),
                        );
                      }).toList(),

                      const SizedBox(height: 8),

                      ElevatedButton.icon(
                        onPressed: respostasSelecionadas.length == _questoes.length
                            ? _enviarRespostas
                            : null,
                        icon: const Icon(Icons.send),
                        label: const Text('Enviar Gabarito'),
                        style: ElevatedButton.styleFrom(
                          minimumSize: const Size.fromHeight(48),
                        ),
                      ),
                    ],
                  );
                }
              },
            ),
          ),
        ],
      ),
    );
  }
}
