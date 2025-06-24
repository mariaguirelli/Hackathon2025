import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

import '../models/turma.dart';
import '../models/aluno.dart';
import '../models/prova.dart';
import '../models/questao.dart';
import '../models/RespostaAlunoDTO.dart';

class ApiService {
  final String baseUrl = 'http://localhost:8080/api';  // Ajuste conforme seu backend

  Future<Map<String, String>> _getHeaders() async {
    final prefs = await SharedPreferences.getInstance();
    final token = prefs.getString('userToken');
    if (token == null) {
      throw Exception('Usuário não autenticado');
    }
    return {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token',
    };
  }

  Future<List<Turma>> fetchTurmas(String email, int anoLetivo) async {
    final headers = await _getHeaders();  // Certifique-se que retorna {'Authorization': 'Bearer <token>'} etc
    final url = Uri.parse('$baseUrl/turmas/por-professor?email=$email&anoLetivo=$anoLetivo');

    print('GET $url');
    print('Headers: $headers');

    final response = await http.get(url, headers: headers);

    print('Status: ${response.statusCode}');
    print('Body: ${response.body}');

    if (response.statusCode == 200) {
      final List data = jsonDecode(response.body);
      return data.map((json) => Turma.fromJson(json)).toList();
    } else {
      throw Exception('Erro ao buscar turmas: ${response.statusCode}');
    }
  }

  // Buscar alunos da turma pelo id da turma
  Future<List<Aluno>> fetchAlunosDaTurma(int turmaId) async {
    final headers = await _getHeaders();
    final url = Uri.parse('$baseUrl/aluno/$turmaId/alunos');

    final response = await http.get(url, headers: headers);

    if (response.statusCode == 200) {
      final List data = jsonDecode(response.body);
      return data.map((json) => Aluno.fromJson(json)).toList();
    } else {
      throw Exception('Erro ao buscar alunos da turma: ${response.statusCode}');
    }
  }

  Future<List<Prova>> fetchProvasDaTurma(int alunoId, {int? bimestre}) async {
    final headers = await _getHeaders();

    // Monta a URL base com alunoId
    String urlString = '$baseUrl/provas/aluno/$alunoId';

    // Se bimestre foi passado, adiciona na query string
    if (bimestre != null) {
      urlString += '?bimestre=$bimestre';
    }

    final url = Uri.parse(urlString);

    final response = await http.get(url, headers: headers);

    if (response.statusCode == 200) {
      final List data = jsonDecode(response.body);
      return data.map((json) => Prova.fromJson(json)).toList();
    } else {
      throw Exception('Erro ao buscar provas da turma: ${response.statusCode}');
    }
  }

  Future<List<Questao>> fetchQuestoesDaProva(int provaId) async {
    final headers = await _getHeaders();
    final url = Uri.parse('$baseUrl/questoes/prova/$provaId');

    final response = await http.get(url, headers: headers);

    if (response.statusCode == 200) {
      final List data = jsonDecode(response.body);
      return data.map((json) => Questao.fromJson(json)).toList();
    } else {
      throw Exception('Erro ao buscar questões da prova: ${response.statusCode}');
    }
  }

  Future<bool> enviarRespostas(List<RespostaAlunoDTO> respostas) async {
    final headers = await _getHeaders();
    final url = Uri.parse('$baseUrl/respostas/enviar');

    final body = jsonEncode(respostas.map((r) => r.toJson()).toList());

    final response = await http.post(
      url,
      headers: headers,
      body: body,
    );

    return response.statusCode == 200;
  }
}
