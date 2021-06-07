/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.dao;

import com.flavio.backend.model.object.FinanciaMensal;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Flavio
 */
public interface DaoFinanciaMensal extends JpaRepository<FinanciaMensal, Date> {
    
    @Query(value = "select f from FinanciaMensal f where extract(month from f.dataPedido) = :mes and extract(year from f.dataPedido) = :ano")
    public List<FinanciaMensal> buscarMes(@Param("mes") int mes, @Param("ano") int ano);
    
}
