package webservice.src.main.java.epify.services;

import java.lang.Integer;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.WebParam;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import com.sun.net.httpserver.HttpExchange;

import webservice.src.main.java.epify.models.*;

@WebService(endpointInterface = "webservice.src.main.java.epify.services.WebServices")
@SOAPBinding(style = Style.DOCUMENT)
public class WebServices {

    @Resource
    WebServiceContext context;

    @WebMethod(operationName = "createSubscriptionRequest")
    public String createSubscriptionRequest(
        @WebParam(name = "creator_id", targetNamespace = "http://services.epify.java.main.src.webservice/") Integer creator_id, 
        @WebParam(name = "subscriber_id", targetNamespace = "http://services.epify.java.main.src.webservice/") Integer subscriber_id){
        System.out.println("----------------------");
        System.out.println("createSubscriptionRequest");
            
        // if param is null
        if (creator_id == null || subscriber_id == null){
            String response = String.format("{\"status\": 400, \"message\": \"Bad request. Missing parameter\", \"creator_id\": %s, \"subscriber_id\": %s}", creator_id, subscriber_id);
            return response;
        }

        MessageContext mc = context.getMessageContext();
        HttpExchange exchange = (HttpExchange)mc.get("com.sun.xml.ws.http.exchange");

        String ip = String.format("%s", exchange.getRemoteAddress());
        String endpoint = String.format("%s", exchange.getRequestURI());

        String apiKey = exchange.getRequestHeaders().getFirst("apiKey"); 
        String clientType = exchange.getRequestHeaders().getFirst("clientType"); 
        
        System.out.println("ip: " + ip);
        System.out.println("endpoint: " + endpoint);        
        try{       
            // need api key validation
            Status status = new Status(apiKey, clientType);
            String response = status.checkClient();
            if (response.equals("{\"status\": 401, \"message\": \"Client is not valid, check api key\"}")){
                return response;
            }

            Subscription subscription = new Subscription(creator_id, subscriber_id);
            response = subscription.createSubscriptionRequest();
            
            Logger log = new Logger(ip, endpoint, response);
            log.log();

            return response;
        }
        catch (Exception e){
            String response = String.format("{\"status\": 500, \"message\": \"Internal server error\", \"error\": [%s]}", e.getMessage());

            return response;
        }
    }

    @WebMethod
    public String isSubscriptionValid(
        @WebParam(name = "creator_id", targetNamespace = "http://services.epify.java.main.src.webservice/") Integer creator_id, 
        @WebParam(name = "subscriber_id", targetNamespace = "http://services.epify.java.main.src.webservice/") Integer subscriber_id){
        
        System.out.println("----------------------");
        System.out.println("isSubscriptionValid");
            
        // if param is null
        if (creator_id == null || subscriber_id == null){
            String response = String.format("{\"status\": 400, \"message\": \"Bad request. Missing parameter\", \"creator_id\": %s, \"subscriber_id\": %s}", creator_id, subscriber_id);
            return response;
        }

        MessageContext mc = context.getMessageContext();
        HttpExchange exchange = (HttpExchange)mc.get("com.sun.xml.ws.http.exchange");

        String ip = String.format("%s", exchange.getRemoteAddress());
        String endpoint = String.format("%s", exchange.getRequestURI());

        String apiKey = exchange.getRequestHeaders().getFirst("apiKey"); 
        String clientType = exchange.getRequestHeaders().getFirst("clientType"); 
        
        System.out.println("ip: " + ip);
        System.out.println("endpoint: " + endpoint);        
        try{       
            // need api key validation
            Status status = new Status(apiKey, clientType);
            String response = status.checkClient();
            if (response.equals("{\"status\": 401, \"message\": \"Client is not valid, check api key\"}")){
                return response;
            }
             
            Subscription subscription = new Subscription(creator_id, subscriber_id);
            response = subscription.isSubscriptionValid();

            Logger log = new Logger(ip, endpoint, response);
            log.log();

            return response;
        }
        catch (Exception e){
            String response = String.format("{\"status\": 500, \"message\": \"Internal server error\", \"error\": [%s]}", e.getMessage());

            return response;
        }
    }

