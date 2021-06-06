class ItemCardapio {
  int id;
  String nome;
  double preco;
  String descricao;
  bool isAtivo;

  ItemCardapio({this.id, this.nome, this.preco, this.descricao, this.isAtivo});

  ItemCardapio.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nome = json['nome'];
    preco = json['preco'];
    descricao = json['descricao'];
    isAtivo = json['isAtivo'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['nome'] = this.nome;
    data['preco'] = this.preco;
    data['descricao'] = this.descricao;
    data['isAtivo'] = this.isAtivo;
    return data;
  }
}
