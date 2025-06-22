import 'dart:convert';
import 'package:http/http.dart' as http;

import '../models/turma.dart';
import '../models/aluno.dart';
import '../models/prova.dart';
import '../models/questao.dart';

class ApiService {
  // ===============================================
  // 🔥 MOCKS - Usados enquanto o backend não está pronto
  // ===============================================

  // 👉 Buscar turmas
  Future<List<Turma>> fetchTurmas() async {
    await Future.delayed(const Duration(seconds: 2));
    return [
      Turma(id: 1, nome: '5º A - Matemática'),
      Turma(id: 2, nome: '5º B - Física'),
      Turma(id: 3, nome: '5º C - Química'),
    ];
  }

  // 👉 Buscar alunos da turma
  Future<List<Aluno>> fetchAlunos(int turmaId) async {
    await Future.delayed(const Duration(seconds: 2));
    return [
      Aluno(id: 1, nome: 'Carlos Eduardo'),
      Aluno(id: 2, nome: 'Maria Silva'),
      Aluno(id: 3, nome: 'João Pedro'),
    ];
  }

  // 👉 Buscar provas do aluno
  Future<List<Prova>> fetchProvas(int alunoId) async {
    await Future.delayed(const Duration(seconds: 2));
    return [
      Prova(id: 1, nome: 'Prova de Matemática', data: '2025-06-30'),
      Prova(id: 2, nome: 'Prova de Física', data: '2025-07-10'),
      Prova(id: 3, nome: 'Prova de Química', data: '2025-07-20'),
    ];
  }

  // 👉 Buscar questões da prova
  Future<List<Questao>> fetchQuestoes(int provaId) async {
    await Future.delayed(const Duration(seconds: 2));
    return [
      Questao(
        id: 1,
        texto: 'Qual é a capital do Brasil?',
        opcoes: ['Brasília', 'Rio de Janeiro', 'São Paulo', 'Salvador'],
      ),
      Questao(
        id: 2,
        texto: 'Quanto é 2 + 2?',
        opcoes: ['3', '4', '5', '6'],
      ),
      Questao(
        id: 3,
        texto: 'Qual a cor do céu?',
        opcoes: ['Azul', 'Verde', 'Vermelho', 'Preto'],
      ),
    ];
  }

  // 👉 Enviar respostas da prova
  Future<bool> enviarRespostas(int provaId, Map<int, String> respostas) async {
    await Future.delayed(const Duration(seconds: 2));
    print('✅ Enviando respostas da prova $provaId: $respostas');
    return true;
  }

  // ===============================================
  // 🔗 API REAL - Use quando o backend estiver pronto
  // ===============================================

  /*
  // 👉 Buscar turmas do professor
  Future<List<Turma>> fetchTurmas() async {
    final response = await http.get(
      Uri.parse('http://SEU_BACKEND/api/professor/1/turmas'),
    );

    if (response.statusCode == 200) {
      final List data = jsonDecode(response.body);
      return data.map((json) => Turma.fromJson(json)).toList();
    } else {
      throw Exception('Erro ao buscar turmas');
    }
  }

  // 👉 Buscar alunos da turma
  Future<List<Aluno>> fetchAlunos(int turmaId) async {
    final response = await http.get(
      Uri.parse('http://SEU_BACKEND/api/turmas/$turmaId/alunos'),
    );

    if (response.statusCode == 200) {
      final List data = jsonDecode(response.body);
      return data.map((json) => Aluno.fromJson(json)).toList();
    } else {
      throw Exception('Erro ao buscar alunos');
    }
  }

  // 👉 Buscar provas do aluno
  Future<List<Prova>> fetchProvas(int alunoId) async {
    final response = await http.get(
      Uri.parse('http://SEU_BACKEND/api/alunos/$alunoId/provas'),
    );

    if (response.statusCode == 200) {
      final List data = jsonDecode(response.body);
      return data.map((json) => Prova.fromJson(json)).toList();
    } else {
      throw Exception('Erro ao buscar provas');
    }
  }

  // 👉 Buscar questões da prova
  Future<List<Questao>> fetchQuestoes(int provaId) async {
    final response = await http.get(
      Uri.parse('http://SEU_BACKEND/api/provas/$provaId/questoes'),
    );

    if (response.statusCode == 200) {
      final List data = jsonDecode(response.body);
      return data.map((json) => Questao.fromJson(json)).toList();
    } else {
      throw Exception('Erro ao buscar questões');
    }
  }

  // 👉 Enviar respostas da prova
  Future<bool> enviarRespostas(int provaId, Map<int, String> respostas) async {
    final response = await http.post(
      Uri.parse('http://SEU_BACKEND/api/provas/$provaId/respostas'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'respostas': respostas}),
    );

    return response.statusCode == 200;
  }
  */
}
