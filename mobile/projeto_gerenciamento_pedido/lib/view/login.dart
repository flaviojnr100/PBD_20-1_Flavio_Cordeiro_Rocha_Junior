import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class Login extends StatelessWidget {
  Login({Key key}) : super(key: key);
  TextEditingController login = TextEditingController();
  TextEditingController senha = TextEditingController();

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
                        onPressed: () => {},
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
