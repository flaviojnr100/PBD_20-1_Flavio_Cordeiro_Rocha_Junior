package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ControllerMesa implements Initializable {

    @FXML
    private TableView<?> tableMesa;

    @FXML
    private TableColumn<?, ?> colNumero;

    @FXML
    private TableColumn<?, ?> colDisponibilidade;

    @FXML
    private TextField buscarTxt;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnAdicionar;

    @FXML
    void buscar(ActionEvent event) {

    }

    @FXML
    void cadastrar(ActionEvent event) {

    }

    @FXML
    void informacoes(ActionEvent event) {

    }

    @FXML
    void mudarEstado(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
