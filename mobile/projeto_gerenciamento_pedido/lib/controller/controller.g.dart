// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'controller.dart';

// **************************************************************************
// StoreGenerator
// **************************************************************************

// ignore_for_file: non_constant_identifier_names, unnecessary_lambdas, prefer_expression_function_bodies, lines_longer_than_80_chars, avoid_as, avoid_annotating_with_dynamic

mixin _$Controller on ControllerBase, Store {
  final _$precoAtom = Atom(name: 'ControllerBase.preco');

  @override
  double get preco {
    _$precoAtom.context.enforceReadPolicy(_$precoAtom);
    _$precoAtom.reportObserved();
    return super.preco;
  }

  @override
  set preco(double value) {
    _$precoAtom.context.conditionallyRunInAction(() {
      super.preco = value;
      _$precoAtom.reportChanged();
    }, _$precoAtom, name: '${_$precoAtom.name}_set');
  }

  final _$itensAtom = Atom(name: 'ControllerBase.itens');

  @override
  ObservableList<ItemCardapio> get itens {
    _$itensAtom.context.enforceReadPolicy(_$itensAtom);
    _$itensAtom.reportObserved();
    return super.itens;
  }

  @override
  set itens(ObservableList<ItemCardapio> value) {
    _$itensAtom.context.conditionallyRunInAction(() {
      super.itens = value;
      _$itensAtom.reportChanged();
    }, _$itensAtom, name: '${_$itensAtom.name}_set');
  }

  final _$precoEditarAtom = Atom(name: 'ControllerBase.precoEditar');

  @override
  double get precoEditar {
    _$precoEditarAtom.context.enforceReadPolicy(_$precoEditarAtom);
    _$precoEditarAtom.reportObserved();
    return super.precoEditar;
  }

  @override
  set precoEditar(double value) {
    _$precoEditarAtom.context.conditionallyRunInAction(() {
      super.precoEditar = value;
      _$precoEditarAtom.reportChanged();
    }, _$precoEditarAtom, name: '${_$precoEditarAtom.name}_set');
  }

  final _$itensEditarAtom = Atom(name: 'ControllerBase.itensEditar');

  @override
  ObservableList<ItemCardapio> get itensEditar {
    _$itensEditarAtom.context.enforceReadPolicy(_$itensEditarAtom);
    _$itensEditarAtom.reportObserved();
    return super.itensEditar;
  }

  @override
  set itensEditar(ObservableList<ItemCardapio> value) {
    _$itensEditarAtom.context.conditionallyRunInAction(() {
      super.itensEditar = value;
      _$itensEditarAtom.reportChanged();
    }, _$itensEditarAtom, name: '${_$itensEditarAtom.name}_set');
  }

  final _$ControllerBaseActionController =
      ActionController(name: 'ControllerBase');

  @override
  dynamic mudarPreco(double p) {
    final _$actionInfo = _$ControllerBaseActionController.startAction();
    try {
      return super.mudarPreco(p);
    } finally {
      _$ControllerBaseActionController.endAction(_$actionInfo);
    }
  }

  @override
  dynamic addItem(ItemCardapio item) {
    final _$actionInfo = _$ControllerBaseActionController.startAction();
    try {
      return super.addItem(item);
    } finally {
      _$ControllerBaseActionController.endAction(_$actionInfo);
    }
  }

  @override
  dynamic excluirItem(ItemCardapio item) {
    final _$actionInfo = _$ControllerBaseActionController.startAction();
    try {
      return super.excluirItem(item);
    } finally {
      _$ControllerBaseActionController.endAction(_$actionInfo);
    }
  }

  @override
  dynamic excluirItemEditar(ItemCardapio item) {
    final _$actionInfo = _$ControllerBaseActionController.startAction();
    try {
      return super.excluirItemEditar(item);
    } finally {
      _$ControllerBaseActionController.endAction(_$actionInfo);
    }
  }
}
