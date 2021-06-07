/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.RepositoryCardapio;
import repository.RepositoryFinancia;
import repository.RepositoryFuncionario;
import repository.RepositoryMesa;
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
    private static RepositoryMesa repositoryMesa;
    private static RepositoryFinancia repositoryFinancia;
    private static List<Funcionario> funcionarios;
    private static List<ItemCardapio> cardapio;
    private static List<SenhaReset> resets;
    private static List<Pedido> pedidos;
    private static List<Mesa> mesas;
    private static List<FinanciaMensal> financia;
    private static List<FinanciaMensal> financiaMensal;
    private static List<String> meses;
    private static List<String> anos;
    private static ObservableList mesesFx;
    private static ObservableList anosFx;
    private static int status;
    
    public static void atualizarMeses(){
        meses.add("Janeiro");
        meses.add("Fevereiro");
        meses.add("Março");
        meses.add("Abril");
        meses.add("Maio");
        meses.add("Junho");
        meses.add("Julho");
        meses.add("Agosto");
        meses.add("Setembro");
        meses.add("Outubro");
        meses.add("Novembro");
        meses.add("Dezembro");
        mesesFx = FXCollections.observableArrayList(getMeses());
    }
    public static void atualizarAnos(){
        anos.add("2021");
        anosFx = FXCollections.observableArrayList(anos);
    }
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
    public static void atualizarMesa(){
        mesas = getRepositoryMesa().buscarTodos();
    }
    public static void atualizarMesaNumero(int numero){
        mesas.clear();
        mesas.add(getRepositoryMesa().buscarMesaNumero(numero));
    }
    public static void atualizarFinanciaMensal(int mes,int ano){
        financiaMensal = getRepositoryFinancia().buscarMes(mes, ano);
    }
    public static void atualizarFinancia(){
        financia = getRepositoryFinancia().buscarTodos();
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

    public static RepositoryMesa getRepositoryMesa() {
        if(repositoryMesa == null){
            repositoryMesa = new RepositoryMesa();
        }
        return repositoryMesa;
    }
    
    

    public static List<Mesa> getMesas() {
        return mesas;
    }

    public static RepositoryFinancia getRepositoryFinancia() {
        if(repositoryFinancia == null){
            repositoryFinancia = new RepositoryFinancia();
        }
        return repositoryFinancia;
    }

    public static List<FinanciaMensal> getFinanciaMensal() {
        return financiaMensal;
    }

    public static List<FinanciaMensal> getFinancia() {
        
        return financia;
    }

    public static List<String> getMeses() {
        if(meses == null){
            meses = new ArrayList<>();
            atualizarMeses();
        }
        return meses;
    }

    public static ObservableList getMesesFx() {
        return mesesFx;
    }

    public static List<String> getAnos() {
        if(anos == null){
            anos = new ArrayList<>();
            atualizarAnos();
        }
        return anos;
    }

    public static ObservableList getAnosFx() {
        return anosFx;
    }
    
    
    
}
