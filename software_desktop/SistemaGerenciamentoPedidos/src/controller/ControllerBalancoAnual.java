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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ControllerBalancoAnual implements Initializable {

    @FXML
    private TableView<?> tableFinancia;

    @FXML
    private TableColumn<?, ?> colMes;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private ComboBox<?> comboAno;

    @FXML
    private Button btnRelatorio;

    @FXML
    private Button btnPesquisaDetalhada;

    @FXML
    void buscarAno(ActionEvent event) {

    }

    @FXML
    void gerarRelatorio(ActionEvent event) {

    }

    @FXML
    void pesquisaDetalhada(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
