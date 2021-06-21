/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import model.BaseDados;
import model.FinanciaMensal;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class ControllerBalancoDetalhado implements Initializable {

    @FXML
    private TableView<FinanciaMensal> tableFinancia;

    @FXML
    private TableColumn<FinanciaMensal, String> colData;

    @FXML
    private TableColumn<FinanciaMensal, String> colTotal;

    @FXML
    private DatePicker dateDe;

    @FXML
    private DatePicker dateAte;

    @FXML
    private Button btnRelatorio;

    @FXML
    private Button btnBuscar;
    
    @FXML
    private Label lblTotal;

    @FXML
    private Button btnOk;


    @FXML
    void buscar(ActionEvent event) {
        if(dateAte.getValue()!=null && dateDe!=null){
            if(dateDe.getValue().isBefore(dateAte.getValue()) || dateDe.getValue().isEqual(dateAte.getValue())){
                BaseDados.atualizarFinanciaDetalhadoEntreDatas(dateDe.getValue()+"", dateAte.getValue()+"");
                atualizar();
                lblTotal.setText(calcularTotal(BaseDados.getFinanciaDetalhado()));
            }else{
                JOptionPane.showMessageDialog(null, "A data 'de' tem que ser menor que a data 'até' !!");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Há um campo em branco!!");
        }
    }

    @FXML
    void gerarPdf(ActionEvent event) {
         try {
            JasperReport report = JasperCompileManager.compileReport("src/main/java/report/FinanciaDetalhada.jrxml");
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("mes", dateDe.getValue()+" até "+dateAte.getValue());
             System.out.println("Texto: "+dateDe.getValue()+" até "+dateAte.getValue());
            JasperPrint print = JasperFillManager.fillReport(report, param,new JRBeanCollectionDataSource(BaseDados.getFinanciaDetalhado()));
            JasperViewer viewer = new JasperViewer(print,false);
            viewer.show();
        } catch (JRException ex) {
            Logger.getLogger(ControllerBalancoDetalhado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         BaseDados.atualizarFinanciaDetalhado();
         atualizar();
         lblTotal.setText(calcularTotal(BaseDados.getFinanciaDetalhado()));
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
       tableFinancia.setItems(FXCollections.observableArrayList(BaseDados.getFinanciaDetalhado()));
        
    }
    @FXML
    void KeyBuscar(KeyEvent event) {
        
       if(event.getCode() == KeyCode.ENTER){
           btnBuscar.fire();
       } 
     
       if(event.getCode() == KeyCode.DELETE || event.getCode() == KeyCode.BACK_SPACE){
           BaseDados.atualizarFinanciaDetalhado();
           atualizar();
           lblTotal.setText(calcularTotal(BaseDados.getFinanciaDetalhado()));
           dateDe.setValue(null);
           dateAte.setValue(null);
       }
    }
    
    @FXML
    void ok(ActionEvent event) {
        Stage stage = (Stage)btnOk.getScene().getWindow();
        stage.close();
    }
    
    private String calcularTotal(List<FinanciaMensal>financia){
        double total=0;
        for(FinanciaMensal f:financia){
        
            total+=f.getTotal();
        }
        return "R$ "+String.format("%.2f", total);
    }

}
