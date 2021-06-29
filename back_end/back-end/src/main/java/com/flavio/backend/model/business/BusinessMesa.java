/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.business;

import com.flavio.backend.model.dao.DaoMesa;
import com.flavio.backend.model.object.Mesa;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author Flavio
 */
@Service
public class BusinessMesa {
    
    @Autowired
    DaoMesa dao;
    
    public Mesa salvar(Mesa mesa){
        return dao.save(mesa);
    }
    public ResponseEntity<Mesa> buscarId(int id){
        Mesa mesa = dao.findById(id).get();
        if(mesa == null){
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(mesa);
    }
    public List<Mesa> buscarTodos(){
        return dao.findAll();
    }
    public Mesa buscarNumero(int numero){
        return dao.buscarNumero(numero);
    }
    
    public ResponseEntity<Mesa> editar(int id,Mesa mesa){
        Mesa mesa1 = dao.getOne(id);
        if(mesa1 == null){
            return ResponseEntity.notFound().build();
        }
        
        BeanUtils.copyProperties(mesa, mesa1,"id");
        mesa1 = dao.save(mesa1);
        return ResponseEntity.ok(mesa1);
    }
    public ResponseEntity<Mesa> alterarEstado(int id){
        Mesa mesa = dao.getOne(id);
        if(mesa == null){
            return ResponseEntity.notFound().build();
        }
        mesa.setIsLivre(!mesa.isIsLivre());
        mesa = dao.save(mesa);
        return ResponseEntity.ok(mesa);
    }
    
    public boolean verificarMesas(int tamanho){
       return dao.verificarMesas(tamanho);
    }

}
