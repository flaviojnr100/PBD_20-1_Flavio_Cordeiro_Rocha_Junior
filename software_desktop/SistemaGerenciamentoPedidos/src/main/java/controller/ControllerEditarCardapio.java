/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;
import model.BaseDados;
import model.ItemCardapio;

public class ControllerEditarCardapio implements Initializable {

    @FXML
    private TextField nomeTxt;

    @FXML
    private TextField precoTxt;

    @FXML
    private TextField descricaoTxt;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;
    
    private static ItemCardapio item;

    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }

    @FXML
    void keySalvar(KeyEvent event) {
        if(KeyCode.ENTER == event.getCode()){
            btnSalvar.fire();
        }
    }

    @FXML
    void salvar(ActionEvent event) {
        if(JOptionPane.showConfirmDialog(null, "Deseja editar o registro ?","Aviso",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            if(!nomeTxt.getText().equals("") && !precoTxt.getText().equals("") && !descricaoTxt.getText().equals("")){
                String nome = nomeTxt.getText().toLowerCase();
            if(nome.equals(item.getNome()) || BaseDados.getRepositoryCardapio().buscarNomeUnico(nome)==null){
                if(verificarDigitoNome(nomeTxt.getText())){
                    String preco = precoTxt.getText();
                    if(!verificarDigito(preco)){
                        if(preco.contains(",")){
                            preco = preco.replace(",", ".");
                        }
                        item.setNome(nomeTxt.getText());
                        item.setDescricao(descricaoTxt.getText());
                        item.setPreco(Double.parseDouble(preco));
                        if(BaseDados.getRepositoryCardapio().editar(item.getId(), item)){
                            JOptionPane.showMessageDialog(null, "Registro editado com sucesso");
                            btnCancelar.fire();
                        }else{
                            JOptionPane.showMessageDialog(null, "Erro, informe o administrador");
                        }
                        }else{
                            JOptionPane.showMessageDialog(null, "Formato de preço inválido!");
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Digite apenas texto no campo do nome");
                    
                    }
            }else{
                 JOptionPane.showMessageDialog(null, "Esse item já existe no sistema!!");
            }
            }else{
            JOptionPane.showMessageDialog(null, "Não deixe nenhum campo de texto em branco!");
        }
        }
        }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nomeTxt.setText(item.getNome());
        descricaoTxt.setText(item.getDescricao());
        precoTxt.setText(item.getPreco()+"");
    }

    public static void setItem(ItemCardapio item) {
        ControllerEditarCardapio.item = item;
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
