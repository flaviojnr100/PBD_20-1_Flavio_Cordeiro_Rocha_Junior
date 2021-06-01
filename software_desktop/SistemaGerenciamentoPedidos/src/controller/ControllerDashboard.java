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
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.BaseDados;

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
        acessarTela("Cardapio", "view/FXMLCardapio.fxml");
    }

    @FXML
    void configuracao(ActionEvent event) {
        if(BaseDados.getAutenticado().getTipoAcesso().equals("superusuario")){
            acessarTela("Configuracao", "view/FXMLConfiguracao.fxml");
        }else{
            acessarTela("Editar", "view/FXMLEditarFuncionario.fxml");
        }
    }

    @FXML
    void financia(ActionEvent event) {
        acessarTela("Financia", "view/FXMLFinancia.fxml");
    }

    @FXML
    void funcionario(ActionEvent event) {
        acessarTela("Funcionário", "view/FXMLFuncionario.fxml");
    }

    @FXML
    void mesa(ActionEvent event) {
        acessarTela("Mesa", "view/FXMLMesa.fxml");
    }


    @FXML
    void pedido(ActionEvent event) {
        acessarTela("Pedido", "view/FXMLPedido.fxml");
    }
    private void acessarTela(String nome,String caminho){
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(caminho));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle(nome);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initOwner((Stage) btnCardapio.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("asset/icone.png")));
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

}
