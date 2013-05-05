/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package irigui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
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

        JPanel ultrasonicpanel = new JPanel(new GridLayout(1, 2));
        JLabel ultratitlelabel = new JLabel("Ultrasoon");
        graphpanel = new JPanel();
        valuepanel = new JPanel();
        splitgraphvalues = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitgraphvalues.setBottomComponent(graphpanel);
        splitgraphvalues.setTopComponent(valuepanel);
        splitgraphvalues.setDividerLocation(MainFrame.getHeight() / 2);
        splitgraphvalues.setEnabled(false);
        ultratitlelabel.setFont(titlefont);
        valuepanel.add(ultratitlelabel);
        ultrasonicpanel.add(splitgraphvalues);

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

        JPanel infraredpanel = new JPanel(new GridLayout(1, 2));
        JLabel infratitlelabel = new JLabel("Infrarood");
        graphpanel = new JPanel();
        valuepanel = new JPanel();
        splitgraphvalues = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitgraphvalues.setBottomComponent(graphpanel);
        splitgraphvalues.setTopComponent(valuepanel);
        splitgraphvalues.setDividerLocation(MainFrame.getHeight() / 2);
        infratitlelabel.setFont(titlefont);
        splitgraphvalues.setEnabled(false);
        valuepanel.add(infratitlelabel);
        infraredpanel.add(splitgraphvalues);

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



        tabbedPane.add("Temperatuur", temperaturepanel);
        tabbedPane.add("Ultrasoon", ultrasonicpanel);//TODO wat moet er ultrasoon gemeten worden?
        tabbedPane.add("Vochtigheid", humiditypanel);
        tabbedPane.add("Infrarood", infraredpanel);//TODO wat moet er infrarood gemeten worden
        tabbedPane.add("Niveau", levelpanel);

        MainFrame.add(tabbedPane);
        MainFrame.setVisible(true);
    }

    public void ShowWarning(String text, float value, String countermeasurement) {
        WarningFrame warningframe = new WarningFrame(text, value, countermeasurement);
    }
}
