/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Flavio
 */
public class Mesa {
    private int id;
    private int numero;
    private boolean isLivre;

    public Mesa() {
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean isIsLivre() {
        return isLivre;
    }

    public void setIsLivre(boolean isLivre) {
        this.isLivre = isLivre;
    }

    @Override
    public String toString() {
        return ""+numero;
    }
    
    
}
