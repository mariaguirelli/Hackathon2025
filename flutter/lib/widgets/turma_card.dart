import 'package:flutter/material.dart';
import '../models/turma.dart';

class TurmaCard extends StatelessWidget {
  final Turma turma;
  final VoidCallback onTap;

  const TurmaCard({
    super.key,
    required this.turma,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
      child: ListTile(
        title: Text(turma.nome),
        trailing: const Icon(Icons.arrow_forward_ios),
        onTap: onTap,
      ),
    );
  }
}
