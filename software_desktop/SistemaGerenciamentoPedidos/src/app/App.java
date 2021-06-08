package app;


import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.BaseDados;
import model.FinanciaMensal;
import model.Funcionario;
import model.ItemCardapio;
import model.Mesa;
import model.Pedido;
import model.SenhaReset;
import repository.RepositoryCardapio;
import repository.RepositoryFinancia;
import repository.RepositoryFuncionario;
import repository.RepositoryPedido;
import repository.RepositoryReset;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Flavio
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent login = FXMLLoader.load(getClass().getClassLoader().getResource("view/FXMLLogin.fxml"));
        Scene scene = new Scene(login);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("asset/icone.png")));
        primaryStage.setResizable(false);
        primaryStage.show();
        

    }
    
    public static void main(String[] args) {
        launch(args);
       /* RepositoryFinancia rp = new RepositoryFinancia();
       for(FinanciaMensal pedido:rp.buscarTodos()){
           
           System.out.println("Data: "+pedido.getDataPedido());
           System.out.println("-------------------");
       }
        */
      /*  RepositoryPedido rp = new RepositoryPedido();
        for(Pedido p: rp.buscarTodos()){
            System.out.println("Data: "+p.getDataPedido());
        }*/
       // System.out.println(""+BaseDados.getMeses().get(1));
    }
    
}
