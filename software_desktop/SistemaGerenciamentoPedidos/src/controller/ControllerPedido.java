package controller;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.BaseDados;
import model.Mesa;
import model.Pedido;

public class ControllerPedido implements Initializable {

    @FXML
    private TableView<Pedido> tablePedido;

    @FXML
    private TableColumn<Pedido, Integer> colCodigo;

    @FXML
    private TableColumn<Pedido, String> colMesa;

    @FXML
    private TableColumn<Pedido, String> colFuncionario;

    @FXML
    private TableColumn<Pedido, String> colData;

    @FXML
    private TableColumn<Pedido, String> colTotal;

    @FXML
    private TableColumn<Pedido, String> colStatus;

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
        try {
            ControllerInformacoesPedido.setPedido(tablePedido.getSelectionModel().getSelectedItem());
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/FXMLCadastrarPedido.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Cadastro");
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("asset/icone.png")));
            stage.setResizable(false);
            stage.initOwner((Stage) btnAdiciionar.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            ControllerInformacoesPedido.setPedido(tablePedido.getSelectionModel().getSelectedItem());
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/FXMLVerInformacoesPedido.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Informações");
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("asset/icone.png")));
            stage.setResizable(false);
            stage.initOwner((Stage) btnAdiciionar.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void atualizar(){
         colCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
         colData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pedido, String>, ObservableValue<String>>() {
             @Override
             public ObservableValue<String> call(TableColumn.CellDataFeatures<Pedido, String> param) {
                final Pedido pedido = param.getValue();
                 final SimpleObjectProperty sop = new SimpleObjectProperty(sdf.format(pedido.getDataPedido()));
                 return sop;
             }
         });
         colFuncionario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pedido, String>, ObservableValue<String>>() {
             @Override
             public ObservableValue<String> call(TableColumn.CellDataFeatures<Pedido, String> param) {
                 final Pedido pedido = param.getValue();
                 final SimpleObjectProperty sop = new SimpleObjectProperty(pedido.getFuncionario().getNome()+" "+pedido.getFuncionario().getSobrenome());
                 return sop;
             }
         });
         colMesa.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pedido, String>, ObservableValue<String>>() {
             @Override
             public ObservableValue<String> call(TableColumn.CellDataFeatures<Pedido, String> param) {
                 final Pedido pedido = param.getValue();
                 final SimpleObjectProperty sop = new SimpleObjectProperty(pedido.getMesa().getNumero());
                 return sop;
             }
         });
         colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
         colTotal.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pedido, String>, ObservableValue<String>>() {
             @Override
             public ObservableValue<String> call(TableColumn.CellDataFeatures<Pedido, String> param) {
                final Pedido pedido = param.getValue();
                final SimpleObjectProperty sop = new SimpleObjectProperty("R$ "+String.format("%.2f", pedido.getTotal()));
                return sop;
             }
         });;
         BaseDados.atualizarPedido();
         tablePedido.setItems(FXCollections.observableArrayList(BaseDados.getPedidos()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        atualizar();
    }

}
