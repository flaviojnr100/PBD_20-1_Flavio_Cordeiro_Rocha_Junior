import 'package:flutter/material.dart';

class Perfil extends StatelessWidget {
  const Perfil({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    var mediaQuery = MediaQuery.of(context).size;
    return Container(
        child: Column(
      mainAxisAlignment: MainAxisAlignment.start,
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        Padding(
          padding: const EdgeInsets.only(left: 50),
          child: Container(
            height: mediaQuery.height * 0.4,
            width: mediaQuery.height * 0.4,
            decoration: BoxDecoration(
                color: Colors.black12,
                borderRadius: BorderRadius.all(Radius.circular(200))),
            child: Image.asset(
              "assets/usuario.png",
              cacheHeight: 250,
              cacheWidth: 250,
            ),
          ),
        ),
        Padding(
          padding: const EdgeInsets.only(top: 10, left: 140, bottom: 50),
          child: Text(
            "Usuario",
            style: TextStyle(fontSize: 40),
          ),
        ),
        Divider(
          color: Colors.black,
          height: 5,
        ),
        Padding(
          padding: const EdgeInsets.only(left: 20),
          child: Container(
            width: mediaQuery.width * 0.9,
            height: mediaQuery.height * 0.1,
            color: Colors.white,
            child: FlatButton(
                onPressed: () {},
                child: Text(
                  "Editar informações",
                  style: TextStyle(fontSize: 30),
                )),
          ),
        ),
        Divider(
          color: Colors.black,
          height: 5,
        ),
        Padding(
          padding: const EdgeInsets.only(left: 20),
          child: Container(
            width: mediaQuery.width * 0.9,
            height: mediaQuery.height * 0.1,
            color: Colors.white,
            child: FlatButton(
                onPressed: () {},
                child: Text(
                  "Encerrar sessão",
                  style: TextStyle(fontSize: 30),
                )),
          ),
        ),
        Divider(
          color: Colors.black,
          height: 5,
        ),
      ],
    ));
  }
}
