import '../models/usuario.dart';
import '../models/turma.dart';
import '../models/prova.dart';
import '../models/aluno.dart';
import '../models/questao.dart';

class ApiService {
  static Future<Usuario?> login(String email, String senha) async {
    if (email == 'professor@teste.com' && senha == '123456') {
      return Usuario(
        id: 1,
        nome: 'Prof. João',
        email: email,
        perfil: 'PROFESSOR',
      );
    }
    return null;
  }

  static Future<List<Turma>> getTurmas(int professorId) async {
    return [
      Turma(id: 1, nome: 'Turma A'),
      Turma(id: 2, nome: 'Turma B'),
    ];
  }

  static Future<List<Prova>> getProvas(int turmaId) async {
    return [
      Prova(id: 1, titulo: 'Prova 1'),
      Prova(id: 2, titulo: 'Prova 2'),
    ];
  }

  static Future<List<Aluno>> getAlunos(int turmaId) async {
    return [
      Aluno(id: 1, nome: 'Aluno A'),
      Aluno(id: 2, nome: 'Aluno B'),
    ];
  }

  static Future<List<Questao>> getQuestoes(int provaId) async {
    return [
      Questao(
        id: 1,
        enunciado: 'Quanto é 2 + 2?',
        a: '2',
        b: '3',
        c: '4',
        d: '5',
        e: '6',
      ),
      Questao(
        id: 2,
        enunciado: 'Capital do Brasil?',
        a: 'São Paulo',
        b: 'Rio de Janeiro',
        c: 'Brasília',
        d: 'Belo Horizonte',
        e: 'Recife',
      ),
    ];
  }

  static Future<void> enviarRespostas({
    required int alunoId,
    required int provaId,
    required Map<int, String> respostas,
  }) async {
    print('Respostas enviadas (simulação):');
    print('Aluno: $alunoId | Prova: $provaId');
    print('Respostas: $respostas');
  }
}
