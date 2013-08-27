/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Operations;

/**
 *
 * @author user
 */
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestHandler implements Runnable {

    final static int REQUEST_ALL_SENSORS = 3;
    final static int REQUEST_GRAPH_DATA = 4;
    static RequestHandler instance = null;

    private RequestHandler() {
    }

    public static RequestHandler getInstance() {
        if (instance == null) {
            instance = new RequestHandler();
        }
        return instance;
    }
    
    public void requestGraphData(String sensortypename, int sensorNr){
         if(Connection.getInstance().isConnected()) {
            try {
                Connection.getInstance().sendRequest(REQUEST_GRAPH_DATA, sensortypename, null, null, sensorNr);
            } catch (IOException ex) {
                Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
    }
    
    @Override
    public void run() {
        while (Connection.getInstance().isConnected()) {
            try {
                Connection.getInstance().sendRequest(REQUEST_ALL_SENSORS, null, null, null, 0);
            } catch (IOException ex) {
                Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
