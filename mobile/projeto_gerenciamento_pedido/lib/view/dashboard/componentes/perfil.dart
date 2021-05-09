import 'package:flutter/material.dart';
import 'package:projeto_gerenciamento_pedido/repository/RepositoryFuncionario.dart';
import 'package:projeto_gerenciamento_pedido/view/dashboard/dashboard.dart';

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
                  showDialog(
                      context: context,
                      builder: (BuildContext context) {
                        return AlertDialog(
                          title: Text("Aviso"),
                          content: Text("Deseja sair do sistema ?"),
                          actions: <Widget>[
                            FlatButton(
                              onPressed: () {
                                Dashboard.repositoryFuncionario
                                    .logout(Dashboard.repositoryFuncionario
                                        .funcionario.login)
                                    .whenComplete(() {
                                  if (Dashboard
                                          .repositoryFuncionario.statusCode ==
                                      200) {
                                    Dashboard.repositoryFuncionario = null;
                                    Navigator.popAndPushNamed(context, "login");
                                  }
                                });
                              },
                              child: Text("Sim"),
                            ),
                            FlatButton(
                              onPressed: () {
                                Navigator.pop(context);
                              },
                              child: Text("Não"),
                            ),
                          ],
                        );
                      });
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
}
