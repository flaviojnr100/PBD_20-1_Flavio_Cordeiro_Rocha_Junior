/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.dao;

import com.flavio.backend.model.object.Funcionario;
import com.flavio.backend.model.object.SenhaReset;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Flavio
 */

public interface DaoSenhaReset extends JpaRepository<SenhaReset, Long>{

    @Query(name = "select r.* from SenhaReset r where r.id = :id",nativeQuery = true)
    public SenhaReset findById(@Param("id")int id);
    
    public SenhaReset findByFuncionario(Funcionario funcionario);
    
 
    

}
