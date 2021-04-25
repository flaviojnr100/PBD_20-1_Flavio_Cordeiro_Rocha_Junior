/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.dao;

import com.flavio.backend.model.object.Funcionario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Flavio
 */
public interface DaoFuncionario extends JpaRepository<Funcionario, Integer> {
    
    @Query(value = "select * from Funcionario f where f.cpf = :cpf",nativeQuery = true)
    public Funcionario buscarCpf(@Param("cpf") String cpf);
    @Query(value = "select * from Funcionario f where f.nome = :nome",nativeQuery = true)
    public Funcionario buscarNome(@Param("nome") String nome);
    @Query(value = "select * from Funcionario f where f.login = :login",nativeQuery = true)
    public Funcionario buscarLogin(@Param("login") String nome);
    
}
