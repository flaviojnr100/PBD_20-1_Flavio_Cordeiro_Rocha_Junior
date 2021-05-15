/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.business;

import com.flavio.backend.model.dao.DaoFuncionario;
import com.flavio.backend.model.dao.DaoSenhaReset;
import com.flavio.backend.model.object.Criptografia;
import com.flavio.backend.model.object.Funcionario;
import com.flavio.backend.model.object.SenhaReset;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Flavio
 */
@Service
public class BusinessSenhaReset {
    @Autowired
    DaoSenhaReset dao;
    
    @Autowired
    DaoFuncionario daoF;
    
    public SenhaReset salvar(int idFuncionario){
        SenhaReset senha = new SenhaReset();
        Optional<Funcionario> funcionario = daoF.findById(idFuncionario);
        
        if(funcionario == null){
            return null;
        }else{
            SenhaReset reset = dao.findByFuncionario(funcionario.get());
            
            if(reset == null){
                Funcionario f1 = funcionario.get();
                f1.setIsPermissao(false);
                daoF.save(f1);
                senha.setFuncionario(f1);
                senha.setData(new Date());
        
                return dao.save(senha);
            }
        }
        
        return null;
      
    }
    public List<SenhaReset> buscarTodos(){
        return dao.findAll();
    }
    //retorna null
    public void resetar(int id){
       SenhaReset reset = dao.findById(id);
        Criptografia.getMd();
       if(reset != null){
           Funcionario funcionario = reset.getFuncionario();
           funcionario.setSenha(Criptografia.criptografar(funcionario.getCpf()));
           funcionario.setIsPermissao(true);
           funcionario.setIsReset(true);
           daoF.save(funcionario);
           dao.delete(reset);
       }
    }
    
    public SenhaReset buscarLogin(String login){
        Funcionario f = daoF.findByLogin(login);
        if(f==null){
            return null;
        }else{
            f.setSenha(null);
            return dao.findByFuncionario(f);
        }
    }
    
}
