/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.business;

import com.flavio.backend.model.dao.DaoFuncionario;
import com.flavio.backend.model.object.Funcionario;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Flavio
 */
@Service
public class BusinessFuncionario {
    
    @Autowired
    DaoFuncionario dao;
    
    
    public Funcionario salvar(Funcionario funcionario){
       return dao.save(funcionario);
    }
    
    public List<Funcionario> listarTodos(){
        List<Funcionario> all = dao.findAll();
        for(Funcionario f:all){
            f.setSenha(null);
        }
        
        return all;
    }
    public ResponseEntity<Funcionario> buscarId(int id){
        Funcionario funcionario = dao.findById(id).get();
        if(funcionario == null){
            return ResponseEntity.notFound().build();
        }
        funcionario.setSenha(null);
        return ResponseEntity.ok(funcionario);
    }
    public ResponseEntity<Optional<Funcionario>> editar(int id,Funcionario funcionario){
        Optional<Funcionario> funcionario1 = dao.findById(id);
        if(funcionario1 == null){
            return ResponseEntity.notFound().build();
        }
        Funcionario f = funcionario1.get();
        BeanUtils.copyProperties(funcionario, f,"id");
        dao.save(f);
        return ResponseEntity.ok(funcionario1);
    }
    
    public ResponseEntity<Optional<Funcionario>> alterarEstado(int id){
        Optional<Funcionario> funcionario = dao.findById(id);
        if(funcionario == null){
            return ResponseEntity.notFound().build();
        }
        Funcionario f = funcionario.get();
        f.setIsPermissao(!f.isIsPermissao());
        dao.save(f);
        
        return ResponseEntity.ok(funcionario);
    }
    public List<Funcionario> buscarCpf(String cpf){
        List<Funcionario> f = (List<Funcionario>) dao.buscarCpf(cpf);
        
        if(f == null){
            return null;
        }
        for(Funcionario f1:f){
            f1.setSenha(null);
        }
        return f;
    }
    public List<Funcionario> buscarNome(String nome){
        List<Funcionario> f = (List<Funcionario>) dao.buscarNome(nome);
        
        if(f == null){
            return null;
        }
        for(Funcionario f1:f){
            f1.setSenha(null);
        }
        return f;
    }
    public List<Funcionario> buscarLogin(String login){
       List<Funcionario> f = (List<Funcionario>) dao.buscarLogin(login);
        
        if(f == null){
            return null;
        }
        for(Funcionario f1:f){
            f1.setSenha(null);
        }
        return f;
    }
   
}
