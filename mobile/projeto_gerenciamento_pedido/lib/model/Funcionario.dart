class Funcionario {
  int id;
  String nome;
  String sobrenome;
  String telefone;
  String cpf;
  String login;
  String senha;
  bool isPermissao;
  String tipoAcesso;

  bool isReset;
  bool isLogado;

  Funcionario(
      {this.id,
      this.nome,
      this.sobrenome,
      this.telefone,
      this.cpf,
      this.login,
      this.senha,
      this.isPermissao,
      this.tipoAcesso,
      this.isReset,
      this.isLogado});

  Funcionario.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nome = json['nome'];
    sobrenome = json['sobrenome'];
    telefone = json['telefone'];
    cpf = json['cpf'];
    login = json['login'];
    senha = json['senha'];
    isPermissao = json['isPermissao'];
    tipoAcesso = json['tipoAcesso'];

    isReset = json['isReset'];
    isLogado = json['isLogado'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['nome'] = this.nome;
    data['sobrenome'] = this.sobrenome;
    data['telefone'] = this.telefone;
    data['cpf'] = this.cpf;
    data['login'] = this.login;
    data['senha'] = this.senha;
    data['isPermissao'] = this.isPermissao;
    data['tipoAcesso'] = this.tipoAcesso;

    data['isReset'] = this.isReset;
    data['isLogado'] = this.isLogado;
    return data;
  }
}
