package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;
import model.BaseDados;
import model.Mesa;

public class ControllerCadastroMesa {

    @FXML
    private TextField numeroTxt;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnCancelar;

    @FXML
    void cadastrar(ActionEvent event) {

        if(JOptionPane.showConfirmDialog(null, "Deseja salvar a mesa ?","Aviso",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            if(!numeroTxt.getText().equals("")){
            Mesa mesa = new Mesa();
            mesa.setNumero(Integer.parseInt(numeroTxt.getText()));
            if(!verificar(mesa.getNumero())){
                if(BaseDados.getRepositoryMesa().salvar(mesa)){
                    
                    JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
                    btnCancelar.fire();
                }else{
                    JOptionPane.showMessageDialog(null, "Erro, contate o administrador!");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Essa mesa já está cadastrada no sistema!");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Não deixe o campo em branco!");
            }
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage)btnCancelar.getScene().getWindow();
        stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }
    public boolean verificar(int mesaV){
        
        for(Mesa mesa: BaseDados.getMesas()){
            if(mesa.getNumero() == mesaV){
                return true;
            }
        }
        return false;
    }
}
