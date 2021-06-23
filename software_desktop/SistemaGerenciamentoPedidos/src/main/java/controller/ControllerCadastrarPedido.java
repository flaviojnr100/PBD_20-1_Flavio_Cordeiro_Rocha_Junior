/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;
import model.BaseDados;
import model.ItemCardapio;
import model.Mesa;
import model.Pedido;

public class ControllerCadastrarPedido implements Initializable{

    @FXML
    private ComboBox<Integer> comboMesa;
    
    @FXML
    private TextArea pedioArea;

    @FXML
    private Label lblTotal;

    @FXML
    private Pane paneCardapio;

    @FXML
    private Button btnFinalizar;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnLimpar;

    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }

    @FXML
    void finalizar(ActionEvent event) {
        if(JOptionPane.showConfirmDialog(null, "Deseja finalizar o pedido ?","Aviso",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            if(comboMesa.getSelectionModel().getSelectedIndex() != -1){
                Pedido pedido = new Pedido();
                pedido.setFuncionario(BaseDados.getAutenticado());
                pedido.setItens(itemPedido);
                Mesa mesa = new Mesa();
                mesa.setNumero(comboMesa.getSelectionModel().getSelectedItem());
                pedido.setMesa(mesa);
                pedido.setStatus("pendente");
                pedido.setTotal(total);
            
            
                if(BaseDados.getRepositoryPedido().salvar(pedido)){
                    JOptionPane.showMessageDialog(null, "Pedido salvo com sucesso !");
                    //ControllerDashboard.setAlterouPedido(true);
                    btnCancelar.fire();
                }else{
                    JOptionPane.showMessageDialog(null, "Erro, contate o administrador !");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Selecione uma mesa!");
            }

        }
    }

    @FXML
    void limpar(ActionEvent event) {
        pedido = "";
        total = 0;
        lblTotal.setText(String.format("%.2f", total));
        pedioArea.setText(pedido);
        itemPedido.clear();
        comboMesa.getSelectionModel().select(-1);
    }
    private String pedido="";
    private double total=0;
    List<ItemCardapio> itemPedido = new ArrayList<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Integer> m = new ArrayList<>();
        for(Mesa mesa:BaseDados.getMesas()){
            m.add(mesa.getNumero());
        }
        comboMesa.setItems(FXCollections.observableArrayList(m));
        
        BaseDados.atualizarCardapio();
        List<ItemCardapio> cardapio = BaseDados.getCardapio();
        GridPane grid = new GridPane();
        int p=0;
        for(ItemCardapio item:cardapio){
            Button button = new Button(item.getNome()+" R$"+String.format("%.2f", item.getPreco()));
            button.setMinWidth(220);
            button.setCursor(Cursor.HAND);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    pedido+=item.getNome()+" R$"+String.format("%.2f", item.getPreco())+"\n";
                    total+=item.getPreco();
                    lblTotal.setText(String.format("%.2f", total));
                    pedioArea.setText(pedido);
                    itemPedido.add(item);
                }
            });
            grid.add(button, 0, p);
            p++;
        }
        paneCardapio.getChildren().add(grid);
    }

}
