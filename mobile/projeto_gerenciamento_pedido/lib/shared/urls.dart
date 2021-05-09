import 'package:projeto_gerenciamento_pedido/repository/RepositoryUtils.dart';

class Urls {
  static const String autenticar =
      "${RepositoryUtils.URL}/funcionario/autenticar";
  static const String funcionarios = "${RepositoryUtils.URL}/funcionario";
  static const String logout = "${RepositoryUtils.URL}/funcionario/logout";
  static const String editar = "${RepositoryUtils.URL}/funcionario";
}
