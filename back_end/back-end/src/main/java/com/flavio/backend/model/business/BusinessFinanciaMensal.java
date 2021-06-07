/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.business;

import com.flavio.backend.model.dao.DaoFinanciaMensal;
import com.flavio.backend.model.object.FinanciaMensal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Flavio
 */
@Service
public class BusinessFinanciaMensal {
    
    @Autowired
    DaoFinanciaMensal dao;
    
    public List<FinanciaMensal> buscarTodos(){
        return dao.findAll();
    }
    
    public List<FinanciaMensal> buscarMes(int mes,int ano){
        return dao.buscarMes(mes,ano);
    }
    
}
