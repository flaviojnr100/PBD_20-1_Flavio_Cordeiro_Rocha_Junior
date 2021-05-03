package controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.SimpleFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Funcionario;

public class ControllerInformacoesFuncionario implements Initializable {

     @FXML
    private Label lblNome;

    @FXML
    private Label lblSobrenome;

    @FXML
    private Label lblCpf;

    @FXML
    private Label lblTelefone;

    @FXML
    private Label lblLogin;

    @FXML
    private Label lblAcesso;

    @FXML
    private Button btnOk;
    
    private static Funcionario funcionario;

    @FXML
    void KeyPressedOk(KeyEvent event) {
        if(KeyCode.ENTER == event.getCode()){
            btnOk.fire();
        }
    }

    @FXML
    void ok(ActionEvent event) {
        Stage stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
    }

    public static void setFuncionario(Funcionario funcionario) {
        ControllerInformacoesFuncionario.funcionario = funcionario;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblNome.setText(funcionario.getNome());
        lblSobrenome.setText(funcionario.getSobrenome());
        lblCpf.setText(funcionario.getCpf());
        lblTelefone.setText(funcionario.getTelefone());
        lblLogin.setText(funcionario.getLogin());
      //  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
       // lblAcesso.setText(sdf.format(funcionario.getUltimoAcesso()));
    }

}
