package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class ControllerFuncionario implements Initializable {

    @FXML
    private TableView<?> tableFuncionario;

    @FXML
    private TableColumn<?, ?> colCpf;

    @FXML
    private TableColumn<?, ?> colNome;

    @FXML
    private TableColumn<?, ?> colSobrenome;

    @FXML
    private TableColumn<?, ?> colTelefone;

    @FXML
    private TableColumn<?, ?> colAcesso;

    @FXML
    private TextField buscaTxt;

    @FXML
    private Button btnPesquisar;

    @FXML
    private RadioButton rbtNome;

    @FXML
    private ToggleGroup busca;

    @FXML
    private RadioButton rbtCpf;

    @FXML
    private RadioButton rbtLogin;

    @FXML
    private Button btnAdicionar;

    @FXML
    void adicionarFuncionario(ActionEvent event) {

    }

    @FXML
    void mudarAcesso(ActionEvent event) {

    }

    @FXML
    void pesquisar(ActionEvent event) {

    }

    @FXML
    void verInformacoes(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
