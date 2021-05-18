package app;


import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Funcionario;
import model.Mesa;
import model.SenhaReset;
import repository.RepositoryFuncionario;
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
        primaryStage.show();
        

    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
