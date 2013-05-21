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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.Series;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author user
 */
public class SensorPage implements MouseListener{

    private final Font titlefont = new Font("SansSerif", Font.BOLD, 48);
    private final int amntofsensors;
    private final JPanel mainpanel;
    private final String one = "1";//ROW key
    private final String type1 = "Type 1";//Column key
    private JPanel graphpanel;
    private JPanel valuepanel;
    private final JLabel title;
    private final String titletext;
    private JScrollPane scroller;
    private final JSplitPane splitmids;
    HashMap<Integer, JLabel> sensors;
    JLabel selectedlabel;
    String selectedlabelsubstr;
    MainScreen mainscreen;
    XYSeries series1;

    public SensorPage(String titletext, int amntofsensors, MainScreen mainscreen) {
        this.amntofsensors = amntofsensors;
        this.titletext = titletext;
        this.mainscreen = mainscreen;
        sensors = new HashMap<>();
        //The line below must stay commented until we can safely connect to the server, we need to be sure to set the data in the initial setup!
        mainpanel = new JPanel(new BorderLayout());
        title = new JLabel(titletext);
        scroller = new JScrollPane();
        graphpanel = new JPanel();
        graphpanel.add(new JLabel("Klik op een sensor om een grafiek van die sensor te bekijken"));
        valuepanel = new JPanel(new GridLayout(30, 3));
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroller.getHorizontalScrollBar().setUnitIncrement(16);
        scroller.getVerticalScrollBar().setUnitIncrement(16);
        scroller.setViewportView(graphpanel);
        splitmids = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitmids.setBottomComponent(scroller);
        splitmids.setTopComponent(valuepanel);
        splitmids.setDividerLocation(mainscreen.getFrameHeight() / 2);
        title.setFont(titlefont);
        splitmids.setEnabled(false);


        //Loop to add the senors
        int x = 0;
        JLabel sensorlabel;
        while (x != amntofsensors) {
            sensorlabel = new JLabel(titletext + " sensor " + (x + 1) + ": ");
            sensorlabel.addMouseListener(this);
            valuepanel.add(sensorlabel);
            sensors.put(x, sensorlabel);
            x++;
        }
        mainpanel.add(title, BorderLayout.PAGE_START);
        mainpanel.add(splitmids, BorderLayout.CENTER);
    }

    public JPanel getPanel() {
        return mainpanel;
    }
    
    public void updateSensorValues(int key, int value){
        JLabel tempLabel = (JLabel)sensors.get(key);
        sensors.remove(key);
        String tempText = tempLabel.getText();
        tempText = tempText.substring(0, tempText.lastIndexOf(": "));
        String valuestring = Integer.toString(value);
        tempText = tempText + valuestring;
        tempLabel.setText(tempText);
        sensors.put(key, tempLabel);
    }

    private void makeGraph(final XYDataset dataset) {
        final JFreeChart chart = ChartFactory.createXYLineChart(
                selectedlabelsubstr, // chart title
                "Tijd", // x axis label
                "Value", // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
                );

        chart.setBackgroundPaint(Color.white);

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        ChartPanel chartpanel = new ChartPanel(chart);
        graphpanel.removeAll();
        graphpanel.add(chartpanel);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        //Just handling a border, for the user to recognize that the JLabel is in fact clicked
        if (selectedlabel != null) {
            selectedlabel.setBorder(null);
        }
        selectedlabel = (JLabel) e.getSource();
        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK);
        selectedlabel.setBorder(lineBorder);
        
        //The Real WORK!!
        String full = selectedlabel.getText();
        selectedlabelsubstr = full.substring(0, full.lastIndexOf(":"));
        series1 = new XYSeries(selectedlabelsubstr);
        //Some command to req data from the server
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
    
    public HashMap getSensors(){
        return sensors;
    }
    
    public void addValuesToGraph(int key, int []values){
        int index = 0;
        while(index != values.length){
            series1.add(index, values[index]);
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        makeGraph(dataset);
    }
    
}
