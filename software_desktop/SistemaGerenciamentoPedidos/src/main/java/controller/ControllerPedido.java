package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import model.BaseDados;
import model.Mesa;
import model.Pedido;

public class ControllerPedido implements Initializable {

    @FXML
    private TableView<Pedido> tablePedido;

    @FXML
    private TableColumn<Pedido, Integer> colCodigo;

    @FXML
    private TableColumn<Pedido, String> colMesa;

    @FXML
    private TableColumn<Pedido, String> colFuncionario;

    @FXML
    private TableColumn<Pedido, String> colData;

    @FXML
    private TableColumn<Pedido, String> colTotal;

    @FXML
    private TableColumn<Pedido, String> colStatus;

    @FXML
    private TextField buscarTxt;

    @FXML
    private Button btnBuscar;

    @FXML
    private RadioButton rbtnMesa;

    @FXML
    private ToggleGroup grupo;


    @FXML
    private RadioButton rbtnData;

    @FXML
    private RadioButton rbtnCodigo;

    @FXML
    private DatePicker dateData;

    @FXML
    private Button btnAdiciionar;    
    
    @FXML
    private ContextMenu contextMenu;
    
    
    @FXML
    private MenuItem contextMenuPendente;

    @FXML
    private MenuItem contextMenuConcluido;

    @FXML
    private MenuItem contextMenuCancelado;

