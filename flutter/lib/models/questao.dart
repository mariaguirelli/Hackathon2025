class Questao {
  final int id;
  final String enunciado;
  final String a, b, c, d, e;

  Questao({
    required this.id,
    required this.enunciado,
    required this.a,
    required this.b,
    required this.c,
    required this.d,
    required this.e,
  });

  factory Questao.fromJson(Map<String, dynamic> json) {
    return Questao(
      id: json['id'],
      enunciado: json['enunciado'],
      a: json['a'],
      b: json['b'],
      c: json['c'],
      d: json['d'],
      e: json['e'],
    );
  }

  Map<String, dynamic> toJson() => {
        'id': id,
        'enunciado': enunciado,
        'a': a,
        'b': b,
        'c': c,
        'd': d,
        'e': e,
      };
}
List<Questao> questoesFromJson(List<dynamic> json) {
  return json.map((item) => Questao.fromJson(item)).toList();
} 