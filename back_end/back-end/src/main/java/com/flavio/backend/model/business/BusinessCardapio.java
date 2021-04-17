/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.business;

import com.flavio.backend.model.dao.DaoItemCardapio;
import com.flavio.backend.model.object.ItemCardapio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Flavio
 */
@Service
public class BusinessCardapio {
    
    @Autowired
    DaoItemCardapio dao;
    
    public ItemCardapio salvar(ItemCardapio item){
        return dao.save(item);
    }
}
