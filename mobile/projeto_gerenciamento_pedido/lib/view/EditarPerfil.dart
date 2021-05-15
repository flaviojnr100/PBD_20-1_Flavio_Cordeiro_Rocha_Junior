import 'dart:convert';

import 'package:crypto/crypto.dart';
import 'package:flutter/material.dart';
import 'package:projeto_gerenciamento_pedido/view/dashboard/dashboard.dart';

class EditarPerfil extends StatelessWidget {
  EditarPerfil({Key key}) : super(key: key);
  TextEditingController nomeC = new TextEditingController();
  TextEditingController sobrenomeC = new TextEditingController();
  TextEditingController cpfC = new TextEditingController();
  TextEditingController telefoneC = new TextEditingController();
  TextEditingController loginC = new TextEditingController();
  TextEditingController senhaC = new TextEditingController();
  TextEditingController senhaDC = new TextEditingController();

  @override
  Widget build(BuildContext context) {
    nomeC.text = Dashboard.repositoryFuncionario.funcionario.nome;
    sobrenomeC.text = Dashboard.repositoryFuncionario.funcionario.sobrenome;
    telefoneC.text = Dashboard.repositoryFuncionario.funcionario.telefone;
    cpfC.text = Dashboard.repositoryFuncionario.funcionario.cpf;
    loginC.text = Dashboard.repositoryFuncionario.funcionario.login;

    var mediaQuery = MediaQuery.of(context).size;
    return Scaffold(
      appBar: AppBar(
          title: Text("Editar informações"),
          backgroundColor: Color.fromRGBO(129, 126, 240, 1)),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.start,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          Padding(
            padding: const EdgeInsets.only(top: 30),
            child: Row(
              children: <Widget>[
                Text(
                  "Nome:                     ",
                  style: TextStyle(fontSize: 20),
                ),
                Container(
                  height: mediaQuery.height * 0.05,
                  width: mediaQuery.width * 0.58,
                  decoration: BoxDecoration(
                      color: Colors.black12,
                      borderRadius: BorderRadius.all(Radius.circular(15))),
                  child: Padding(
                    padding: const EdgeInsets.only(left: 10),
                    child: TextFormField(
                      controller: nomeC,
                      style: TextStyle(fontSize: 25),
                      decoration: InputDecoration(border: InputBorder.none),
                    ),
                  ),
                )
              ],
            ),
          ),
          Padding(
            padding: const EdgeInsets.only(top: 5),
            child: Row(
              children: <Widget>[
                Text(
                  "Sobrenome:           ",
                  style: TextStyle(fontSize: 20),
                ),
                Container(
                  height: mediaQuery.height * 0.05,
                  width: mediaQuery.width * 0.58,
                  decoration: BoxDecoration(
                      color: Colors.black12,
                      borderRadius: BorderRadius.all(Radius.circular(15))),
                  child: Padding(
                    padding: const EdgeInsets.only(left: 10),
                    child: TextFormField(
                      controller: sobrenomeC,
                      style: TextStyle(fontSize: 25),
                      decoration: InputDecoration(border: InputBorder.none),
                    ),
                  ),
                )
              ],
            ),
          ),
          Padding(
            padding: const EdgeInsets.only(top: 5),
            child: Row(
              children: <Widget>[
                Text(
                  "CPF:                        ",
                  style: TextStyle(fontSize: 20),
                ),
                Container(
                  height: mediaQuery.height * 0.05,
                  width: mediaQuery.width * 0.58,
                  decoration: BoxDecoration(
                      color: Colors.black12,
                      borderRadius: BorderRadius.all(Radius.circular(15))),
                  child: Padding(
                    padding: const EdgeInsets.only(left: 10),
                    child: TextFormField(
                      controller: cpfC,
                      style: TextStyle(fontSize: 25),
                      decoration: InputDecoration(border: InputBorder.none),
                    ),
                  ),
                )
              ],
            ),
          ),
          Padding(
            padding: const EdgeInsets.only(top: 5),
            child: Row(
              children: <Widget>[
                Text(
                  "Telefone:                ",
                  style: TextStyle(fontSize: 20),
                ),
                Container(
                  height: mediaQuery.height * 0.05,
                  width: mediaQuery.width * 0.58,
                  decoration: BoxDecoration(
                      color: Colors.black12,
                      borderRadius: BorderRadius.all(Radius.circular(15))),
                  child: Padding(
                    padding: const EdgeInsets.only(left: 10),
                    child: TextFormField(
                      controller: telefoneC,
                      style: TextStyle(fontSize: 25),
                      decoration: InputDecoration(border: InputBorder.none),
                    ),
                  ),
                )
              ],
            ),
          ),
          Padding(
            padding: const EdgeInsets.only(top: 5),
            child: Row(
              children: <Widget>[
                Text(
                  "Login:                      ",
                  style: TextStyle(fontSize: 20),
                ),
                Container(
                  height: mediaQuery.height * 0.05,
                  width: mediaQuery.width * 0.58,
                  decoration: BoxDecoration(
                      color: Colors.black12,
                      borderRadius: BorderRadius.all(Radius.circular(15))),
                  child: Padding(
                    padding: const EdgeInsets.only(left: 10),
                    child: TextFormField(
                      controller: loginC,
                      style: TextStyle(fontSize: 25),
                      decoration: InputDecoration(border: InputBorder.none),
                    ),
                  ),
                )
              ],
            ),
          ),
          Padding(
            padding: const EdgeInsets.only(top: 5),
            child: Row(
              children: <Widget>[
                Text(
                  "Senha:                     ",
                  style: TextStyle(fontSize: 20),
                ),
                Container(
                  height: mediaQuery.height * 0.05,
                  width: mediaQuery.width * 0.58,
                  decoration: BoxDecoration(
                      color: Colors.black12,
                      borderRadius: BorderRadius.all(Radius.circular(15))),
                  child: Padding(
                    padding: const EdgeInsets.only(left: 10),
                    child: TextFormField(
                      controller: senhaC,
                      obscureText: true,
                      style: TextStyle(fontSize: 25),
                      decoration: InputDecoration(border: InputBorder.none),
                    ),
                  ),
                )
              ],
            ),
          ),
          Padding(
            padding: const EdgeInsets.only(top: 5),
            child: Row(
              children: <Widget>[
                Text(
                  "Senha novamente:",
                  style: TextStyle(fontSize: 20),
                ),
                Container(
                  height: mediaQuery.height * 0.05,
                  width: mediaQuery.width * 0.58,
                  decoration: BoxDecoration(
                      color: Colors.black12,
                      borderRadius: BorderRadius.all(Radius.circular(15))),
                  child: Padding(
                    padding: const EdgeInsets.only(left: 10),
                    child: TextFormField(
                      controller: senhaDC,
                      obscureText: true,
                      style: TextStyle(fontSize: 25),
                      decoration: InputDecoration(border: InputBorder.none),
                    ),
                  ),
                )
              ],
            ),
          ),
          Padding(
            padding: const EdgeInsets.only(top: 20),
            child: Container(
              height: mediaQuery.height * 0.06,
              width: mediaQuery.width * 0.25,
              decoration: BoxDecoration(
                  color: Color.fromRGBO(129, 126, 240, 0.8),
                  borderRadius: BorderRadius.all(Radius.circular(20))),
              child: FlatButton(
                  onPressed: () {
                    showDialog(
                        context: context,
                        builder: (BuildContext context) {
                          return AlertDialog(
                            title: Text("Aviso"),
                            content: Text("Deseja salvar as alterações ?"),
                            actions: <Widget>[
                              FlatButton(
                                onPressed: () {
                                  if (senhaC.text == senhaDC.text) {
                                    Dashboard.repositoryFuncionario.funcionario
                                        .nome = nomeC.text;
                                    Dashboard.repositoryFuncionario.funcionario
                                        .sobrenome = sobrenomeC.text;
                                    Dashboard.repositoryFuncionario.funcionario
                                        .telefone = telefoneC.text;
                                    Dashboard.repositoryFuncionario.funcionario
                                        .cpf = cpfC.text;
                                    Dashboard.repositoryFuncionario.funcionario
                                        .login = loginC.text;
                                    Dashboard.repositoryFuncionario.funcionario
                                            .senha =
                                        md5
                                            .convert(utf8.encode(senhaC.text))
                                            .toString()
                                            .toUpperCase();
                                    Dashboard.repositoryFuncionario
                                        .editar(Dashboard
                                            .repositoryFuncionario.funcionario)
                                        .whenComplete(() {
                                      Navigator.pop(context);
                                      Navigator.pop(context);
                                    });
                                  } else {
                                    showDialog(
                                        context: context,
                                        builder: (BuildContext context) {
                                          return AlertDialog(
                                            title: Text("Aviso"),
                                            content: Text("Senhas diferentes!"),
                                            actions: <Widget>[
                                              FlatButton(
                                                onPressed: () {
                                                  Navigator.of(context).pop();
                                                },
                                                child: Text("ok"),
                                              ),
                                            ],
                                          );
                                        });
                                  }
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
