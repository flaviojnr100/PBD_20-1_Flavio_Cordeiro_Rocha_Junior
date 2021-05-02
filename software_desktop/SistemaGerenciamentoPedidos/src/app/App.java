package app;


import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Funcionario;
import model.Mesa;
import repository.RepositoryFuncionario;

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

        Parent login = FXMLLoader.load(getClass().getClassLoader().getResource("view/FXMLDashBoard.fxml"));
        Scene scene = new Scene(login);
        primaryStage.setScene(scene);
        primaryStage.show();
        

    }
    
    public static void main(String[] args) {
        RepositoryFuncionario r = new RepositoryFuncionario();
    /*    Funcionario f = r.buscarId(1);
        
        System.out.println("Nome: "+f.getNome());
        System.out.println("Nome: "+f.getCpf());
      */
    
      /*  List<Funcionario> fs = r.buscarTodos();
        for(Funcionario f:fs){
            System.out.println("Nome: "+f.getNome());
            System.out.println("Login: "+f.getLogin());
        }*/
   /*   Funcionario f = new Funcionario();
      f.setNome("Roberta");
      f.setSobrenome("Rocha");
      f.setCpf("12378945623");
      f.setLogin("roberta123");
      f.setSenha("123");
      f.setIsPermissao(true);
      f.setTelefone("123456789");
      f.setTipoAcesso("funcionario");
      r.salvar(f);
    */
        launch(args);
    }
    
}
