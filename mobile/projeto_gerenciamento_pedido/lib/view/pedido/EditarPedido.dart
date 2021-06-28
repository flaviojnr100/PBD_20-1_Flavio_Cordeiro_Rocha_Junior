import 'package:flutter/material.dart';
import 'package:flutter_mobx/flutter_mobx.dart';
import 'package:mobx/mobx.dart';
import 'package:projeto_gerenciamento_pedido/model/Cardapio.dart';
import 'package:projeto_gerenciamento_pedido/model/Mesa.dart';
import 'package:projeto_gerenciamento_pedido/model/Pedido.dart';
import 'package:projeto_gerenciamento_pedido/repository/RepositoryCardapio.dart';
import 'package:projeto_gerenciamento_pedido/repository/RepositoryPedido.dart';
import 'package:projeto_gerenciamento_pedido/view/cardapio/ItemCard.dart';
import 'package:projeto_gerenciamento_pedido/view/dashboard/componentes/inicio.dart';
import 'package:projeto_gerenciamento_pedido/view/dashboard/dashboard.dart';
import 'package:projeto_gerenciamento_pedido/view/pedido/ItemCardapio.dart';

class EditarPedido extends StatefulWidget {
  PedidoModel pedido;
  List<ItemCardapio> cardapio;
  EditarPedido({Key key, this.pedido}) : super(key: key);
  TextEditingController controllerMesa = TextEditingController();
  RepositoryPedido repositoryPedido = RepositoryPedido();
  RepositoryCardapio repositoryCardapio = RepositoryCardapio();
  @override
  _EditarPedidoState createState() => _EditarPedidoState();
}

class _EditarPedidoState extends State<EditarPedido> {
  var itemSelecionado = "pendente";
  var _lista = ["pendente", "cancelado", "concluido"];
  @override
  void initState() {
    widget.repositoryCardapio.buscarTodos().whenComplete(() {
      widget.cardapio = widget.repositoryCardapio.cardapio;
    });
    widget.controllerMesa.text = widget.pedido.mesa.numero.toString();

    Inicio.controller.precoEditar = widget.pedido.total;
    Inicio.controller.itensEditar = ObservableList.of(widget.pedido.itens);
    for (var item in _lista) {
      if (item == widget.pedido.status) {
        itemSelecionado = item;
      }
    }
    setState(() {});
    super.initState();
  }

