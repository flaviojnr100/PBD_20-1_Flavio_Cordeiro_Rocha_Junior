package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Mesa;

public class ControllerInformacoesMesa implements Initializable {

    @FXML
    private Label lblCodigo;

    @FXML
    private Label lblNumero;

    @FXML
    private Label lblDisponibilidade;

    @FXML
    private Button btnOk;
    
    private static Mesa mesa;

    @FXML
    void KeyOk(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            btnOk.fire();
        }
    }

    @FXML
    void ok(ActionEvent event) {
        Stage stage = (Stage)btnOk.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblCodigo.setText(mesa.getId()+"");
        lblNumero.setText(mesa.getNumero()+"");
        lblDisponibilidade.setText(mesa.isIsLivre()?"Disponível":"Indísponivel");
    }

    public static void setMesa(Mesa mesa) {
        ControllerInformacoesMesa.mesa = mesa;
    }

}

