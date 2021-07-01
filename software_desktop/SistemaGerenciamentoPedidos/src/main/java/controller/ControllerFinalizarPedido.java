/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.BaseDados;
import model.ItemCardapio;
import model.Pedido;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class ControllerFinalizarPedido implements Initializable{

    @FXML
    private Pane panePedidos;

    @FXML
    private TextArea itensArea;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblMesa;

    @FXML
    private TextField trocoTxt;

    @FXML
    private Label lblTroco;

    @FXML
    private Button btnEfetuarPagamento;

    @FXML
    private Button btnAnular;
    
    @FXML
    private Button btnNota;

    @FXML
    private Button btnCancelar;
    
    private static List<Pedido> pedidos;
    private String itensPedido;

    @FXML
    void anularPedido(ActionEvent event) {
        if(JOptionPane.showConfirmDialog(null, "Deseja cancelar o pedido ?","Aviso",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            BaseDados.getRepositoryPedido().efetuarCancelamento(pedidos.get(0).getMesa().getId());
            JOptionPane.showMessageDialog(null, "Cancelamento feito com sucesso!");
            btnCancelar.fire();
        }else{
            JOptionPane.showConfirmDialog(null, "Erro ao efetuar o pagamento!!","Erro",JOptionPane.YES_OPTION,JOptionPane.ERROR);
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        Stage stage = (Stage)btnEfetuarPagamento.getScene().getWindow();
        stage.close();
    }

    @FXML
    void efetuarPagamento(ActionEvent event) {
        if(JOptionPane.showConfirmDialog(null, "Deseja efetuar o pagamento do pedido ?","Aviso",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            BaseDados.getRepositoryPedido().efetuarPagamento(pedidos.get(0).getMesa().getId());
            JOptionPane.showMessageDialog(null, "Pagamento realizado com sucesso!");
            BaseDados.atualizarPedidosPendente();
            if(JOptionPane.showConfirmDialog(null, "Deseja imprimir a nota do pagamento ?","Aviso",JOptionPane.YES_OPTION) == JOptionPane.YES_OPTION){
                btnNota.fire();
            }
            btnCancelar.fire();
        }else{
            JOptionPane.showConfirmDialog(null, "Erro ao efetuar o pagamento!!","Erro",JOptionPane.YES_OPTION);
        }
    }

    @FXML
    void keyTroco(KeyEvent event) {
        if(trocoTxt.getText().equals("") || trocoTxt.getText().equals(",") || trocoTxt.getText().equals(".")){
            lblTroco.setText("R$ 0,00");
            trocoTxt.setText("");
        }else if(!verificarDigito(trocoTxt.getText())){
            
            if(trocoTxt.getText().contains(",")){
            
                double total = montarTotal(pedidos);
                double troco = Double.parseDouble(trocoTxt.getText().replace(",", "."));
                lblTroco.setText("R$ "+String.format("%.2f", (troco - total)));
            }else{
                double total = montarTotal(pedidos);
                double troco = Double.parseDouble(trocoTxt.getText());
                lblTroco.setText("R$ "+String.format("%.2f", (troco - total)));
            }
        }else{
            trocoTxt.setText("");
        }
    }
    private boolean verificarDigito(String digito){
        boolean condicao = false;
        
        for(int i=0;i<digito.length();i++){
            int caract = digito.charAt(i);
            if(caract>=48 && caract<=57 || caract==44 || caract==46){
                condicao=false;
            }else{
                condicao=true;
                break;
            }
        }
        return condicao;
    
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblMesa.setText(""+pedidos.get(0).getMesa().getNumero());
        lblTotal.setText("R$ "+String.format("%.2f", montarTotal(pedidos)));
        lblTroco.setText("R$ 0,00");
        GridPane grid = new GridPane();
        int i=0;
        for(Pedido pedido:pedidos){
            Button button = new Button("Código: "+pedido.getId()+",     R$ "+String.format("%.2f", pedido.getTotal()));
            button.setCursor(Cursor.HAND);
            button.setPrefWidth(253);
            
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    montarListPedido(pedido);
                    itensArea.setText(itensPedido);
                }
            });
            if(i==0){
                button.fire();
            }
            grid.add(button, 0, i);
            i++;
        }
        panePedidos.getChildren().add(grid);
        
        
    }
    
    private void montarListPedido(Pedido pedido){
        itensPedido = "";
        for(ItemCardapio item:pedido.getItens()){
            itensPedido+=item.getNome()+"  R$ "+String.format("%.2f", item.getPreco())+"\n";
        }
    }
    
    private double montarTotal(List<Pedido> pedidos){
        double total=0;
        for(Pedido p:pedidos){
            total+=p.getTotal();
        }
        return total;
    }

    public static void setPedidos(List<Pedido> pedidos) {
        ControllerFinalizarPedido.pedidos = pedidos;
    }
    
    @FXML
    void gerarNota(ActionEvent event) {
        try {
            JasperReport report = JasperCompileManager.compileReport("src/main/java/report/Pagamento.jrxml");
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("N_MESA",lblMesa.getText());
            param.put("COD_PEDIDO",pedidos.get(0).getId()+"");
            param.put("TROCO",lblTroco.getText());
            param.put("VALOR_RECEBIDO","R$ "+(trocoTxt.getText().equals("")?"0,00":trocoTxt.getText()));
            param.put("TOTAL",lblTotal.getText());
            JasperPrint print = JasperFillManager.fillReport(report, param,new JRBeanCollectionDataSource(listaItens()));
            JasperViewer viewer = new JasperViewer(print,false);
            viewer.show();
        } catch (JRException ex) {
            Logger.getLogger(ControllerFinanciaMensal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private List<ItemCardapio> listaItens(){
        List<ItemCardapio> lista = new ArrayList<>();
        for(Pedido p:pedidos){
            for(ItemCardapio item:p.getItens()){
                lista.add(item);
            }
        }
        
        return lista;
    }

}
