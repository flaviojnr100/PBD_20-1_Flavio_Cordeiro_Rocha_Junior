import 'package:projeto_gerenciamento_pedido/repository/RepositoryPedido.dart';

import 'model/Pedido.dart';

main(List<String> args) {
  RepositoryPedido rp = RepositoryPedido();
  rp.buscarTodos().whenComplete(() {
    for (PedidoModel item in rp.pedidos) {
      print(item);
    }
  });
}
