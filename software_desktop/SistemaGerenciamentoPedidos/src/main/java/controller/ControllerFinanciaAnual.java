package controller;

import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.BaseDados;
import model.FinanciaAnual;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class ControllerFinanciaAnual implements Initializable {

    @FXML
    private TableView<FinanciaAnual> tableFinancia;

    @FXML
    private TableColumn<FinanciaAnual, String> colData;

    @FXML
    private TableColumn<FinanciaAnual, String> colTotal;

    @FXML
    private Label lblTotal;

    @FXML
    private Button btnOk;
    
    @FXML
    private Button btnRelatorio;

    @FXML
    private ComboBox<?> comboAno;

    @FXML
    void KeyOk(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            btnOk.fire();
        }
    }

    @FXML
    void mudarAno(ActionEvent event) {
        BaseDados.atualizarFinanciaAnual(Integer.parseInt((String)comboAno.getSelectionModel().getSelectedItem()));
        atualizar();
        lblTotal.setText(total(BaseDados.getFinanciaAnual()));
    }

    @FXML
    void ok(ActionEvent event) {
        Stage stage = (Stage)btnOk.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboAno.setItems(BaseDados.getAnosFx());
        comboAno.getSelectionModel().selectLast();
        BaseDados.atualizarFinanciaAnual(LocalDate.now().getYear());
        atualizar();
        lblTotal.setText(total(BaseDados.getFinanciaAnual()));
    }

    private void atualizar(){
       colData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FinanciaAnual, String>, ObservableValue<String>>() {
           @Override
           public ObservableValue<String> call(TableColumn.CellDataFeatures<FinanciaAnual, String> param) {
               final FinanciaAnual financia = param.getValue();
               final SimpleObjectProperty sop = new SimpleObjectProperty(financia.getMes());
               return sop;
           }
       });
       colTotal.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<FinanciaAnual, String>, ObservableValue<String>>() {
           @Override
           public ObservableValue<String> call(TableColumn.CellDataFeatures<FinanciaAnual, String> param) {
               final FinanciaAnual financia = param.getValue();
               final SimpleObjectProperty sop = new SimpleObjectProperty("R$ "+String.format("%.2f", financia.getTotal()));
               return sop;
           }
       });
       tableFinancia.setItems(FXCollections.observableArrayList(BaseDados.getFinanciaAnual()));
    }
    
    private String total(List<FinanciaAnual> lista){
        double t=0;
        for(FinanciaAnual fa:lista){
            t+=fa.getTotal();
        }
        return String.format("%.2f", t);
    }
    
    @FXML
    void gerarRelatorio(ActionEvent event) {
        try {
            JasperReport report = JasperCompileManager.compileReport("src/main/java/report/FinanciaAnual.jrxml");
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("ANO", (String) comboAno.getSelectionModel().getSelectedItem());
            JasperPrint print = JasperFillManager.fillReport(report, param,new JRBeanCollectionDataSource(BaseDados.getFinanciaAnual()));
            JasperViewer viewer = new JasperViewer(print,false);
            viewer.show();
        } catch (JRException ex) {
            Logger.getLogger(ControllerFinanciaMensal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
