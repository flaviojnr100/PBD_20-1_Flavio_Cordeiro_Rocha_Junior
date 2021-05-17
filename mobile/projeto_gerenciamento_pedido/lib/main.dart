import 'package:flutter/material.dart';
import 'package:projeto_gerenciamento_pedido/view/EditarPerfil.dart';
import 'package:projeto_gerenciamento_pedido/view/dashboard/dashboard.dart';
import 'package:projeto_gerenciamento_pedido/view/login.dart';
import 'package:projeto_gerenciamento_pedido/view/pedido/EditarPedido.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    Map<String, WidgetBuilder> rotas = {
      'dashboard': (context) => Dashboard(),
      'login': (context) => Login(),
      'editar': (context) => EditarPerfil(),
      'pedido': (context) => EditarPedido()
    };
    return MaterialApp(
      title: 'Flutter Demo',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or simply save your changes to "hot reload" in a Flutter IDE).
        // Notice that the counter didn't reset back to zero; the application
        // is not restarted.
        primarySwatch: Colors.blue,
      ),
      routes: rotas,
      initialRoute: "login",
    );
  }
}
