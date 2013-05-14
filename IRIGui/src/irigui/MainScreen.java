/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package irigui;

import Objects.SensorPage;
import Operations.PhotoResize;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author user
 */
public class MainScreen {

    Font titlefont = new Font("SansSerif", Font.BOLD, 48);
    JFrame MainFrame;

    public MainScreen() {
        try {
            // Set set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("System Look and feel not supported");
        } catch (ClassNotFoundException e) {
            System.err.println("No such Look and Feel");
        } catch (InstantiationException e) {
            System.err.println("Nu instantiation of system look and feel");
        } catch (IllegalAccessException e) {
            System.err.println("Illegal acces to Look and Feel");
        }

        ImageIcon tempicon = null;
        ImageIcon niveauicon = null;
        ImageIcon humidicon = null;
        ImageIcon velocityicon = null;
        ImageIcon fullemptyicon = null;
        BufferedImage imagetoresize;
        PhotoResize photoresizer = new PhotoResize();

        try {
            imagetoresize = ImageIO.read(new File("resources//hotcold.jpg"));
            tempicon = new ImageIcon(photoresizer.resizeImage(imagetoresize, 10, 10));
            imagetoresize = ImageIO.read(new File("resources//level.png"));
            niveauicon = new ImageIcon(photoresizer.resizeImage(imagetoresize, 10, 10));
            imagetoresize = ImageIO.read(new File("resources//humidity.jpg"));
            humidicon = new ImageIcon(photoresizer.resizeImage(imagetoresize, 10, 10));
            imagetoresize = ImageIO.read(new File("resources//velocity.png"));
            velocityicon = new ImageIcon(photoresizer.resizeImage(imagetoresize, 10, 10));
            imagetoresize = ImageIO.read(new File("resources//full.empty.png"));
            fullemptyicon = new ImageIcon(photoresizer.resizeImage(imagetoresize, 10, 10));

        } catch (IOException e) {
            System.out.println("Fout in het laden!");
            e.printStackTrace();
        }

        MainFrame = new JFrame("Reactor Controller");
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xSize = ((int) tk.getScreenSize().getWidth());
        int ySize = ((int) tk.getScreenSize().getHeight());
        MainFrame.setResizable(false);
        MainFrame.setSize(xSize, ySize);
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        
        SensorPage tempsensorpage = new SensorPage("Temperatuur",90, this);
        SensorPage velocitypage = new SensorPage("Snelheid", 110, this);
        SensorPage humiditypage = new SensorPage("Vochtigheid", 110,this);
        SensorPage fullemptypage = new SensorPage("Vol/Leeg",80,this);
        SensorPage levelpage = new SensorPage("Niveau",120,this);

        tabbedPane.addTab("Temperatuur", tempicon, tempsensorpage.getPanel());
        tabbedPane.addTab("Snelheid", velocityicon, velocitypage.getPanel());//TODO wat moet er ultrasoon gemeten worden?
        tabbedPane.addTab("Vochtigheid", humidicon, humiditypage.getPanel());
        tabbedPane.addTab("Vol/leeg", fullemptyicon, fullemptypage.getPanel());//TODO wat moet er infrarood gemeten worden
        tabbedPane.addTab("Niveau", niveauicon, levelpage.getPanel());

        MainFrame.add(tabbedPane);
        MainFrame.setVisible(true);
    }

    public void ShowWarning(String text, float value, int sensorid, String countermeasurement) {
        WarningFrame warningframe = new WarningFrame(text, value, sensorid, countermeasurement);
    }
    
    public int getFrameHeight(){
        return MainFrame.getHeight();
    }
}
