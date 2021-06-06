import 'package:flutter/cupertino.dart';
import "package:mobx/mobx.dart";
import 'package:flutter_mobx/flutter_mobx.dart';
import 'package:projeto_gerenciamento_pedido/model/Cardapio.dart';
part "controller.g.dart";

class Controller = ControllerBase with _$Controller;

abstract class ControllerBase with Store {
  @observable
  double preco = 0;

  @action
  mudarPreco(double p) {
    this.preco += p;
  }

  @observable
  ObservableList<ItemCardapio> itens = ObservableList();

  @action
  addItem(ItemCardapio item) {
    itens.add(item);
  }

  @action
  excluirItem(ItemCardapio item) {
    int j = 0;
    for (ItemCardapio i in itens) {
      if (i.nome == item.nome &&
          i.preco == item.preco &&
          i.descricao == item.descricao) {
        itens.removeAt(j);
        break;
      }
      j++;
    }
  }

  @observable
  double precoEditar = 0;

  @observable
  ObservableList<ItemCardapio> itensEditar = ObservableList();

  @action
  excluirItemEditar(ItemCardapio item) {
    int j = 0;
    for (ItemCardapio i in itensEditar) {
      if (i.nome == item.nome &&
          i.preco == item.preco &&
          i.descricao == item.descricao) {
        itensEditar.removeAt(j);
        break;
      }
      j++;
    }
  }
}
