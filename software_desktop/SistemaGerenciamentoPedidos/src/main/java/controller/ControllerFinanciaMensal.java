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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

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
    private Button btnRelatorio;

    @FXML
    private ComboBox<?> comboMes;

    @FXML
    private ComboBox<?> comboAno;

    @FXML
    void KeyOk(KeyEvent event) {

    }

    @FXML
    void mudarAno(ActionEvent event) {
         BaseDados.atualizarFinanciaMensal(comboMes.getSelectionModel().getSelectedIndex()+1, Integer.parseInt(BaseDados.getAnos().get(comboAno.getSelectionModel().getSelectedIndex())));
        atualizar();
        lblTotal.setText("R$"+String.format("%.2f", total(BaseDados.getFinanciaMensal())));
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
        BaseDados.getMeses();
        comboMes.setItems(BaseDados.getMesesFx());
        comboMes.getSelectionModel().select(LocalDate.now().getMonthValue()-1);
        comboAno.setItems(BaseDados.getAnosFx());
        comboAno.getSelectionModel().selectLast();
        BaseDados.atualizarFinanciaMensal(comboMes.getSelectionModel().getSelectedIndex()+1, Integer.parseInt(BaseDados.getAnos().get(comboAno.getSelectionModel().getSelectedIndex())));
        atualizar();
        lblTotal.setText("R$"+String.format("%.2f", total(BaseDados.getFinanciaMensal())));
    }
    
    private void atualizar(){
        
        SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
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
    
    @FXML
    void gerarRelatorio(ActionEvent event) {
            
            
        try {
            JasperReport report = JasperCompileManager.compileReport("src/main/java/report/FinanciaMensal.jrxml");
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("mes", (String) comboMes.getSelectionModel().getSelectedItem());
            JasperPrint print = JasperFillManager.fillReport(report, param,new JRBeanCollectionDataSource(BaseDados.getFinanciaMensal()));
            JasperViewer viewer = new JasperViewer(print,false);
            viewer.show();
        } catch (JRException ex) {
            Logger.getLogger(ControllerFinanciaMensal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