    @FXML
    void buscar(ActionEvent event) {
        if(rbtnMesa.isSelected()){
            if(!buscarTxt.getText().equals("")){
                if(!verificarDigito(buscarTxt.getText())){
                    BaseDados.atualizarPedidoMesaTodos(Integer.parseInt(buscarTxt.getText()));
                    atualizar();
                }else{
                    JOptionPane.showMessageDialog(null, "Só é permitido numeros!");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Não pode deixar campo de texto em branco!");
            }
        }else if(rbtnCodigo.isSelected()){
            
           if(!buscarTxt.getText().equals("")){
                if(!verificarDigito(buscarTxt.getText())){
                   BaseDados.atualizarPedidoId(Integer.parseInt(buscarTxt.getText()));
                   atualizar();
                }else{
                    JOptionPane.showMessageDialog(null, "Só é permitido numeros!");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Não pode deixar campo de texto em branco!");
            }
        }else if(rbtnData.isSelected()){
            if(dateData.getValue()!=null){
                BaseDados.atualizarPedidoData(dateData.getValue().getDayOfMonth(), dateData.getValue().getMonthValue(), dateData.getValue().getYear());
                atualizar();
            }else{
                JOptionPane.showMessageDialog(null, "Formato de data invalido!");
            }
        }
    }

    @FXML
    void cadastrar(ActionEvent event) {
        try {
            ControllerInformacoesPedido.setPedido(tablePedido.getSelectionModel().getSelectedItem());
            Parent root = FXMLLoader.load(new File("src/main/java/view/FXMLCadastrarPedido.fxml").toURL());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Cadastro");
            stage.setScene(scene);
            stage.getIcons().add(new Image("file:src/main/java/asset/icone.png"));
            stage.setResizable(false);
            stage.initOwner((Stage) btnAdiciionar.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
             stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    BaseDados.atualizarPedido();
                    atualizar();
                }
            });
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void colocarCampo(ActionEvent event) {
        if(event.getSource() == rbtnData){
            dateData.setVisible(true);
            buscarTxt.setVisible(false);
        }else{
            dateData.setVisible(false);
            buscarTxt.setVisible(true);
        }
    }
    @FXML
    void update(ActionEvent event) {
         try {
            if(!tablePedido.getSelectionModel().getSelectedItem().getStatus().equals("pago")){
                ControllerEditarPedido.setPedido(tablePedido.getSelectionModel().getSelectedItem());
                Parent root = FXMLLoader.load(new File("src/main/java/view/FXMLEditarPedido.fxml").toURL());
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Editar");
                stage.setScene(scene);
                stage.getIcons().add(new Image("file:src/main/java/asset/icone.png"));
                stage.setResizable(false);
                stage.initOwner((Stage) btnAdiciionar.getScene().getWindow());
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        BaseDados.atualizarCardapio();
                        atualizar();
                    }
                });
                stage.showAndWait();
                }else{
                    contextMenu.hide();
                    JOptionPane.showMessageDialog(null, "Não pode editar um pedido pago!");
            }
        } catch (IOException ex) {
            Logger.getLogger(ControllerPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void excluir(ActionEvent event) {
        contextMenu.hide();
        if(JOptionPane.showConfirmDialog(null, "Deseja excluir o pedido ?","Aviso",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            BaseDados.getRepositoryPedido().deletar(tablePedido.getSelectionModel().getSelectedItem().getId());
            BaseDados.atualizarPedido();
            atualizar();
        }
    }

    @FXML
    void mudarStatus(ActionEvent event) {
        if(event.getSource().equals(contextMenuPendente)){
            tablePedido.getSelectionModel().getSelectedItem().setStatus("pendente");
        }else if(event.getSource().equals(contextMenuConcluido)){
            tablePedido.getSelectionModel().getSelectedItem().setStatus("concluido");
        }else{
            tablePedido.getSelectionModel().getSelectedItem().setStatus("cancelado");
        }
        BaseDados.getRepositoryPedido().mudarStatus(tablePedido.getSelectionModel().getSelectedItem());
        BaseDados.atualizarPedido();
        atualizar();
    }

    @FXML
    void verInformacoes(ActionEvent event) {
        try {
            ControllerInformacoesPedido.setPedido(tablePedido.getSelectionModel().getSelectedItem());
            Parent root = FXMLLoader.load(new File("src/main/java/view/FXMLVerInformacoesPedido.fxml").toURL());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Informações");
            stage.setScene(scene);
            stage.getIcons().add(new Image("file:src/main/java/asset/icone.png"));
            stage.setResizable(false);
            stage.initOwner((Stage) btnAdiciionar.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void atualizar(){
         colCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
         colData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pedido, String>, ObservableValue<String>>() {
             @Override
             public ObservableValue<String> call(TableColumn.CellDataFeatures<Pedido, String> param) {
                final Pedido pedido = param.getValue();
                 final SimpleObjectProperty sop = new SimpleObjectProperty(sdf.format(pedido.getDataPedido()));
                 return sop;
             }
         });
         colFuncionario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pedido, String>, ObservableValue<String>>() {
             @Override
             public ObservableValue<String> call(TableColumn.CellDataFeatures<Pedido, String> param) {
                 final Pedido pedido = param.getValue();
                 final SimpleObjectProperty sop = new SimpleObjectProperty(pedido.getFuncionario().getNome()+" "+pedido.getFuncionario().getSobrenome());
                 return sop;
             }
         });
         colMesa.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pedido, String>, ObservableValue<String>>() {
             @Override
             public ObservableValue<String> call(TableColumn.CellDataFeatures<Pedido, String> param) {
                 final Pedido pedido = param.getValue();
                 final SimpleObjectProperty sop = new SimpleObjectProperty(pedido.getMesa().getNumero());
                 return sop;
             }
         });
         colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
         colTotal.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pedido, String>, ObservableValue<String>>() {
             @Override
             public ObservableValue<String> call(TableColumn.CellDataFeatures<Pedido, String> param) {
                final Pedido pedido = param.getValue();
                final SimpleObjectProperty sop = new SimpleObjectProperty("R$ "+String.format("%.2f", pedido.getTotal()));
                return sop;
             }
         });;
         
         tablePedido.setItems(FXCollections.observableArrayList(BaseDados.getPedidos()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BaseDados.atualizarPedido();
        atualizar();
        dateData.setValue(LocalDate.now());
    }
    @FXML
    void keyBuscar(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            btnBuscar.fire();
        }
        if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE){
            BaseDados.atualizarPedido();
            atualizar();
            buscarTxt.setText("");
        }
    }
    @FXML
    void KeyBuscarData(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            btnBuscar.fire();
        }
        if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE){
            BaseDados.atualizarPedido();
            atualizar();
            dateData.setValue(LocalDate.now());
        }
    }
    
    private boolean verificarDigito(String digito){
        boolean condicao = false;
        
        for(int i=0;i<digito.length();i++){
            int caract = digito.charAt(i);
            if(caract>=48 && caract<=57){
                condicao=false;
            }else{
                condicao=true;
                break;
            }
        }
        return condicao;
    
    }
}
