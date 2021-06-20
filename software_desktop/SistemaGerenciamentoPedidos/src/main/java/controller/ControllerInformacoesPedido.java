package controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
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
import model.ItemCardapio;
import model.Pedido;

public class ControllerInformacoesPedido implements Initializable {

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblLogin;

    @FXML
    private Label lblMesa;

    @FXML
    private Label lblData;

    @FXML
    private Label lblStatus;

    @FXML
    private TextArea cardapioArea;

    @FXML
    private Button btnOk;

    private static Pedido pedido;
    
    @FXML
    void ok(ActionEvent event) {
        Stage stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblLogin.setText(pedido.getFuncionario().getLogin());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        lblData.setText(sdf.format(pedido.getDataPedido()));
        lblMesa.setText(pedido.getMesa().getNumero()+"");
        lblStatus.setText(pedido.getStatus());
        
        
        lblTotal.setText("R$ "+String.format("%.2f", pedido.getTotal()));
        String itens="";
        for(ItemCardapio item:pedido.getItens()){
            itens+=item.getNome()+" | Preço: R$ "+String.format("%.2f", item.getPreco())+"\n";
        }
        cardapioArea.setText(itens);
    }
    
    @FXML
    void keyOk(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            btnOk.fire();
        }
    }

    public static void setPedido(Pedido pedido) {
        ControllerInformacoesPedido.pedido = pedido;
    }

}
