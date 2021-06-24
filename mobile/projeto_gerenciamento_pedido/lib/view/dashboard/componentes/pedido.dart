import 'package:flutter/material.dart';
import 'package:projeto_gerenciamento_pedido/model/Pedido.dart';
import 'package:projeto_gerenciamento_pedido/repository/RepositoryPedido.dart';
import 'package:projeto_gerenciamento_pedido/view/dashboard/PedidoCard.dart';
import 'package:projeto_gerenciamento_pedido/view/dashboard/componentes/inicio.dart';

class Pedido extends StatefulWidget {
  List<PedidoModel> pedidos;
  Pedido({Key key}) : super(key: key);
  RepositoryPedido repositoryPedido = RepositoryPedido();

  @override
  _PedidoState createState() => _PedidoState();
}

class _PedidoState extends State<Pedido> {
  bool isCarregou = false;
  @override
  void initState() {
    widget.repositoryPedido.buscarTodos().whenComplete(() {
      widget.pedidos = List.from(widget.repositoryPedido.pedidos);
      setState(() {
        isCarregou = true;
      });
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return isCarregou
        ? Expanded(
            child: Container(
              color: Colors.white,
              child: ListView.builder(
                  itemCount: widget.pedidos.length,
                  itemBuilder: (context, index) => PedidoCard(
                        pedido: widget.pedidos[index],
                      )),
            ),
          )
        : Text("");
  }
}
