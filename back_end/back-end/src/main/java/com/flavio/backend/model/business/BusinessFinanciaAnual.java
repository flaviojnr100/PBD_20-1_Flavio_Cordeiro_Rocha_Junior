/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.business;

import com.flavio.backend.model.dao.DaoFinanciaAnual;
import com.flavio.backend.model.object.FinanciaAnual;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Flavio
 */
@Service
public class BusinessFinanciaAnual {
    
    @Autowired
    DaoFinanciaAnual dao;
    
    public List<FinanciaAnual> buscarTodos(){
        return dao.findAll();
    }
    public List<FinanciaAnual> buscarPorAno(int ano){
        return dao.buscarPorAno(ano);
    }
    
}
