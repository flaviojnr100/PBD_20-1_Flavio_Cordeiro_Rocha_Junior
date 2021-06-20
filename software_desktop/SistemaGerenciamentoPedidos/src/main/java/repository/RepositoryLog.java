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
import model.Log;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Flavio
 */
public class RepositoryLog {
    private String url = RepositoryUtil.url+"/log";
    private Gson gson;
    private final DefaultHttpClient client = new DefaultHttpClient();

    public RepositoryLog() {
        gson = new Gson();
    }
    
    public List<Log> buscarTodos(){
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

            Type listType = new TypeToken<ArrayList<Log>>(){}.getType();
            List<Log> logs = gson.fromJson(output, listType);
            return logs;
        
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryLog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(RepositoryLog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryLog.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
    }
     public List<Log> buscarData(int dia,int mes,int ano){
        try {
            
            HttpURLConnection conn = (HttpURLConnection) new URL(url+"/"+dia+"/"+mes+"/"+ano).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output = "";
            String line;
            
            while((line = br.readLine()) != null){
                output += line;
            }
            
            conn.disconnect();

            Type listType = new TypeToken<ArrayList<Log>>(){}.getType();
            List<Log> logs = gson.fromJson(output, listType);
            return logs;
        
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryLog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(RepositoryLog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryLog.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
    }
    
}
