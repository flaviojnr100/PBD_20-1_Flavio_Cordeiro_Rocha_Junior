/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import repository.RepositoryFuncionario;

/**
 *
 * @author Flavio
 */
public class BaseDados {
    private static RepositoryFuncionario repositoryFuncionario;
    private static List<Funcionario> funcionarios;
    
    public static void atualizarFuncionarios(){
        funcionarios = getRepositoryFuncionario().buscarTodos();
    }
    public static void atualizarFuncionariosNome(String nome){
        funcionarios = getRepositoryFuncionario().buscarNome(nome);
    }
    public static void atualizarFuncionariosCpf(String cpf){
        funcionarios = getRepositoryFuncionario().buscarCpf(cpf);
    }
    public static void atualizarFuncionariosLogin(String login){
        funcionarios = getRepositoryFuncionario().buscarLogin(login);
    }
            
    
    public static List<Funcionario> getFuncionarios(){
        if(funcionarios==null){
            atualizarFuncionarios();
        }
        return funcionarios;
    }

    public static RepositoryFuncionario getRepositoryFuncionario() {
        if(repositoryFuncionario==null){
            repositoryFuncionario = new RepositoryFuncionario();
        }
        return repositoryFuncionario;
    }
    
    
    
}
