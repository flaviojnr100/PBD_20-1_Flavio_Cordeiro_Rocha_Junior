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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Funcionario;
import model.ItemCardapio;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Flavio
 */
public class RepositoryCardapio {
    private String url = RepositoryUtil.url+"/cardapio";
    private Gson gson;
    private final DefaultHttpClient client = new DefaultHttpClient();

    public RepositoryCardapio() {
        gson = new Gson();
    }
    
    
    
    public List<ItemCardapio> buscarTodos(){
        try {
            
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json;charset=utf-8");
            
             BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String output = "";
            String line;
            
            while((line = br.readLine()) != null){
                output += line;
            }
            
            conn.disconnect();

            Type listType = new TypeToken<ArrayList<ItemCardapio>>(){}.getType();
            List<ItemCardapio> cardapio = gson.fromJson(output, listType);
            return cardapio;
        
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryCardapio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(RepositoryCardapio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryCardapio.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
    }
    public List<ItemCardapio> buscarNome(String nome){
        try {
            String nomeNovo = URLEncoder.encode(nome, "UTF-8");
            if(nomeNovo.contains("+")){
                nomeNovo = nomeNovo.replace("+", "%20");
            }
             
            HttpURLConnection conn = (HttpURLConnection) new URL(url+"/buscarNome/"+nomeNovo).openConnection();
            
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
             BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String output = "";
            String line;
            
            while((line = br.readLine()) != null){
                output += line;
            }
            
            conn.disconnect();

            Type listType = new TypeToken<ArrayList<ItemCardapio>>(){}.getType();
            List<ItemCardapio> cardapio = gson.fromJson(output, listType);
            return cardapio;
        
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryCardapio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(RepositoryCardapio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryCardapio.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
    }
    
    public ItemCardapio buscarNomeUnico(String nome){
        try {
            String nomeNovo = URLEncoder.encode(nome, "UTF-8");
            if(nomeNovo.contains("+")){
                nomeNovo = nomeNovo.replace("+", "%20");
            }
            HttpURLConnection conn = (HttpURLConnection) new URL(url+"/buscarNomeUnico/"+nomeNovo).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json;charset=utf-8");
            
             BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String output = "";
            String line;
            
            while((line = br.readLine()) != null){
                output += line;
            }
            
            conn.disconnect();

           
            ItemCardapio cardapio = gson.fromJson(output, ItemCardapio.class);
            return cardapio;
        
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryCardapio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(RepositoryCardapio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryCardapio.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
    }
    
    public boolean salvar(ItemCardapio item){
        
        try {
            URL url = new URL(this.url);
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("nome", item.getNome());
            params.put("preco", item.getPreco());
            params.put("descricao", item.getDescricao());
            params.put("isAtivo", item.isIsAtivo());
            
            
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
            Logger.getLogger(RepositoryCardapio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RepositoryCardapio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryCardapio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public ItemCardapio alterarEstado(int id){
        try {
            URL url = new URL(this.url+"/mudarEstado/"+id);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("PUT");
            httpURLConnection.setRequestProperty("Accept", "application/json;charset=utf-8");
            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"utf-8"));
            String output = "";
            String line;
            
            while((line = br.readLine()) != null){
                output += line;
            }
            
            httpURLConnection.disconnect();
            
            ItemCardapio item = gson.fromJson(new String(output.getBytes()), ItemCardapio.class);
            return item;
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryCardapio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryCardapio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public boolean editar(int id,ItemCardapio item){
        
        try {
            URL url = new URL(this.url+"/"+id);
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("nome", item.getNome());
            params.put("preco", item.getPreco());
            params.put("descricao", item.getDescricao());
            params.put("isAtivo", item.isIsAtivo());
            
            
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
            Logger.getLogger(RepositoryCardapio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RepositoryCardapio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryCardapio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
