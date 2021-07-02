package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Log;

public class ControllerInformacoesLog implements Initializable {

    @FXML
    private Label lblUsuario;

    @FXML
    private Label lblIp;

    @FXML
    private Label lblTabela;

    @FXML
    private TextArea dadosArea;

    @FXML
    private Label lblOperacao;

    @FXML
    private Button btnOk;
    
    private static Log log;

    @FXML
    void KeyOk(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            btnOk.fire();
        }
    }

    @FXML
    void ok(ActionEvent event) {
        Stage stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblIp.setText(log.getEnderecoIp());
        lblTabela.setText(log.getTabela());
        lblUsuario.setText(log.getUsuario());
        String operacao ="";
        if(log.getOperacao().equals("INSERT")){
            operacao = "Inserção";
            Map<String, Object> retMap = new Gson().fromJson(log.getDadosNew(), new TypeToken<HashMap<String, Object>>() {}.getType());
            String formato="";
            for(String obj:retMap.keySet()){
                formato+=obj+" : "+retMap.get(obj)+"\n";
            }
            dadosArea.setText(formato);
        }else if(log.getOperacao().equals("UPDATE")){
            operacao = "Atualização";
            Map<String, Object> retMap = new Gson().fromJson(log.getDadosNew(), new TypeToken<HashMap<String, Object>>() {}.getType());
            Map<String, Object> retMap1 = new Gson().fromJson(log.getDadosOld(), new TypeToken<HashMap<String, Object>>() {}.getType());
            dadosArea.setText(atualizacaoLog(retMap, retMap1));
        }else{
            operacao = "Remoção";
            Map<String, Object> retMap = new Gson().fromJson(log.getDadosNew(), new TypeToken<HashMap<String, Object>>() {}.getType());
            String formato="";
            for(String obj:retMap.keySet()){
                formato+=obj+" : "+retMap.get(obj)+"\n";
            }
            dadosArea.setText(formato);
        }
        lblOperacao.setText(operacao);
    }
    
    private String atualizacaoLog(Map<String,Object>novo,Map<String,Object>antigo){
        String formato="";
        
        for(String obj:novo.keySet()){
            if(!obj.equals("ultimo_acesso")){
                if(!novo.get(obj).equals(antigo.get(obj))){
                    formato+=obj+" : "+novo.get(obj)+"\n";
                }
            }
        }
        
        return formato;
    }
    

    public static void setLog(Log log) {
        ControllerInformacoesLog.log = log;
    }

}
