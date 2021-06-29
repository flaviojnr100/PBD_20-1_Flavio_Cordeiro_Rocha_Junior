/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.business;

import com.flavio.backend.model.dao.DaoFinanciaMensal;
import com.flavio.backend.model.object.FinanciaMensal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
        return dao.findAll(Sort.by(Sort.Direction.DESC,"dataPedido"));
    }
    
    public List<FinanciaMensal> buscarMes(int mes,int ano){
        return dao.buscarMes(mes,ano);
    }
    
    public List<FinanciaMensal> buscarEntreDatas(String inicio,String fim){
        String [] inicio1 = inicio.split("-");
        String [] fim1 = fim.split("-");
        return dao.buscaEntreDatas(inicio1[2],inicio1[1],inicio1[0],fim1[2],fim1[1],fim1[0]);
        
    }
    
}
