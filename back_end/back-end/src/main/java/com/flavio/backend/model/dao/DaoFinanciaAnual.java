/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.dao;

import com.flavio.backend.model.object.FinanciaAnual;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Flavio
 */
public interface DaoFinanciaAnual extends JpaRepository<FinanciaAnual, Integer> {
    
    @Query(value = "select f from FinanciaAnual f where f.ano = :ano")
    public List<FinanciaAnual> buscarPorAno(@Param("ano")int ano);
    
}
