/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.BaseDados;
import model.ItemCardapio;
import model.Pedido;

public class ControllerVisualizacaoPedido implements Initializable {

    @FXML
    private Label lblMesa;

    @FXML
    private Label lblData;

    @FXML
    private Label lblTotal;

    @FXML
    private TextArea itensArea;

    @FXML
    private Button btnOk;

    @FXML
    private Button btnConcluir;
    
    private static Pedido pedido;
    private String itens="";
    private double total = 0;
    
    @FXML
    void concluir(ActionEvent event) {
        pedido.setStatus("concluido");
        BaseDados.getRepositoryPedido().mudarStatus(pedido);
        
        btnOk.fire();
    }

    @FXML
    void keyOk(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            btnOk.fire();
        }
    }

    @FXML
    void ok(ActionEvent event) {
        Stage stage = (Stage)btnOk.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        montarItens(pedido.getItens());
        itensArea.setText(itens);
        lblMesa.setText(pedido.getMesa().getNumero()+"");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        lblData.setText(sdf.format(pedido.getDataPedido()));
        lblTotal.setText("R$ "+String.format("%.2f",total ));
    }

    public static void setPedido(Pedido pedido) {
        ControllerVisualizacaoPedido.pedido = pedido;
    }
    
    private void montarItens(List<ItemCardapio>itens){
        this.itens = "";
        this.total=0;
        for(ItemCardapio item:itens){
            this.itens+=item.getNome()+"    "+String.format("%.2f", item.getPreco())+"\n";
            this.total+=item.getPreco();
        }
    }
}
