import 'package:flutter/material.dart';

class EditarPedido extends StatefulWidget {
  EditarPedido({Key key}) : super(key: key);

  @override
  _EditarPedidoState createState() => _EditarPedidoState();
}

class _EditarPedidoState extends State<EditarPedido> {
  var itemSelecionado = "pendente";
  var _lista = ["pendente", "cancelado", "concluido"];
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Pedido"),
        backgroundColor: Color.fromRGBO(129, 126, 240, 1),
      ),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          Text(
            "Itens",
            style: TextStyle(fontSize: 40),
          ),
          Padding(
            padding: const EdgeInsets.only(top: 400, left: 140),
            child: Row(
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
              ],
            ),
          ),
          Row(
            children: <Widget>[
              Text(
                "Mesa: ",
                style: TextStyle(fontSize: 20),
              ),
              Container(
                height: MediaQuery.of(context).size.height * 0.06,
                width: MediaQuery.of(context).size.width * 0.8,
                decoration: BoxDecoration(
                    color: Colors.black12,
                    borderRadius: BorderRadius.all(Radius.circular(20))),
                child: TextFormField(
                  decoration: InputDecoration(border: InputBorder.none),
                ),
              )
            ],
          ),
          Padding(
            padding: const EdgeInsets.only(top: 30),
            child: Row(
              children: <Widget>[
                Text(
                  "Total: 0,00",
                  style: TextStyle(fontSize: 40),
                ),
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
                  borderRadius: BorderRadius.all(Radius.circular(30))),
              child: FlatButton(
                  onPressed: () {},
                  child: Text(
                    "Salvar",
                    style: TextStyle(fontSize: 20, color: Colors.white),
                  )),
            ),
          )
        ],
      ),
    );
  }
}
