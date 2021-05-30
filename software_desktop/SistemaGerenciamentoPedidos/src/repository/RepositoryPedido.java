/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ItemCardapio;
import model.Pedido;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Flavio
 */
public class RepositoryPedido {
    private String url = RepositoryUtil.url+"/pedido";
    private Gson gson;
    private final DefaultHttpClient client = new DefaultHttpClient();

    public RepositoryPedido() {
        gson = new Gson();
    }
    
    public List<Pedido> buscarTodos(){
        try {
            
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
             BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output = "";
            String line;
            
            while((line = br.readLine()) != null){
                output += line;
            }
            
            conn.disconnect();

            Type listType = new TypeToken<ArrayList<Pedido>>(){}.getType();
            List<Pedido> pedidos = gson.fromJson(output, listType);
            return pedidos;
        
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryPedido.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(RepositoryPedido.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
    }
    public boolean salvar(Pedido pedido){
        
        try {
            URL url = new URL(this.url);
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("funcionario", pedido.getFuncionario().getId());
            params.put("status", "pendente");
            params.put("total", pedido.getTotal());
            params.put("mesa", pedido.getMesa().getNumero());
            params.put("cardapio", converterArray(pedido.getItens()));

            System.out.println("Formato: "+params.toString());
            
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);
            
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            if(conn.getResponseCode()==200){
                return true;
            }else{
                return false;
            }
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryPedido.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RepositoryPedido.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private String converterArray(List<ItemCardapio> itens){
        String cardapio="";
        for(int i=0;i<itens.size();i++){
            cardapio+=itens.get(i).getId();
            if(i<itens.size()-1){
                cardapio+=",";
            }
        }
        
        return cardapio;
    }
    
        public boolean mudarStatus(Pedido pedido){
        
        try {
            URL url = new URL(this.url+"/alterarEstado/"+pedido.getId());
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("status", pedido.getStatus());
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);
            
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            if(conn.getResponseCode()==200){
                return true;
            }else{
                return false;
            }
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryPedido.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RepositoryPedido.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    

}
