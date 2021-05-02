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
import javafx.scene.input.KeyEvent;

public class ControllerInformacoesCardapio implements Initializable {

    @FXML
    private Label lblNome;

    @FXML
    private Label lblPreco;

    @FXML
    private Label lblDescricao;

    @FXML
    private Label lblStatus;

    @FXML
    private Button btnOk;

    @FXML
    void keyOk(KeyEvent event) {

    }

    @FXML
    void ok(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
