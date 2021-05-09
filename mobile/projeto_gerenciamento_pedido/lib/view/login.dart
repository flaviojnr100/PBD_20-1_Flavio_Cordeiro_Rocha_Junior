import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
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
                            hintText: "Usuário",
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
                      onTap: () {},
                      child: Text(
                        "Esqueçeu a senha ?",
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
                              .autenticar(login.text.trim(), senha.text.trim())
                              .whenComplete(() {
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
                              Navigator.pushReplacementNamed(
                                  context, 'dashboard');
                            } else if (repositoryFuncionario.statusCode ==
                                403) {
                              //usuario logado
                              showDialog(
                                  context: context,
                                  builder: (BuildContext context) {
                                    return AlertDialog(
                                      title: Text("Aviso"),
                                      content: Text(
                                          "Esse usuário já esta logado no sistema!"),
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
                                      content: Text("Usuário invalido!"),
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
                              //usuario sem permissao
                              showDialog(
                                  context: context,
                                  builder: (BuildContext context) {
                                    return AlertDialog(
                                      title: Text("Aviso"),
                                      content: Text(
                                          "Usuário sem permissão de acessar o sistema!"),
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
