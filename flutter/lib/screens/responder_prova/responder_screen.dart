import 'package:flutter/material.dart';
import '../../services/api_service.dart';

class ResponderScreen extends StatefulWidget {
  final int alunoId;
  final int provaId;

  const ResponderScreen({
    super.key,
    required this.alunoId,
    required this.provaId,
  });

  @override
  State<ResponderScreen> createState() => _ResponderScreenState();
}

class _ResponderScreenState extends State<ResponderScreen> {
  List<Map<String, dynamic>> questoes = [];
  final Map<int, String> respostasSelecionadas = {};

  @override
  void initState() {
    super.initState();
    carregarQuestoes();
  }

  Future<void> carregarQuestoes() async {
    final resultado = await ApiService.getQuestoes(widget.provaId);
    setState(() {
      questoes = resultado;
    });
  }

  Future<void> enviarRespostas() async {
    await ApiService.enviarRespostas(
      alunoId: widget.alunoId,
      provaId: widget.provaId,
      respostas: respostasSelecionadas,
    );

    if (!mounted) return;
    ScaffoldMessenger.of(context).showSnackBar(
      const SnackBar(content: Text('Respostas enviadas com sucesso!')),
    );
    Navigator.pop(context);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Responder Prova')),
      body: questoes.isEmpty
          ? const Center(child: CircularProgressIndicator())
          : ListView(
              children: [
                ...questoes.map((q) {
                  int id = q['id'];
                  return Card(
                    margin: const EdgeInsets.all(12),
                    child: Padding(
                      padding: const EdgeInsets.all(10),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(q['enunciado'], style: const TextStyle(fontWeight: FontWeight.bold)),
                          ...['a', 'b', 'c', 'd', 'e'].map((letra) {
                            return RadioListTile<String>(
                              title: Text(q[letra]),
                              value: letra.toUpperCase(),
                              groupValue: respostasSelecionadas[id],
                              onChanged: (valor) {
                                setState(() {
                                  respostasSelecionadas[id] = valor!;
                                });
                              },
                            );
                          }).toList(),
                        ],
                      ),
                    ),
                  );
                }),
                Padding(
                  padding: const EdgeInsets.all(16.0),
                  child: ElevatedButton(
                    onPressed: enviarRespostas,
                    child: const Text('Enviar Respostas'),
                  ),
                )
              ],
            ),
    );
  }
}
