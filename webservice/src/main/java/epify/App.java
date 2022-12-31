package webservice.src.main.java.epify;

import javax.xml.ws.Endpoint;
import webservice.src.main.java.epify.services.*;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class App 
{
    public static void main( String[] args )
    {
        // webservice running
        System.out.println( "Epify Web Service is running" );
        
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 8080);
            String ip = socket.getLocalAddress().getHostAddress();
            System.out.println("IP Address: " + ip);

            Endpoint.publish("http://" + ip + ":8080/webservice/services", new WebServices());
        }catch(Exception e){
            
            e.printStackTrace();
        }
    }
}
