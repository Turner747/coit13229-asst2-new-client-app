/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fireservice.newclientapp;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author joshturner
 */
public class FireServiceClient {
    final private String BASE_URL = "http://localhost:8080/FireWebService";
    final private String FIRE_EXT = "/fires";
    
    public FireServiceClient(){
        
    }
    
    public ArrayList<FireDetails> getFires(){
        ArrayList<FireDetails> fires = null;
        
        try {
            URL url = new URL(BASE_URL + FIRE_EXT);
            
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            
            int responseCode = conn.getResponseCode();
            
            if (responseCode != 200){
               throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                String jsonString = "";
                Scanner scanner = new Scanner(url.openStream());

               //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                   jsonString += scanner.nextLine();
                }
                
                JSONObject obj = new JSONObject(jsonString);
                JSONArray arr = obj.getJSONArray("fires");
                
                for(int i = 0; i < arr.length(); i++){
                    
                    int id = arr.getJSONObject(i).getInt("id");
                    int x_pos = arr.getJSONObject(i).getInt("x_pos");
                    int y_pos = arr.getJSONObject(i).getInt("y_pos");
                    int droneId = arr.getJSONObject(i).getInt("droneId");
                    int severity = arr.getJSONObject(i).getInt("severity");
                    double burnArea = arr.getJSONObject(i).getDouble("burningAreaRadius");
                    
                    fires.add(new FireDetails(id,x_pos, y_pos, droneId, severity, burnArea));
                }
            }
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(FireServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FireServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(FireServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return fires;
    }
    
    public int sendFireTruck(int fireId){
        
        int responseCode = 0;
        
        try {
            URL url = new URL(BASE_URL + FIRE_EXT + "/" + fireId);
            
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.connect();
            
            responseCode = conn.getResponseCode();
            
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(FireServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FireServiceClient.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return responseCode;
    }
}
