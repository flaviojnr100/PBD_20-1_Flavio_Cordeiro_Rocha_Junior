import 'dart:convert';

class Validador {
  static bool verificarNumero(String senha) {
    //ascii 48-57 numeros
    AsciiCodec ac = new AsciiCodec();
    bool estado = false;
    for (int i = 0; i < senha.length - 1; i++) {
      var caract = ac.encode(senha.substring(i, i + 1));
      if (caract[0] >= 48 && caract[0] <= 57) {
        estado = true;
        break;
      }
    }
    return estado;
  }

  static bool verificarLetras(String senha) {
    //ascii 58-122 letras
    AsciiCodec ac = new AsciiCodec();
    bool estado = false;
    for (int i = 0; i < senha.length - 1; i++) {
      var caract = ac.encode(senha.substring(i, i + 1));
      if (caract[0] >= 58 && caract[0] <= 122) {
        estado = true;
        break;
      }
    }
    return estado;
  }
}
