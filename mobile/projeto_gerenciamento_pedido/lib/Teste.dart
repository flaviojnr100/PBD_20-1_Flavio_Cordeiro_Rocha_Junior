import 'package:projeto_gerenciamento_pedido/model/Funcionario.dart';
import 'package:projeto_gerenciamento_pedido/repository/RepositoryFuncionario.dart';
import 'package:projeto_gerenciamento_pedido/repository/RepositoryPedido.dart';

import 'model/Pedido.dart';

main(List<String> args) {
  RepositoryFuncionario rf = new RepositoryFuncionario();

  Funcionario f = new Funcionario();
  f.nome = "rodrigo";
  f.login = "rodrigo123";
  f.sobrenome = "santos";
  f.cpf = "71625432982";
  f.id = 10;
  f.isLogado = true;
  f.isPermissao = true;
  f.isReset = false;
  f.senha = "rodrigo123";
  f.tipoAcesso = "gerente";
  f.telefone = "234654675467";
  rf.editar(f);
}
