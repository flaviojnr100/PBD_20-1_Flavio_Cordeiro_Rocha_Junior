package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

public class ControllerInformacoesPedido implements Initializable {

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblLogin;

    @FXML
    private Label lblMesa;

    @FXML
    private Label lblData;

    @FXML
    private Label lblStatus;

    @FXML
    private TextArea cardapioArea;

    @FXML
    private Button btnOk;

    @FXML
    void ok(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    
    @FXML
    void keyOk(KeyEvent event) {

    }

}
