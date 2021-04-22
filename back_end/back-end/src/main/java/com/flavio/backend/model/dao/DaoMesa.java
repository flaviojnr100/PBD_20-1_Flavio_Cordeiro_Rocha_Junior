/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.dao;

import com.flavio.backend.model.object.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Flavio
 */
public interface DaoMesa extends JpaRepository<Mesa, Integer> {
    
    @Query(value = "select * from Mesa m where m.numero = :numero",nativeQuery = true)
    public Mesa buscarNumero(@Param("numero") int numero);
    
}
