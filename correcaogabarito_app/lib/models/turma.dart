class Turma {
  final int id;
  final String nome;

  Turma({
    required this.id,
    required this.nome,
  });

  factory Turma.fromJson(Map<String, dynamic> json) {
    return Turma(
      id: json['id'],
      nome: json['nome'],
    );
  }
}
