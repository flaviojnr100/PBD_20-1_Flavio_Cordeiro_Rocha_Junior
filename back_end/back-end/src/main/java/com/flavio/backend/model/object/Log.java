/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.object;

import com.google.gson.Gson;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.hibernate.annotations.Immutable;

/**
 *
 * @author Flavio
 */
@Entity
@Table(name = "auditoria")
public class Log implements Serializable{
    @Id
    private int id;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "operacao")
    private String operacao;
    @Column(name = "endereco_ip")
    private String enderecoIp;
    @Column(name = "nome_tabela")
    private String tabela;
    @Column(name = "criacao_alteracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    
    @Column(name = "dados_new")
    private String dadosNew;
    @Column(name = "dados_old")
    private String dadosOld;
    


    public Log() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDadosNew() {
        return dadosNew;
    }

    public void setDadosNew(String dadosNew) {
        this.dadosNew = dadosNew;
    }

    public String getDadosOld() {
        return dadosOld;
    }

    public void setDadosOld(String dadosOld) {
        this.dadosOld = dadosOld;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.id;
        hash = 59 * hash + Objects.hashCode(this.usuario);
        hash = 59 * hash + Objects.hashCode(this.operacao);
        hash = 59 * hash + Objects.hashCode(this.enderecoIp);
        hash = 59 * hash + Objects.hashCode(this.tabela);
        hash = 59 * hash + Objects.hashCode(this.data);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Log other = (Log) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.operacao, other.operacao)) {
            return false;
        }
        if (!Objects.equals(this.enderecoIp, other.enderecoIp)) {
            return false;
        }
        if (!Objects.equals(this.tabela, other.tabela)) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }

   
}
