package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class ControllerPedido {

    @FXML
    private TableView<?> tablePedido;

    @FXML
    private TableColumn<?, ?> colCodigo;

    @FXML
    private TableColumn<?, ?> colMesa;

    @FXML
    private TableColumn<?, ?> colFuncionario;

    @FXML
    private TableColumn<?, ?> colData;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TextField buscarTxt;

    @FXML
    private Button btnBuscar;

    @FXML
    private RadioButton rbtnMesa;

    @FXML
    private ToggleGroup grupo;


    @FXML
    private RadioButton rbtnData;

    @FXML
    private RadioButton rbtnCodigo;

    @FXML
    private DatePicker dateData;

    @FXML
    private Button btnAdiciionar;

    @FXML
    void buscar(ActionEvent event) {

    }

    @FXML
    void cadastrar(ActionEvent event) {

    }

    @FXML
    void colocarCampo(ActionEvent event) {

    }

    @FXML
    void excluir(ActionEvent event) {

    }

    @FXML
    void mudarStatus(ActionEvent event) {

    }

    @FXML
    void verInformacoes(ActionEvent event) {

    }

}
