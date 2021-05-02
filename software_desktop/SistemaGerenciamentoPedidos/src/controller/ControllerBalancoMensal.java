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

public class ControllerBalancoMensal implements Initializable {

    @FXML
    private TableView<?> tableFinancia;

    @FXML
    private TableColumn<?, ?> colData;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private Button btnRelatorio;

    @FXML
    private ComboBox<?> comboMes;

    @FXML
    private ComboBox<?> comboAno;

    @FXML
    void buscarAno(ActionEvent event) {

    }

    @FXML
    void buscarMes(ActionEvent event) {

    }

    @FXML
    void gerarPdf(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
