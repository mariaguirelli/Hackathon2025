class Turma {
  final int id;
  final String nome;

  Turma({required this.id, required this.nome});

  factory Turma.fromJson(Map<String, dynamic> json) {
    return Turma(
      id: json['id'],
      nome: json['nome'],
    );
  }

  Map<String, dynamic> toJson() => {
        'id': id,
        'nome': nome,
      };
}
  List<Turma> turmasFromJson(List<dynamic> json) {
  return json.map((item) => Turma.fromJson(item)).toList();
}