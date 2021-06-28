import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_mobx/flutter_mobx.dart';

import 'package:projeto_gerenciamento_pedido/controller/controller.dart';
import 'package:projeto_gerenciamento_pedido/model/Cardapio.dart';
import 'package:projeto_gerenciamento_pedido/model/Mesa.dart';
import 'package:projeto_gerenciamento_pedido/model/Pedido.dart';

import 'package:projeto_gerenciamento_pedido/repository/RepositoryCardapio.dart';
import 'package:projeto_gerenciamento_pedido/repository/RepositoryFuncionario.dart';
import 'package:projeto_gerenciamento_pedido/repository/RepositoryPedido.dart';
import 'package:projeto_gerenciamento_pedido/view/cardapio/ItemCard.dart';
import 'package:projeto_gerenciamento_pedido/view/dashboard/componentes/perfil.dart';
import 'package:projeto_gerenciamento_pedido/view/dashboard/dashboard.dart';

class Inicio extends StatefulWidget {
  Inicio({Key key}) : super(key: key);
  List<ItemCardapio> cardapio;
  static PedidoModel pedido;
  static Controller controller = Controller();
  TextEditingController mesaController = TextEditingController();
  @override
  _InicioState createState() => _InicioState();
  RepositoryCardapio repositoryCardapio = RepositoryCardapio();
  RepositoryPedido repositoryPedido = RepositoryPedido();
}

class _InicioState extends State<Inicio> {
  bool isCarregou = false;
  @override
  void initState() {
    // TODO: implement initState

    widget.repositoryCardapio.buscarTodos().whenComplete(() {
      widget.cardapio = widget.repositoryCardapio.cardapio;

      setState(() {
        isCarregou = true;
      });
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    var mediaQuery = MediaQuery.of(context).size;
    return Stack(
      children: <Widget>[
        Positioned(
            child: SizedBox(
          height: mediaQuery.height * 0.82,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              Padding(
                padding: const EdgeInsets.only(top: 10),
                child: Text(
                  "Pedido",
                  style: TextStyle(fontSize: 40),
                ),
              ),
              Container(
                width: MediaQuery.of(context).size.width * 0.95,
                height: MediaQuery.of(context).size.height * 0.65,
                color: Colors.white,
                child: isCarregou
                    ? ListView.builder(
                        itemCount: widget.cardapio.length,
                        scrollDirection: Axis.vertical,
                        itemBuilder: (context, index) =>
                            ItemCard(item: widget.cardapio[index]))
                    : Text(""),
              ),
              Padding(
                padding: const EdgeInsets.only(top: 10, right: 10),
                child: Row(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: <Widget>[],
                ),
              ),
            ],
          ),
        )),
        Positioned(
          bottom: 10,
          right: 5,
          child: Row(
            mainAxisAlignment: MainAxisAlignment.end,
            children: <Widget>[
              Observer(
                  builder: (_) => Text(
                        "Total:R\$${Inicio.controller.preco.toStringAsFixed(2)}",
                        style: TextStyle(fontSize: 40),
                      )),
              Padding(
                padding: const EdgeInsets.only(right: 10, left: 30),
                child: Container(
                  height: mediaQuery.height * 0.06,
                  width: mediaQuery.width * 0.3,
                  decoration: BoxDecoration(
                      color: Colors.red,
                      borderRadius: BorderRadius.all(Radius.circular(30))),
                  child: FlatButton.icon(
                      onPressed: () {
                        exibirPedido(context);
                      },
                      icon: Icon(
                        Icons.shopping_cart,
                        color: Colors.white,
                      ),
                      label: Text(
                        "Visualizar",
                        style: TextStyle(fontSize: 15, color: Colors.white),
                      )),
                ),
              ),
            ],
          ),
        )
      ],
    );
  }

