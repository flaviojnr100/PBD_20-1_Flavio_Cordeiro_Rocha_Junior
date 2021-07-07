/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.dao;

import com.flavio.backend.model.object.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 *
 * @author Flavio
 */
public interface DaoLog extends JpaRepository<Log, Integer>{
    
    @Query(value = "select * from auditoria as a where extract( day from (CAST(a.criacao_alteracao as DATE))) = :dia and extract( month from (CAST(a.criacao_alteracao as DATE))) = :mes and extract( year from (CAST(a.criacao_alteracao as DATE))) = :ano order by id desc limit 50 ",nativeQuery = true)
    public List<Log> buscarData(@Param("dia")int dia,@Param("mes")int mes,@Param("ano")int ano);
    
    @Query(value = "select * from auditoria order by id desc limit 50",nativeQuery = true)
    @Override
    public List<Log> findAll();
}
