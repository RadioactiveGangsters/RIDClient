/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Operations;

import java.io.IOException;
import java.net.*;
import java.io.*;


/**
 *
 * @author Mitchell
 */
public class Connection {
    
    Socket connection;
    DataOutputStream output;
    BufferedReader input;
    
    
    /** Initialize the connection and streams*/
    public void Connect(final String ip, final int port){
        try 
        {
            
            connection = new Socket(ip, port);
            output = new DataOutputStream(connection.getOutputStream());
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            
            
        } 
        catch (UnknownHostException ex) 
        {
            System.out.println(ex.getMessage());
        } 
        catch (IOException ex) 
        {
            System.out.println(ex.getMessage());
        } 
 	finally

 	{
            if(connection!=null)

                    try
                    {
                            connection.close();
                    }

                    catch (IOException e)
                    {
                            System.out.println("close:"+e.getMessage());
                    }

 	}
    
    }
    
    public int getData()
    {
        int data = 0;

        try 
        {      
            data = input.read();
        } 
        catch (IOException ex) 
        {   
            System.out.println(ex.getMessage());
        }
        return data;
    } 
    
    
}