  Widget exibirPedido(context) {
    showModalBottomSheet(
        context: context,
        builder: (BuildContext bc) {
          return Container(
            color: Colors.black54,
            child: Center(
              child: Container(
                width: MediaQuery.of(context).size.width * 0.98,
                decoration: BoxDecoration(
                    color: Colors.white,
                    borderRadius: BorderRadius.all(Radius.circular(5))),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.start,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: <Widget>[
                    Padding(
                      padding: const EdgeInsets.only(top: 10, bottom: 5),
                      child: Center(
                        child: Container(
                          height: 10,
                          width: MediaQuery.of(context).size.width * 0.8,
                          decoration: BoxDecoration(
                              color: Colors.black26,
                              borderRadius:
                                  BorderRadius.all(Radius.circular(15))),
                        ),
                      ),
                    ),
                    Container(
                      width: 100,
                      height: 40,
                      decoration: BoxDecoration(
                          color: Color.fromRGBO(118, 226, 244, 0.7),
                          borderRadius: BorderRadius.all(Radius.circular(20))),
                      child: Padding(
                        padding: const EdgeInsets.only(left: 10),
                        child: TextFormField(
                          controller: widget.mesaController,
                          decoration: InputDecoration(
                            border: InputBorder.none,
                            hintText: "Nº mesa",
                          ),
                        ),
                      ),
                    ),
                    Text(
                      "Itens",
                      style:
                          TextStyle(fontSize: 30, fontWeight: FontWeight.bold),
                    ),
                    Inicio.controller.preco != 0
                        ? tituloItensPedido()
                        : Padding(
                            padding: const EdgeInsets.only(top: 170),
                            child: Text("Não há itens no pedido"),
                          ),
                    Inicio.controller.preco != 0
                        ? Observer(
                            builder: (_) => Text(
                                  "Total:R\$ ${Inicio.controller.preco.toStringAsFixed(2)}",
                                  style: TextStyle(
                                      fontSize: 30,
                                      fontWeight: FontWeight.bold),
                                ))
                        : Text(""),
                    Inicio.controller.preco != 0
                        ? Padding(
                            padding: const EdgeInsets.only(bottom: 5),
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                              children: <Widget>[
                                Container(
                                    width: 150,
                                    height: 50,
                                    decoration: BoxDecoration(
                                        color: Colors.red,
                                        borderRadius: BorderRadius.all(
                                            Radius.circular(20))),
                                    child: FlatButton(
                                        onPressed: () {
                                          Inicio.pedido.funcionario = Dashboard
                                              .repositoryFuncionario
                                              .funcionario;
                                          Mesa mesa = Mesa();
                                          mesa.id = int.parse(
                                              widget.mesaController.text);

                                          Inicio.pedido.mesa = mesa;
                                          Inicio.pedido.total =
                                              Inicio.controller.preco;
                                          Inicio.pedido.itens =
                                              Inicio.controller.itens.toList();
                                          Inicio.pedido.status = "pendente";
                                          widget.repositoryPedido
                                              .salvar(Inicio.pedido)
                                              .whenComplete(() {
                                            if (widget.repositoryPedido
                                                    .statusCode ==
                                                200) {
                                              showDialog(
                                                  context: context,
                                                  builder: (BuildContext bc) {
                                                    return AlertDialog(
                                                      title: Text("Aviso"),
                                                      content: Text(
                                                          "Pedido salvo com sucesso!"),
                                                      actions: <Widget>[
                                                        FlatButton(
                                                            onPressed: () {
                                                              Navigator.pop(
                                                                  context);
                                                              Inicio.pedido =
                                                                  null;
                                                              Inicio.controller
                                                                  .itens
                                                                  .clear();
                                                              Inicio.controller
                                                                  .preco = 0;
                                                              widget
                                                                  .mesaController
                                                                  .text = "";
                                                              Navigator.pop(
                                                                  context);
                                                            },
                                                            child: Text("Ok"))
                                                      ],
                                                    );
                                                  });
                                            } else {
                                              showDialog(
                                                  context: context,
                                                  builder: (BuildContext bc) {
                                                    return AlertDialog(
                                                      title: Text("Aviso"),
                                                      content: Text(
                                                          "Erro ao efetuar o pedido!"),
                                                      actions: <Widget>[
                                                        FlatButton(
                                                            onPressed: () {
                                                              Navigator.pop(
                                                                  context);
                                                            },
                                                            child: Text("Ok"))
                                                      ],
                                                    );
                                                  });
                                            }
                                          });
                                        },
                                        child: Text(
                                          "Finalizar",
                                          style: TextStyle(
                                              fontSize: 20,
                                              color: Colors.white),
                                        ))),
                                Container(
                                    width: 150,
                                    height: 50,
                                    decoration: BoxDecoration(
                                        border: Border.all(
                                            color: Colors.red, width: 2),
                                        color: Colors.white,
                                        borderRadius: BorderRadius.all(
                                            Radius.circular(20))),
                                    child: FlatButton(
                                        onPressed: () {
                                          Inicio.pedido = null;
                                          Inicio.controller.preco = 0;
                                          Inicio.controller.itens.clear();
                                          Navigator.pop(context);
                                          setState(() {});
                                        },
                                        child: Text(
                                          "Cancelar",
                                          style: TextStyle(
                                              fontSize: 20, color: Colors.red),
                                        )))
                              ],
                            ),
                          )
                        : Text(""),
                  ],
                ),
              ),
            ),
          );
        });
  }

  Widget tituloItensPedido() {
    return Expanded(
        child: Observer(
            builder: (_) => ListView.builder(
                itemCount: Inicio.controller.itens.length,
                itemBuilder: (context, index) => Padding(
                      padding: const EdgeInsets.only(top: 5),
                      child: GestureDetector(
                        onTap: () {
                          showDialog(
                              context: context,
                              builder: (BuildContext context) {
                                return AlertDialog(
                                  title: Text("Aviso"),
                                  content: Text("Deseja exluir o item ?"),
                                  actions: <Widget>[
                                    FlatButton(
                                        onPressed: () {
                                          Inicio.controller.preco -= Inicio
                                              .controller.itens[index].preco;
                                          Inicio.controller.excluirItem(
                                              Inicio.controller.itens[index]);
                                          Navigator.pop(context);
                                        },
                                        child: Text("Sim")),
                                    FlatButton(
                                        onPressed: () {
                                          Navigator.pop(context);
                                        },
                                        child: Text("Não"))
                                  ],
                                );
                              });
                        },
                        child: Center(
                          child: Container(
                            width: MediaQuery.of(context).size.width * 0.95,
                            height: MediaQuery.of(context).size.height * 0.06,
                            color: Color.fromRGBO(118, 226, 244, 0.7),
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.spaceAround,
                              children: <Widget>[
                                Text(
                                  "${Inicio.controller.itens[index].nome}",
                                  style: TextStyle(fontSize: 20),
                                ),
                                Text(
                                  "R\$ ${Inicio.controller.itens[index].preco.toStringAsFixed(2)}",
                                  style: TextStyle(fontSize: 20),
                                ),
                              ],
                            ),
                          ),
                        ),
                      ),
                    ))));
  }
}
