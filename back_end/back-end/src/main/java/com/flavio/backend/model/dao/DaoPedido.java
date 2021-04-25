/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.dao;

import com.flavio.backend.model.object.Pedido;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Flavio
 */
public interface DaoPedido extends JpaRepository<Pedido, Integer> {
    @Query(value = "select * from Pedido where extract(day from CAST(data_pedido as DATE)) = :dia and extract(month from CAST(data_pedido as DATE)) = :mes and extract(year from CAST(data_pedido as DATE)) = :ano",nativeQuery = true)
    public Pedido buscarData(@Param("dia") int dia,@Param("mes") int mes,@Param("ano") int ano);
    
    @Query(value = "select * from Pedido where status=:pendente",nativeQuery = true)
    public List<Pedido> buscarPendente(@Param("pendente") String pendente);
}