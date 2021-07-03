import 'package:projeto_gerenciamento_pedido/repository/RepositoryUtils.dart';

class Urls {
  static const String autenticar =
      "${RepositoryUtils.URL}/funcionario/autenticar";
  static const String funcionarios = "${RepositoryUtils.URL}/funcionario";
  static const String logout = "${RepositoryUtils.URL}/funcionario/logout";
  static const String editar = "${RepositoryUtils.URL}/funcionario";
  static const String buscarLoginUnico =
      "${RepositoryUtils.URL}/funcionario/login";
  static const String reset = "${RepositoryUtils.URL}/senhaReset";
  static const String cardapio = "${RepositoryUtils.URL}/cardapio";
  static const String pedidoFuncionario =
      "${RepositoryUtils.URL}/pedidosFuncionario";
  static const String pedido = "${RepositoryUtils.URL}/pedido";
  static const String mesa = "${RepositoryUtils.URL}/mesa";
}
