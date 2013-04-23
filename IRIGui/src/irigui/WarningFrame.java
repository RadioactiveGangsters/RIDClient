/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package irigui;

import java.awt.Color;
import javax.swing.*;

/**
 *
 * @author user
 */
public class WarningFrame implements Runnable{
   JFrame WarningFrame;
   JPanel WarningPanel;
    public WarningFrame(String text, float value){
        WarningFrame = new JFrame();
        WarningPanel = new JPanel();
        JLabel WarningText = new JLabel();
        JButton OkButton = new JButton("Take Action!");
        
        
        WarningText.setText(text);
        
        WarningPanel.add(WarningText);
        WarningPanel.add(OkButton);
        WarningFrame.add(WarningPanel);
        WarningFrame.setSize(500,300);
        WarningFrame.setLocationRelativeTo(null);
        Thread flasher = new Thread(this);
        flasher.start();
        WarningFrame.setVisible(true);
    }

    @Override
    public void run() {
        
        while(true){
            WarningPanel.setBackground(Color.red);
            try{
                Thread.sleep(500);
            }catch (Exception e){
                System.out.println("SLEEP FAIL!");
            }
            WarningPanel.setBackground(Color.YELLOW);
            try{
                Thread.sleep(500);
            }catch (Exception e){
                System.out.println("SLEEP FAIL!");
            }
        }
    }
    
}
