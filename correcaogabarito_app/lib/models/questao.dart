class Questao {
  final int id;
  final String enunciado;
  final String alternativaA;
  final String alternativaB;
  final String alternativaC;
  final String alternativaD;
  final String alternativaE;
  final String respostaCorreta;
  final double valor;

  Questao({
    required this.id,
    required this.enunciado,
    required this.alternativaA,
    required this.alternativaB,
    required this.alternativaC,
    required this.alternativaD,
    required this.alternativaE,
    required this.respostaCorreta,
    required this.valor,
  });

  factory Questao.fromJson(Map<String, dynamic> json) {
    return Questao(
      id: json['id'],
      enunciado: json['enunciado'],
      alternativaA: json['alternativaA'],
      alternativaB: json['alternativaB'],
      alternativaC: json['alternativaC'],
      alternativaD: json['alternativaD'],
      alternativaE: json['alternativaE'],
      respostaCorreta: json['respostaCorreta'],
      valor: (json['valor'] as num).toDouble(),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'enunciado': enunciado,
      'alternativaA': alternativaA,
      'alternativaB': alternativaB,
      'alternativaC': alternativaC,
      'alternativaD': alternativaD,
      'alternativaE': alternativaE,
      'respostaCorreta': respostaCorreta,
      'valor': valor,
    };
  }
}
