import 'package:flutter/material.dart';
import 'package:projeto_gerenciamento_pedido/view/dashboard/componentes/inicio.dart';
import 'package:projeto_gerenciamento_pedido/view/dashboard/componentes/pedido.dart';
import 'package:projeto_gerenciamento_pedido/view/dashboard/componentes/perfil.dart';

class Dashboard extends StatefulWidget {
  Dashboard({Key key}) : super(key: key);

  @override
  _DashboardState createState() => _DashboardState();
}

class _DashboardState extends State<Dashboard> {
  int index = 0;
  List<Widget> widgets = <Widget>[Perfil(), Inicio(), Pedido()];
  void _mudarMenu(int i) {
    setState(() {
      index = i;
    });
  }

  @override
  void initState() {
    _mudarMenu(1);
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    var mediaQuery = MediaQuery.of(context).size;
    return Scaffold(
        appBar: AppBar(
          title: Text("Dashboard"),
          backgroundColor: Color.fromRGBO(129, 126, 240, 1),
        ),
        bottomNavigationBar: BottomNavigationBar(
          items: const <BottomNavigationBarItem>[
            BottomNavigationBarItem(
                icon: Icon(Icons.person), title: Text("Perfil")),
            BottomNavigationBarItem(
                icon: Icon(Icons.home), title: Text("Incio")),
            BottomNavigationBarItem(
                icon: Icon(Icons.list), title: Text("Pedido")),
          ],
          currentIndex: index,
          onTap: _mudarMenu,
          selectedItemColor: Color.fromRGBO(129, 126, 240, 1),
          backgroundColor: Colors.white60,
        ),
        body: Column(
          children: <Widget>[widgets.elementAt(index)],
        ));
  }
}
