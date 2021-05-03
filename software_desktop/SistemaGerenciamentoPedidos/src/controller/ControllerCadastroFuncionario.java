package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.BaseDados;
import model.Funcionario;

public class ControllerCadastroFuncionario {

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
    void cancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
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
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nomeTxt.getText());
        funcionario.setSobrenome(sobrenomeTxt.getText());
        funcionario.setCpf(cpfTxt.getText());
        funcionario.setTelefone(telefoneTxt.getText());
        funcionario.setLogin(loginTxt.getText());
        funcionario.setSenha(senhaTxt.getText());
        funcionario.setTipoAcesso("funcionario");
        funcionario.setIsPermissao(true);
        
        BaseDados.getRepositoryFuncionario().salvar(funcionario);
        btnCancelar.fire();
        
    }

}
