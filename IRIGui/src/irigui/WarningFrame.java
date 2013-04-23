/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package irigui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author user
 */
public class WarningFrame implements Runnable, ActionListener{
   JFrame WarningFrame;
   JPanel WarningPanel;
   JButton OkButton;
    public WarningFrame(String text, float value){
        WarningFrame = new JFrame();
        WarningPanel = new JPanel(new BorderLayout());
        JLabel WarningText = new JLabel();
        OkButton = new JButton("Take Action!");
        
        
        WarningText.setText(text);
        
        WarningPanel.add(WarningText, BorderLayout.CENTER);
        WarningPanel.add(OkButton, BorderLayout.PAGE_END);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(OkButton)){
            WarningFrame.dispose();
        }
    }
    
    
    
}
