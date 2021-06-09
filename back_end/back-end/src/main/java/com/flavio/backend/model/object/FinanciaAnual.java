/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.object;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Flavio
 */
@Entity
@Table(name = "financia_anual")
public class FinanciaAnual implements Serializable{
    
    @Id
    @Column(name = "id")
    private int id;
    
    @Column(name = "ano")
    private int ano;
    @Column(name = "mes")
    private int mes;
    @Column(name = "total")
    private double total; 

    public FinanciaAnual() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    
   
    
    
    
}
