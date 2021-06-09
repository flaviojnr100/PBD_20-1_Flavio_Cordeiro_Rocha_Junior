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
import model.FinanciaAnual;
import model.FinanciaMensal;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Flavio
 */
public class RepositoryFinanciaAnual {
     private String url = RepositoryUtil.url+"/financiaAnual";
    private Gson gson;
    private final DefaultHttpClient client = new DefaultHttpClient();

    public RepositoryFinanciaAnual() {
        gson = new Gson();
        
    }
     public List<FinanciaAnual> buscarTodos(){
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

            Type listType = new TypeToken<ArrayList<FinanciaAnual>>(){}.getType();
            List<FinanciaAnual> financia = gson.fromJson(output, listType);
            return financia;
        
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryFinanciaAnual.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(RepositoryFinanciaAnual.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryFinanciaAnual.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
    }
        
     public List<FinanciaAnual> buscarPorAno(int ano){
        try {
            
            HttpURLConnection conn = (HttpURLConnection) new URL(url+"/"+ano).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
             BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output = "";
            String line;
            
            while((line = br.readLine()) != null){
                output += line;
            }
            
            conn.disconnect();

            Type listType = new TypeToken<ArrayList<FinanciaAnual>>(){}.getType();
            List<FinanciaAnual> financia = gson.fromJson(output, listType);
            return financia;
        
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryFinanciaAnual.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(RepositoryFinanciaAnual.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryFinanciaAnual.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
    }
        
    
}
