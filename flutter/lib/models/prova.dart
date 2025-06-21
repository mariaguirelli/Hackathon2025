class Prova {
  final int id;
  final String titulo;

  Prova({required this.id, required this.titulo});

  factory Prova.fromJson(Map<String, dynamic> json) {
    return Prova(
      id: json['id'],
      titulo: json['titulo'],
    );
  }

  Map<String, dynamic> toJson() => {
        'id': id,
        'titulo': titulo,
      };
}
