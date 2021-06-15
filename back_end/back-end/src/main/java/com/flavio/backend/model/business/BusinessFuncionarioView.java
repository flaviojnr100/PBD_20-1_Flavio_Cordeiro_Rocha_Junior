/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.business;

import com.flavio.backend.model.dao.DaoFuncionarioView;
import com.flavio.backend.model.object.FuncionarioView;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Flavio
 */
@Service
public class BusinessFuncionarioView {
    
    @Autowired
    DaoFuncionarioView dao;

    public BusinessFuncionarioView() {
    }
    
    public List<FuncionarioView> buscarTodos(){
        return dao.findAll();
    }
    public List<FuncionarioView> buscarNome(String nome){
        return dao.buscarNome(nome);
    }
    public List<FuncionarioView> buscarLogin(String login){
        return dao.buscarNome(login);
    }
    public List<FuncionarioView> buscarCpf(String cpf){
        return dao.buscarCpf(cpf);
    }
}
