/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableArrayBase;
import javafx.collections.ObservableListBase;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.converter.TimeStringConverter;
import javax.swing.JOptionPane;
import model.BaseDados;
import model.Pedido;

public class ControllerDashboard implements Initializable {

    @FXML
    private Button btnMesa;

    @FXML
    private Button btnPedido;

    @FXML
    private Button btnFuncionario;

    @FXML
    private Button btnCardapio;

    @FXML
    private Button btnFinancia;

    @FXML
    private Button btnConfiguracao;
    
    @FXML
    private ScrollPane scroll;
    
    @FXML
    private Pane principalPane;
    
    @FXML
    private Pane pedidosPane;
    
    @FXML
    private Button btnAtualizarpedido;
    
    private int n_mesa=0;
    private static boolean alterouPedido = false;
    private static Task<Void> task;
    private static Task<Void> task1;
    private int quant_mesas=0;
    private static boolean alterou=false;
    private static boolean janelaAberta=false;
    
    @FXML
    void cardapio(ActionEvent event) {
        acessarTela("Cardapio", "view/FXMLCardapio.fxml");
    }

    @FXML
    void configuracao(ActionEvent event) {
        if(BaseDados.getAutenticado().getTipoAcesso().equals("superusuario")){
            acessarTela("Configuracao", "view/FXMLConfiguracao.fxml");
        }else{
            acessarTela("Editar", "view/FXMLEditarFuncionario.fxml");
        }
    }

    @FXML
    void financia(ActionEvent event) {
        acessarTela("Financia", "view/FXMLFinancia.fxml");
    }

    @FXML
    void funcionario(ActionEvent event) {
        acessarTela("Funcion??rio", "view/FXMLFuncionario.fxml");
    }

