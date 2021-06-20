/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.ItemCardapio;

public class ControllerInformacoesCardapio implements Initializable {

    @FXML
    private Label lblNome;

    @FXML
    private Label lblPreco;

    @FXML
    private TextArea descricaoArea;

    @FXML
    private Label lblStatus;

    @FXML
    private Button btnOk;
    
    private static ItemCardapio item;
    @FXML
    void keyOk(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            btnOk.fire();
        }
    }

    @FXML
    void ok(ActionEvent event) {
        Stage stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblNome.setText(item.getNome());
        descricaoArea.setText(item.getDescricao());
        lblPreco.setText(item.getPreco()+"");
        lblStatus.setText(item.isIsAtivo()?"Ativo":"Desativado");
    }

    public static void setItem(ItemCardapio item) {
        ControllerInformacoesCardapio.item = item;
    }

}
