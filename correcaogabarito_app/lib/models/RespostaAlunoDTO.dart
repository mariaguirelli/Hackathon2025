class RespostaAlunoDTO {
  final int alunoId;
  final int questaoId;
  final String respostaMarcada;

  RespostaAlunoDTO({
    required this.alunoId,
    required this.questaoId,
    required this.respostaMarcada,
  });

  Map<String, dynamic> toJson() => {
    'alunoId': alunoId,
    'questaoId': questaoId,
    'respostaMarcada': respostaMarcada,
  };
}
