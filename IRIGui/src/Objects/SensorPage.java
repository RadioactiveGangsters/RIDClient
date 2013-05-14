/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import irigui.MainScreen;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.Border;
import org.jfree.chart.*;

/**
 *
 * @author user
 */
public class SensorPage implements MouseListener{
    
    private final Font titlefont = new Font("SansSerif", Font.BOLD, 48);
    private final int amntofsensors;
    private final JPanel mainpanel;
    private JPanel graphpanel;
    private JPanel valuepanel;
    private final JLabel title;
    private final String titletext;
    private JScrollPane scroller;
    private final JSplitPane splitmids;
    HashMap<Integer, JLabel> sensors;
    JLabel selectedlabel;
    
    public SensorPage(String titletext, int amntofsensors, MainScreen mainscreen){
        this.amntofsensors = amntofsensors;
        this.titletext = titletext;
        sensors = new HashMap<>();
        mainpanel = new JPanel(new BorderLayout());
        title = new JLabel(titletext);
        scroller = new JScrollPane();
        graphpanel = new JPanel();
        valuepanel = new JPanel(new GridLayout(30, 3));
        graphpanel.add(new JLabel("test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test test "));
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroller.getHorizontalScrollBar().setUnitIncrement(16);
        scroller.setViewportView(graphpanel);
        splitmids = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitmids.setBottomComponent(scroller);
        splitmids.setTopComponent(valuepanel);
        splitmids.setDividerLocation(mainscreen.getFrameHeight()/ 2);
        title.setFont(titlefont);
        splitmids.setEnabled(false);
        
        
        //Loop to add the senors
        int x = 0;
        JLabel sensorlabel;
        while(x!=amntofsensors){
            sensorlabel = new JLabel(titletext +" sensor " + (x+1) + ": ");
            sensorlabel.addMouseListener(this);
            valuepanel.add(sensorlabel);
            sensors.put(x, sensorlabel);
            x++;
        }
        mainpanel.add(title, BorderLayout.PAGE_START);
        mainpanel.add(splitmids, BorderLayout.CENTER);
        
        
    }
    public JPanel getPanel(){
        return mainpanel;
    }
    
//    public void createChart(dataset){
//        
//    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(selectedlabel != null){
            selectedlabel.setBorder(null);
        }
        selectedlabel = (JLabel)e.getSource();
        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK);
        selectedlabel.setBorder(lineBorder);
        System.out.println("This Label is selected: " + selectedlabel.getText());
    }

    @Override
    public void mousePressed(MouseEvent e) {
       
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
}
