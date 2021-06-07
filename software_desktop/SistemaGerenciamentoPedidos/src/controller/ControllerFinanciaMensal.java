/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.BaseDados;
import model.FinanciaMensal;

public class ControllerFinanciaMensal implements Initializable {

    @FXML
    private TableView<FinanciaMensal> tableFinancia;

    @FXML
    private TableColumn<FinanciaMensal, String> colData;

    @FXML
    private TableColumn<FinanciaMensal, String> colTotal;

    @FXML
    private Label lblTotal;

    @FXML
    private Button btnOk;

    @FXML
    private ComboBox<?> comboMes;

    @FXML
    private ComboBox<?> comboAno;

    @FXML
    void KeyOk(KeyEvent event) {

    }

    @FXML
    void mudarAno(ActionEvent event) {

    }

    @FXML
    void mudarMes(ActionEvent event) {
        BaseDados.atualizarFinanciaMensal(comboMes.getSelectionModel().getSelectedIndex()+1, Integer.parseInt(BaseDados.getAnos().get(comboAno.getSelectionModel().getSelectedIndex())));
        atualizar();
        lblTotal.setText("R$"+String.format("%.2f", total(BaseDados.getFinanciaMensal())));
    }   

    @FXML
    void ok(ActionEvent event) {
        Stage stage = (Stage)btnOk.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Date date = new Date();
        GregorianCalendar gc = new GregorianCalendar();
        BaseDados.atualizarFinanciaMensal(date.getMonth(),gc.toZonedDateTime().getYear());
        atualizar();
        lblTotal.setText("R$"+String.format("%.2f", total(BaseDados.getFinanciaMensal())));
        BaseDados.getMeses();
        comboMes.setItems(BaseDados.getMesesFx());
        comboMes.getSelectionModel().select(date.getMonth()-1);
        BaseDados.getAnos();
        comboAno.setItems(BaseDados.getAnosFx());
        comboAno.getSelectionModel().select(0);
    }
    
    private void atualizar(){
        //bug da data null
        SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
        colData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FinanciaMensal, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FinanciaMensal, String> param) {
               final FinanciaMensal financia = param.getValue();
               
                final SimpleObjectProperty sop = new SimpleObjectProperty(sdf.format(financia.getData()));
                return sop;
            }
        });
        colTotal.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FinanciaMensal, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<FinanciaMensal, String> param) {
                final FinanciaMensal financia = param.getValue();
                SimpleObjectProperty sop = new SimpleObjectProperty("R$ "+String.format("%.2f", financia.getTotal()));
                return sop;
            }
        });
        tableFinancia.setItems(FXCollections.observableArrayList(BaseDados.getFinanciaMensal()));
    }
    private double total(List<FinanciaMensal>lista){
        double total=0;
        for(FinanciaMensal fm:lista){
            total+=fm.getTotal();
        }
        
        return total;
    }
}
