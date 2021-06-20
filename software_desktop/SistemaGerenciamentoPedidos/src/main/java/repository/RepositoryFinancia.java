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
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.FinanciaMensal;
import model.ItemCardapio;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Flavio
 */
public class RepositoryFinancia {
    private String url = RepositoryUtil.url+"/financiaMensal";
    private Gson gson;
    private final DefaultHttpClient client = new DefaultHttpClient();

    public RepositoryFinancia() {
        gson = new Gson();
        
    }
    
        public List<FinanciaMensal> buscarTodos(){
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

            Type listType = new TypeToken<ArrayList<FinanciaMensal>>(){}.getType();
            List<FinanciaMensal> financia = gson.fromJson(output, listType);
            return financia;
        
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryFinancia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(RepositoryFinancia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryFinancia.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
    }
        
     public List<FinanciaMensal> buscarMes(int mes,int ano){
        try {
            
            HttpURLConnection conn = (HttpURLConnection) new URL(url+"/"+mes+"/"+ano).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output = "";
            String line;
            
            while((line = br.readLine()) != null){
                output += line;
            }
            
            conn.disconnect();

            Type listType = new TypeToken<ArrayList<FinanciaMensal>>(){}.getType();
            List<FinanciaMensal> financia = gson.fromJson(output, listType);
            return financia;
        
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryFinancia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(RepositoryFinancia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryFinancia.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
    }
    
    
}
