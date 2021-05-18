package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import model.BaseDados;
import model.Funcionario;
import repository.RepositoryFuncionario;

public class ControllerFuncionario implements Initializable {

    @FXML
    private TableView<Funcionario> tableFuncionario;

    @FXML
    private TableColumn<Funcionario, String> colCpf;

    @FXML
    private TableColumn<Funcionario, String> colNome;

    @FXML
    private TableColumn<Funcionario, String> colSobrenome;

    @FXML
    private TableColumn<Funcionario, String> colTelefone;

    @FXML
    private TableColumn<Funcionario, String> colAcesso;
    
    @FXML
    private TableColumn<Funcionario, Boolean> colPermissao;

    @FXML
    private TextField buscaTxt;

    @FXML
    private Button btnPesquisar;

    @FXML
    private RadioButton rbtNome;

    @FXML
    private ToggleGroup busca;

    @FXML
    private RadioButton rbtCpf;

    @FXML
    private RadioButton rbtLogin;

    @FXML
    private Button btnAdicionar;
    
    @FXML
    private Button btnReset;
    
    @FXML
    void adicionarFuncionario(ActionEvent event) {
         try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/FXMLCadastrarFuncionario.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Funcionário");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("asset/icone.png")));
            stage.initOwner((Stage) btnAdicionar.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    atualizar();
                }
            });
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void mudarAcesso(ActionEvent event) {
        BaseDados.getRepositoryFuncionario().alterarEstado(tableFuncionario.getSelectionModel().getSelectedItem().getId());
        if(BaseDados.getAutenticado().getTipoAcesso().equals("superusuario")){
            BaseDados.atualizarFuncionariosSU();
        }else{
            BaseDados.atualizarFuncionarios();
        }
        atualizar();
    }

    @FXML
    void pesquisar(ActionEvent event) {
        
        if(BaseDados.getAutenticado().getTipoAcesso().equals("superusuario")){
            if(rbtNome.isSelected()){
                BaseDados.atualizarFuncionariosNomeSU(buscaTxt.getText());
                atualizar();
            }else if(rbtCpf.isSelected()){
                BaseDados.atualizarFuncionariosCpfSU(buscaTxt.getText());
                atualizar();
            }else if(rbtLogin.isSelected()){
                BaseDados.atualizarFuncionariosLoginSU(buscaTxt.getText());
                atualizar();
            }
        }else{
        
            if(rbtNome.isSelected()){
                BaseDados.atualizarFuncionariosNome(buscaTxt.getText());
                atualizar();
            }else if(rbtCpf.isSelected()){
                BaseDados.atualizarFuncionariosCpf(buscaTxt.getText());
                atualizar();
            }else if(rbtLogin.isSelected()){
                BaseDados.atualizarFuncionariosLogin(buscaTxt.getText());atualizar();
            }
        }
        
    }

    @FXML
    void verInformacoes(ActionEvent event) {
        try {
            ControllerInformacoesFuncionario.setFuncionario(tableFuncionario.getSelectionModel().getSelectedItem());
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/FXMLInformacoesFuncionario.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Informações");
            stage.setScene(scene);
            stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("asset/icone.png")));
            stage.setResizable(false);
            stage.initOwner((Stage) btnAdicionar.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(BaseDados.getAutenticado().getTipoAcesso().equals("superusuario")){
            btnReset.setVisible(true);
            BaseDados.atualizarFuncionariosSU();
        }else{
            BaseDados.atualizarFuncionarios();
        }
        
        atualizar();
    }
    
    private void atualizar(){
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colSobrenome.setCellValueFactory(new PropertyValueFactory<>("sobrenome"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colAcesso.setCellValueFactory(new PropertyValueFactory<>("tipoAcesso"));
        colPermissao.setCellValueFactory(new PropertyValueFactory<>("isPermissao"));
        
        tableFuncionario.setItems(FXCollections.observableArrayList(BaseDados.getFuncionarios()));
    }
    
    @FXML
    void keyBusca(KeyEvent event) {
        if(KeyCode.ENTER == event.getCode()){
            btnPesquisar.fire();
        }
        
        if(KeyCode.BACK_SPACE == event.getCode() || KeyCode.DELETE == event.getCode()){
            buscaTxt.setText("");
            if(BaseDados.getAutenticado().getTipoAcesso().equals("superusuario")){
                BaseDados.atualizarFuncionariosSU();
            }else{
                BaseDados.atualizarFuncionarios();
            }
            atualizar();
         }
        
    }
    
    @FXML
    void reset(ActionEvent event) {
         try {
            ControllerInformacoesFuncionario.setFuncionario(tableFuncionario.getSelectionModel().getSelectedItem());
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/FXMLReset.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Reset");
            stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("asset/icone.png")));
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initOwner((Stage) btnAdicionar.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
