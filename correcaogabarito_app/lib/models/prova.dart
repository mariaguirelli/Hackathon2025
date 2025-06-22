class Prova {
  final int id;
  final String nome;
  final String data;

  Prova({required this.id, required this.nome, required this.data});

  factory Prova.fromJson(Map<String, dynamic> json) {
    return Prova(
      id: json['id'],
      nome: json['nome'],
      data: json['data'],
    );
  }
}
