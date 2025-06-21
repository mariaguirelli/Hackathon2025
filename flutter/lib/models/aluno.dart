class Aluno {
  final int id;
  final String nome;

  Aluno({required this.id, required this.nome});

  factory Aluno.fromJson(Map<String, dynamic> json) {
    return Aluno(
      id: json['id'],
      nome: json['nome'],
    );
  }

  Map<String, dynamic> toJson() => {
        'id': id,
        'nome': nome,
      };
}
List<Aluno> alunosFromJson(List<dynamic> json) {
  return json.map((item) => Aluno.fromJson(item)).toList();
}