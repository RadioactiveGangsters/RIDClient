/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package irigui;

import Operations.Connection;
import Operations.RequestHandler;
import Operations.tabbedPaneThreader;



/**
 *
 * @author user
 */
public class IRIGui {

    /**
     * @param args the command line arguments
     */
    public static String ConnectionType = "Connection";
    public static String NodataType = "NoData";
    public static void main(String[] args) {
        MainScreen.getInstance();
        Connection.getInstance().Connect("192.168.2.10", 61014);
//        tabbedPaneThreader thread = new tabbedPaneThreader();
//        Thread t = new Thread(thread);
//        t.start();
        Thread con = new Thread(Connection.getInstance());
        con.start();
        Thread requester = new Thread(RequestHandler.getInstance());
        requester.start();
    }
}