    @WebMethod
    public String acceptSubscription(
        @WebParam(name = "creator_id", targetNamespace = "http://services.epify.java.main.src.webservice/") Integer creator_id, 
        @WebParam(name = "subscriber_id", targetNamespace = "http://services.epify.java.main.src.webservice/") Integer subscriber_id){
        
        System.out.println("----------------------");
        System.out.println("acceptSubscription");

        // if param is null
        if (creator_id == null || subscriber_id == null){
            String response = String.format("{\"status\": 400, \"message\": \"Bad request. Missing parameter\", \"creator_id\": %s, \"subscriber_id\": %s}", creator_id, subscriber_id);
            return response;
        }

        MessageContext mc = context.getMessageContext();
        HttpExchange exchange = (HttpExchange)mc.get("com.sun.xml.ws.http.exchange");

        String ip = String.format("%s", exchange.getRemoteAddress());
        String endpoint = String.format("%s", exchange.getRequestURI());

        String apiKey = exchange.getRequestHeaders().getFirst("apiKey"); 
        String clientType = exchange.getRequestHeaders().getFirst("clientType"); 
        
        System.out.println("ip: " + ip);
        System.out.println("endpoint: " + endpoint);        
        try{       
            // need api key validation
            Status status = new Status(apiKey, clientType);
            String response = status.checkClient();
            if (response.equals("{\"status\": 401, \"message\": \"Client is not valid, check api key\"}")){
                return response;
            }

            Subscription subscription = new Subscription(creator_id, subscriber_id);
            response = subscription.acceptSubscription();

            Logger log = new Logger(ip, endpoint, response);
            log.log();

            return response;
        }
        catch (Exception e){
            String response = String.format("{\"status\": 500, \"message\": \"Internal server error\", \"error\": [%s]}", e.getMessage());

            return response;
        }
    }

    @WebMethod
    public String rejectSubscription(
        @WebParam(name = "creator_id", targetNamespace = "http://services.epify.java.main.src.webservice/") Integer creator_id, 
        @WebParam(name = "subscriber_id", targetNamespace = "http://services.epify.java.main.src.webservice/") Integer subscriber_id){
        
        System.out.println("-----------------------");
        System.out.println("rejectSubscription");

        // if param is null
        if (creator_id == null || subscriber_id == null){
            String response = String.format("{\"status\": 400, \"message\": \"Bad request. Missing parameter\", \"creator_id\": %s, \"subscriber_id\": %s}", creator_id, subscriber_id);
            return response;
        }

        MessageContext mc = context.getMessageContext();
        HttpExchange exchange = (HttpExchange)mc.get("com.sun.xml.ws.http.exchange");

        String ip = String.format("%s", exchange.getRemoteAddress());
        String endpoint = String.format("%s", exchange.getRequestURI());

        String apiKey = exchange.getRequestHeaders().getFirst("apiKey"); 
        String clientType = exchange.getRequestHeaders().getFirst("clientType"); 
        
        System.out.println("ip: " + ip);
        System.out.println("endpoint: " + endpoint);

        try{       
            // need api key validation  
            Status status = new Status(apiKey, clientType);
            String response = status.checkClient();
            if (response.equals("{\"status\": 401, \"message\": \"Client is not valid, check api key\"}")){
                return response;
            }        

            Subscription subscription = new Subscription(creator_id, subscriber_id);
            response = subscription.rejectSubscription();

            Logger log = new Logger(ip, endpoint, response);
            log.log();

            return response;
        }
        catch (Exception e){
            String response = String.format("{\"status\": 500, \"message\": \"Internal server error\", \"error\": [%s]}", e.getMessage());

            return response;
        }
    }

    @WebMethod
    public String listSubscription(
        @WebParam(name = "creator_id", targetNamespace = "http://services.epify.java.main.src.webservice/") Integer creator_id,
        @WebParam(name = "subscriber_id", targetNamespace = "http://services.epify.java.main.src.webservice/") Integer subscriber_id){

        System.out.println("-----------------------");
        System.out.println("listSubscription");

        MessageContext mc = context.getMessageContext();
        HttpExchange exchange = (HttpExchange)mc.get("com.sun.xml.ws.http.exchange");

        String ip = String.format("%s", exchange.getRemoteAddress());
        String endpoint = String.format("%s", exchange.getRequestURI());

        String apiKey = exchange.getRequestHeaders().getFirst("apiKey"); 
        String clientType = exchange.getRequestHeaders().getFirst("clientType"); 

        System.out.println("ip: " + ip);
        System.out.println("endpoint: " + endpoint);

        try{       
            // need api key validation
            Status status = new Status(apiKey, clientType);
            String response = status.checkClient();
            if (response.equals("{\"status\": 401, \"message\": \"Client is not valid, check api key\"}")){
                return response;
            }
             
            Subscription subscription = new Subscription(creator_id, subscriber_id);
            response = subscription.listSubscription();

            Logger log = new Logger(ip, endpoint, response);
            log.log();

            return response;
        }
        catch (Exception e){
            String response = String.format("{\"status\": 500, \"message\": \"Internal server error\", \"error\": [%s]}", e.getMessage());

            return response;
        }
    }
}
