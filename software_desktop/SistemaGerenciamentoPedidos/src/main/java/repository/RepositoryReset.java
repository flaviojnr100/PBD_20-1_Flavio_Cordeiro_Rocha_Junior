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
import model.SenhaReset;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Flavio
 */
public class RepositoryReset {
    private String url = RepositoryUtil.url+"/senhaReset";
    private Gson gson;
    private final DefaultHttpClient client = new DefaultHttpClient();

    public RepositoryReset() {
        this.gson = new Gson();
    }
    
    
    
    public List<SenhaReset> buscarTodos(){
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
            Type listType = new TypeToken<ArrayList<SenhaReset>>(){}.getType();
            List<SenhaReset> resets = gson.fromJson(output, listType);
            return resets;
            
            
        } catch (ProtocolException ex) {
            Logger.getLogger(RepositoryReset.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryReset.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void salvarReset(int id){
         try {
            URL url = new URL(this.url);
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("id", id);
            
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
            conn.setDoInput(true);
            conn.getOutputStream().write(postDataBytes);
            
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String output = "";
            String line;
            
            while((line = in.readLine()) != null){
                output += line;
            }
            
            conn.disconnect();
            
           
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void resetar(int id){
         try {
            URL url = new URL(this.url);
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("id", id);
            
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postDataBytes = postData.toString().getBytes("UTF-8");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.getOutputStream().write(postDataBytes);
            
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String output = "";
            String line;
            
            while((line = in.readLine()) != null){
                output += line;
            }
            
            conn.disconnect();
            
           
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public SenhaReset buscarLogin(String login){
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url+"/buscalogin/"+login).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            BaseDados.setStatus(conn.getResponseCode());
            if(conn.getResponseCode() == 202){
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String output = "";
                String line;
                while((line = br.readLine()) != null){
                    output += line;
                }
                conn.disconnect();
                SenhaReset sr = gson.fromJson(new String(output.getBytes()), SenhaReset.class);
                return sr;
                }
            conn.disconnect();
            
        } catch (ProtocolException ex) {
            Logger.getLogger(RepositoryReset.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryReset.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
