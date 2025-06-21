class ApiService {
  static Future<Map<String, dynamic>?> login(String email, String senha) async {
    if (email == 'professor@teste.com' && senha == '123456') {
      return {
        'id': 1,
        'nome': 'Prof. João',
        'email': email,
        'perfil': 'PROFESSOR',
      };
    }

    return null;
  }

  static Future<List<Map<String, dynamic>>> getTurmas(int professorId) async {
    return [
      {'id': 1, 'nome': 'Turma A'},
      {'id': 2, 'nome': 'Turma B'},
    ];
  }

  static Future<List<Map<String, dynamic>>> getProvas(int turmaId) async {
    return [
      {'id': 1, 'titulo': 'Prova 1'},
      {'id': 2, 'titulo': 'Prova 2'},
    ];
  }

  static Future<List<Map<String, dynamic>>> getAlunos(int turmaId) async {
    return [
      {'id': 1, 'nome': 'Aluno A'},
      {'id': 2, 'nome': 'Aluno B'},
    ];
  }

  static Future<List<Map<String, dynamic>>> getQuestoes(int provaId) async {
    return [
      {
        'id': 1,
        'enunciado': 'Quanto é 2 + 2?',
        'a': '2',
        'b': '3',
        'c': '4',
        'd': '5',
        'e': '6',
      },
      {
        'id': 2,
        'enunciado': 'Capital do Brasil?',
        'a': 'São Paulo',
        'b': 'Rio de Janeiro',
        'c': 'Brasília',
        'd': 'Belo Horizonte',
        'e': 'Recife',
      },
    ];
  }

  static Future<void> enviarRespostas({
    required int alunoId,
    required int provaId,
    required Map<int, String> respostas,
  }) async {
    print('Enviando respostas para aluno $alunoId na prova $provaId: $respostas');
   
  }
}
