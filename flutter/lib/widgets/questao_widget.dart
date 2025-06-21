import 'package:flutter/material.dart';
import '../models/questao.dart';

class QuestaoWidget extends StatelessWidget {
  final Questao questao;
  final String? respostaSelecionada;
  final ValueChanged<String> onChanged;

  const QuestaoWidget({
    super.key,
    required this.questao,
    required this.respostaSelecionada,
    required this.onChanged,
  });

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.all(12),
      child: Padding(
        padding: const EdgeInsets.all(10),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(questao.enunciado,
                style: const TextStyle(fontWeight: FontWeight.bold)),
            ...['a', 'b', 'c', 'd', 'e'].map((letra) {
              final texto = questao.toJson()[letra] ?? '';
              return RadioListTile<String>(
                title: Text(texto),
                value: letra.toUpperCase(),
                groupValue: respostaSelecionada,
                onChanged: (valor) {
                  onChanged(valor!);
                },
              );
            }).toList(),
          ],
        ),
      ),
    );
  }
}
