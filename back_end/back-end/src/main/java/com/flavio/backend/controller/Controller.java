/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flavio.backend.controller;

import com.flavio.backend.model.business.BusinessCardapio;
import com.flavio.backend.model.business.BusinessConfiguracao;
import com.flavio.backend.model.business.BusinessFinanciaAnual;
import com.flavio.backend.model.business.BusinessFinanciaMensal;
import com.flavio.backend.model.business.BusinessFuncionario;
import com.flavio.backend.model.business.BusinessFuncionarioView;
import com.flavio.backend.model.business.BusinessLog;
import com.flavio.backend.model.business.BusinessMesa;
import com.flavio.backend.model.business.BusinessPedido;
import com.flavio.backend.model.business.BusinessSenhaReset;
import com.flavio.backend.model.object.Configuracao;
import com.flavio.backend.model.object.FinanciaAnual;
import com.flavio.backend.model.object.FinanciaMensal;
import com.flavio.backend.model.object.Funcionario;
import com.flavio.backend.model.object.FuncionarioView;
import com.flavio.backend.model.object.ItemCardapio;
import com.flavio.backend.model.object.Log;
import com.flavio.backend.model.object.Mesa;
import com.flavio.backend.model.object.Pedido;
import com.flavio.backend.model.object.SenhaReset;
import com.flavio.backend.model.object.Util;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    
    @Autowired
    BusinessMesa bm;
    
    @Autowired
    BusinessPedido bp;
    
    @Autowired
    BusinessSenhaReset bsr;
    
    @Autowired
    BusinessFinanciaMensal bfm;
    
    @Autowired
    BusinessFinanciaAnual bfa;
    
    @Autowired
    BusinessLog blo;
    
    @Autowired
    BusinessFuncionarioView bfv;
    
    @Autowired
    BusinessConfiguracao bconfig;
    
    @GetMapping("/funcionario")
    public List<FuncionarioView> buscarTodos(){
        return bfv.buscarTodos();
    }
    @GetMapping("/funcionarioSU")
    public List<Funcionario> buscarTodosSU(){
        return bf.listarTodosSU();
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
    public ResponseEntity<Funcionario> editar(@Validated Funcionario funcionario,@PathVariable("id") int id){
        return bf.editar(id,funcionario);
    }
    @PutMapping("/funcionario/alterar_estado/{id}")
    public ResponseEntity<Optional<Funcionario>> alterarPermissao(@PathVariable("id") int id){
        return bf.alterarEstado(id);
    }
    
    @GetMapping("/funcionario/buscarCpf/{cpf}")
    public List<FuncionarioView> buscarCpf(@PathVariable("cpf")String cpf){
        return bfv.buscarCpf(cpf);
    }
    
    @GetMapping("/funcionarioSU/buscarCpf/{cpf}")
    public List<Funcionario> buscarCpfSU(@PathVariable("cpf")String cpf){
        return bf.buscarCpfSU(cpf);
    }
    
    @GetMapping("/funcionario/buscarNome/{nome}")
    public List<FuncionarioView> buscarNome(@PathVariable("nome")String nome){
        return bfv.buscarNome(nome);
    }
    
    @GetMapping("/funcionarioSU/buscarNome/{nome}")
    public List<Funcionario> buscarNomeSU(@PathVariable("nome")String nome){
        return bf.buscarNomeSU(nome);
    }
    
    @GetMapping("/funcionario/buscarLoginUnico/{login}")
    public Funcionario buscarLoginUnico(@PathVariable String login){
        return bf.buscarLoginUnico(login);
    }
    @GetMapping("/funcionario/buscarCpfUnico/{cpf}")
    public Funcionario buscarCpfUnico(@PathVariable String cpf){
        return bf.buscarCpfUnico(cpf);
    }
    
    @GetMapping("/funcionario/buscarLogin/{login}")
    public List<FuncionarioView> buscarLogin(@PathVariable("login")String login){
        return bfv.buscarLogin(login);
    }
    
    @GetMapping("/funcionarioSU/buscarLogin/{login}")
    public List<Funcionario> buscarLoginSU(@PathVariable("login")String login){
        return bf.buscarLoginSU(login);
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
    @GetMapping("/cardapio/buscarNome/{nome}")
    public List<ItemCardapio> buscarNomeItem(@PathVariable("nome")String nome){
        return bc.buscarNome(nome);
    }
    @GetMapping("/cardapio/buscarNomeUnico/{nome}")
    public ItemCardapio buscarNomeUnico(@PathVariable("nome")String nome){
        return bc.buscarNomeUnico(nome);
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
    @PutMapping("/mesa/{id}")
    public ResponseEntity<Mesa> editarMesa(@PathVariable("id")int id, @Validated Mesa mesa){
        return bm.editar(id, mesa);
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
    

    @PutMapping("/mesa/alterarEstado/{id}")
    public ResponseEntity<Mesa> alterarEstadoMesa(@PathVariable("id") int id){
        return bm.alterarEstado(id);
    }
    
    @GetMapping("/mesa/verificarMesas/{tamanho}")
    public boolean verificarMesas(@PathVariable("tamanho")int tamanho){
        return bm.verificarMesas(tamanho);
    }
    
    
    @PostMapping("/pedido")
    public Pedido salvarPedido(Pedido pedido,@RequestParam("cardapio") String cardapio){
        return bp.salvar(pedido,cardapio);
    }
    @DeleteMapping("/pedido")
    public void deletarPedido(@RequestParam("id") int id){
        bp.excluirPedido(id);
    }
 
    @GetMapping("/pedido")
    public List<Pedido> buscarTodosPedido(){
        return bp.buscarTodos();
    }
    
    @GetMapping("/pedidosFuncionario/{id}")
    public List<Pedido> buscarPedidosFuncionario(@PathVariable("id") int id){
        return bp.buscarPedidoFuncionario(id);
    }
    
    @GetMapping("/pedido/{id}")
    public Optional<Pedido> buscarIdPedido(@PathVariable("id") int id){
        return bp.buscarId(id);
    }
    
    @PutMapping("/pedido/{id}")
    public ResponseEntity<Pedido> editarPedido(@PathVariable("id") int id,@Validated Pedido pedido,String cardapio){
        return bp.editar(id, pedido,cardapio);
    }
    
    @PutMapping("/pedido/alterarEstado/{id}")
    public ResponseEntity<Optional<Pedido>> alterarEstadoPedido(@PathVariable("id") int id,@Validated String status){
        return bp.alterarEstado(id, status);
    }
    
    @PostMapping("/pedido/buscarData")
    public List<Pedido> buscarData(@RequestParam("dia") int dia,@RequestParam("mes") int mes,@RequestParam("ano") int ano){
        return bp.buscarData(dia,mes,ano);
    }
    
    @GetMapping("/pedido/pendente")
    public List<Pedido> buscarPendente(){
        return bp.buscarPendente();
    }
    @GetMapping("/pedido/buscarMesa/{numero}")
    public List<Pedido> buscarMesaPedido(@PathVariable("numero")int numero){
        return bp.buscarMesa(numero);
    }
    
    @GetMapping("/pedido/buscarMesaTodos/{numero}")
    public List<Pedido> buscarMesaPedidoTodos(@PathVariable("numero")int numero){
        return bp.buscarMesaTodos(numero);
    }
    @PostMapping("/pedido/pagamento")
    public List<Pedido> efetuarPagamento(@RequestParam("id")int mesa){
        return bp.efetuarPagamento(mesa);
        
    }
    @PostMapping("/pedido/cancelamento")
    public List<Pedido> efetuarCancelamento(@RequestParam("id")int mesa){
        return bp.efetuarCancelamento(mesa);
        
    }
    
    @GetMapping("/pedido/verificarPedidos/{tamanho}")
    public boolean verificarPedidos(@PathVariable("tamanho")int tamanho){
        return bp.verificarPedidos(tamanho);
    }
    
    
    @GetMapping("/senhaReset")
    public List<SenhaReset> buscarTodosResets(){
        return bsr.buscarTodos();
    }
    @PostMapping("/senhaReset")
    public SenhaReset salvarReset(@Validated int id,HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
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
    
    @GetMapping("/financiaMensal")
    public List<FinanciaMensal> buscarTodosFinanciaMensal(){
        return bfm.buscarTodos();
    }
    @GetMapping("/financiaMensal/{mes}/{ano}")
    public List<FinanciaMensal> buscarFinanciaMes(@PathVariable("mes")int mes,@PathVariable("ano")int ano){
        return bfm.buscarMes(mes,ano);
    }
    
    @GetMapping("financiaMensal/entreDatas/{inicio}/{fim}")
    public List<FinanciaMensal> buscarEntreDatas(@PathVariable("inicio")String inicio,@PathVariable("fim")String fim){
        return bfm.buscarEntreDatas(inicio, fim);
        
    }
    
    @GetMapping("/financiaAnual")
    public List<FinanciaAnual> buscarFinanciaAnual(){
        return bfa.buscarTodos();
    }
    @GetMapping("/financiaAnual/{ano}")
    public List<FinanciaAnual> buscarFinanciaAnualPorAno(@PathVariable("ano") int ano){
        return bfa.buscarPorAno(ano);
    }
    
    @GetMapping("/log")
    public List<Log> buscarTodosLog(){
        return blo.buscarTodos();
    }
    @GetMapping("/log/{dia}/{mes}/{ano}")
    public List<Log> buscarLogData(@PathVariable("dia")int dia,@PathVariable("mes")int mes,@PathVariable("ano")int ano){
        return blo.buscarData(dia, mes, ano);
    }
    
    @PostMapping("/backup/salvar")
    public Configuracao salvarBackup(@RequestParam("id") int id,@RequestParam("horario") String horario,@RequestParam("alteracao") boolean alteracao){
        Configuracao c = new Configuracao();
        c.setId(id);
        c.setHora(horario);
        c.setAlteracao(alteracao);
        return bconfig.salvar(c);
    }
    
    @GetMapping("/backup/backup")
    public void backup(){
        final List<String> comandos = new ArrayList<String>();
        comandos.add(Util.PG_DUMP);
           
           comandos.add("-h");      
           comandos.add("localhost");     
           comandos.add("-p");      
           comandos.add("5432");      
           comandos.add("-U");      
           comandos.add("postgres");      
           comandos.add("-F");      
           comandos.add("c");      
           comandos.add("-b");      
           comandos.add("-v");
           comandos.add("-f");      
           comandos.add("src/main/java/com/flavio/backend/backup/SGPEDIDO.backup");  
           comandos.add("SGPedido");      
           ProcessBuilder pb = new ProcessBuilder(comandos);      
           try {      
               final Process process = pb.start();      
         
               final BufferedReader r = new BufferedReader(      
                   new InputStreamReader(process.getErrorStream()));      
               String line = r.readLine();      
               while (line != null) {      
               System.err.println(line);      
               line = r.readLine();      
               }      
               r.close();      
         
               process.waitFor();    
               process.destroy(); 
               System.out.println("backup realizado com sucesso.");  
         
           } catch (IOException e) {      
               e.printStackTrace();      
           } catch (InterruptedException ie) {      
               ie.printStackTrace();      
           }         
    }
    
    @GetMapping("/backup/restaurar")
    public void restaurar(){
        File file = new File("src/main/java/com/flavio/backend/backup/SGPEDIDO.backup");
        if(file.exists()){
        final List<String> comandos = new ArrayList<String>();      
           comandos.add(Util.PG_RESTORE); 
           comandos.add("-h");      
           comandos.add("localhost");
           comandos.add("-p");      
           comandos.add("5432");      
           comandos.add("-U");      
           comandos.add("postgres");      
           comandos.add("-d");      
           comandos.add("SGPedido");     
           comandos.add("-v");      
           comandos.add("src/main/java/com/flavio/backend/backup/SGPEDIDO.backup"); 
           ProcessBuilder pb = new ProcessBuilder(comandos);      
           try {      
               final Process process = pb.start();      
               final BufferedReader r = new BufferedReader(      
                   new InputStreamReader(process.getErrorStream()));      
               String line = r.readLine();      
               while (line != null) {      
               System.err.println(line);      
               line = r.readLine();      
               }      
               r.close();      
         
               process.waitFor();    
               process.destroy(); 
               System.out.println("Restore realizado com sucesso.");  
         
           } catch (IOException e) {      
               e.printStackTrace();      
           } catch (InterruptedException ie) {      
               ie.printStackTrace();      
           }         
        }else{
            System.out.println("Não existe o arquivo de backup!");
        }
    }
    
    @GetMapping("/backup/variaveis")
    public Configuracao variaveisBackup(){
        return bconfig.buscar();
    }
    
}
