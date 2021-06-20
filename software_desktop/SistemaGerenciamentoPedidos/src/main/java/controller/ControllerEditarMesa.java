package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;
import model.BaseDados;
import model.Mesa;

public class ControllerEditarMesa implements Initializable {

    @FXML
    private TextField numeroTxt;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnCancelar;
    
    private static Mesa mesa;

    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }

    @FXML
    void editar(ActionEvent event) {
        if(JOptionPane.showConfirmDialog(null, "Deseja alterar a mesa ?","Aviso",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            mesa.setNumero(Integer.parseInt(numeroTxt.getText()));
            if(BaseDados.getRepositoryMesa().editar(mesa)){
                JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
                btnCancelar.fire();
            }else{
                JOptionPane.showMessageDialog(null, "Erro, contate o administrado!");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numeroTxt.setText(mesa.getNumero()+"");
    }

    public static void setMesa(Mesa mesa) {
        ControllerEditarMesa.mesa = mesa;
    }
    
    

}
