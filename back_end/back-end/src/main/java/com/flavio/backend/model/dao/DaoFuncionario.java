/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.dao;

import com.flavio.backend.model.object.Funcionario;
import java.util.List;
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
    
    @Query(value = "select * from Funcionario f where f.tipo_acesso = 'funcionario' and f.cpf LIKE CONCAT('%',:cpf,'%')",nativeQuery = true)
    public List<Funcionario> buscarCpf(@Param("cpf") String cpf);
    @Query(value = "select * from Funcionario f where f.tipo_acesso = 'funcionario' and f.nome LIKE CONCAT('%',:nome,'%')",nativeQuery = true)
    public List<Funcionario> buscarNome(@Param("nome") String nome);
    @Query(value = "select * from Funcionario f where f.tipo_acesso = 'funcionario' and f.login LIKE CONCAT('%',:login,'%')",nativeQuery = true)
    public List<Funcionario> buscarLogin(@Param("login") String login);
    
    @Query(value = "select * from Funcionario f where f.tipo_acesso != 'superusuario' and f.cpf LIKE CONCAT('%',:cpf,'%')",nativeQuery = true)
    public List<Funcionario> buscarCpfSU(@Param("cpf") String cpf);
    @Query(value = "select * from Funcionario f where f.tipo_acesso != 'superusuario' and f.nome LIKE CONCAT('%',:nome,'%')",nativeQuery = true)
    public List<Funcionario> buscarNomeSU(@Param("nome") String nome);
    @Query(value = "select * from Funcionario f where f.tipo_acesso != 'superusuario' and f.login LIKE CONCAT('%',:login,'%')",nativeQuery = true)
    public List<Funcionario> buscarLoginSU(@Param("login") String login);
    
    @Query(value = "select f.* from Funcionario f where f.login = :login",nativeQuery = true)
    public Funcionario autenticar(@Param("login")String login);
    
    @Query(value = "select f from Funcionario f where f.tipoAcesso = 'funcionario'")
    public List<Funcionario> findAll();
    @Query(value = "select f from Funcionario f where f.tipoAcesso = 'funcionario' or  f.tipoAcesso = 'gerente'")
    public List<Funcionario> findAllSU();
    
    @Query(value = "select f.* from Funcionario f where f.login = :login",nativeQuery = true)
    public Funcionario findByLogin(@Param("login")String login);
    
}
