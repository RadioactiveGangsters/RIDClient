/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package irigui;

import Operations.DataHandler;

/**
 *
 * @author user
 */
public class IRIGui {

    /**
     * @param args the command line arguments
     */
    static String ConnectionType = "Connection";
    static String Nodata = "NoData";
    public static void main(String[] args) {
        MainScreen mainscreen = new MainScreen();
        DataHandler.getInstance();

    }
}
