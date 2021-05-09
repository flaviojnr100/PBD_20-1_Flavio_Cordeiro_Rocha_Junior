import 'package:dio/dio.dart';
import 'package:projeto_gerenciamento_pedido/model/Funcionario.dart';
import 'package:projeto_gerenciamento_pedido/repository/RepositoryUtils.dart';
import 'package:projeto_gerenciamento_pedido/shared/urls.dart';

class RepositoryFuncionario {
  int statusCode;
  Funcionario funcionario;
  int id;
  static BaseOptions options = new BaseOptions(
      baseUrl: "${RepositoryUtils.URL}",
      connectTimeout: 5000,
      followRedirects: false,
      validateStatus: (status) {
        return status < 501;
      });
  var dio = new Dio(options);
  Future autenticar(String login, String senha) async {
    FormData form = new FormData.fromMap({'login': login, 'senha': senha});
    Response response = await dio.post("${Urls.autenticar}", data: form);

    statusCode = response.statusCode;
    if (statusCode == 202) {
      funcionario = Funcionario.fromJson(response.data);
    }
  }

  Future logout(String login) async {
    FormData form = new FormData.fromMap({'login': login});
    Response response = await dio.post("${Urls.logout}", data: form);
    statusCode = response.statusCode;
  }

  Future editar(Funcionario funcionario) async {
    FormData form = new FormData.fromMap(funcionario.toJson());
    Response response =
        await dio.put("${Urls.editar}/${funcionario.id}", data: form);
    statusCode = response.statusCode;
  }

  Future buscarLoginUnico(String login) async {
    FormData form = new FormData.fromMap({'login': login});
    Response response = await dio.post("${Urls.buscarLoginUnico}", data: form);
    statusCode = response.statusCode;

    id = response.data['id'];
  }

  Future reset(int id) async {
    FormData form = new FormData.fromMap({'id': id});
    Response response = await dio.post("${Urls.reset}", data: form);
    statusCode = response.statusCode;
  }
}
