package app;





import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.BaseDados;
import repository.RepositoryCardapio;


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
        Parent login = FXMLLoader.load(new File("src/main/java/view/FXMLLogin.fxml").toURL());
        Scene scene = new Scene(login);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("file:src/main/java/asset/icone.png"));
        primaryStage.setResizable(false);
        primaryStage.show();
        BaseDados.getMeses();
        BaseDados.atualizarAnos();

    }
    
    public static void main(String[] args) {
        launch(args);
       
    }
    
}
