package controller;

import com.lowagie.text.pdf.CFFFont;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;
import model.BaseDados;
import model.Criptografia;
import model.Funcionario;

public class ControllerCadastroFuncionario implements Initializable {

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
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;
    
    @FXML
    private Label lblTipo;

    @FXML
    private ComboBox<?> comboTipo;
    
    @FXML
    private PasswordField confirmarSenha;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(BaseDados.getAutenticado().getTipoAcesso().equals("superusuario")){
            String [] tipos = {"gerente","funcionario"};
            ObservableList items = FXCollections.observableArrayList(tipos);
            comboTipo.setItems(items);
            lblTipo.setVisible(true);
            comboTipo.setVisible(true);
            comboTipo.getSelectionModel().selectFirst();
            
        }
        Criptografia.getMd();
        
    }

    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }

    @FXML
    void keyPressedSalvar(KeyEvent event) {
        if(KeyCode.ENTER == event.getCode()){
            btnSalvar.fire();
        }
    }

    @FXML
    void salvar(ActionEvent event) {
        
        if(JOptionPane.showConfirmDialog(null, "Deseja salvar o registro ?","Aviso",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE) == JOptionPane.YES_OPTION){
            if(!nomeTxt.getText().equals("") && !sobrenomeTxt.getText().equals("") && !telefoneTxt.getText().equals("") && !cpfTxt.getText().equals("") && !loginTxt.getText().equals("") && !senhaTxt.getText().equals("") && !confirmarSenha.getText().equals("")){    
                Funcionario funcionario = new Funcionario();
                funcionario.setNome(nomeTxt.getText().toLowerCase());
                funcionario.setSobrenome(sobrenomeTxt.getText().toLowerCase());
                funcionario.setCpf(cpfTxt.getText().toLowerCase());
                funcionario.setTelefone(telefoneTxt.getText().toLowerCase());
                funcionario.setLogin(loginTxt.getText());
                funcionario.setSenha(senhaTxt.getText());
                funcionario.setIsPermissao(true);
                funcionario.setUltimoAcesso(new Date());
                if(BaseDados.getAutenticado().getTipoAcesso().equals("superusuario")){
                    funcionario.setTipoAcesso((String) comboTipo.getSelectionModel().getSelectedItem());
                }else{
                    funcionario.setTipoAcesso("funcionario");
                }
            if(BaseDados.getRepositoryFuncionario().buscarCpfUnicoValidacao(cpfTxt.getText()) == null){
            if(BaseDados.getRepositoryFuncionario().buscarLoginUnicoValidacao(loginTxt.getText()) == null){
                if(senhaTxt.getText().equals(confirmarSenha.getText())){
                    if(Criptografia.validarSenha(funcionario.getSenha())){
                        funcionario.setSenha(Criptografia.criptografar(senhaTxt.getText()));
                        if(BaseDados.getRepositoryFuncionario().salvar(funcionario)){
                            JOptionPane.showMessageDialog(null, "Usuário salvo com sucesso!");
                            if(BaseDados.getAutenticado().getTipoAcesso().equals("superusuario")){
                                 BaseDados.atualizarFuncionariosSU();
                            }else{
                                BaseDados.atualizarFuncionarios();
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "Erro, contate o administrador!","Aviso",JOptionPane.YES_OPTION);
                        }
                        btnCancelar.fire();
                    }
                    }else{
                        JOptionPane.showMessageDialog(null, "As senhas são diferentes, digite novamente","Aviso",JOptionPane.YES_OPTION);
            }
            }else{
                JOptionPane.showMessageDialog(null, "Esse login já está cadastrado no sistema, digite outro !");
            }
            }else{
                JOptionPane.showMessageDialog(null, "Esse cpf já foi cadastrado no sistema, digite outro !");
            }
            
        }else{
             JOptionPane.showMessageDialog(null, "Não é permitido deixar campo de texto em branco!");
            }}
    }



}
