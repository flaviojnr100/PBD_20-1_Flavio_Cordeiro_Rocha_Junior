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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ControllerBalancoDetalhado implements Initializable{

    @FXML
    private TableView<?> tableFinancia;

    @FXML
    private TableColumn<?, ?> colData;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private DatePicker dateDe;

    @FXML
    private DatePicker dateAte;

    @FXML
    private Button btnRelatorio;

    @FXML
    private Button btnBuscar;

    @FXML
    void buscar(ActionEvent event) {

    }

    @FXML
    void gerarPdf(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
