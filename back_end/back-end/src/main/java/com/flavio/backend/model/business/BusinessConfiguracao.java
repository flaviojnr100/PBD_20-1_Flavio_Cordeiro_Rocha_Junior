/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.business;

import com.flavio.backend.model.dao.DaoConfiguracao;
import com.flavio.backend.model.object.Configuracao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Flavio
 */
@Service
public class BusinessConfiguracao {
    
    @Autowired
    private DaoConfiguracao dao;
    
    public Configuracao salvar(Configuracao config){
        return dao.save(config);
    }
    
    public Configuracao buscar(){
        return dao.findById(1).get();
    }
}
