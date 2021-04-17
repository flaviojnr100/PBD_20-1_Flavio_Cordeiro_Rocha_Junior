/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.business;

import com.flavio.backend.model.dao.DaoItemCardapio;
import com.flavio.backend.model.object.ItemCardapio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public Optional<ItemCardapio> buscarId(int id){
        return dao.findById(id);
    }
    public List<ItemCardapio> buscarTodos(){
        return dao.findAll();
    }
    public ResponseEntity<ItemCardapio> editar(int id,ItemCardapio itemCardapio){
        ItemCardapio item = dao.getOne(id);
        if(item == null){
            ResponseEntity.notFound().build();
        }
        BeanUtils.copyProperties(itemCardapio, item,"id");
        item = dao.save(item);
        return ResponseEntity.ok(item);
    }
    
    public ResponseEntity<Optional<ItemCardapio>> alterarEstado(int id){
        Optional<ItemCardapio> item = dao.findById(id);
        if(item == null){
            return ResponseEntity.notFound().build();
        }
        ItemCardapio i = item.get();
        i.setIsAtivo(!i.isIsAtivo());
        dao.save(i);
        
        return ResponseEntity.ok(item);
    }
}
