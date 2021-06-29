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
    private int numero_old;
    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }

    @FXML
    void editar(ActionEvent event) {
        if(JOptionPane.showConfirmDialog(null, "Deseja alterar a mesa ?","Aviso",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            if(!numeroTxt.getText().equals("")){
            mesa.setNumero(Integer.parseInt(numeroTxt.getText()));
            if(!verificar(mesa.getNumero())){
            if(BaseDados.getRepositoryMesa().editar(mesa)){
                ControllerDashboard.setAlterou(true);
                JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
                btnCancelar.fire();
            }else{
                JOptionPane.showMessageDialog(null, "Erro, contate o administrado!");
            }
            }else{
                JOptionPane.showMessageDialog(null, "Essa mesa já está cadastrada no sistema!");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Não deixe nenhum campo de texto em branco");
        }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numeroTxt.setText(mesa.getNumero()+"");
        numero_old = mesa.getNumero();
    }

    public static void setMesa(Mesa mesa) {
        ControllerEditarMesa.mesa = mesa;
    }
    
    public boolean verificar(int mesaV){
        if(mesaV !=numero_old){
            for(Mesa mesa: BaseDados.getMesas()){
                if(mesa.getNumero() == mesaV){
                    return true;
                }
            }
            return false;
        }
        return false;
    }

}
