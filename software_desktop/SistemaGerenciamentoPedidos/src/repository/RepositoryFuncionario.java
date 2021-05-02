/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Funcionario;
import model.Mesa;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 *
 * @author Flavio
 */
public class RepositoryFuncionario {
    private String url = RepositoryUtil.url+"/funcionario";
    private Gson gson;

    public RepositoryFuncionario() {
        this.gson = new Gson();
    }
    
    public Funcionario buscarId(int id){
        try {
            url+="/"+id;
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
            
            Funcionario f = gson.fromJson(new String(output.getBytes()), Funcionario.class);
            return f;
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public List<Funcionario> buscarTodos(){
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

            Type listType = new TypeToken<ArrayList<Funcionario>>(){}.getType();
            List<Funcionario> funcionarios = gson.fromJson(output, listType);
            return funcionarios;
        
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
    }
    
    public void salvar(Funcionario funcionario){
        try {
            String json = gson.toJson(funcionario);
            URL url = new URL(this.url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
                outputStream.write(json.getBytes());
                outputStream.flush();
            }
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                
            } else {
                // ... do something with unsuccessful response
            }
        }   catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
