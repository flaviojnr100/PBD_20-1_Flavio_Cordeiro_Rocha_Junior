/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerDashboard implements Initializable {

    @FXML
    private Button btnMesa;

    @FXML
    private Button btnPedido;

    @FXML
    private Button btnFuncionario;

    @FXML
    private Button btnCardapio;

    @FXML
    private Button btnFinancia;

    @FXML
    private Button btnConfiguracao;

    @FXML
    void cardapio(ActionEvent event) {

    }

    @FXML
    void configuracao(ActionEvent event) {

    }

    @FXML
    void financia(ActionEvent event) {

    }

    @FXML
    void funcionario(ActionEvent event) {
        acessarTela("Funcionário", "view/FXMLFuncionario.fxml");
    }

    @FXML
    void mesa(ActionEvent event) {

    }

    @FXML
    void pedido(ActionEvent event) {

    }
    private void acessarTela(String nome,String caminho){
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(caminho));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle(nome);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.initOwner((Stage) btnCardapio.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}