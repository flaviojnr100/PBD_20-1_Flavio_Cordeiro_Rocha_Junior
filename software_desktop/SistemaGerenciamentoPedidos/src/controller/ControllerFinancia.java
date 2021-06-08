/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
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
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import model.BaseDados;
import model.FinanciaMensal;

public class ControllerFinancia implements Initializable {

    @FXML
    private TableView<FinanciaMensal> tableFinancia;

    @FXML
    private TableColumn<FinanciaMensal, String> colData;

    @FXML
    private TableColumn<FinanciaMensal, String> colTotal;

    @FXML
    private Button btnMensal;

    @FXML
    private Button btnAnual;

    @FXML
    void anual(ActionEvent event) {

    }

    @FXML
    void mensal(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/FXMLFinanciaMensal.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Financia mensal");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("asset/icone.png")));
            stage.initOwner((Stage) btnAnual.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         BaseDados.atualizarFinancia();
         atualizar();
    }
    
    private void atualizar(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //bug da data null
        colData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FinanciaMensal, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FinanciaMensal, String> param) {
                final FinanciaMensal financia = param.getValue();
                final SimpleObjectProperty sop = new SimpleObjectProperty(sdf.format(financia.getDataPedido()));
                return sop;
            }
        });
        colTotal.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FinanciaMensal, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FinanciaMensal, String> param) {
                final FinanciaMensal financia = param.getValue();
                final SimpleObjectProperty sop = new SimpleObjectProperty("R$"+String.format("%.2f", financia.getTotal()));
                return sop;
            }
        });
       tableFinancia.setItems(FXCollections.observableArrayList(BaseDados.getFinancia()));
        
    }

}
