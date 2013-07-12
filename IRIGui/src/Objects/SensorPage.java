/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

import Operations.Connection;
import Operations.RequestHandler;
import irigui.MainScreen;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.jfree.ui.action.ActionMenuItem;

/**
 *
 * @author user
 */
public class SensorPage implements MouseListener, ActionListener {

    private final Font titlefont = new Font("SansSerif", Font.BOLD, 48);
    private final int amntofsensors;
    private final JPanel mainpanel;
    private final String one = "1";//ROW key
    private final String type1 = "Type 1";//Column key
    private JPanel graphpanel;
    private JPanel valuepanel;
    private final JLabel title;
    private final String titletext;
    JFrame minmaxframe;
    private JScrollPane scroller;
    private final JSplitPane splitmids;
    JTextField min;
    JTextField max;
    HashMap<Integer, JLabel> sensors;
    JLabel selectedlabel;
    String selectedlabelsubstr;
    MainScreen mainscreen;
    XYSeries series1;
    String type;
    JPopupMenu rmbmenu;
    JMenuItem AdjustSettings;

    public SensorPage(String titletext, int amntofsensors, MainScreen mainscreen, String type) {
        this.amntofsensors = amntofsensors;
        this.titletext = titletext;
        this.mainscreen = mainscreen;
        this.type = type;
        sensors = new HashMap<>();
        //The line below must stay commented until we can safely connect to the server, we need to be sure to set the data in the initial setup!
        mainpanel = new JPanel(new BorderLayout());
        title = new JLabel(titletext);
        scroller = new JScrollPane();
        graphpanel = new JPanel();
        series1 = new XYSeries(type);
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
        AdjustSettings = new ActionMenuItem("Configureren");
        rmbmenu = new JPopupMenu();
        AdjustSettings.addActionListener(this);
        rmbmenu.add(AdjustSettings);

        //Loop to add the senors
        int x = 0;
        JLabel sensorlabel;
        while (x != amntofsensors) {
            sensorlabel = new JLabel(titletext + " sensor " + (x + 1) + ": ");
            sensorlabel.setComponentPopupMenu(rmbmenu);
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

    public void updateSensorValues(int key, int value) {
        JLabel tempLabel = (JLabel) sensors.get(key);
        sensors.remove(key);
        String tempText = tempLabel.getText();
        tempText = tempText.substring(0, (tempText.lastIndexOf(": ") + 1));
        String valuestring = Integer.toString(value);
        tempText = tempText + " " + valuestring;
        tempLabel.setText(tempText);
        sensors.put(key, tempLabel);
    }

    public void refreshSensorLabels() {
        int x = 0;
        valuepanel.removeAll();
        while (x != amntofsensors) {
            valuepanel.add((JLabel) sensors.get(x));
            x++;
        }
        //mainpanel.setVisible(true);
    }

    private void makeGraph(final XYDataset dataset) {
        final JFreeChart chart = ChartFactory.createXYLineChart(
                selectedlabelsubstr, // chart title
                "Tijd", // x axis label
                "Waarde", // y axis label
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
        mainpanel.setVisible(true);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Just handling a border, for the user to recognize that the JLabel is in fact clicked
        if (selectedlabel != null) {
            selectedlabel.setBorder(null);
        }
        selectedlabel = (JLabel) e.getSource();
        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK);
        selectedlabel.setBorder(lineBorder);
        if (SwingUtilities.isRightMouseButton(e)) {
        } else {
            //The Real WORK!!
            String full = selectedlabel.getText();
            selectedlabelsubstr = full.substring(0, full.lastIndexOf(":"));
            String sensor = full.substring(full.lastIndexOf("r") + 2, full.lastIndexOf(": "));
            int sensornr = Integer.parseInt(sensor);
            series1 = new XYSeries(selectedlabelsubstr);
            RequestHandler.getInstance().requestGraphData(full);
            //Some command to req data from the server
        }
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

    public HashMap getSensors() {
        return sensors;
    }

    public void addValuesToGraph(int key, int[] values) {
        int index = 0;
        while (index != (values.length)) {
            series1.add(index, values[index]);
            System.out.println(values[index]);
            index++;
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        makeGraph(dataset);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(AdjustSettings)) {
            minmaxframe = new JFrame("Instellen Min/Max waardes");
            minmaxframe.setLayout(new BorderLayout());
            minmaxframe.setLocationRelativeTo(null);
            minmaxframe.setAlwaysOnTop(true);
            minmaxframe.setResizable(false);
            minmaxframe.setSize(200, 100);
            min = new JTextField("Minimale waarde");
            max = new JTextField("Maximale waarde");
            JButton apply = new JButton("Toepassen");
            apply.addActionListener(this);

            minmaxframe.add(min, BorderLayout.PAGE_START);
            minmaxframe.add(max, BorderLayout.CENTER);
            minmaxframe.add(apply, BorderLayout.PAGE_END);
            minmaxframe.setVisible(true);
        } else {
            minmaxframe.dispose();
            String minText = min.getText();
            String maxText = max.getText();

            String text = selectedlabel.getText();
            if (isInteger(minText) && isInteger(maxText)) {
                String full = selectedlabel.getText();
                String part1 = full.substring(0, full.lastIndexOf(" sensor"));
                String part2 = full.substring(full.lastIndexOf("r ") + 2, full.lastIndexOf(":"));
                full = part1 + part2;
                try {
                    Connection.getInstance().sendRequest(6, full, minText, maxText);
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
