/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.object;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Flavio
 */
@Entity
@Table(name = "funcionarios_view")
public class FuncionarioView implements Serializable{
    
    @Id
    private int id;
    private String nome;
    private String sobrenome;
    private String telefone;
    private String cpf;
    private String login;
    private boolean isPermissao;
    private String tipoAcesso;
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimoAcesso;
    private boolean isReset;
    private boolean isLogado;

    public FuncionarioView() {
    }
    
    

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
        int hash = 3;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.nome);
        hash = 97 * hash + Objects.hashCode(this.sobrenome);
        hash = 97 * hash + Objects.hashCode(this.telefone);
        hash = 97 * hash + Objects.hashCode(this.cpf);
        hash = 97 * hash + Objects.hashCode(this.login);
        hash = 97 * hash + (this.isPermissao ? 1 : 0);
        hash = 97 * hash + Objects.hashCode(this.tipoAcesso);
        hash = 97 * hash + Objects.hashCode(this.ultimoAcesso);
        hash = 97 * hash + (this.isReset ? 1 : 0);
        hash = 97 * hash + (this.isLogado ? 1 : 0);
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
        final FuncionarioView other = (FuncionarioView) obj;
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
        if (!Objects.equals(this.tipoAcesso, other.tipoAcesso)) {
            return false;
        }
        if (!Objects.equals(this.ultimoAcesso, other.ultimoAcesso)) {
            return false;
        }
        return true;
    }
    
    
}
