/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BiologicalPark;

import AppDisplay.MessageDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.*;

/**
 * Guardar clientes num ficheiro .json
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class ClientDAOJson {
    
    private static final String fileName = "clients.json";
    
    public ClientDAOJson() {
        
    }
    
    private HashSet<Client> selectAll() {
        try {
            if( !(new File(fileName)).exists() ) return new HashSet<>();
            
            
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            Gson gson = new GsonBuilder().create();
            
            HashSet<Client> list = gson.fromJson(br, new TypeToken<HashSet<Client>>() { }.getType());
            return list;
            
        } catch (IOException ex) {
            Logger.getLogger(ClientDAOJson.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new HashSet<>();
    }
    
    public Client loadClient(String username) {
        HashSet<Client> list = selectAll();
        if(list == null) return null;
        for (Client c : list) {
            if (c.getUsername().equals(username)) {
                return c;
            }
        }
        return null;
    }

    public void saveClient(Client c) {
        FileWriter writer = null;
        try {
            Gson gson = new GsonBuilder().create();
            HashSet<Client> list = selectAll();
            if(list == null) list = new HashSet<Client>();
            for(Client client : list){
                if(client.getUsername().equals(c.getUsername())){ 
                    new MessageDialog("The username entered already exists, please choose another");
                    return;
                }
            }
            list.add(c);
            writer = new FileWriter(fileName);
            gson.toJson(list, writer);
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientDAOJson.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void changeSavedClient(Client c, String username){
        FileWriter writer = null;
        try {
            Gson gson = new GsonBuilder().create();
            HashSet<Client> list = selectAll();
            if(list == null) list = new HashSet<Client>();
            
            for(Client client : list)
                if(client.getUsername().equals(username)) list.remove(client);
            
            list.add(c);
            writer = new FileWriter(fileName);
            gson.toJson(list, writer);
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientDAOJson.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
