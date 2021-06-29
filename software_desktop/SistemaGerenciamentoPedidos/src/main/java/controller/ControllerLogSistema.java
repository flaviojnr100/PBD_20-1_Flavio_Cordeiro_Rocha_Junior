/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import model.BaseDados;
import model.Log;

public class ControllerLogSistema implements Initializable {

    @FXML
    private TableView<Log> tableLog;

    @FXML
    private TableColumn<Log, String> colUsuario;

    @FXML
    private TableColumn<Log, String> colOperacao;

    @FXML
    private TableColumn<Log, String> colEndereco;

    @FXML
    private TableColumn<Log, String> colTabela;

    @FXML
    private TableColumn<Log, String> colData;
    
    @FXML
    private DatePicker dataDate;

    @FXML
    private Button btnBuscar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BaseDados.atualizarLog();
        atualizar();
    }
    
    private void atualizar(){
        
        colData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Log, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Log, String> param) {
                final Log log = param.getValue();
                SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                final SimpleObjectProperty sop = new SimpleObjectProperty(sfd.format(log.getData()));
                return sop;
            }
        });
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("enderecoIp"));
        colOperacao.setCellValueFactory(new PropertyValueFactory<>("operacao"));
        colTabela.setCellValueFactory(new PropertyValueFactory<>("tabela"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        
        tableLog.setItems(FXCollections.observableArrayList(BaseDados.getLogs()));
        
    }
    


    @FXML
    void KeyBusca(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            btnBuscar.fire();
        }
        if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE){
            BaseDados.atualizarLog();
            atualizar();
        }
    }

    @FXML
    void buscar(ActionEvent event) {
        if(dataDate.getValue()!=null){
            BaseDados.atualizarLogData(dataDate.getValue().getDayOfMonth(), dataDate.getValue().getMonthValue(), dataDate.getValue().getYear());
            atualizar();
        }else{
            JOptionPane.showMessageDialog(null, "Formato de data inválido");
        }
    }

}
