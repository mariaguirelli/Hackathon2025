import 'package:flutter/material.dart';

import '../models/prova.dart';
import '../models/questao.dart';
import '../services/api_service.dart';

class RespostasScreen extends StatefulWidget {
  final Prova prova;

  const RespostasScreen({super.key, required this.prova});

  @override
  State<RespostasScreen> createState() => _RespostasScreenState();
}

class _RespostasScreenState extends State<RespostasScreen> {
  final ApiService apiService = ApiService();

  late Future<List<Questao>> _futureQuestoes;
  final Map<int, String> respostasSelecionadas = {};

  @override
  void initState() {
    super.initState();
    _futureQuestoes = apiService.fetchQuestoes(widget.prova.id);
  }

  void _enviarRespostas() async {
    final sucesso = await apiService.enviarRespostas(widget.prova.id, respostasSelecionadas);

    if (sucesso) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Respostas enviadas com sucesso!')),
      );
      Navigator.pop(context);
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Erro ao enviar respostas')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Responder: ${widget.prova.nome}'),
      ),
      body: FutureBuilder<List<Questao>>(
        future: _futureQuestoes,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Erro: ${snapshot.error}'));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center(child: Text('Nenhuma questão encontrada'));
          } else {
            final questoes = snapshot.data!;
            return ListView(
              padding: const EdgeInsets.all(16),
              children: [
                ...questoes.map((questao) {
                  return Card(
                    margin: const EdgeInsets.only(bottom: 16),
                    child: Padding(
                      padding: const EdgeInsets.all(12),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            questao.texto,
                            style: const TextStyle(
                              fontWeight: FontWeight.bold,
                              fontSize: 16,
                            ),
                          ),
                          const SizedBox(height: 8),
                          ...questao.opcoes.map((opcao) {
                            return RadioListTile<String>(
                              title: Text(opcao),
                              value: opcao,
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
                ElevatedButton.icon(
                  onPressed: respostasSelecionadas.length == questoes.length
                      ? _enviarRespostas
                      : null,
                  icon: const Icon(Icons.send),
                  label: const Text('Enviar Respostas'),
                ),
              ],
            );
          }
        },
      ),
    );
  }
}
