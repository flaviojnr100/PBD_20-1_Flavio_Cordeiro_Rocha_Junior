package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import model.BaseDados;
import model.Mesa;

public class ControllerMesa implements Initializable {

    @FXML
    private TableView<Mesa> tableMesa;

    @FXML
    private TableColumn<Mesa, Integer> colNumero;

    @FXML
    private TableColumn<Mesa, String> colDisponibilidade;

    @FXML
    private TextField buscarTxt;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnAdicionar;

    @FXML
    void buscar(ActionEvent event) {
        
        if(!verificarDigito(buscarTxt.getText())){
        if(!buscarTxt.getText().equals("")){
            BaseDados.atualizarMesaNumero(Integer.parseInt(buscarTxt.getText()));
            atualizar();
        }else{
            JOptionPane.showMessageDialog(null, "Campo de busca em branco!");
        }
        }else{
            JOptionPane.showMessageDialog(null, "Digite somente numeros!");
        }
    }
     private boolean verificarDigito(String digito){
        boolean condicao = false;
        
        for(int i=0;i<digito.length();i++){
            int caract = digito.charAt(i);
            if(caract>=48 && caract<=57){
                condicao=false;
            }else{
                condicao=true;
                break;
            }
        }
        return condicao;
    
    }

    @FXML
    void cadastrar(ActionEvent event) {
         try {
            Parent root = FXMLLoader.load(new File("src/main/java/view/FXMLCadastrarMesa.fxml").toURL());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Mesa");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image("file:src/main/java/asset/icone.png"));
            stage.initOwner((Stage) btnAdicionar.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    BaseDados.atualizarMesa();
                    atualizar();
                }
            });
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void informacoes(ActionEvent event) {
        
        try {
            ControllerInformacoesMesa.setMesa(tableMesa.getSelectionModel().getSelectedItem());
            Parent root = FXMLLoader.load(new File("src/main/java/view/FXMLInformacoesMesa.fxml").toURL());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Informações");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image("file:src/main/java/asset/icone.png"));
            stage.initOwner((Stage) btnAdicionar.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    void mudarEstado(ActionEvent event) {
        BaseDados.getRepositoryMesa().alterarEstado(tableMesa.getSelectionModel().getSelectedItem().getId());
        BaseDados.atualizarMesa();
        atualizar();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BaseDados.atualizarMesa();
        atualizar();
        
    }
    
    private void atualizar(){
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colDisponibilidade.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Mesa, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Mesa, String> param) {
                final Mesa mesa = param.getValue();
                final SimpleObjectProperty sop = new SimpleObjectProperty(mesa.isIsLivre()?"Disponível":"Indisponível");
                return sop;
            }
        });
        tableMesa.setItems(FXCollections.observableArrayList(BaseDados.getMesas()));
    }
    
    @FXML
    void KeyBusca(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            btnBuscar.fire();
        }
        if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE){
            BaseDados.atualizarMesa();
            atualizar();
            buscarTxt.setText("");
        }
    }
    @FXML
    void editar(ActionEvent event) {
         try {
            ControllerEditarMesa.setMesa(tableMesa.getSelectionModel().getSelectedItem());
            Parent root = FXMLLoader.load(new File("src/main/java/view/FXMLEditarMesa.fxml").toURL());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Editar");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image("file:src/main/java/asset/icone.png"));
            stage.initOwner((Stage) btnAdicionar.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                   BaseDados.atualizarMesa();
                    atualizar();
                }
            });
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
