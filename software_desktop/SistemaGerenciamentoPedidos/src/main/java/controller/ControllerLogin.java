package controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;
import model.BaseDados;
import model.Criptografia;

public class ControllerLogin {

    @FXML
    private TextField loginTxt;

    @FXML
    private PasswordField senhaTxt;

    @FXML
    private Label lblEsqueceu;

    @FXML
    private Button btnEntrar;
    
    @FXML
    void Entrar(ActionEvent event) {
        Criptografia.getMd();
        String nome,senha;
        nome =  loginTxt.getText();
        senha = senhaTxt.getText();
        BaseDados.setAutenticado(BaseDados.getRepositoryFuncionario().autenticar(nome, Criptografia.criptografar(senha)));
        if(BaseDados.getStatus() == 202 && BaseDados.getAutenticado().getSenha() != null){
            if((BaseDados.getAutenticado().getTipoAcesso().equals("gerente") || BaseDados.getAutenticado().getTipoAcesso().equals("superusuario"))){
            try {
                if(BaseDados.getAutenticado().isIsReset()){
                    BaseDados.getAutenticado().setIsReset(true);
                }
                BaseDados.getAutenticado().setIsLogado(true);
                BaseDados.getAutenticado().setUltimoAcesso(new Date());
                BaseDados.getRepositoryFuncionario().editar(BaseDados.getAutenticado().getId(), BaseDados.getAutenticado());
                Parent root = FXMLLoader.load(new File("src/main/java/view/FXMLDashBoard.fxml").toURL());
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        int resultado = JOptionPane.showConfirmDialog(null, "Deseja sair do sistema e ir para a tela de login?","Aviso",JOptionPane.YES_NO_CANCEL_OPTION); 
                         if(resultado == JOptionPane.YES_NO_OPTION){
                            BaseDados.getRepositoryFuncionario().logout(BaseDados.getAutenticado().getLogin());
                            ControllerDashboard.getTask().cancel();
                            if(ControllerDashboard.getTask1()!=null){
                                ControllerDashboard.getTask1().cancel();
                            }
                            try {
                                Stage stage1 = new Stage();
                                Parent login = FXMLLoader.load(new File("src/main/java/view/FXMLLogin.fxml").toURL());
                                Scene scene = new Scene(login);
                                stage1.setScene(scene);
                                stage1.getIcons().add(new Image("file:src/main/java/asset/icone.png"));
                                stage1.setResizable(false);
                                
                                stage.close();
                                
                                stage1.show();
                                BaseDados.getMeses();
                            } catch (IOException ex) {
                                Logger.getLogger(ControllerLogin.class.getName()).log(Level.SEVERE, null, ex);
                            }
                         
                         }else if(resultado==1){
                            BaseDados.getRepositoryFuncionario().logout(BaseDados.getAutenticado().getLogin());
                            ControllerDashboard.getTask().cancel();
                            if(ControllerDashboard.getTask1()!=null){
                                ControllerDashboard.getTask1().cancel();
                            }
                            
                         }else{
                             event.consume();
                         }
                    }
                });
                stage.setTitle("Dashboard");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.getIcons().add(new Image("file:src/main/java/asset/icone.png"));
                Stage atual = (Stage) btnEntrar.getScene().getWindow();
                atual.close();
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ControllerLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
            }else{
                JOptionPane.showMessageDialog(null, "Usu??rio n??o tem acesso a esse tipo de sistema!","Aviso",JOptionPane.YES_OPTION);
            }
        }else{
            
           
            if(BaseDados.getStatus() == 403){
                JOptionPane.showMessageDialog(null, "Usu??rio j?? est?? logado no sistema!","Aviso",JOptionPane.YES_OPTION);
            }else if(BaseDados.getStatus() == 400){
                JOptionPane.showMessageDialog(null, "Usu??rio n??o tem permiss??o para acessar o sistema!","Aviso",JOptionPane.YES_OPTION);
            }else if(BaseDados.getStatus() == 202 && BaseDados.getAutenticado().getSenha() == null){
                JOptionPane.showMessageDialog(null, "Usu??rio invalido!","Aviso",JOptionPane.YES_OPTION);
            }
            /*else if(BaseDados.getStatus() == 302){
                JOptionPane.showMessageDialog(null, "Senha inv??lida!","Aviso",JOptionPane.YES_OPTION);
            }*/
        }
    }

    @FXML
    void keyReleasedEntrar(KeyEvent event) {
        if(KeyCode.ENTER == event.getCode()){
            btnEntrar.fire();
        }
    }
    
    @FXML
    void esqueceuSenha(MouseEvent event) {
       int id = BaseDados.getRepositoryFuncionario().buscarLoginUnico(loginTxt.getText()).getId();
        if(BaseDados.getStatus()==202){
            BaseDados.getRepositoryReset().salvarReset(id);
            JOptionPane.showMessageDialog(null, "Aguarde o administrador resetar a senha");
        }else{
            JOptionPane.showMessageDialog(null, "Login invalido");
        }
        
    }

}