  Widget exibirItens(context) {
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
                      width: MediaQuery.of(context).size.width * 0.95,
                      height: MediaQuery.of(context).size.height * 0.50,
                      color: Colors.white,
                      child: ListView.builder(
                          itemCount: widget.cardapio.length,
                          scrollDirection: Axis.vertical,
                          itemBuilder: (context, index) =>
                              ItemCardapioCard(item: widget.cardapio[index])),
                    ),
                  ],
                ),
              ),
            ),
          );
        });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text("Pedido"),
          backgroundColor: Color.fromRGBO(129, 126, 240, 1),
        ),
        body: Stack(
          children: <Widget>[
            Positioned(
              left: MediaQuery.of(context).size.width * 0.4,
              child: Text(
                "Itens",
                style: TextStyle(fontSize: 40),
              ),
            ),
            Positioned(
                top: 50,
                left: 4,
                child: Container(
                  width: MediaQuery.of(context).size.width * 0.98,
                  height: MediaQuery.of(context).size.height * 0.5,
                  color: Colors.white,
                  child: Observer(
                      builder: (_) => ListView.builder(
                            itemCount: Inicio.controller.itensEditar.length,
                            itemBuilder: (context, index) => Padding(
                                padding: const EdgeInsets.only(top: 5),
                                child: GestureDetector(
                                  onTap: () {
                                    showDialog(
                                        context: context,
                                        builder: (BuildContext bc) {
                                          return AlertDialog(
                                            title: Text("Aviso"),
                                            content: Text(
                                                "Deseja excluir esse item do pedido ?"),
                                            actions: <Widget>[
                                              FlatButton(
                                                  onPressed: () {
                                                    Inicio.controller
                                                            .precoEditar -=
                                                        Inicio
                                                            .controller
                                                            .itensEditar[index]
                                                            .preco;
                                                    Inicio
                                                        .controller.itensEditar
                                                        .removeAt(index);

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
                                  child: Container(
                                    width: MediaQuery.of(context).size.width *
                                        0.95,
                                    height: MediaQuery.of(context).size.height *
                                        0.08,
                                    color: Color.fromRGBO(118, 226, 244, 0.7),
                                    child: Row(
                                      mainAxisAlignment:
                                          MainAxisAlignment.spaceEvenly,
                                      children: <Widget>[
                                        Text(
                                          "Nome: ${Inicio.controller.itensEditar[index].nome}",
                                          style: TextStyle(fontSize: 20),
                                        ),
                                        Text(
                                            "R\$ ${Inicio.controller.itensEditar[index].preco.toStringAsFixed(2)}")
                                      ],
                                    ),
                                  ),
                                )),
                          )),
                )),
            Positioned(
                bottom: 10,
                left: 10,
                child: Container(
                  height: MediaQuery.of(context).size.height * 0.33,
                  width: MediaQuery.of(context).size.width * 1,
                  child: Column(
                    children: <Widget>[
                      Row(
                        children: <Widget>[
                          Text(
                            "Status:  ",
                            style: TextStyle(fontSize: 20),
                          ),
                          DropdownButton<String>(
                              items: _lista.map((String dropDownStringItem) {
                                return DropdownMenuItem<String>(
                                  value: dropDownStringItem,
                                  child: Text(dropDownStringItem),
                                );
                              }).toList(),
                              onChanged: (String novoItemSelecionado) {
                                setState(() {
                                  this.itemSelecionado = novoItemSelecionado;
                                });
                              },
                              value: itemSelecionado),
                          Padding(
                            padding: const EdgeInsets.only(left: 20),
                            child: Container(
                              decoration: BoxDecoration(
                                  color: Color.fromRGBO(118, 226, 244, 0.7),
                                  borderRadius:
                                      BorderRadius.all(Radius.circular(30))),
                              child: FlatButton.icon(
                                  onPressed: () {
                                    exibirItens(context);
                                  },
                                  icon: Icon(Icons.add),
                                  label: Text("Adicionar")),
                            ),
                          )
                        ],
                      ),
                      Row(
                        children: <Widget>[
                          Text(
                            "Mesa: ",
                            style: TextStyle(fontSize: 20),
                          ),
                          Container(
                            height: MediaQuery.of(context).size.height * 0.06,
                            width: MediaQuery.of(context).size.width * 0.2,
                            decoration: BoxDecoration(
                                color: Color.fromRGBO(118, 226, 244, 0.7),
                                borderRadius:
                                    BorderRadius.all(Radius.circular(20))),
                            child: Padding(
                              padding: const EdgeInsets.only(left: 8),
                              child: TextFormField(
                                controller: widget.controllerMesa,
                                decoration:
                                    InputDecoration(border: InputBorder.none),
                              ),
                            ),
                          )
                        ],
                      ),
                      Padding(
                        padding: const EdgeInsets.only(top: 30),
                        child: Row(
                          children: <Widget>[
                            Observer(
                                builder: (_) => Text(
                                      "Total: R\$${Inicio.controller.precoEditar.toStringAsFixed(2)}",
                                      style: TextStyle(fontSize: 40),
                                    ))
                          ],
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.only(top: 15),
                        child: Container(
                          height: MediaQuery.of(context).size.height * 0.06,
                          width: MediaQuery.of(context).size.width * 0.25,
                          decoration: BoxDecoration(
                              color: Color.fromRGBO(129, 126, 240, 1),
                              borderRadius:
                                  BorderRadius.all(Radius.circular(30))),
                          child: FlatButton(
                              onPressed: () {
                                showDialog(
                                    context: context,
                                    builder: (BuildContext bc) {
                                      return AlertDialog(
                                        title: Text("Aviso"),
                                        content:
                                            Text("Deseja alterar o pedido ?"),
                                        actions: <Widget>[
                                          FlatButton(
                                              onPressed: () {
                                                widget.pedido.itens = Inicio
                                                    .controller.itensEditar
                                                    .toList();
                                                Mesa mesa = Mesa();
                                                mesa.numero = int.parse(
                                                    widget.controllerMesa.text);
                                                widget.pedido.mesa = mesa;
                                                widget.pedido.status =
                                                    this.itemSelecionado;
                                                widget.pedido.total = Inicio
                                                    .controller.precoEditar;
                                                widget.repositoryPedido
                                                    .editar(widget.pedido)
                                                    .whenComplete(() {
                                                  if (widget.repositoryPedido
                                                          .statusCode ==
                                                      200) {
                                                    showDialog(
                                                        context: context,
                                                        builder:
                                                            (BuildContext bc) {
                                                          return AlertDialog(
                                                            title:
                                                                Text("Aviso"),
                                                            content: Text(
                                                                "Pedido alterado com sucesso!"),
                                                            actions: <Widget>[
                                                              FlatButton(
                                                                  onPressed:
                                                                      () {
                                                                    Navigator.pop(
                                                                        context);
                                                                    Navigator.pop(
                                                                        context);
                                                                    Navigator.pop(
                                                                        context);
                                                                  },
                                                                  child: Text(
                                                                      "Ok"))
                                                            ],
                                                          );
                                                        });
                                                  } else {
                                                    showDialog(
                                                        context: context,
                                                        builder:
                                                            (BuildContext bc) {
                                                          return AlertDialog(
                                                            title:
                                                                Text("Aviso"),
                                                            content: Text(
                                                                "Erro ao alterar o pedido"),
                                                            actions: <Widget>[
                                                              FlatButton(
                                                                  onPressed:
                                                                      () {
                                                                    Navigator.pop(
                                                                        context);
                                                                  },
                                                                  child: Text(
                                                                      "Ok"))
                                                            ],
                                                          );
                                                        });
                                                  }
                                                });
                                              },
                                              child: Text("sim")),
                                          FlatButton(
                                              onPressed: () {
                                                Navigator.pop(context);
                                              },
                                              child: Text("não"))
                                        ],
                                      );
                                    });
                              },
                              child: Text(
                                "Salvar",
                                style: TextStyle(
                                    fontSize: 20, color: Colors.white),
                              )),
                        ),
                      )
                    ],
                  ),
                ))
          ],
        ));
  }
}
