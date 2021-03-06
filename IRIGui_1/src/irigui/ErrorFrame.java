/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package irigui;

import Operations.Connection;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author user
 */
public class ErrorFrame implements ActionListener {

    JDialog ErrorFrame;
    JPanel ErrorPanel;
    JButton CounterButton;
    String type;
    Font titlefont = new Font("SansSerif", Font.BOLD, 12);
    static boolean erroroccured = false;

    public ErrorFrame(String text, String countermeasurement, String type) {
        if (!erroroccured) {
            erroroccured = true;
            this.type = type;
            ErrorFrame = new JDialog();
            ErrorPanel = new JPanel();
            JLabel WarningText = new JLabel();
            CounterButton = new JButton(countermeasurement);
            CounterButton.addActionListener(this);
            erroroccured = true;
            WarningText.setFont(titlefont);
            WarningText.setHorizontalTextPosition(JLabel.CENTER);
            WarningText.setVerticalTextPosition(JLabel.CENTER);
            WarningText.setText(text);
            ErrorPanel.add(WarningText);
            ErrorPanel.add(CounterButton);
            ErrorFrame.add(ErrorPanel);
            ErrorFrame.setSize(300, 75);
            ErrorFrame.setLocationRelativeTo(null);
            ErrorFrame.setResizable(false);
            ErrorFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            ErrorFrame.setAlwaysOnTop(true);
            ErrorFrame.setVisible(true);
            ErrorFrame.pack();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (type.equals("Connection")) {
            Connection connection = Connection.getInstance();
            if(connection.Connect(Connection.getInstance().ip, Connection.getInstance().port)){
                erroroccured = false;
            }else{
                erroroccured = true;
            }
            //Still Commented
        } else if (type.equals("NoDataType")) {
        }
        ErrorFrame.dispose();

    }
}
