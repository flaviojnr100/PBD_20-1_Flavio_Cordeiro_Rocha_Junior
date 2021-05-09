/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Cascade;

/**
 *
 * @author Flavio
 */
@Entity
@Table(name = "funcionario")
public class Funcionario implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String sobrenome;
    private String telefone;
    @Column(unique = true)
    private String cpf;
    @Column(unique = true)
    private String login;
    private String senha;
    private boolean isPermissao;
    private String tipoAcesso;
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimoAcesso;
    private boolean isReset;
    private boolean isLogado;
  
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isIsPermissao() {
        return isPermissao;
    }

    public void setIsPermissao(boolean isPermissao) {
        this.isPermissao = isPermissao;
    }

    public String getTipoAcesso() {
        return tipoAcesso;
    }

    public void setTipoAcesso(String tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
    }

    public Date getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(Date ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    public boolean isIsReset() {
        return isReset;
    }

    public void setIsReset(boolean isReset) {
        this.isReset = isReset;
    }

    public boolean isIsLogado() {
        return isLogado;
    }

    public void setIsLogado(boolean isLogado) {
        this.isLogado = isLogado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.id;
        hash = 31 * hash + Objects.hashCode(this.nome);
        hash = 31 * hash + Objects.hashCode(this.sobrenome);
        hash = 31 * hash + Objects.hashCode(this.telefone);
        hash = 31 * hash + Objects.hashCode(this.cpf);
        hash = 31 * hash + Objects.hashCode(this.login);
        hash = 31 * hash + Objects.hashCode(this.senha);
        hash = 31 * hash + (this.isPermissao ? 1 : 0);
        hash = 31 * hash + Objects.hashCode(this.tipoAcesso);
        hash = 31 * hash + Objects.hashCode(this.ultimoAcesso);
        hash = 31 * hash + (this.isReset ? 1 : 0);
        hash = 31 * hash + (this.isLogado ? 1 : 0);
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
        final Funcionario other = (Funcionario) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.isPermissao != other.isPermissao) {
            return false;
        }
        if (this.isReset != other.isReset) {
            return false;
        }
        if (this.isLogado != other.isLogado) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.sobrenome, other.sobrenome)) {
            return false;
        }
        if (!Objects.equals(this.telefone, other.telefone)) {
            return false;
        }
        if (!Objects.equals(this.cpf, other.cpf)) {
            return false;
        }
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (!Objects.equals(this.senha, other.senha)) {
            return false;
        }
        if (!Objects.equals(this.tipoAcesso, other.tipoAcesso)) {
            return false;
        }
        if (!Objects.equals(this.ultimoAcesso, other.ultimoAcesso)) {
            return false;
        }
        return true;
    }

   

 
   

    

   
   
    
    
}
