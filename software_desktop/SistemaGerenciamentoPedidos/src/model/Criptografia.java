/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Flavio
 */
public class Criptografia {
    
    private static MessageDigest md = null;
    
    public static MessageDigest getMd(){
        try {
            if(md==null){
                md = MessageDigest.getInstance("MD5");
            }
            return md;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Criptografia.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private static char[] hexCodes(byte[] text){
        char[] hexOutput = new char[text.length * 2];
        String hexString;
        for (int i = 0; i < text.length; i++) {
            hexString = "00" + Integer.toHexString(text[i]);
            hexString.toUpperCase().getChars(hexString.length() - 2,
                                	hexString.length(), hexOutput, i * 2);
        }
        return hexOutput;
    }
    
    public static String criptografar(String pwd) {
        if (md != null) {
            return new String(hexCodes(md.digest(pwd.getBytes())));
        }
        return null;
    }
    
    public static boolean validarSenha(String senha){
        //ascii 48-57 numeros
        //ascii 97-122 letras minusculas
        //ascii 65-90 letras maisculas
        boolean numero = false;
        boolean charact = false;
       if(senha.length()>=6 && senha.length()<=11){
           if(verificarNumero(senha)){
               numero = true;
               
           }else{
               JOptionPane.showMessageDialog(null, "A senha deve conter numeros e letras");
           }
           if(verificarCaracteres(senha)){
               charact = true;
           }else{
               JOptionPane.showMessageDialog(null, "A senha deve conter numeros e letras");
           }
           
           if(numero && charact){
               return true;
           }
           
       }else{
           JOptionPane.showMessageDialog(null, "Tamanho de senha inválido, tem que ter entre 6 a 11 caracteres");
       }
        return false;
    }
    
    private static boolean verificarNumero(String senha){
        boolean condicao = false;
        
        for(int i=0;i<senha.length();i++){
            int caract = senha.charAt(i);
            if(caract>=48 && caract<=57){
                condicao=true;
                break;
            }
        }
        return condicao;
    
    }
    private static boolean verificarCaracteres(String senha){
        boolean condicao = false;
        
        for(int i=0;i<senha.length();i++){
            int caract = senha.charAt(i);
            if(caract>=58 && caract<=122){
                condicao=true;
                break;
            }
        }
        return condicao;
    
    }
    
}