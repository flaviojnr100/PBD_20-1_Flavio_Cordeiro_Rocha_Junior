import 'dart:convert';

import 'package:crypto/crypto.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:projeto_gerenciamento_pedido/main.dart';
import 'package:projeto_gerenciamento_pedido/repository/RepositoryFuncionario.dart';
import 'package:projeto_gerenciamento_pedido/view/dashboard/dashboard.dart';

class Login extends StatelessWidget {
  Login({Key key}) : super(key: key);
  TextEditingController login = TextEditingController();
  TextEditingController senha = TextEditingController();
  RepositoryFuncionario repositoryFuncionario = new RepositoryFuncionario();

  @override
  Widget build(BuildContext context) {
    var mediaQuery = MediaQuery.of(context).size;
    return Scaffold(
      backgroundColor: Colors.white,
      body: Stack(
        children: <Widget>[
          Positioned(
            top: mediaQuery.height * 0.12,
            left: -mediaQuery.width * 0.03,
            width: mediaQuery.width,
            child: Image.asset(
              "assets/logo.png",
              width: 500,
            ),
          ),
          Positioned(
            top: mediaQuery.height * 0.4,
            left: mediaQuery.width * 0.05,
            child: Container(
              height: mediaQuery.height * 0.4,
              width: mediaQuery.width * 0.9,
              decoration: BoxDecoration(
                  color: Color.fromRGBO(118, 226, 244, 0.7),
                  borderRadius: BorderRadius.all(Radius.circular(15))),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  Container(
                    height: mediaQuery.height * 0.08,
                    width: mediaQuery.width * 0.8,
                    decoration: BoxDecoration(
                        color: Colors.white,
                        borderRadius: BorderRadius.all(Radius.circular(40))),
                    child: Padding(
                      padding: const EdgeInsets.only(left: 15, top: 2),
                      child: TextFormField(
                        controller: login,
                        keyboardType: TextInputType.text,
                        cursorColor: Colors.black12,
                        style: TextStyle(fontSize: 30),
                        decoration: InputDecoration(
                            hintStyle: TextStyle(fontSize: 25),
                            icon: Icon(
                              Icons.person,
                              size: 35,
                              color: Colors.black26,
                            ),
                            hintText: "Usu??rio",
                            border: InputBorder.none),
                      ),
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.only(top: 20),
                    child: Container(
                      height: mediaQuery.height * 0.08,
                      width: mediaQuery.width * 0.8,
                      decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius: BorderRadius.all(Radius.circular(40))),
                      child: Padding(
                        padding: const EdgeInsets.only(left: 15, top: 2),
                        child: TextFormField(
                          controller: senha,
                          cursorColor: Colors.black12,
                          keyboardType: TextInputType.visiblePassword,
                          obscureText: true,
                          style: TextStyle(fontSize: 30),
                          decoration: InputDecoration(
                              hintStyle: TextStyle(fontSize: 25),
                              icon: Icon(
                                Icons.https,
                                size: 35,
                                color: Colors.black26,
                              ),
                              hintText: "Senha",
                              border: InputBorder.none),
                        ),
                      ),
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.only(top: 15),
                    child: GestureDetector(
                      onTap: () {
                        if (login.text != "") {
                          repositoryFuncionario
                              .buscarLoginUnico(login.text)
                              .whenComplete(() {
                            print(repositoryFuncionario.statusCode);
                            if (repositoryFuncionario.statusCode == 202) {
                              repositoryFuncionario
                                  .reset(repositoryFuncionario.id);
                              showDialog(
                                  context: context,
                                  builder: (BuildContext context) {
                                    return AlertDialog(
                                      title: Text("Aviso"),
                                      content: Text(
                                          "Aguarde o administrador resetar a senha!"),
                                      actions: <Widget>[
                                        FlatButton(
                                          onPressed: () {
                                            Navigator.pop(context);
                                          },
                                          child: Text("Ok"),
                                        ),
                                      ],
                                    );
                                  });
                            } else {
                              showDialog(
                                  context: context,
                                  builder: (BuildContext context) {
                                    return AlertDialog(
                                      title: Text("Aviso"),
                                      content: Text(
                                          "N??o existe esse usu??rio na base de dados!"),
                                      actions: <Widget>[
                                        FlatButton(
                                          onPressed: () {
                                            Navigator.pop(context);
                                          },
                                          child: Text("Ok"),
                                        ),
                                      ],
                                    );
                                  });
                            }
                          });
                        }
                      },
                      child: Text(
                        "Esque??eu a senha ?",
                        style: TextStyle(
                            color: Colors.black,
                            fontSize: 20,
                            decoration: TextDecoration.underline),
                      ),
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.only(top: 20),
                    child: Container(
                      height: mediaQuery.height * 0.07,
                      width: mediaQuery.width * 0.32,
                      decoration: BoxDecoration(
                          color: Color.fromRGBO(129, 126, 240, 0.8),
                          borderRadius: BorderRadius.all(Radius.circular(30))),
                      child: FlatButton(
                        onPressed: () => {
                          repositoryFuncionario
                              .autenticar(
                                  login.text.trim(),
                                  md5
                                      .convert(utf8.encode(senha.text))
                                      .toString()
                                      .toUpperCase())
                              .whenComplete(() {
                            if (repositoryFuncionario.funcionario != null &&
                                repositoryFuncionario.funcionario.isReset ==
                                    true) {
                              showDialog(
                                  context: context,
                                  builder: (BuildContext context) {
                                    return AlertDialog(
                                      title: Text("Aviso"),
                                      content: Text(
                                          "A senha desse usu??rio for resetado para o modo padr??o!"),
                                      actions: <Widget>[
                                        FlatButton(
                                          onPressed: () {
                                            Navigator.pop(context);
                                          },
                                          child: Text("Ok"),
                                        ),
                                      ],
                                    );
                                  });
                            }
                            if (repositoryFuncionario.statusCode == 202 &&
                                repositoryFuncionario.funcionario.senha !=
                                    null) {
                              repositoryFuncionario.funcionario.isLogado = true;
                              if (repositoryFuncionario.funcionario.isReset) {
                                repositoryFuncionario.funcionario.isReset =
                                    false;
                              }

                              repositoryFuncionario
                                  .editar(repositoryFuncionario.funcionario);

                              Dashboard.repositoryFuncionario =
                                  repositoryFuncionario;
                              Navigator.of(context).pushAndRemoveUntil(
                                  MaterialPageRoute(
                                      builder: (context) => Dashboard()),
                                  (Route<dynamic> route) => false);
                            } else if (repositoryFuncionario.statusCode ==
                                403) {
                              //usuario logado
                              showDialog(
                                  context: context,
                                  builder: (BuildContext context) {
                                    return AlertDialog(
                                      title: Text("Aviso"),
                                      content: Text(
                                          "Esse usu??rio j?? esta logado no sistema!"),
                                      actions: <Widget>[
                                        FlatButton(
                                          onPressed: () {
                                            Navigator.pop(context);
                                          },
                                          child: Text("Ok"),
                                        ),
                                      ],
                                    );
                                  });
                            } else if (repositoryFuncionario.statusCode ==
                                    202 &&
                                repositoryFuncionario.funcionario.senha ==
                                    null) {
                              //usuario invalido
                              showDialog(
                                  context: context,
                                  builder: (BuildContext context) {
                                    return AlertDialog(
                                      title: Text("Aviso"),
                                      content: Text("Usu??rio invalido!"),
                                      actions: <Widget>[
                                        FlatButton(
                                          onPressed: () {
                                            Navigator.pop(context);
                                          },
                                          child: Text("Ok"),
                                        ),
                                      ],
                                    );
                                  });
                            } else if (repositoryFuncionario.statusCode ==
                                400) {
                              //usuario sem permissao
                              showDialog(
                                  context: context,
                                  builder: (BuildContext context) {
                                    return AlertDialog(
                                      title: Text("Aviso"),
                                      content: Text(
                                          "Usu??rio sem permiss??o de acessar o sistema!"),
                                      actions: <Widget>[
                                        FlatButton(
                                          onPressed: () {
                                            Navigator.pop(context);
                                          },
                                          child: Text("Ok"),
                                        ),
                                      ],
                                    );
                                  });
                            } else if (repositoryFuncionario.statusCode ==
                                500) {
                              //usuario nao cadastrado
                              showDialog(
                                  context: context,
                                  builder: (BuildContext context) {
                                    return AlertDialog(
                                      title: Text("Aviso"),
                                      content: Text(
                                          "Usu??rio n??o cadastrado no sistema!"),
                                      actions: <Widget>[
                                        FlatButton(
                                          onPressed: () {
                                            Navigator.pop(context);
                                          },
                                          child: Text("Ok"),
                                        ),
                                      ],
                                    );
                                  });
                            }
                          })
                        },
                        child: Text(
                          "Entrar",
                          style: TextStyle(fontSize: 22, color: Colors.white),
                        ),
                      ),
                    ),
                  )
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}
