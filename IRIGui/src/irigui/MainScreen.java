/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package irigui;

import Objects.SensorPage;
import Operations.Connection;
import Operations.PhotoResize;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author user
 */
public class MainScreen implements ActionListener {

    Font titlefont = new Font("SansSerif", Font.BOLD, 48);
    JFrame MainFrame;
    Connection connection;
    SensorPage tempsensorpage;
    SensorPage flowpage;
    SensorPage pressurepage;
    SensorPage fullnesspage;
    SensorPage radiationpage;
    private static MainScreen instance = null;
    MenuBar menuBar;
    Menu menu;
    MenuItem menuItem;
    JFrame flowadjustframe;
    JTextField min;
    JTextField max;

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

        ImageIcon TemperatureIcon = null;
        ImageIcon FlowIcon = null;
        ImageIcon PressureIcon = null;
        ImageIcon FullnessIcon = null;
        ImageIcon RadiationIcon = null;
        BufferedImage imagetoresize;
        PhotoResize photoresizer = new PhotoResize();

        try {
            imagetoresize = ImageIO.read(new File("resources//TemperatureIcon.png"));
            TemperatureIcon = new ImageIcon(photoresizer.resizeImage(imagetoresize, 10, 10));
            imagetoresize = ImageIO.read(new File("resources//FlowIcon.png"));
            FlowIcon = new ImageIcon(photoresizer.resizeImage(imagetoresize, 10, 10));
            imagetoresize = ImageIO.read(new File("resources//PressureIcon.png"));
            PressureIcon = new ImageIcon(photoresizer.resizeImage(imagetoresize, 10, 10));
            imagetoresize = ImageIO.read(new File("resources//FullnessIcon.png"));
            FullnessIcon = new ImageIcon(photoresizer.resizeImage(imagetoresize, 10, 10));
            imagetoresize = ImageIO.read(new File("resources//RadiationIcon.png"));
            RadiationIcon = new ImageIcon(photoresizer.resizeImage(imagetoresize, 10, 10));

        } catch (IOException e) {
            System.out.println("Error with loading!");
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

        menuBar = new MenuBar();
        menu = new Menu("Configureren");
        menu.addActionListener(this);
        menuBar.add(menu);
        menuItem = new MenuItem("Flow aanpassen");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        JTabbedPane tabbedPane = new JTabbedPane();

        tempsensorpage = new SensorPage("Temperature", 150, this, "Temperature");
        flowpage = new SensorPage("Flow", 70, this, "Flow");
        pressurepage = new SensorPage("Pressure", 100, this, "Pressure");
        fullnesspage = new SensorPage("Fullness", 30, this, "Fullness");
        radiationpage = new SensorPage("Radiation", 150, this, "Radiation");

        tabbedPane.addTab("Temperature", TemperatureIcon, tempsensorpage.getPanel());
        tabbedPane.addTab("Flow", FlowIcon, flowpage.getPanel());
        tabbedPane.addTab("Pressure", PressureIcon, pressurepage.getPanel());
        tabbedPane.addTab("Fullness", FullnessIcon, fullnesspage.getPanel());
        tabbedPane.addTab("Radiation", RadiationIcon, radiationpage.getPanel());

        MainFrame.setMenuBar(menuBar);
        MainFrame.add(tabbedPane);
        MainFrame.setVisible(true);
    }

    public static MainScreen getInstance() {
        if (instance == null) {
            instance = new MainScreen();
        }
        return instance;
    }

    public void refreshSensors(int typeofsensor) {
        if (typeofsensor == 0) {
            tempsensorpage.refreshSensorLabels();
        } else if (typeofsensor == 1) {
            flowpage.refreshSensorLabels();
        } else if (typeofsensor == 2) {
            pressurepage.refreshSensorLabels();
        } else if (typeofsensor == 3) {
            fullnesspage.refreshSensorLabels();
        } else if (typeofsensor == 4) {
            radiationpage.refreshSensorLabels();
        }
    }

    public void updateAllSensors(int typeofsensor, int key, int value) {
        if (typeofsensor == 0) {
            tempsensorpage.updateSensorValues(key, value);
        } else if (typeofsensor == 1) {
            flowpage.updateSensorValues(key, value);
        } else if (typeofsensor == 2) {
            pressurepage.updateSensorValues(key, value);
        } else if (typeofsensor == 3) {
            fullnesspage.updateSensorValues(key, value);
        } else if (typeofsensor == 4) {
            radiationpage.updateSensorValues(key, value);
        }
    }

    public void ConnectToServer() {
        connection = Connection.getInstance();
        //Let's keep the last line commented until we are sure the connection works
    }

    public boolean isConnected() {
        return connection.isConnected();
    }

    public int getFrameHeight() {
        return MainFrame.getHeight();
    }

    public void addValuesToGraph(int typeofsensor, int key, int[] values) {
        if (typeofsensor == 1) {
            tempsensorpage.addValuesToGraph(key, values);
        } else if (typeofsensor == 2) {
            flowpage.addValuesToGraph(key, values);
        } else if (typeofsensor == 3) {
            pressurepage.addValuesToGraph(key, values);
        } else if (typeofsensor == 4) {
            fullnesspage.addValuesToGraph(key, values);
        } else if (typeofsensor == 5) {
            radiationpage.addValuesToGraph(key, values);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(menuItem)) {
            flowadjustframe = new JFrame("Instellen flow");
            flowadjustframe.setLayout(new BorderLayout());
            flowadjustframe.setLocationRelativeTo(null);
            flowadjustframe.setSize(200, 75);
            min = new JTextField("Flow");
            flowadjustframe.setAlwaysOnTop(true);
            flowadjustframe.setResizable(false);
            JButton apply = new JButton("Toepassen");
            apply.addActionListener(this);

            flowadjustframe.add(min, BorderLayout.PAGE_START);
            flowadjustframe.add(apply, BorderLayout.PAGE_END);
            flowadjustframe.setVisible(true);
        } else {
            flowadjustframe.dispose();
            String minText = min.getText();

            if (isInteger(minText)) {
                try {
                    Connection.getInstance().sendRequest(7, null, minText, null);
                } catch (IOException ex) {
                    Logger.getLogger(SensorPage.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //Fuck this shit
            }
        }
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
