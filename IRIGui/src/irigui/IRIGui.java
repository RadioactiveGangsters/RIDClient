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
    public static String ConnectionType = "Connection";
    public static String NodataType = "NoData";
    public static void main(String[] args) {
        MainScreen.getInstance();
        DataHandler.getInstance();
    }
}
