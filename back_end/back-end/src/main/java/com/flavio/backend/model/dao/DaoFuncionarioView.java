/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.dao;

import com.flavio.backend.model.object.FuncionarioView;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Flavio
 */
public interface DaoFuncionarioView extends JpaRepository<FuncionarioView, Integer> {
    
    @Query(value = "select * from funcionarios_view as f where f.cpf =:cpf",nativeQuery = true)
    public List<FuncionarioView> buscarCpf(@Param("cpf")String cpf);
    
    @Query(value = "select * from funcionarios_view as f where f.nome =:nome",nativeQuery = true)
    public List<FuncionarioView> buscarNome(@Param("nome")String nome);
    
    @Query(value = "select * from funcionarios_view as f where f.login =:login",nativeQuery = true)
    public List<FuncionarioView> buscarLogin(@Param("login")String login);
}
