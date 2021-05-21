/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.dao;

import com.flavio.backend.model.object.ItemCardapio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Flavio
 */
public interface DaoItemCardapio extends JpaRepository<ItemCardapio, Integer> {
    @Query(value = "select i from ItemCardapio i where i.nome LIKE CONCAT('%',:nome,'%')")
    public List<ItemCardapio> buscarNome(@Param("nome") String nome);
    
    @Query(value = "select i from ItemCardapio i where i.nome = :nome")
    public ItemCardapio buscarNomeUnico(@Param("nome") String nome);
}
