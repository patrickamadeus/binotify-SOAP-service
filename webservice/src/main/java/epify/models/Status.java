package webservice.src.main.java.epify.models;

import java.sql.*;

import webservice.src.main.java.epify.utils.*;
import io.github.cdimascio.dotenv.Dotenv;

public class Status {
    //create static variable apiKey and clientType
    String apiKey;
    String clientType;
    Dotenv dotenv;

    public Status(String apiKey, String clientType){
        //if apiKey and clientType is null
        if (apiKey == null || clientType == null){
            apiKey = "";
            clientType = "";
        }

        this.apiKey = apiKey;
        this.clientType = clientType;
        this.dotenv = Dotenv.load();
    }

    public String checkClient(){
        String response;

        if (this.clientType.equals("REST_SERVICE")){
            System.out.println("Client is a REST service");
            
            if (this.apiKey.equals(this.dotenv.get("API_KEY_REST"))){
                response = String.format("{\"status\": 200, \"message\": \"Rest Service client is valid\"}");
            } else{
                response = String.format("{\"status\": 401, \"message\": \"apiKey is not valid\"}");
            }

        }else if(this.clientType.equals("EPIFY_APP")){

            if (this.apiKey.equals(this.dotenv.get("API_KEY_EPIFY_APP"))){
                response = String.format("{\"status\": 200, \"message\": \"Rest Service client is valid\"}");
            } else{
                response = String.format("{\"status\": 401, \"message\": \"apiKey is not valid\"}");
            }
            
            System.out.println("Client is an Epify App");
            response = String.format("{\"status\": 200, \"message\": \"Epify App client is valid\"}");
        }else if(this.clientType.equals("EPIFY_PREMIUM")){
            if (this.apiKey.equals(this.dotenv.get("API_KEY_EPIFY_PREMIUM"))){
                response = String.format("{\"status\": 200, \"message\": \"Rest Service client is valid\"}");
            } else{
                response = String.format("{\"status\": 401, \"message\": \"apiKey is not valid\"}");
            }
            
            System.out.println("Client is an Epify Premium");
            response = String.format("{\"status\": 200, \"message\": \"Epify Premium client is valid\"}");
        }else{
            System.out.println("Client is not valid, check api key");
            response = String.format("{\"status\": 401, \"message\": \"Client is not valid, check api key\"}");
        }

        return response;
    }

}
