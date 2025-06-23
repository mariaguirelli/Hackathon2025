class Prova {
  final int id;
  final String titulo;
  final String dataAplicacao;
  final int bimestre;

  Prova({
    required this.id,
    required this.titulo,
    required this.dataAplicacao,
    required this.bimestre,
  });

  factory Prova.fromJson(Map<String, dynamic> json) {
    return Prova(
      id: json['id'],
      titulo: json['nome'],           // usa 'nome' do JSON
      dataAplicacao: json['data'],    // usa 'data' do JSON
      bimestre: json['bimestre'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'titulo': titulo,
      'dataAplicacao': dataAplicacao,
      'bimestre': bimestre,
    };
  }
}
