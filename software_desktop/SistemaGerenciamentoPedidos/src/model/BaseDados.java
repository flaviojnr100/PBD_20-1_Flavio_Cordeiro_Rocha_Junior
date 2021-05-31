/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import repository.RepositoryCardapio;
import repository.RepositoryFuncionario;
import repository.RepositoryPedido;
import repository.RepositoryReset;

/**
 *
 * @author Flavio
 */
public class BaseDados {
    private static Funcionario autenticado;
    private static RepositoryFuncionario repositoryFuncionario;
    private static RepositoryCardapio repositoryCardapio;
    private static RepositoryPedido repositoryPedido;
    private static RepositoryReset repositoryReset;
    private static List<Funcionario> funcionarios;
    private static List<ItemCardapio> cardapio;
    private static List<SenhaReset> resets;
    private static List<Pedido> pedidos;
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
    
    public static void atualizarCardapio(){
        cardapio = getRepositoryCardapio().buscarTodos();
    }
    public static void atualizarCardapioNome(String nome){
        cardapio = getRepositoryCardapio().buscarNome(nome);
    }
    
    public static void atualizarPedido(){
        pedidos = getRepositoryPedido().buscarTodos();
    }
    public static void atualizarPedidoMesa(int mesa){
        pedidos = getRepositoryPedido().buscarMesa(mesa);
    }
    public static void atualizarPedidoData(int dia,int mes,int ano){
        pedidos = getRepositoryPedido().buscarData(dia, mes, ano);
    }
    public static void atualizarPedidoId(int id){
        pedidos.clear();
        pedidos.add(getRepositoryPedido().buscarId(id));
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
    public static List<ItemCardapio> getCardapio(){
        if(cardapio==null){
            atualizarCardapio();
        }
        return cardapio;
    }

    public static RepositoryFuncionario getRepositoryFuncionario() {
        if(repositoryFuncionario==null){
            repositoryFuncionario = new RepositoryFuncionario();
        }
        return repositoryFuncionario;
    }
    public static RepositoryCardapio getRepositoryCardapio() {
        if(repositoryCardapio==null){
            repositoryCardapio = new RepositoryCardapio();
        }
        return repositoryCardapio;
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

    public static RepositoryPedido getRepositoryPedido() {
        if(repositoryPedido == null){
            repositoryPedido = new RepositoryPedido();
        }
        return repositoryPedido;
    }

    public static List<Pedido> getPedidos() {
        return pedidos;
    }
    
    
    
}
