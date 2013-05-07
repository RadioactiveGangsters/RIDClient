/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package irigui;

import Operations.PhotoResize;
import java.awt.Font;
import java.awt.GridLayout;
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
        
        try{
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
            
        }catch(IOException e){
            System.out.println("Fout in het laden!");
            e.printStackTrace();
        }
        
        JFrame MainFrame = new JFrame("Reactor Controller");
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xSize = ((int) tk.getScreenSize().getWidth());
        int ySize = ((int) tk.getScreenSize().getHeight());
        MainFrame.setSize(xSize, ySize);
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel graphpanel;
        JPanel valuepanel;
        JSplitPane splitgraphvalues;

        JPanel temperaturepanel = new JPanel(new GridLayout(1, 2));
        JLabel temptitlelabel = new JLabel("Temperatuur");
        graphpanel = new JPanel();
        valuepanel = new JPanel();
        splitgraphvalues = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitgraphvalues.setBottomComponent(graphpanel);
        splitgraphvalues.setTopComponent(valuepanel);
        splitgraphvalues.setDividerLocation(MainFrame.getHeight() / 2);
        temptitlelabel.setFont(titlefont);
        splitgraphvalues.setEnabled(false);
        valuepanel.add(temptitlelabel);
        temperaturepanel.add(splitgraphvalues);

        JPanel velocitypanel = new JPanel(new GridLayout(1, 2));
        JLabel veloctitlelabel = new JLabel("Snelheid");
        graphpanel = new JPanel();
        valuepanel = new JPanel();
        splitgraphvalues = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitgraphvalues.setBottomComponent(graphpanel);
        splitgraphvalues.setTopComponent(valuepanel);
        splitgraphvalues.setDividerLocation(MainFrame.getHeight() / 2);
        splitgraphvalues.setEnabled(false);
        veloctitlelabel.setFont(titlefont);
        valuepanel.add(veloctitlelabel);
        velocitypanel.add(splitgraphvalues);

        JPanel humiditypanel = new JPanel(new GridLayout(1, 2));
        JLabel humtitlelabel = new JLabel("Vochtigheid");
        graphpanel = new JPanel();
        valuepanel = new JPanel();
        splitgraphvalues = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitgraphvalues.setBottomComponent(graphpanel);
        splitgraphvalues.setTopComponent(valuepanel);
        splitgraphvalues.setDividerLocation(MainFrame.getHeight() / 2);
        splitgraphvalues.setEnabled(false);
        humtitlelabel.setFont(titlefont);
        valuepanel.add(humtitlelabel);
        humiditypanel.add(splitgraphvalues);

        JPanel fullemptypanel = new JPanel(new GridLayout(1, 2));
        JLabel infratitlelabel = new JLabel("Vol/Leeg");
        graphpanel = new JPanel();
        valuepanel = new JPanel();
        splitgraphvalues = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitgraphvalues.setBottomComponent(graphpanel);
        splitgraphvalues.setTopComponent(valuepanel);
        splitgraphvalues.setDividerLocation(MainFrame.getHeight() / 2);
        infratitlelabel.setFont(titlefont);
        splitgraphvalues.setEnabled(false);
        valuepanel.add(infratitlelabel);
        fullemptypanel.add(splitgraphvalues);

        JPanel levelpanel = new JPanel(new GridLayout(1, 2));
        JLabel leveltitlelabel = new JLabel("Niveau");
        graphpanel = new JPanel();
        valuepanel = new JPanel();
        splitgraphvalues = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitgraphvalues.setBottomComponent(graphpanel);
        splitgraphvalues.setTopComponent(valuepanel);
        splitgraphvalues.setDividerLocation(MainFrame.getHeight() / 2);
        splitgraphvalues.setEnabled(false);
        leveltitlelabel.setFont(titlefont);
        valuepanel.add(leveltitlelabel);
        levelpanel.add(splitgraphvalues);



        tabbedPane.addTab("Temperatuur", tempicon, temperaturepanel);
        tabbedPane.addTab("Snelheid",velocityicon, velocitypanel);//TODO wat moet er ultrasoon gemeten worden?
        tabbedPane.addTab("Vochtigheid",humidicon, humiditypanel);
        tabbedPane.addTab("Vol/leeg",fullemptyicon,fullemptypanel);//TODO wat moet er infrarood gemeten worden
        tabbedPane.addTab("Niveau",niveauicon,levelpanel);

        MainFrame.add(tabbedPane);
        MainFrame.setVisible(true);
    }

    public void ShowWarning(String text, float value, String countermeasurement) {
        WarningFrame warningframe = new WarningFrame(text, value, countermeasurement);
    }
}
