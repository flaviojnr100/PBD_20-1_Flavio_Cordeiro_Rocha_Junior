/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.business;

import com.flavio.backend.model.dao.DaoFuncionario;
import com.flavio.backend.model.object.Funcionario;
import java.util.Date;
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
    public List<Funcionario> listarTodosSU(){
        List<Funcionario> all = dao.findAllSU();
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
    
    public Funcionario buscarLoginUnico(String login){
        Funcionario funcionario = dao.findByLogin(login);
        if(funcionario!=null){
            funcionario.setNome(null);
            funcionario.setCpf(null);
            funcionario.setSobrenome(null);
            funcionario.setTelefone(null);
            funcionario.setIsLogado(false);
            funcionario.setIsPermissao(false);
            funcionario.setIsReset(false);
            funcionario.setSenha(null);
            funcionario.setLogin(null);
            funcionario.setTipoAcesso(null);
            funcionario.setUltimoAcesso(null);
            
        }
        return funcionario;
    }
    public Funcionario buscarCpfUnico(String cpf){
        Funcionario funcionario = dao.findByCpf(cpf);
        if(funcionario!=null){
            funcionario.setNome(null);
            funcionario.setCpf(null);
            funcionario.setSobrenome(null);
            funcionario.setTelefone(null);
            funcionario.setIsLogado(false);
            funcionario.setIsPermissao(false);
            funcionario.setIsReset(false);
            funcionario.setSenha(null);
            funcionario.setLogin(null);
            funcionario.setTipoAcesso(null);
            funcionario.setUltimoAcesso(null);
            
        }
        return funcionario;
    }
    public ResponseEntity<Funcionario> editar(int id,Funcionario funcionario){
        Optional<Funcionario> funcionario1 = dao.findById(id);
        if(funcionario1 == null){
            return ResponseEntity.notFound().build();
        }
        
        Funcionario f = funcionario1.get();
        BeanUtils.copyProperties(funcionario, f,"id");
        dao.save(f);
        return ResponseEntity.ok(f);
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
    public List<Funcionario> buscarCpfSU(String cpf){
        List<Funcionario> f = (List<Funcionario>) dao.buscarCpfSU(cpf);
        
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
    public List<Funcionario> buscarNomeSU(String nome){
        List<Funcionario> f = (List<Funcionario>) dao.buscarNomeSU(nome);
        
        if(f == null){
            return null;
        }
        for(Funcionario f1:f){
            f1.setSenha(null);
        }
        return f;
    }
    public List<Funcionario> buscarLogin(String login){
       List<Funcionario> f =  dao.buscarLogin(login);
        
        if(f == null){
            return null;
        }
        for(Funcionario f1:f){
            f1.setSenha(null);
        }
        return f;
    }
    public List<Funcionario> buscarLoginSU(String login){
       List<Funcionario> f =  dao.buscarLoginSU(login);
        
        if(f == null){
            return null;
        }
        for(Funcionario f1:f){
            f1.setSenha(null);
        }
        return f;
    }
    
    public Funcionario autenticar(String login,String senha){
        Funcionario funcionario = dao.autenticar(login);
        
        if(funcionario ==null){
            return new Funcionario();
        }
        if(funcionario.getSenha().equals(senha) && funcionario.isIsPermissao() && !funcionario.isIsLogado()){
            funcionario.setUltimoAcesso(new Date());
            return funcionario;
        }else{
             funcionario.setCpf(null);
             funcionario.setSenha(null);
             funcionario.setId(0);
             funcionario.setTelefone(null);
             funcionario.setTipoAcesso(null);
             funcionario.setUltimoAcesso(null);
             funcionario.setNome(null);
             funcionario.setSobrenome(null);
             
               
            return funcionario;
            
        }
        
        
    }
    public Funcionario logout(String login){
        Funcionario funcionario = dao.autenticar(login);
        funcionario.setIsLogado(false);
        funcionario.setUltimoAcesso(new Date());
        return dao.save(funcionario);
    }
   
}
