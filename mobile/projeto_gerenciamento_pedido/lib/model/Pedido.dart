import 'package:projeto_gerenciamento_pedido/model/Cardapio.dart';

import 'Funcionario.dart';
import 'Mesa.dart';

class PedidoModel {
  int id;
  Funcionario funcionario;
  double total;
  Mesa mesa;
  String dataPedido;
  String status;
  List<ItemCardapio> itens;

  PedidoModel(
      {this.id,
      this.funcionario,
      this.total,
      this.mesa,
      this.dataPedido,
      this.status,
      this.itens});

  PedidoModel.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    funcionario = json['funcionario'] != null
        ? new Funcionario.fromJson(json['funcionario'])
        : null;
    total = json['total'];
    mesa = json['mesa'] != null ? new Mesa.fromJson(json['mesa']) : null;
    dataPedido = json['dataPedido'];
    status = json['status'];
    if (json['itens'] != null) {
      itens = new List<ItemCardapio>();
      json['itens'].forEach((v) {
        itens.add(new ItemCardapio.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    if (this.funcionario != null) {
      data['funcionario'] = this.funcionario.toJson();
    }
    data['total'] = this.total;
    if (this.mesa != null) {
      data['mesa'] = this.mesa.toJson();
    }
    data['dataPedido'] = this.dataPedido;
    data['status'] = this.status;
    if (this.itens != null) {
      data['itens'] = this.itens.map((v) => v.toJson()).toList();
    }
    return data;
  }
}
