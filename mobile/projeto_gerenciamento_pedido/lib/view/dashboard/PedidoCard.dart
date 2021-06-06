import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

import 'package:projeto_gerenciamento_pedido/model/Pedido.dart';
import 'package:projeto_gerenciamento_pedido/view/dashboard/componentes/pedido.dart';
import 'package:projeto_gerenciamento_pedido/view/pedido/EditarPedido.dart';

class PedidoCard extends StatelessWidget {
  PedidoModel pedido = PedidoModel();
  PedidoCard({Key key, @required this.pedido}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) => EditarPedido(
                      pedido: pedido,
                    )));
      },
      child: Padding(
        padding: const EdgeInsets.only(top: 10),
        child: Center(
          child: Container(
            width: MediaQuery.of(context).size.width * 0.98,
            height: MediaQuery.of(context).size.height * 0.15,
            decoration: BoxDecoration(
                color: Colors.black12,
                borderRadius: BorderRadius.all(Radius.circular(15))),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
              children: <Widget>[
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: <Widget>[
                    Text("Status: ${pedido.status}",
                        style: TextStyle(fontSize: 20)),
                    Text(
                        "Data: ${DateFormat('dd/MM/yyyy hh:mm').format(DateTime.parse(pedido.dataPedido))}",
                        style: TextStyle(fontSize: 20)),
                  ],
                ),
                Padding(
                  padding: const EdgeInsets.only(top: 25),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceAround,
                    children: <Widget>[
                      Text(
                        "Nº ${pedido.id}",
                        style: TextStyle(fontSize: 20),
                      ),
                      Text("Total: R\$${pedido.total.toStringAsFixed(2)}",
                          style: TextStyle(fontSize: 20)),
                    ],
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
