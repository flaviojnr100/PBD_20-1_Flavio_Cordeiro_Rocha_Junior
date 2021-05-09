/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.controller;

import com.flavio.backend.model.business.BusinessCardapio;
import com.flavio.backend.model.business.BusinessFuncionario;
import com.flavio.backend.model.business.BusinessMesa;
import com.flavio.backend.model.business.BusinessPedido;
import com.flavio.backend.model.business.BusinessSenhaReset;
import com.flavio.backend.model.object.Funcionario;
import com.flavio.backend.model.object.ItemCardapio;
import com.flavio.backend.model.object.Mesa;
import com.flavio.backend.model.object.Pedido;
import com.flavio.backend.model.object.SenhaReset;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    
    @Autowired
    BusinessMesa bm;
    
    @Autowired
    BusinessPedido bp;
    
    @Autowired
    BusinessSenhaReset bsr;
    
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
    public List<Funcionario> buscarCpf(@PathVariable("cpf")String cpf){
        return bf.buscarCpf(cpf);
    }
    @GetMapping("/funcionario/buscarNome/{nome}")
    public List<Funcionario> buscarNome(@PathVariable("nome")String nome){
        return bf.buscarNome(nome);
    }
    @GetMapping("/funcionario/buscarLogin/{login}")
    public List<Funcionario> buscarLogin(@PathVariable("login")String login){
        return bf.buscarLogin(login);
    }
    @PostMapping("/funcionario/autenticar")
    public Funcionario autenticar(@Validated String login, @Validated String senha,HttpServletResponse response){
        Funcionario funcionario =  bf.autenticar(login, senha);
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        if(funcionario.isIsLogado()){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }else if(funcionario.getLogin().equals(null)){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }   
        else if(funcionario.isIsPermissao() == false){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return funcionario;
    }
    
    @PostMapping("/funcionario/logout")
    public Funcionario logout(@Validated String login){
        return bf.logout(login);
    }
    
    @PostMapping("/funcionario/login")
    public Funcionario buscarLoginUnico(@Validated String login,HttpServletResponse response){
        Funcionario funcionario = bf.buscarLoginUnico(login);
        if(funcionario == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }else{
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        }
        return funcionario;
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
    
    @PostMapping("/mesa")
    public Mesa salvarMesa(@Validated Mesa mesa){
        return bm.salvar(mesa);
    }
    @GetMapping("/mesa")
    public List<Mesa> buscatTodosMesa(){
        return bm.buscarTodos();
    }
    @GetMapping("/mesa/{id}")
    public ResponseEntity<Mesa> buscarIdMesa(@PathVariable("id") int id){
        return bm.buscarId(id);
    }
    @GetMapping("/mesa/buscarNumero/{numero}")
    public Mesa buscarNumero(@PathVariable("numero")int numero){
        return bm.buscarNumero(numero);
    }
    
    @PutMapping("/mesa/{id}")
    public ResponseEntity<Mesa> edtiarMesa(@PathVariable("id") int id,@Validated Mesa mesa){
        return bm.editar(id, mesa);
    }
    @PutMapping("/mesa/alterarEstado/{id}")
    public ResponseEntity<Mesa> alterarEstadoMesa(@PathVariable("id") int id){
        return bm.alterarEstado(id);
    }
    
    @PostMapping("/pedido")
    public Pedido salvarPedido(Pedido pedido){
        return bp.salvar(pedido);
    }
  /*  @PutMapping("/pedido/adicionarItem?id_pedido={id_pedido}&ïd_item={id_item}")
    public ResponseEntity<Pedido> adicionarItem(@PathVariable("id_pedido")int idPedido,@PathVariable("id_item")int idItem){
        return bp.adicionarItem(idPedido, idItem);
    }*/
    @GetMapping("/pedido")
    public List<Pedido> buscarTodosPedido(){
        return bp.buscarTodos();
    }
    @GetMapping("/pedido/{id}")
    public Optional<Pedido> buscarIdPedido(@PathVariable("id") int id){
        return bp.buscarId(id);
    }
    
    @PutMapping("/pedido/{id}")
    public ResponseEntity<Pedido> editarPedido(@PathVariable("id") int id,@Validated Pedido pedido){
        return bp.editar(id, pedido);
    }
    
    @PutMapping("/pedido/alterarEstado/{id}")
    public ResponseEntity<Optional<Pedido>> alterarEstadoPedido(@PathVariable("id") int id,@Validated String status){
        return bp.alterarEstado(id, status);
    }
    
    @GetMapping("/pedido/buscarData")
    public Pedido buscarData(@Validated int dia,@Validated int mes,@Validated int ano){
        return bp.buscarData(dia,mes,ano);
    }
    
    @GetMapping("/pedido/pendente")
    public List<Pedido> buscarPendente(){
        return bp.buscarPendente();
    }
    
    @GetMapping("/senhaReset")
    public List<SenhaReset> buscarTodosResets(){
        return bsr.buscarTodos();
    }
    @PostMapping("/senhaReset")
    public SenhaReset salvarReset(@Validated int id){
        return bsr.salvar(id);
    }
    
    @DeleteMapping("/senhaReset")
    public void resetar(@Validated int id){
        bsr.resetar(id);
    }
    
    @GetMapping("/senhaReset/buscalogin/{login}")
    public SenhaReset buscarLoginReset(@PathVariable("login")String login,HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        SenhaReset sr = bsr.buscarLogin(login);
        if(sr==null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return sr;
    }
    
}
