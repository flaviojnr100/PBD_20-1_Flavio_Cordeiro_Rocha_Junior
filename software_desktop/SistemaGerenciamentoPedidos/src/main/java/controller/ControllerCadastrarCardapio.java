/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;
import model.BaseDados;
import model.ItemCardapio;

public class ControllerCadastrarCardapio implements Initializable {

    @FXML
    private TextField nomeTxt;

    @FXML
    private TextField precoTxt;

    @FXML
    private TextField descricaoTxt;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnCancelar;

    @FXML
    void cadastrar(ActionEvent event) {

        if(JOptionPane.showConfirmDialog(null, "Deseja cadastrar esse item ?","Aviso",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            ItemCardapio item = new ItemCardapio();
            item.setNome(nomeTxt.getText());
            item.setPreco(Double.parseDouble(precoTxt.getText()));
            item.setDescricao(descricaoTxt.getText());
            item.setIsAtivo(true);
            ItemCardapio verificar = BaseDados.getRepositoryCardapio().buscarNomeUnico(nomeTxt.getText());
            if(verificar==null){
                if(BaseDados.getRepositoryCardapio().salvar(item)){
                    JOptionPane.showMessageDialog(null, "Item salvo com sucesso");
                    btnCancelar.fire();
                }}else{
                JOptionPane.showMessageDialog(null, "Esse item já foi cadastrado!");
            }
        }
        
        
    }

    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }

    @FXML
    void keyCadastrar(KeyEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

}
