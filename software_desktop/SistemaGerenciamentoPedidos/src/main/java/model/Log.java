/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.Gson;
import java.util.Date;

/**
 *
 * @author Flavio
 */
public class Log {
    private int id;
    private String usuario;
    private String operacao;
    private String enderecoIp;
    private String tabela;
    private Date data;
    private String dadosOld;
    private String dadosNew;
    
    

    public Log() {
    }
    
    

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getEnderecoIp() {
        return enderecoIp;
    }

    public void setEnderecoIp(String enderecoIp) {
        this.enderecoIp = enderecoIp;
    }

    public String getTabela() {
        return tabela;
    }

    public void setTabela(String tabela) {
        this.tabela = tabela;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDadosOld() {
        return dadosOld;
    }

    public void setDadosOld(String dadosOld) {
        this.dadosOld = dadosOld;
    }

    public String getDadosNew() {
        return dadosNew;
    }

    public void setDadosNew(String dadosNew) {
        this.dadosNew = dadosNew;
    }


    
    
}
