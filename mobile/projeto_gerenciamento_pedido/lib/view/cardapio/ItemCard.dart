import 'package:flutter/material.dart';
import 'package:projeto_gerenciamento_pedido/model/Cardapio.dart';
import 'package:projeto_gerenciamento_pedido/model/Pedido.dart';
import 'package:projeto_gerenciamento_pedido/view/dashboard/componentes/inicio.dart';
import 'package:toast/toast.dart';

class ItemCard extends StatelessWidget {
  ItemCardapio item;
  ItemCard({Key key, @required this.item}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(top: 10),
      child: GestureDetector(
        onTap: () {
          if (Inicio.pedido == null) {
            Inicio.pedido = PedidoModel();
            Inicio.pedido.total = 0;
            Inicio.pedido.itens = List();
          }
          Inicio.controller.itens.add(item);

          Inicio.controller.mudarPreco(item.preco);
          Toast.show(
            "Foi adicionado o item ${item.nome}",
            context,
            duration: Toast.LENGTH_LONG,
            gravity: Toast.BOTTOM,
            backgroundColor: Colors.black87,
          );
        },
        child: Container(
          width: MediaQuery.of(context).size.width * 0.95,
          height: MediaQuery.of(context).size.height * 0.15,
          decoration: BoxDecoration(
              color: Color.fromRGBO(118, 226, 244, 0.7),
              borderRadius: BorderRadius.all(Radius.circular(15))),
          child: Row(
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: <Widget>[
              Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  Text("${item.nome}", style: TextStyle(fontSize: 25)),
                ],
              ),
              Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  Text("R\$${item.preco.toStringAsFixed(2)}",
                      style: TextStyle(fontSize: 25)),
                ],
              )
            ],
          ),
        ),
      ),
    );
  }
}
