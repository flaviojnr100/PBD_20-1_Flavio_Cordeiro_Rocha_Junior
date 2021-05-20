package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

public class ControllerInformacoesMesa implements Initializable {

    @FXML
    private Label lblCodigo;

    @FXML
    private Label lblNumero;

    @FXML
    private Label lblDisponibilidade;

    @FXML
    private Button btnOk;

    @FXML
    void KeyOk(KeyEvent event) {

    }

    @FXML
    void ok(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}

