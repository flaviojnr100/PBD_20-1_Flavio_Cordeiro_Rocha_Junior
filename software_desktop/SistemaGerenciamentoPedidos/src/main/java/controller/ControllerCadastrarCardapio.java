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
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;
import model.BaseDados;
import model.ItemCardapio;

public class ControllerCadastrarCardapio{

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
            if(!nomeTxt.getText().equals("") && !precoTxt.getText().equals("") && !descricaoTxt.getText().equals("")){
            String preco = precoTxt.getText();
            if(!verificarDigito(preco)){
                if(verificarDigitoNome(nomeTxt.getText())){
                    
                    if(preco.contains(",")){
                        preco = preco.replace(",", ".");
                    }
            
                    ItemCardapio item = new ItemCardapio();
                    item.setNome(nomeTxt.getText().toLowerCase());
            
                    item.setPreco(Double.parseDouble(preco));
                    item.setDescricao(descricaoTxt.getText());
                    item.setIsAtivo(true);
                    ItemCardapio verificar = BaseDados.getRepositoryCardapio().buscarNomeUnico(nomeTxt.getText().toLowerCase());
                    if(verificar==null){
                        if(BaseDados.getRepositoryCardapio().salvar(item)){
                            JOptionPane.showMessageDialog(null, "Item salvo com sucesso");
                            btnCancelar.fire();
                        }}else{
                        JOptionPane.showMessageDialog(null, "Esse item já foi cadastrado!");
                    }
                    }else{
                        JOptionPane.showMessageDialog(null, "Digite apenas texto no campo do nome do item!");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Digite apenas numeros no campo de preço!");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Não deixe nenhum campo de texto em branco");
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
        if(event.getCode() == KeyCode.ENTER){
            btnCadastrar.fire();
        }
    }
    private boolean verificarDigito(String digito){
        boolean condicao = false;
        
        for(int i=0;i<digito.length();i++){
            int caract = digito.charAt(i);
            if(caract>=48 && caract<=57 || caract==44 || caract==46){
                condicao=false;
            }else{
                condicao=true;
                break;
            }
        }
        return condicao;
    
    }
    
    private boolean verificarDigitoNome(String digito){
        boolean condicao = false;
        
        for(int i=0;i<digito.length();i++){
            int caract = digito.charAt(i);
            if(caract>=48 && caract<=57 || caract==44 || caract==46){
                condicao=false;
                break;
            }else{
                condicao=true;
                
            }
        }
        return condicao;
    
    } 
    
}
