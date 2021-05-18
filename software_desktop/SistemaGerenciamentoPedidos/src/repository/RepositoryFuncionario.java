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
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import model.BaseDados;
import org.apache.http.impl.client.DefaultHttpClient;
/**
 *
 * @author Flavio
 */
public class RepositoryFuncionario {
    private String url = RepositoryUtil.url+"/funcionario";
    private String urlSU = RepositoryUtil.url+"/funcionarioSU";
    private Gson gson;
    private final DefaultHttpClient client = new DefaultHttpClient();

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
    
        public List<Funcionario> buscarTodosSU(){
        try {
            
            HttpURLConnection conn = (HttpURLConnection) new URL(urlSU).openConnection();
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
    public Funcionario autenticar(String login,String senha){
        try {
            URL url = new URL(this.url+"/autenticar");
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("login", login);
            params.put("senha", senha);
            
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
            BaseDados.setStatus(conn.getResponseCode());
            
            if(conn.getResponseCode() == 202){
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String output = "";
            String line;
            
            while((line = in.readLine()) != null){
                output += line;
            }
            
            
            conn.disconnect();
            
            
            Funcionario f = gson.fromJson(new String(output.getBytes()), Funcionario.class);
            
            return f;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public boolean salvar(Funcionario funcionario){
        
        try {
            URL url = new URL(this.url);
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("nome", funcionario.getNome());
            params.put("sobrenome", funcionario.getSobrenome());
            params.put("cpf", funcionario.getCpf());
            params.put("telefone", funcionario.getTelefone());
            params.put("login", funcionario.getLogin());
            params.put("senha", funcionario.getSenha());
            params.put("isPermissao", funcionario.isIsPermissao());
            params.put("tipoAcesso", funcionario.getTipoAcesso());
            params.put("isLogado", funcionario.isIsLogado());
            params.put("isReset", funcionario.isIsReset());
            
            
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
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean editar(int id,Funcionario funcionario){
         try {
            URL url = new URL(this.url+"/"+funcionario.getId());
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("nome", funcionario.getNome());
            params.put("sobrenome", funcionario.getSobrenome());
            params.put("cpf", funcionario.getCpf());
            params.put("telefone", funcionario.getTelefone());
            params.put("login", funcionario.getLogin());
            params.put("senha", funcionario.getSenha());
            params.put("isPermissao", funcionario.isIsPermissao());
            params.put("tipoAcesso", funcionario.getTipoAcesso());
            params.put("isLogado", funcionario.isIsLogado());
            params.put("isReset", funcionario.isIsReset());
            //params.put("ultimoAcesso", funcionario.getUltimoAcesso());
            
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
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public void logout(String login){
         try {
            URL url = new URL(this.url+"/logout");
            Map<String,Object> params = new LinkedHashMap<>();
            
            params.put("login", login);
           
            
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
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Funcionario alterarEstado(int id){
        try {
            URL url = new URL(this.url+"/alterar_estado/"+id);
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
            
            Funcionario f = gson.fromJson(new String(output.getBytes()), Funcionario.class);
            return f;
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
        public List<Funcionario> buscarNome(String nome){
        try {
            URL url = new URL(this.url+"/buscarNome/"+nome);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String output = "";
            String line;
            
            while((line = br.readLine()) != null){
                output += line;
            }
            
            httpURLConnection.disconnect();
            
            Type listType = new TypeToken<ArrayList<Funcionario>>(){}.getType();
            List<Funcionario> funcionarios = gson.fromJson(output, listType);
            return funcionarios;
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<Funcionario> buscarNomeSU(String nome){
        try {
            URL url = new URL(this.urlSU+"/buscarNome/"+nome);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String output = "";
            String line;
            
            while((line = br.readLine()) != null){
                output += line;
            }
            
            httpURLConnection.disconnect();
            
            Type listType = new TypeToken<ArrayList<Funcionario>>(){}.getType();
            List<Funcionario> funcionarios = gson.fromJson(output, listType);
            return funcionarios;
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public List<Funcionario> buscarCpf(String cpf){
        try {
            URL url = new URL(this.url+"/buscarCpf/"+cpf);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String output = "";
            String line;
            
            while((line = br.readLine()) != null){
                output += line;
            }
            
            httpURLConnection.disconnect();
            
            Type listType = new TypeToken<ArrayList<Funcionario>>(){}.getType();
            List<Funcionario> funcionarios = gson.fromJson(output, listType);
            return funcionarios;
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public List<Funcionario> buscarCpfSU(String cpf){
        try {
            URL url = new URL(this.urlSU+"/buscarCpf/"+cpf);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String output = "";
            String line;
            
            while((line = br.readLine()) != null){
                output += line;
            }
            
            httpURLConnection.disconnect();
            
            Type listType = new TypeToken<ArrayList<Funcionario>>(){}.getType();
            List<Funcionario> funcionarios = gson.fromJson(output, listType);
            return funcionarios;
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
     public List<Funcionario> buscarLogin(String login){
        try {
            URL url = new URL(this.url+"/buscarLogin/"+login);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String output = "";
            String line;
            
            while((line = br.readLine()) != null){
                output += line;
            }
            
            httpURLConnection.disconnect();
            
            Type listType = new TypeToken<ArrayList<Funcionario>>(){}.getType();
            List<Funcionario> funcionarios = gson.fromJson(output, listType);
            return funcionarios;
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
     public List<Funcionario> buscarLoginSU(String login){
        try {
            URL url = new URL(this.urlSU+"/buscarLogin/"+login);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String output = "";
            String line;
            
            while((line = br.readLine()) != null){
                output += line;
            }
            
            httpURLConnection.disconnect();
            
            Type listType = new TypeToken<ArrayList<Funcionario>>(){}.getType();
            List<Funcionario> funcionarios = gson.fromJson(output, listType);
            return funcionarios;
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
     
     
      public Funcionario buscarLoginUnico(String login){
        
        try {
            URL url = new URL(this.url+"/login");
            Map<String,Object> params = new LinkedHashMap<>();
            params.put("login", login);
            
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
            BaseDados.setStatus(conn.getResponseCode());
            if(conn.getResponseCode() == 202){
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            
                String output = "";
                String line;
            
                while((line = in.readLine()) != null){
                    output += line;
                }
            
            
            
                conn.disconnect();
            
            
                Funcionario f = gson.fromJson(new String(output.getBytes()), Funcionario.class);
            
                return f;
            }
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
