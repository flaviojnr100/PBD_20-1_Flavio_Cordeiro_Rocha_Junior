import 'package:dio/dio.dart';
import 'package:projeto_gerenciamento_pedido/model/Cardapio.dart';
import 'package:projeto_gerenciamento_pedido/model/Pedido.dart';
import 'package:projeto_gerenciamento_pedido/shared/urls.dart';
import 'package:projeto_gerenciamento_pedido/view/dashboard/dashboard.dart';

import 'RepositoryUtils.dart';

class RepositoryPedido {
  List<PedidoModel> pedidos = List();
  int statusCode;
  static BaseOptions options = new BaseOptions(
      baseUrl: "${RepositoryUtils.URL}",
      connectTimeout: 5000,
      followRedirects: false,
      validateStatus: (status) {
        return status < 501;
      });
  var dio = new Dio(options);
  Future buscarTodos() async {
    pedidos.clear();
    Response response = await dio.get(
        "${Urls.pedidoFuncionario}/${Dashboard.repositoryFuncionario.funcionario.id}");
    for (var p in response.data) {
      pedidos.add(PedidoModel.fromJson(p));
    }
  }

  Future salvar(PedidoModel pedido) async {
    FormData form = new FormData.fromMap({
      'funcionario': pedido.funcionario.id,
      'status': pedido.status,
      'total': pedido.total,
      'mesa': pedido.mesa.id,
      'cardapio': _formatarItens(pedido.itens)
    });

    Response response = await dio.post("${Urls.pedido}", data: form);
    statusCode = response.statusCode;
  }

  Future editar(PedidoModel pedido) async {
    FormData form = new FormData.fromMap({
      'funcionario': pedido.funcionario.id,
      'status': pedido.status,
      'total': pedido.total,
      'mesa': pedido.mesa.numero,
      'cardapio': _formatarItens(pedido.itens)
    });

    Response response =
        await dio.put("${Urls.pedido}/${pedido.id}", data: form);
    statusCode = response.statusCode;
  }

  String _formatarItens(List<ItemCardapio> itens) {
    String formato = "";
    int i = 0;
    for (ItemCardapio item in itens) {
      formato += item.id.toString();
      if (i < itens.length - 1) {
        formato += ",";
      }
      i++;
    }
    return formato;
  }
}
