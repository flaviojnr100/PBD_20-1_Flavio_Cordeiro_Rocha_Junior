import 'package:flutter/material.dart';
import 'package:projeto_gerenciamento_pedido/repository/RepositoryFuncionario.dart';
import 'package:projeto_gerenciamento_pedido/view/dashboard/dashboard.dart';
import 'package:projeto_gerenciamento_pedido/view/login.dart';

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
          padding: const EdgeInsets.only(top: 10, bottom: 50),
          child: Center(
            child: Text(
              "${Dashboard.repositoryFuncionario.funcionario.nome != null ? Dashboard.repositoryFuncionario.funcionario.nome : ""} ${Dashboard.repositoryFuncionario.funcionario.sobrenome != null ? Dashboard.repositoryFuncionario.funcionario.sobrenome : ""} ",
              style: TextStyle(fontSize: 40),
            ),
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
                onPressed: () {
                  Navigator.of(context).pushNamed('editar');
                },
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
                onPressed: () {
                  _sair(context);
                },
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

  void _sair(context) {
    showModalBottomSheet(
        context: context,
        builder: (build) {
          return Wrap(children: <Widget>[
            Container(
              color: Colors.black54,
              child: Center(
                child: Container(
                  width: MediaQuery.of(context).size.width * 0.98,
                  decoration: BoxDecoration(
                      color: Colors.white,
                      borderRadius: BorderRadius.only(
                          topLeft: Radius.circular(10),
                          topRight: Radius.circular(10))),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: <Widget>[
                      Padding(
                        padding: const EdgeInsets.only(top: 10, bottom: 22),
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
                      Padding(
                        padding: const EdgeInsets.only(bottom: 20),
                        child: Center(
                          child: Text(
                            "Deseja encerrar a sessão ?",
                            style: TextStyle(fontSize: 25),
                          ),
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.only(left: 45, bottom: 35),
                        child: Row(
                          children: <Widget>[
                            Container(
                                height:
                                    MediaQuery.of(context).size.height * 0.07,
                                width: MediaQuery.of(context).size.height * 0.2,
                                decoration: BoxDecoration(
                                  color: Color.fromRGBO(129, 126, 240, 0.8),
                                ),
                                child: FlatButton(
                                    onPressed: () {
                                      Dashboard.repositoryFuncionario
                                          .logout(Dashboard
                                              .repositoryFuncionario
                                              .funcionario
                                              .login)
                                          .whenComplete(() {
                                        if (Dashboard.repositoryFuncionario
                                                .statusCode ==
                                            200) {
                                          Dashboard.repositoryFuncionario =
                                              null;
                                          Navigator.of(context)
                                              .pushAndRemoveUntil(
                                                  MaterialPageRoute(
                                                      builder: (context) =>
                                                          Login()),
                                                  (Route<dynamic> route) =>
                                                      false);
                                        }
                                      });
                                    },
                                    child: Text(
                                      "Sim",
                                      style: TextStyle(
                                          fontSize: 20, color: Colors.white),
                                    ))),
                            Padding(
                              padding: const EdgeInsets.only(left: 15),
                              child: Container(
                                  height:
                                      MediaQuery.of(context).size.height * 0.07,
                                  width:
                                      MediaQuery.of(context).size.height * 0.2,
                                  decoration: BoxDecoration(
                                    color: Color.fromRGBO(129, 126, 240, 0.8),
                                  ),
                                  child: FlatButton(
                                      onPressed: () {
                                        Navigator.pop(context);
                                      },
                                      child: Text(
                                        "Não",
                                        style: TextStyle(
                                            fontSize: 20, color: Colors.white),
                                      ))),
                            )
                          ],
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ),
          ]);
        });
  }
}
