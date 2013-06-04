/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Operations;

import irigui.ErrorFrame;
import irigui.IRIGui;
import irigui.MainScreen;
import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mitchell
 */
public class Connection implements Runnable {

    Socket connection;
    DataOutputStream output;
    BufferedReader input;
    private static Connection instance = null;

    /**
     * Initialize the connection and streams This class is now Singleton, since
     * we needed to be able to connect from different other classes. Also there
     * should only exist one instance of this class at any time
     */
    private Connection() {
    }

    public static Connection getInstance() {
        if (instance == null) {
            instance = new Connection();
        }
        return instance;
    }

    public void Connect(final String ip, final int port) {
        try {

            connection = new Socket(ip, port);
            output = new DataOutputStream(connection.getOutputStream());
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));


        } catch (UnknownHostException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public boolean isConnected() {
        if (connection != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void run() {
        int[] inputarray = null;
        int allsensorreq = 0;
        char opcode = '0';
        while (connection.isConnected()) {
            try {
                opcode = (char) input.read();
            } catch (IOException ex) {
                ErrorFrame erfframe = new ErrorFrame("De verbinding is verbroken voordat we de opcode van het packet konden lezen", "Herverbinden", IRIGui.ConnectionType);
            }
            int opcodenr = Character.getNumericValue(opcode);
            char sensortype = '0';
            try {
                sensortype = (char) input.read();
            } catch (IOException ex) {
                ErrorFrame erfframe = new ErrorFrame("De verbinding is verbroken voordat we de sensortype van het packet konden lezen", "Herverbinden", IRIGui.ConnectionType);
            }
            int sensortypenr = Character.getNumericValue(sensortype);
            switch (opcodenr) {
                case 0:
                    System.out.println("Not defined yet, see Berend for details, Opcode = 0");
                    break;
                case 1:
                    System.out.println("Not defined yet, see Berend for details, Opcode = 1");
                    break;
                case 2:
                    System.out.println("Not defined yet, see Berend for details, Opcode = 2");
                    break;
                case 3:
                    System.out.println("Updating sensors, hold on!");
                    int amntofsensors = 0;
                    try {
                        do {
                            amntofsensors = input.read();
                        } while (amntofsensors == 0);
                    } catch (IOException ex) {
                        ErrorFrame erfframe = new ErrorFrame("De verbinding is verbroken voordat we het aantal sensoren van het packet konden lezen", "Herverbinden", IRIGui.ConnectionType);
                    }
                    System.out.println("THIS BIG: " + amntofsensors);
                    int arrayspace = amntofsensors + 3;
                    inputarray = new int[arrayspace];
                    int index = 0;
                    inputarray[index] = opcodenr;
                    index++;
                    inputarray[index] = sensortypenr;
                    index++;
                    inputarray[index] = amntofsensors;

                    int temp;
                    while (index <= amntofsensors) {
                        try {
                            do {
                                temp = input.read();
                            } while (temp == 0);
                            inputarray[index] = temp;
                        } catch (IOException ex) {
                            ErrorFrame erfframe = new ErrorFrame("De verbinding is verbroken voordat we een waarde van het packet konden lezen", "Herverbinden", IRIGui.ConnectionType);
                        }

                        index++;
                    }
                    break;
                case 4:
                    int amntofvalues = 0;
                    try {
                        amntofvalues =  input.read();
                    } catch (IOException ex) {
                        ErrorFrame erfframe = new ErrorFrame("De verbinding is verbroken voordat we het aantal sensoren van het packet konden lezen", "Herverbinden", IRIGui.ConnectionType);
                    }
                    arrayspace = amntofvalues + 3;
                    inputarray = new int[arrayspace];
                    index = 0;
                    inputarray[index] = opcodenr;
                    index++;
                    inputarray[index] = sensortypenr;
                    index++;
                    inputarray[index] = amntofvalues;

                    while (index <= amntofvalues) {
                        try {
                            temp = input.read();
                            inputarray[index] = temp;
                        } catch (IOException ex) {
                            ErrorFrame erfframe = new ErrorFrame("De verbinding is verbroken voordat we een waarde van het packet konden lezen", "Herverbinden", IRIGui.ConnectionType);
                        }

                        index++;
                    }
                    System.out.println("Building graphs...");
                    break;
                case 5:
                    System.out.println("Alarm!!");
                    int value = 1;
                    try {
                        do {
                            value = input.read();
                        } while (value == 0);
                    } catch (IOException ex) {
                        Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    int counteractiontype = -1;
                    try {
                        do {
                            counteractiontype = input.read();
                        } while (counteractiontype == 0);
                    } catch (IOException ex) {
                        Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    int sensornr = 0;
                    try {
                        do {
                            sensornr = input.read();
                        } while (sensornr == 0);
                    } catch (IOException ex) {
                        Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    inputarray = new int[6];
                    index = 0;
                    inputarray[index] = opcodenr;
                    index++;
                    inputarray[index] = sensortypenr;
                    index++;
                    inputarray[index] = value;
                    index++;
                    inputarray[index] = counteractiontype;
                    index++;
                    inputarray[index] = sensornr;
                    break;
            }
            DataHandler.getInstance().handleData(inputarray);


            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}