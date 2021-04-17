/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.dao;

import com.flavio.backend.model.object.ItemCardapio;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Flavio
 */
public interface DaoItemCardapio extends JpaRepository<ItemCardapio, Integer> {
    
}
