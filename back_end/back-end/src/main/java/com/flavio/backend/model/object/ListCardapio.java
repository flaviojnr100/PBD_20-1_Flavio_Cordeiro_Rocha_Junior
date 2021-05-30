/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.model.object;

import java.util.Arrays;
import java.util.List;
import org.springframework.cglib.core.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Flavio
 */
@Component
public class ListCardapio implements Converter {
    private List<ItemCardapio> cardapio;

    public List<ItemCardapio> getCardapio() {
        return cardapio;
    }

    public void setCardapio(List<ItemCardapio> cardapio) {
        this.cardapio = cardapio;
    }

    @Override
    public Object convert(Object o, Class type, Object o1) {

        List itens = Arrays.asList(o);
        return itens;
    }


}
