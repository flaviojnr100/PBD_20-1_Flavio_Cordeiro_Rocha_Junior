/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.BaseDados;

public class ControllerConfiguracao implements Initializable{

    @FXML
    private TextField tempoTxt;

    @FXML
    private Button btnRedefinir;

    @FXML
    private Button btnLog;

    @FXML
    private Button btnSalvar;
    
    @FXML
    private Button btnBackup;


    @FXML
    void log(ActionEvent event) {
         try {
            Parent root = FXMLLoader.load(new File("src/main/java/view/FXMLLogSistema.fxml").toURL());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Log");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initOwner((Stage) btnLog.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image("file:src/main/java/asset/icone.png"));
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void backup(ActionEvent event){
        if(JOptionPane.showConfirmDialog(null, "Deseja fazer o backup ?","Aviso",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            BaseDados.getRepositoryBackup().backup();
        }
    }
    
    @FXML
    void redefinir(ActionEvent event) {
        if(JOptionPane.showConfirmDialog(null, "Deseja restaurar o backup ?","Aviso",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            BaseDados.getRepositoryBackup().restaurar();
        }
    }
    
    @FXML
    void salvar(ActionEvent event) {
        if(!tempoTxt.getText().equals("") && tempoTxt.getText().contains(":")){
            BaseDados.setHorario(tempoTxt.getText());
            BaseDados.getRepositoryBackup().salvar(tempoTxt.getText(),BaseDados.isAlteracao());
            JOptionPane.showMessageDialog(null, "Salvo com sucesso");
            Stage stage = (Stage)btnLog.getScene().getWindow();
            stage.close();
        }else{
            JOptionPane.showMessageDialog(null, "Formato de hora inválido");
        }
    }    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tempoTxt.setText(BaseDados.getHorario());
    }

}
