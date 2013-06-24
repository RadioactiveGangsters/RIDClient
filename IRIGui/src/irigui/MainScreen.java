/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package irigui;

import Objects.SensorPage;
import Operations.Connection;
import Operations.PhotoResize;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author user
 */
public class MainScreen {

    Font titlefont = new Font("SansSerif", Font.BOLD, 48);
    JFrame MainFrame;
    Connection connection;
    SensorPage tempsensorpage;
    SensorPage velocitypage;
    SensorPage humiditypage;
    SensorPage fullemptypage;
    SensorPage levelpage;
    private static MainScreen instance = null;
    

    private MainScreen() {
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
        ConnectToServer();
        MainFrame = new JFrame("Reactor Controller");
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xSize = ((int) tk.getScreenSize().getWidth());
        int ySize = ((int) tk.getScreenSize().getHeight());
        MainFrame.setResizable(false);
        MainFrame.setSize(xSize, ySize);
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        tempsensorpage = new SensorPage("Temperatuur", 90, this, "Temperature");
        velocitypage = new SensorPage("Snelheid", 110, this, "Velocity");
        humiditypage = new SensorPage("Vochtigheid", 110, this, "Humidity");
        fullemptypage = new SensorPage("Vol/Leeg", 80, this, "Fullness");
        levelpage = new SensorPage("Niveau", 120, this, "Level");

        tabbedPane.addTab("Temperatuur", tempicon, tempsensorpage.getPanel());
        tabbedPane.addTab("Snelheid", velocityicon, velocitypage.getPanel());
        tabbedPane.addTab("Vochtigheid", humidicon, humiditypage.getPanel());
        tabbedPane.addTab("Vol/leeg", fullemptyicon, fullemptypage.getPanel());
        tabbedPane.addTab("Niveau", niveauicon, levelpage.getPanel());

        MainFrame.add(tabbedPane);
        MainFrame.setVisible(true);
    }
   
    public static MainScreen getInstance(){
        if(instance == null){
            instance = new MainScreen();
        }
        return instance;
    }
    
    public void refreshSensors(int typeofsensor){
        if(typeofsensor == 0){
            tempsensorpage.refreshSensorLabels();
        }else if(typeofsensor == 1){
            velocitypage.refreshSensorLabels();
        }else if(typeofsensor == 2){
            humiditypage.refreshSensorLabels();
        }else if(typeofsensor == 3){
            fullemptypage.refreshSensorLabels();
        }else if(typeofsensor == 4){
            levelpage.refreshSensorLabels();
        }
    }
    public void updateAllSensors(int typeofsensor, int key, int value){
        if(typeofsensor == 0){
            tempsensorpage.updateSensorValues(key, value);
        }else if(typeofsensor == 1){
            velocitypage.updateSensorValues(key, value);
        }else if(typeofsensor == 2){
            humiditypage.updateSensorValues(key, value);
        }else if(typeofsensor == 3){
            fullemptypage.updateSensorValues(key, value);
        }else if(typeofsensor == 4){
            levelpage.updateSensorValues(key, value);
        }
    }
    
    public void ConnectToServer() {
        connection = Connection.getInstance();
        //connection.Connect(127.0.0.1, 1);
        //Let's keep the last line commented until we are sure the connection works
    }
    
    public boolean isConnected(){
        return connection.isConnected();
    }

    public int getFrameHeight() {
        return MainFrame.getHeight();
    }
    public void addValuesToGraph(int typeofsensor, int key, int[] values){
        if(typeofsensor == 1){
            tempsensorpage.addValuesToGraph(key, values);
        }else if(typeofsensor == 2){
            velocitypage.addValuesToGraph(key, values);
        }else if(typeofsensor == 3){
            humiditypage.addValuesToGraph(key, values);
        }else if(typeofsensor == 4){
            fullemptypage.addValuesToGraph(key, values);
        }else if(typeofsensor == 5){
            levelpage.addValuesToGraph(key, values);
        }
    }
}
