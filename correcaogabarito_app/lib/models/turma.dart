class Turma {
  final int id;
  final String nome; // supondo que tenha um nome, adapte conforme seu model

  Turma({
    required this.id,
    required this.nome,
  });

  factory Turma.fromJson(Map<String, dynamic> json) {
    return Turma(
      id: json['id'],
      nome: json['nome'] ?? '', // ajuste conforme campo real
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'nome': nome,
    };
  }
}
