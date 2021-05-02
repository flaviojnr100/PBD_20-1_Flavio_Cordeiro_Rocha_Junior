/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class ControllerCardapio {

    @FXML
    private TableView<?> tableCardapio;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colNome;

    @FXML
    private TableColumn<?, ?> colPreco;

    @FXML
    private TableColumn<?, ?> colDescricao;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TextField buscarTxt;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnCadastrar;

    @FXML
    void buscar(ActionEvent event) {

    }

    @FXML
    void cadastrar(ActionEvent event) {

    }

    @FXML
    void editar(ActionEvent event) {

    }

    @FXML
    void informacoes(ActionEvent event) {

    }

    @FXML
    void mudarStatus(ActionEvent event) {

    }

    @FXML
    void sair(ActionEvent event) {

    }
    
    @FXML
    void keyBuscar(KeyEvent event) {

    }

}
