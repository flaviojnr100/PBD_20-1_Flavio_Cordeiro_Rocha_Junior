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
import model.Funcionario;
import model.Mesa;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Flavio
 */
public class RepositoryMesa {
    private String url = RepositoryUtil.url+"/mesa";
    private Gson gson;
    private final DefaultHttpClient client = new DefaultHttpClient();

    public RepositoryMesa() {
        gson = new Gson();
    }
    public List<Mesa> buscarTodos(){
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

            Type listType = new TypeToken<ArrayList<Mesa>>(){}.getType();
            List<Mesa> mesas = gson.fromJson(output, listType);
            return mesas;
        
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryMesa.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(RepositoryMesa.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryMesa.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
    }
    public Mesa buscarMesaNumero(int numero){
        try {
            
            HttpURLConnection conn = (HttpURLConnection) new URL(url+"/buscarNumero/"+numero).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
             BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output = "";
            String line;
            
            while((line = br.readLine()) != null){
                output += line;
            }
            
            conn.disconnect();

            
            Mesa mesas = gson.fromJson(output, Mesa.class);
            return mesas;
        
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryMesa.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(RepositoryMesa.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryMesa.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
    }
    
     public boolean salvar(Mesa mesa){
        
        try {
            URL url = new URL(this.url);
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("numero", mesa.getNumero());
            params.put("isLivre", true);
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
            Logger.getLogger(RepositoryMesa.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RepositoryMesa.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryMesa.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
      public Mesa alterarEstado(int id){
        try {
            URL url = new URL(this.url+"/alterarEstado/"+id);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("PUT");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String output = "";
            String line;
            
            while((line = br.readLine()) != null){
                output += line;
            }
            
            httpURLConnection.disconnect();
            
            Mesa m = gson.fromJson(new String(output.getBytes()), Mesa.class);
            return m;
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryMesa.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryMesa.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
     
}
