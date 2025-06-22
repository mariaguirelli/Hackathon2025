class Questao {
  final int id;
  final String texto;
  final List<String> opcoes;

  Questao({
    required this.id,
    required this.texto,
    required this.opcoes,
  });

  // Método para converter JSON em Questao (para quando tiver API)
  factory Questao.fromJson(Map<String, dynamic> json) {
    return Questao(
      id: json['id'],
      texto: json['texto'],
      opcoes: List<String>.from(json['opcoes']),
    );
  }
}
