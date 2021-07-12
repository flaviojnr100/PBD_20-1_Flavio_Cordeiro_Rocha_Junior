package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.BaseDados;
import model.Criptografia;

public class ControllerEditarFuncionario implements Initializable{

    @FXML
    private TextField nomeTxt;

    @FXML
    private TextField sobrenomeTxt;

    @FXML
    private TextField telefoneTxt;

    @FXML
    private TextField cpfTxt;

    @FXML
    private TextField loginTxt;

    @FXML
    private PasswordField senhaTxt;
    
    @FXML
    private PasswordField confirmarSenha;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnCancelar;

    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage)btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void editar(ActionEvent event) {
        Criptografia.getMd();
        if(JOptionPane.showConfirmDialog(null, "Deseja editar o registro ?","Aviso",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            if(!nomeTxt.getText().equals("") && !sobrenomeTxt.getText().equals("") && !telefoneTxt.getText().equals("") && !cpfTxt.getText().equals("") && !loginTxt.getText().equals("") && !senhaTxt.getText().equals("") && !confirmarSenha.getText().equals("")){    
            if(senhaTxt.getText().equals(confirmarSenha.getText())){
            if(Criptografia.validarSenha(senhaTxt.getText())){
                 if(BaseDados.getRepositoryFuncionario().buscarLoginUnicoValidacao(loginTxt.getText()) == null || BaseDados.getAutenticado().getLogin().equals(loginTxt.getText())){
                     if(BaseDados.getRepositoryFuncionario().buscarCpfUnicoValidacao(cpfTxt.getText()) == null || BaseDados.getAutenticado().getCpf().equals(cpfTxt.getText())){
                       BaseDados.getAutenticado().setNome(nomeTxt.getText());
                       BaseDados.getAutenticado().setSobrenome(sobrenomeTxt.getText());
                       BaseDados.getAutenticado().setTelefone(telefoneTxt.getText());
                       BaseDados.getAutenticado().setCpf(cpfTxt.getText());
                       BaseDados.getAutenticado().setLogin(loginTxt.getText());
                       BaseDados.getAutenticado().setSenha(Criptografia.criptografar(senhaTxt.getText()));
                    if(BaseDados.getRepositoryFuncionario().editar(BaseDados.getAutenticado().getId(), BaseDados.getAutenticado())){
                        JOptionPane.showMessageDialog(null, "Usuário salvo com sucesso!");
                        btnCancelar.fire();
                    }else{
                        JOptionPane.showMessageDialog(null, "Erro, contate o administrador!");
                    }
                 }else{
                     JOptionPane.showMessageDialog(null, "Esse cpf já existe no banco de dados, digite outro!");
                 }
                 }else{
                     JOptionPane.showMessageDialog(null, "Esse login existe no banco de dados, digite outro!");
                 }
            
            }
            }else{
                JOptionPane.showMessageDialog(null, "As senhas são diferentes, digite novamente!");
            }
            }else{
                JOptionPane.showMessageDialog(null, "Não pode deixar campo de texto em branco!");
            }
        }
    }

    @FXML
    void keyPressedSalvar(KeyEvent event) {
        if(KeyCode.ENTER == event.getCode()){
            btnEditar.fire();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nomeTxt.setText(BaseDados.getAutenticado().getNome());
        sobrenomeTxt.setText(BaseDados.getAutenticado().getSobrenome());
        telefoneTxt.setText(BaseDados.getAutenticado().getTelefone());
        cpfTxt.setText(BaseDados.getAutenticado().getCpf());
        loginTxt.setText(BaseDados.getAutenticado().getLogin());
        
        
    }

}
