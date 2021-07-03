import 'package:dio/dio.dart';
import 'package:projeto_gerenciamento_pedido/model/Mesa.dart';
import 'package:projeto_gerenciamento_pedido/shared/urls.dart';

import 'RepositoryUtils.dart';

class RepositoryMesa {
  List<Mesa> mesas = List();
  static BaseOptions options = new BaseOptions(
      baseUrl: "${RepositoryUtils.URL}",
      connectTimeout: 5000,
      followRedirects: false,
      validateStatus: (status) {
        return status < 501;
      });
  var dio = new Dio(options);

  Future buscarTodos() async {
    mesas.clear();
    Response response = await dio.get("${Urls.mesa}");
    for (var item in response.data) {
      mesas.add(Mesa.fromJson(item));
    }
  }
}
