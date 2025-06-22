import 'package:flutter/material.dart';

import '../models/aluno.dart';
import '../models/prova.dart';
import '../services/api_service.dart';
import 'respostas_screen.dart'; // Importa a próxima tela que será criada

class ProvasScreen extends StatefulWidget {
  final Aluno aluno;

  const ProvasScreen({super.key, required this.aluno});

  @override
  State<ProvasScreen> createState() => _ProvasScreenState();
}

class _ProvasScreenState extends State<ProvasScreen> {
  final ApiService apiService = ApiService();
  late Future<List<Prova>> futureProvas;

  @override
  void initState() {
    super.initState();
    futureProvas = apiService.fetchProvas(widget.aluno.id);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Provas de ${widget.aluno.nome}'),
      ),
      body: FutureBuilder<List<Prova>>(
        future: futureProvas,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return const Center(child: Text('Erro ao carregar provas'));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center(child: Text('Nenhuma prova encontrada'));
          } else {
            final provas = snapshot.data!;
            return ListView.builder(
              itemCount: provas.length,
              itemBuilder: (context, index) {
                final prova = provas[index];
                return Card(
                  margin: const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
                  child: ListTile(
                    title: Text(prova.nome),
                    subtitle: Text('Data: ${prova.data}'),
                    leading: const Icon(Icons.assignment),
                    onTap: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => RespostasScreen(prova: prova),
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
    );
  }
}
