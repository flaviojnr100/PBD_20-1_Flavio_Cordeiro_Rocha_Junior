package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ControllerEditarPedido implements Initializable {

    @FXML
    private TextField mesaTxt;

    @FXML
    private TextArea pedioArea;

    @FXML
    private Label lblTotal;

    @FXML
    private Pane paneCardapio;

    @FXML
    private Button btnFinalizar;

    @FXML
    private Button btnCancelar;

    @FXML
    void cancelar(ActionEvent event) {

    }

    @FXML
    void finalizar(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
