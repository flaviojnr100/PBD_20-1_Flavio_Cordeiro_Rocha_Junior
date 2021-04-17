/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.controller;

import com.flavio.backend.model.business.BusinessCardapio;
import com.flavio.backend.model.business.BusinessFuncionario;
import com.flavio.backend.model.object.Funcionario;
import com.flavio.backend.model.object.ItemCardapio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Flavio
 */
@RestController
@RequestMapping(path = "/api")
public class Controller {
    
    @Autowired
    BusinessFuncionario bf;
    
    @Autowired
    BusinessCardapio bc;
    
    @GetMapping("/funcionario")
    public List<Funcionario> buscarTodos(){
        return bf.listarTodos();
    }
    
    @GetMapping("/funcionario/{id}")
    public ResponseEntity<Funcionario> buscarId(@PathVariable(value = "id")int id){
        return bf.buscarId(id);
    }
    
    @PostMapping("/funcionario")
    public Funcionario salvar(@Validated Funcionario funcionario){
        return bf.salvar(funcionario);
    }
    
    @PutMapping("/funcionario/{id}")
    public ResponseEntity<Optional<Funcionario>> editar(@Validated Funcionario funcionario,@PathVariable("id") int id){
        return bf.editar(id,funcionario);
    }
    @PutMapping("/funcionario/alterar_estado/{id}")
    public ResponseEntity<Optional<Funcionario>> alterarPermissao(@PathVariable("id") int id){
        return bf.alterarEstado(id);
    }
    
    @GetMapping("/funcionario/buscarCpf/{cpf}")
    public ResponseEntity<Funcionario> buscarCpf(@PathVariable("cpf")String cpf){
        return bf.buscarCpf(cpf);
    }
    
    @PostMapping("/cardapio")
    public ItemCardapio salvar(@Validated ItemCardapio item){
        return bc.salvar(item);
    }
    
    @GetMapping("/cardapio/{id}")
    public Optional<ItemCardapio> buscarIdCardapio(@PathVariable("id")int id){
        return bc.buscarId(id);
    }
    @GetMapping("/cardapio")
    public List<ItemCardapio> buscarTodosCardapio(){
        return bc.buscarTodos();
    }
    @PutMapping("/cardapio/{id}")
    public ResponseEntity<ItemCardapio> editarCardapio(@Validated ItemCardapio itemCardapio,@PathVariable("id")int id){
        return bc.editar(id, itemCardapio);
    }
    
    @PutMapping("/cardapio/mudarEstado/{id}")
    public ResponseEntity<Optional<ItemCardapio>> mudarEstadoCardapio(@PathVariable("id")int id){
        return bc.alterarEstado(id);
    }
}
