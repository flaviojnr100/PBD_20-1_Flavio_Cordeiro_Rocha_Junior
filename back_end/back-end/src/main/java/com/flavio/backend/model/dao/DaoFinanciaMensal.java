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
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Flavio
 */
public interface DaoFinanciaMensal extends JpaRepository<FinanciaMensal, Date> {
    
    @Query(value = "select f from FinanciaMensal f where extract(month from f.dataPedido) = :mes and extract(year from f.dataPedido) = :ano order by f.dataPedido")
    public List<FinanciaMensal> buscarMes(@Param("mes") int mes, @Param("ano") int ano);
    
    
    @Query(nativeQuery = true,value = "select * from  buscar_entre_datas(:dia1,:mes1,:ano1,:dia2,:mes2,:ano2)")
    public List<FinanciaMensal>buscaEntreDatas(@Param("dia1") String dia1, @Param("mes1") String mes1, @Param("ano1")String ano1,@Param("dia2") String dia2, @Param("mes2") String mes2, @Param("ano2")String ano2);
}
