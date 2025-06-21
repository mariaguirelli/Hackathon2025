import 'package:flutter/material.dart';
import '../../services/api_service.dart';
import 'alunos_screen.dart';

class ProvasScreen extends StatefulWidget {
  final int turmaId;
  const ProvasScreen({super.key, required this.turmaId});

  @override
  State<ProvasScreen> createState() => _ProvasScreenState();
}

class _ProvasScreenState extends State<ProvasScreen> {
  List<Map<String, dynamic>> provas = [];
  bool carregando = true;
  bool erro = false;

  @override
  void initState() {
    super.initState();
    carregarProvas();
  }

  Future<void> carregarProvas() async {
    try {
      final resultado = await ApiService.getProvas(widget.turmaId);
      setState(() {
        provas = resultado;
        carregando = false;
      });
    } catch (e) {
      setState(() {
        erro = true;
        carregando = false;
      });
      if (!mounted) return;
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text('Erro ao carregar provas: $e')));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Provas')),

      body: carregando
          ? const Center(child: CircularProgressIndicator())
          : erro
          ? const Center(child: Text('Erro ao carregar as provas.'))
          : provas.isEmpty
          ? const Center(child: Text('Nenhuma prova encontrada.'))
          : ListView.builder(
              itemCount: provas.length,
              itemBuilder: (context, index) {
                final prova = provas[index];
                return ListTile(
                  title: Text(prova['titulo']),
                  trailing: const Icon(Icons.arrow_forward_ios),
                  onTap: () {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (_) => AlunosScreen(
                          turmaId: widget.turmaId,
                          provaId: prova['id'],
                        ),
                      ),
                    );
                  },
                );
              },
            ),
    );
  }
}
