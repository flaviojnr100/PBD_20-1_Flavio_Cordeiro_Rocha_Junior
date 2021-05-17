/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import repository.RepositoryFuncionario;
import repository.RepositoryReset;

/**
 *
 * @author Flavio
 */
public class BaseDados {
    private static Funcionario autenticado;
    private static RepositoryFuncionario repositoryFuncionario;
    private static RepositoryReset repositoryReset;
    private static List<Funcionario> funcionarios;
    private static List<SenhaReset> resets;
    private static int status;
    
    public static void atualizarFuncionarios(){
        funcionarios = getRepositoryFuncionario().buscarTodos();
    }
    public static void atualizarFuncionariosSU(){
       funcionarios = getRepositoryFuncionario().buscarTodosSU();
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
    
    public static void atualizarFuncionariosNomeSU(String nome){
        funcionarios = getRepositoryFuncionario().buscarNomeSU(nome);
    }
    public static void atualizarFuncionariosCpfSU(String cpf){
        funcionarios = getRepositoryFuncionario().buscarCpfSU(cpf);
    }
    public static void atualizarFuncionariosLoginSU(String login){
        funcionarios = getRepositoryFuncionario().buscarLoginSU(login);
    }
    
    public static void atualizarResets(){
        resets = getRepositoryReset().buscarTodos();
    }
    public static void atualizarResetLogin(String login){
        SenhaReset sr = getRepositoryReset().buscarLogin(login);
        if(status==202){
            resets.clear();
            resets.add(sr);
        }
        
        
    }        
    
    public static List<Funcionario> getFuncionarios(){
        if(funcionarios==null){
            atualizarFuncionarios();
        }
        return funcionarios;
    }
    
    public static List<SenhaReset> getResets(){
        if(resets==null){
            atualizarResets();
        }
        return resets;
    }

    public static RepositoryFuncionario getRepositoryFuncionario() {
        if(repositoryFuncionario==null){
            repositoryFuncionario = new RepositoryFuncionario();
        }
        return repositoryFuncionario;
    }

    public static Funcionario getAutenticado() {
        return autenticado;
    }

    public static void setAutenticado(Funcionario autenticado) {
        BaseDados.autenticado = autenticado;
    }

    public static RepositoryReset getRepositoryReset() {
        if(repositoryReset == null){
            repositoryReset = new RepositoryReset();
        }
        return repositoryReset;
    }

    public static int getStatus() {
        return status;
    }

    public static void setStatus(int status) {
        BaseDados.status = status;
    }
    
    
    
}
