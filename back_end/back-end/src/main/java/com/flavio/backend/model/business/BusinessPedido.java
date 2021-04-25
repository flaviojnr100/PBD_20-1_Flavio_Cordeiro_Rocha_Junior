/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.business;

import com.flavio.backend.model.dao.DaoItemCardapio;
import com.flavio.backend.model.dao.DaoPedido;
import com.flavio.backend.model.object.ItemCardapio;
import com.flavio.backend.model.object.Pedido;
import com.flavio.backend.model.object.Pedido_Cardapio;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.Entity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author Flavio
 */
@Service
public class BusinessPedido {
    @Autowired
    DaoPedido dao;
    
    @Autowired
    DaoItemCardapio daoC;
    
    public Pedido salvar(Pedido pedido){
        Date date = new Date();
        pedido.setDataPedido(date);
        return dao.save(pedido);
    }

    public List<Pedido> buscarTodos(){
        return dao.findAll();
    }
    public Optional<Pedido> buscarId(int id){
        return dao.findById(id);
        
    }
    public ResponseEntity<Pedido> editar(int id,Pedido pedido){
        Pedido pedido1 = dao.getOne(id);
        
        if(pedido1==null){
            return ResponseEntity.noContent().build();
        }
        BeanUtils.copyProperties(pedido, pedido1,"id");
        pedido1 = dao.save(pedido1);
        return ResponseEntity.ok(pedido1);
        
    }
    public ResponseEntity<Optional<Pedido>> alterarEstado(int id,String estado){
        Optional<Pedido> pedido = dao.findById(id);
        if(pedido==null){
            return ResponseEntity.noContent().build();
        }
        Pedido p = pedido.get();
        p.setStatus(estado);
        
        dao.save(p);
        System.out.println("Estado: "+estado);
        return ResponseEntity.ok(pedido);
    }
    
    public Pedido buscarData(int dia,int mes,int ano){
        Pedido pedido = dao.buscarData(dia,mes,ano);
        if(pedido ==null){
            return null;
        }
        return pedido;
    }
    public List<Pedido> buscarPendente(){
        return dao.buscarPendente("pendente");
    }
    
}