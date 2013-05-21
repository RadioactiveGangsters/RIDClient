/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Operations;

import irigui.ErrorFrame;
import irigui.IRIGui;
import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Mitchell
 */
public class Connection implements Runnable{

    Socket connection;
    DataOutputStream output;
    BufferedReader input;
    private static Connection instance = null;

    /**
     * Initialize the connection and streams This class is now Singleton, since
     * we needed to be able to connect from different other classes. Also there
     * should only exist one instance of this class at any time
     */
    private Connection() {
    }

    public static Connection getInstance() {
        if (instance == null) {
            instance = new Connection();
        }
        return instance;
    }

    public void Connect(final String ip, final int port) {
        try {

            connection = new Socket(ip, port);
            output = new DataOutputStream(connection.getOutputStream());
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));


        } catch (UnknownHostException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (final IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
            }

        }

    }
    
//    public int getAllSensors() throws IOException, InterruptedException{
//       
//    }

    public int getData() {
        int data = 0;
        return data;
    }

    public boolean isConnected() {
        if (connection != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void run() {
        int [] dataarray = new int[100];
        int data;
        int allsensorreq = 0;
        int amountofseconds = 0;
        try {
            output.write(allsensorreq);
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while(input.read() == 0 && amountofseconds != 15){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                }
                amountofseconds++;
            }
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(amountofseconds == 15){
            ErrorFrame err = new ErrorFrame("De verbinding is verbroken", "Opnieuw verbinden", IRIGui.ConnectionType);
            data = 0;
        }else{
            try {
                data = input.read();
                DataHandler.getInstance().handleData(dataarray);
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}