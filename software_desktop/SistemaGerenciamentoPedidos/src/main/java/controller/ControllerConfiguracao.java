/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerConfiguracao {

    @FXML
    private TextField tempoTxt;

    @FXML
    private Button btnRedefinir;

    @FXML
    private Button btnLog;

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
    void redefinir(ActionEvent event) {

    }

}
