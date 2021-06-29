/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import model.BaseDados;
import model.SenhaReset;

public class ControllerReset implements Initializable {

    @FXML
    private TableView<SenhaReset> tableReset;

    @FXML
    private TableColumn<SenhaReset, Integer> colId;

    @FXML
    private TableColumn<SenhaReset, String> colNome;

    @FXML
    private TableColumn<SenhaReset, String> colSobrenome;

    @FXML
    private TableColumn<SenhaReset, String> colLogin;

    @FXML
    private TableColumn<SenhaReset, String> colData;

    @FXML
    private TextField buscaTxt;

    @FXML
    private Button btnBusca;


    @FXML
    void buscar(ActionEvent event) {
        if(!buscaTxt.getText().equals("")){
            BaseDados.atualizarResetLogin(buscaTxt.getText());
            if(BaseDados.getStatus()==202){
                BaseDados.atualizarResetLogin(buscaTxt.getText());
                atualizar();
            }else{
                JOptionPane.showMessageDialog(null, "Login inválido");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Não deixe o campo em branco!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BaseDados.atualizarResets();
        atualizar();
    }
    
    public void atualizar(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SenhaReset, String>, ObservableValue<String>>() {
             @Override
             public ObservableValue<String> call(TableColumn.CellDataFeatures<SenhaReset, String> param) {
                 final String nome = param.getValue().getFuncionario().getNome();
                 SimpleObjectProperty sop = new SimpleObjectProperty(nome);
                 return sop;
             }
         });
        colSobrenome.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SenhaReset, String>, ObservableValue<String>>() {
             @Override
             public ObservableValue<String> call(TableColumn.CellDataFeatures<SenhaReset, String> param) {
                 final String sobrenome = param.getValue().getFuncionario().getSobrenome();
                 SimpleObjectProperty sop = new SimpleObjectProperty(sobrenome);
                 return sop;
             }
         });
        colLogin.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SenhaReset, String>, ObservableValue<String>>() {
             @Override
             public ObservableValue<String> call(TableColumn.CellDataFeatures<SenhaReset, String> param) {
                 final String login = param.getValue().getFuncionario().getLogin();
                 SimpleObjectProperty sop = new SimpleObjectProperty(login);
                 return sop;
             }
         });
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        colData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<SenhaReset, String>, ObservableValue<String>>() {
             @Override
             public ObservableValue<String> call(TableColumn.CellDataFeatures<SenhaReset, String> param) {
                 final Date data = param.getValue().getData();
                 SimpleObjectProperty sop = new SimpleObjectProperty(sdf.format(data));
                 return sop;
             }
         });
        
        tableReset.setItems(FXCollections.observableArrayList(BaseDados.getResets()));
         
    }
    
    @FXML
    void resetar(ActionEvent event) {
        if(JOptionPane.showConfirmDialog(null, "Deseja resetar a senha desse usuário ?","Aviso",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            BaseDados.getRepositoryReset().resetar(tableReset.getSelectionModel().getSelectedItem().getId());
            BaseDados.atualizarResets();
            atualizar();
        }
    }
    @FXML
    void keyPressed(KeyEvent event) {
        if(KeyCode.ENTER == event.getCode()){
            btnBusca.fire();
        }
        if(KeyCode.DELETE == event.getCode() || KeyCode.BACK_SPACE == event.getCode()){
            buscaTxt.setText("");
            BaseDados.atualizarResets();
            atualizar();
        }
    }
    

}
