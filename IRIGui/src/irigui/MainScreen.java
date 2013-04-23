/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package irigui;

import java.awt.Toolkit;
import javax.swing.*;

/**
 *
 * @author user
 */
public class MainScreen {

    public MainScreen() {
        JFrame MainFrame = new JFrame("Reactor Controller");
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xSize = ((int) tk.getScreenSize().getWidth());
        int ySize = ((int) tk.getScreenSize().getHeight());
        MainFrame.setSize(xSize, ySize);
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        MainFrame.setVisible(true);
    }
    public void ShowWarning(String text, float value){
        WarningFrame warningframe = new WarningFrame(text, value);
    }
}
