package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;
import model.BaseDados;
import model.ItemCardapio;
import model.Pedido;

public class ControllerEditarPedido implements Initializable {

    @FXML
    private TextField mesaTxt;

    @FXML
    private Label lblTotal;

    @FXML
    private Pane paneCardapio;

    @FXML
    private Button btnFinalizar;

    @FXML
    private Button btnCancelar;
    
    @FXML
    private AnchorPane itensPane;

    private static Pedido pedido;
    private GridPane grid1 = new GridPane();
    private int pItem=0;
    private List<Object[]> itensPedido = new ArrayList<>();
    private double preco=0;
    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage)btnCancelar.getScene().getWindow();
        stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }

    @FXML
    void finalizar(ActionEvent event) {
        if(JOptionPane.showConfirmDialog(null, "Deseja editar o registro ?","Aviso",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            pedido.setTotal(preco);
            if(BaseDados.getRepositoryPedido().editar(pedido)){
                JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
               // ControllerDashboard.setAlterouPedido(true);
                btnCancelar.fire();
            }else{
                JOptionPane.showMessageDialog(null, "Erro,contate o administrador!");
                
            }
    }}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mesaTxt.setText(pedido.getMesa().getNumero()+"");
        BaseDados.atualizarCardapio();
        List<ItemCardapio> cardapio = BaseDados.getCardapio();
        GridPane grid = new GridPane();
        itensPane.getChildren().add(grid1);
        int p=0;
        for(ItemCardapio item:cardapio){
            Button button = new Button(item.getNome()+" R$"+String.format("%.2f", item.getPreco()));
            button.setMinWidth(220);
            button.setCursor(Cursor.HAND);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                   Button button1 = new Button("X "+item.getNome()+" R$"+String.format("%.2f", item.getPreco()));
                   button1.setMinWidth(200);
                   button1.setCursor(Cursor.HAND);
                   Object[] o = new Object[2];
                   o[0] = pItem;
                   o[1] = button1;
                   itensPedido.add(o);
                   pedido.getItens().add(item);
                   preco+=item.getPreco();
                   lblTotal.setText(String.format("%.2f", preco));

                   button1.setOnAction(new EventHandler<ActionEvent>() {
                       @Override
                       public void handle(ActionEvent event) {
                           preco-=item.getPreco();
                           int v =  (int) o[0];
                           itensPedido.remove(v);
                           grid1.getChildren().remove(v);
                           pedido.getItens().remove(v);
                           pItem--;
                           lblTotal.setText(String.format("%.2f", preco));

                       }
                       
                   });
                  
                   grid1.add(button1, 0, pItem);
                   pItem++;
                   
                }
            });
            grid.add(button, 0, p);
            p++;
        }
        paneCardapio.getChildren().add(grid);
        
        for(ItemCardapio item:pedido.getItens()){
            preco+=item.getPreco();
             Button button1 = new Button("X "+item.getNome()+" R$"+String.format("%.2f", item.getPreco()));
                   button1.setMinWidth(200);
                   button1.setCursor(Cursor.HAND);
                   Object[] o = new Object[2];
                   o[0] = pItem;
                   o[1] = button1;
                   itensPedido.add(o);
                   button1.setOnAction(new EventHandler<ActionEvent>() {
                       @Override
                       public void handle(ActionEvent event) {
                           preco-=item.getPreco();
                           int v =  (int) o[0];
                           itensPedido.remove(v);
                           grid1.getChildren().remove(v);
                           pedido.getItens().remove(v);
                           pItem--;
                           lblTotal.setText(String.format("%.2f", preco));

                           
                       }
                       
                   });
                  
                   grid1.add(button1, 0, pItem);
                   pItem++;
           
        }
        //Modificar preço
        lblTotal.setText(String.format("%.2f", preco));
        }
        
        
    

    public static void setPedido(Pedido pedido) {
        ControllerEditarPedido.pedido = pedido;
    }

}