    @FXML
    void mesa(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(new File("src/main/java/view/FXMLMesa.fxml").toURL());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Mesa");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initOwner((Stage) btnCardapio.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image("file:src/main/java/asset/icone.png"));
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    BaseDados.atualizarMesa();
                    montarPainelMesas();
                    janelaAberta=false;
                    
                }
            });
            janelaAberta=true;
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    void pedido(ActionEvent event) {
        acessarTela("Pedido", "view/FXMLPedido.fxml");
    }
    private void acessarTela(String nome,String caminho){
        try {
            Parent root = FXMLLoader.load(new File("src/main/java/"+caminho).toURL());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle(nome);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initOwner((Stage) btnCardapio.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image("file:src/main/java/asset/icone.png"));
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quant_mesas = BaseDados.getMesas().size();
        montarPainelMesas();
        montarPainelPedidos();
        verificarPedidos();
        if(BaseDados.getAutenticado().getTipoAcesso().equals("superusuario")){
            BaseDados.getRepositoryBackup().bucarVariaveis();
            verificarBackup();
        }
    }
    
    private void verificarPedidos(){
         task = new Task<Void>() {
            
            @Override
            protected Void call() throws Exception {
                while(true){
                    
                    if(BaseDados.getRepositoryPedido().verificarPedidos(BaseDados.getPedidoPendente().size()) == false){
                        alterouPedido = true;
                        
                    }
                    if(BaseDados.getRepositoryMesa().verificarMesas(BaseDados.getMesas().size()) == false && janelaAberta==false){
                        alterou = true;
                    }
                    
                    if(alterou){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                montarPainelMesas();
                                alterou=false;
                                
                            }
                        });
                    }
                    if(alterouPedido){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                montarPainelPedidos();
                                alterouPedido = false;
                                
                            }
                        });
                    }
                    Thread.sleep(1000);
                }
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }
    private boolean verificarHorario(String horario){
        
        int seg=0;
        String [] temp = horario.split(":");
        seg+=(Integer.parseInt(temp[1]) *60);
        seg+=(Integer.parseInt(temp[0]) * 3600);
        
        Calendar tempoVerif = Calendar.getInstance();
        tempoVerif.setTimeInMillis(Long.parseLong(seg+""));
        
        if(tempoVerif.getTimeInMillis()<=LocalTime.now().toSecondOfDay()){
            
            return true;
        }else{
            if(BaseDados.isAlteracao()==true){
                BaseDados.setAlteracao(false);
            }
            
        }
        return false;
    }
    private void verificarBackup(){
        task1 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while(true){
                    if(verificarHorario(BaseDados.getHorario()) && BaseDados.isAlteracao() == false){
                        if(JOptionPane.showConfirmDialog(null, "Chegou o hor??rio do backup","Aviso",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                            BaseDados.setAlteracao(true);
                            BaseDados.getRepositoryBackup().salvar(BaseDados.getHorario(), BaseDados.isAlteracao());
                            BaseDados.getRepositoryBackup().backup();
                        }
                    }else{
                    }
                    Thread.sleep(1000);
                }
                
                
            }
        };
        Thread thread = new Thread(task1);
        thread.start();
    }
    private void montarPainelPedidos(){
        BaseDados.atualizarPedidosPendente();
        pedidosPane.getChildren().clear();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        int i=0;
        for(Pedido p:BaseDados.getPedidoPendente()){
            Button button = new Button("N?? "+p.getId());
            button.setPrefHeight(62.0);
            button.setPrefWidth(195.0);
            button.getStyleClass().add("botaoDashLado");
            button.setFont(Font.font("Century Gothic", 18.0));
            button.setCursor(Cursor.HAND);
            button.setOnAction(new EventHandler<ActionEvent>() {
                private final int n_pedido= p.getId();
                @Override
                public void handle(ActionEvent event) {
                    try {
                        ControllerVisualizacaoPedido.setPedido(BaseDados.getRepositoryPedido().buscarId(n_pedido));
                        Parent root = FXMLLoader.load(new File("src/main/java/view/FXMLVisualizacaoPedido.fxml").toURL());
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setTitle("Pedido de N?? "+n_pedido);
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.initOwner((Stage) btnCardapio.getScene().getWindow());
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.getIcons().add(new Image("file:src/main/java/asset/icone.png"));
                        
                        stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ControllerDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
                }
            });
            
            grid.add(button, 0, i);
            i++;
        }
        pedidosPane.getChildren().add(grid);
    }

    private void montarPainelMesas(){
        n_mesa = 0;
        principalPane.getChildren().clear();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPrefWidth(574.0);
        
            double linhas = (BaseDados.getMesas().size()/4)>0?BaseDados.getMesas().size()/4:1;
            
                
               for(int j=0;j<4;j++){
                    ColumnConstraints col = new ColumnConstraints();
                    col.setHalignment(HPos.CENTER);
                    col.setHgrow(Priority.SOMETIMES);
                    col.setMinWidth(10);
                    col.setPrefWidth(90.0);
                    grid.getColumnConstraints().add(col);
               }
            for(int i=0;i<linhas+1;i++){
                    RowConstraints row = new RowConstraints();
                    row.setMaxHeight(123.79999389648438);
                    row.setMinHeight(10);
                    row.setPrefHeight(123.79999389648438);
                    row.setVgrow(Priority.SOMETIMES);
                    grid.getRowConstraints().add(row);
                 
            }
            
            for(int i=0;i<linhas+1;i++){
                
               
                
                for(int j=0;j<4;j++){

                    
                    
                    if(BaseDados.getMesas().size()==n_mesa){
                        break;
                    }
                    Button button = new Button("Mesa "+BaseDados.getMesas().get(n_mesa).getNumero());
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        final int n = n_mesa;
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                BaseDados.atualizarPedidoMesa(BaseDados.getMesas().get(n).getNumero());
                                ControllerFinalizarPedido.setPedidos(BaseDados.getPedidos());
                                if(BaseDados.getPedidos().size()>0){
                                    Parent root = FXMLLoader.load(new File("src/main/java/view/FXMLFinalizarPedido.fxml").toURL());
                                    Scene scene = new Scene(root);
                                    Stage stage = new Stage();
                                    stage.setTitle("Finalizar pedido");
                                    stage.setScene(scene);
                                    stage.setResizable(false);
                                    stage.initOwner((Stage) btnCardapio.getScene().getWindow());
                                    stage.initModality(Modality.APPLICATION_MODAL);
                                    stage.getIcons().add(new Image("file:src/main/java/asset/icone.png"));
                                    stage.showAndWait();
                                }else{
                                    JOptionPane.showMessageDialog(null, "N??o h?? pedidos nessa mesa!");
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(ControllerDashboard.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
            
                    button.getStyleClass().add("botaoMesa1");
                    button.setPrefHeight(98.0);
                    button.setPrefWidth(129.0);
                    button.setAlignment(Pos.BOTTOM_CENTER);
                    button.setContentDisplay(ContentDisplay.TOP);
            
                    button.setCursor(Cursor.HAND);
                    button.setFont(Font.font("Century Gothic", 13.0));
            
                    ImageView imagem = new ImageView(new Image("file:src/main/java/asset/mesa.png"));
                    imagem.setFitHeight(76.0);
                    imagem.setFitWidth(94.0);
                    imagem.setPickOnBounds(true);
                    imagem.setPreserveRatio(true);
            
                    button.setGraphic(imagem);
                    
                    grid.add(button, j, i);
                    
                    n_mesa++;
                }
            }

           principalPane.getChildren().add(grid);
    }


    @FXML
    void atualizatPedido(ActionEvent event) {
        
        montarPainelPedidos();
    }

    public static void setAlterou(boolean alterou) {
        ControllerDashboard.alterou = alterou;
    }

    public static void setAlterouPedido(boolean alterouPedido) {
        ControllerDashboard.alterouPedido = alterouPedido;
    }





    public static Task<Void> getTask() {
        return task;
    }

    public static Task<Void> getTask1() {
        return task1;
    }

    public static void setJanelaAberta(boolean janelaAberta) {
        ControllerDashboard.janelaAberta = janelaAberta;
    }
    
    
}
