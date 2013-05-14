/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Operations;

import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mitchell
 */
public class Connection {
    
    Socket connection;
    DataOutputStream output;
    DataInputStream input;
    
    public void Connect(final String ip, final int port){
        try {
            connection = new Socket(ip, port);
            output = new DataOutputStream(connection.getOutputStream());
            input = new DataInputStream(connection.getInputStream());
            
            
        } catch (UnknownHostException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
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
    
    
    
}
