/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import model.BaseDados;
import model.ItemCardapio;

public class ControllerCardapio implements Initializable {

    @FXML
    private TableView<ItemCardapio> tableCardapio;

    @FXML
    private TableColumn<ItemCardapio, Integer> colId;

    @FXML
    private TableColumn<ItemCardapio, String> colNome;

    @FXML
    private TableColumn<ItemCardapio, String> colPreco;

    @FXML
    private TableColumn<ItemCardapio, String> colDescricao;

    @FXML
    private TableColumn<ItemCardapio, Boolean> colStatus;

    @FXML
    private TextField buscarTxt;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnCadastrar;

    @FXML
    void buscar(ActionEvent event) {
        if(!"".equals(buscarTxt.getText().trim())){
            BaseDados.atualizarCardapioNome(buscarTxt.getText().trim());
            atualizar();
        }
    }

    @FXML
    void cadastrar(ActionEvent event) {
        try {
            Parent login = FXMLLoader.load(new File("src/main/java/view/FXMLCadastrarCardapio.fxml").toURL());
            Scene scene = new Scene(login);
            Stage stage = new Stage();
            stage.setTitle("Cardápio");
            stage.setScene(scene);
            stage.getIcons().add(new Image("file:src/main/java/asset/icone.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner((Stage)btnCadastrar.getScene().getWindow());
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    BaseDados.atualizarCardapio();
                    atualizar();
                }
            });
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerCardapio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void editar(ActionEvent event) {
        try {
            ControllerEditarCardapio.setItem(tableCardapio.getSelectionModel().getSelectedItem());
            Parent editar = FXMLLoader.load(new File("src/main/java/view/FXMLEditarCardapio.fxml").toURL());
            Scene scene = new Scene(editar);
            Stage stage = new Stage();
            stage.setTitle("Editar");
            stage.setScene(scene);
            stage.getIcons().add(new Image("file:src/main/java/asset/icone.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner((Stage)btnCadastrar.getScene().getWindow());
            stage.setResizable(false);
             stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    BaseDados.atualizarCardapio();
                    atualizar();
                }
            });
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerCardapio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void informacoes(ActionEvent event) {
        try {
            ControllerInformacoesCardapio.setItem(tableCardapio.getSelectionModel().getSelectedItem());
            Parent informacoes = FXMLLoader.load(new File("src/main/java/view/FXMLInformacoesCardapio.fxml").toURL());
            Scene scene = new Scene(informacoes);
            Stage stage = new Stage();
            stage.setTitle("Informações");
            stage.setScene(scene);
            stage.getIcons().add(new Image("file:src/main/java/asset/icone.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner((Stage)btnCadastrar.getScene().getWindow());
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerCardapio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void mudarStatus(ActionEvent event) {
        BaseDados.getRepositoryCardapio().alterarEstado(tableCardapio.getSelectionModel().getSelectedItem().getId());
        BaseDados.atualizarCardapio();
        atualizar();
    }


    
    @FXML
    void keyBuscar(KeyEvent event) {
        if(KeyCode.ENTER == event.getCode()){
            btnBuscar.fire();
        }
        if(KeyCode.BACK_SPACE == event.getCode() || KeyCode.DELETE == event.getCode() ){
            BaseDados.atualizarCardapio();
            atualizar();
            buscarTxt.setText("");
        }
    }
    
    private void atualizar(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPreco.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ItemCardapio, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ItemCardapio, String> param) {
                final ItemCardapio item = param.getValue();
                final SimpleObjectProperty sop = new SimpleObjectProperty("R$ "+String.format("%.2f", item.getPreco()));
                return sop;
            }
        });
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colStatus.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ItemCardapio, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<ItemCardapio, Boolean> param) {
                final ItemCardapio item = param.getValue();
                final SimpleObjectProperty sop = new SimpleObjectProperty(item.isIsAtivo()?"Ativo":"Não ativo");
                return sop;
            }
        });
        
        tableCardapio.setItems(FXCollections.observableArrayList(BaseDados.getCardapio()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BaseDados.atualizarCardapio();
        atualizar();
    }

}
