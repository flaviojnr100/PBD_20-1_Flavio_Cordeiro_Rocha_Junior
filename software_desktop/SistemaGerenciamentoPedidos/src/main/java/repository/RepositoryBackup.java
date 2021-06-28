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
import model.BaseDados;
import model.Configuracao;
import model.ItemCardapio;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Flavio
 */
public class RepositoryBackup {
     private String url = RepositoryUtil.url+"/backup";
     Gson gson = new Gson();
     private final DefaultHttpClient client = new DefaultHttpClient();
     
     public boolean salvar(String horario,boolean alteracao){
        
        try {
            URL url = new URL(this.url+"/salvar");
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("id", 1);
            params.put("horario", horario);
            params.put("alteracao", alteracao);
           
            
            
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
            Logger.getLogger(RepositoryBackup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RepositoryBackup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryBackup.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
     public void backup(){
        try {
            
            HttpURLConnection conn = (HttpURLConnection) new URL(url+"/backup").openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output = "";
            String line;
            
            while((line = br.readLine()) != null){
                output += line;
            }
            
            conn.disconnect();

           
        
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryBackup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(RepositoryBackup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryBackup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void restaurar(){
        try {
            
            HttpURLConnection conn = (HttpURLConnection) new URL(url+"/restaurar").openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output = "";
            String line;
            
            while((line = br.readLine()) != null){
                output += line;
            }
            
            conn.disconnect();

           
        
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryBackup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(RepositoryBackup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryBackup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
      public void bucarVariaveis(){
        try {
            
            HttpURLConnection conn = (HttpURLConnection) new URL(url+"/variaveis").openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output = "";
            String line;
            
            while((line = br.readLine()) != null){
                output += line;
            }
            
            conn.disconnect();
          Configuracao config =  gson.fromJson(output, Configuracao.class);
            System.out.println(""+output);
          BaseDados.setAlteracao(config.isAlteracao());
          BaseDados.setHorario(config.getHora());
            System.out.println(""+BaseDados.getHorario());
            System.out.println(""+BaseDados.isAlteracao());
           
        
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryBackup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(RepositoryBackup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryBackup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
