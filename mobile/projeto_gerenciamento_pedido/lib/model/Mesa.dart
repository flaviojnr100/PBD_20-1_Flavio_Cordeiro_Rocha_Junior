class Mesa {
  int id;
  int numero;
  bool isLivre;

  Mesa({this.id, this.numero, this.isLivre});

  Mesa.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    numero = json['numero'];
    isLivre = json['isLivre'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['numero'] = this.numero;
    data['isLivre'] = this.isLivre;
    return data;
  }
}
