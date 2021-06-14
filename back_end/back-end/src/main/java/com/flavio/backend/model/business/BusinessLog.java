/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.business;

import com.flavio.backend.model.dao.DaoLog;
import com.flavio.backend.model.object.Log;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Flavio
 */
@Service
public class BusinessLog {
    
    @Autowired
    private DaoLog dao;
    
    public List<Log> buscarTodos(){
        return dao.findAll();
    }
    public List<Log> buscarData(int dia,int mes,int ano){
        return dao.buscarData(dia, mes, ano);
    }
}
