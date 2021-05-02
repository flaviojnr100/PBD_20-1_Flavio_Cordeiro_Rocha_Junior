import 'package:flutter/material.dart';

class Inicio extends StatefulWidget {
  Inicio({Key key}) : super(key: key);

  @override
  _InicioState createState() => _InicioState();
}

class _InicioState extends State<Inicio> {
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
              Text(
                "Total: 0.00",
                style: TextStyle(fontSize: 40),
              ),
              Padding(
                padding: const EdgeInsets.only(right: 10, left: 30),
                child: Container(
                  height: mediaQuery.height * 0.06,
                  width: mediaQuery.width * 0.3,
                  decoration: BoxDecoration(
                      color: Colors.red,
                      borderRadius: BorderRadius.all(Radius.circular(30))),
                  child: FlatButton.icon(
                      onPressed: () {},
                      icon: Icon(
                        Icons.shopping_cart,
                        color: Colors.white,
                      ),
                      label: Text(
                        "Finalizar",
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
}
