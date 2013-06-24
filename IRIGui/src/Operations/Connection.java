/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Operations;

import irigui.ErrorFrame;
import irigui.IRIGui;
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

    public void sendRequest(final int request, String typeofsensor, int sensornr) throws IOException {
        if (request == 3) {
            output.write(request);
        } else {
            System.out.println("Request: " + request);
            output.write(request);
            //output.writeInt(request);
            //output.writeChars(typeofsensor);
            System.out.println("Type of sensor " + typeofsensor);
            //output.writeInt(sensornr);
            System.out.println("sensornr: " + sensornr);
        }
    }

    @Override
    public void run() {
        //while (connection.isConnected()) {
        int[] inputarray = null;
//        try {
//            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        } catch (IOException ex) {
//            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
//        }
        char opcode = '0';
        try {
            opcode = (char) input.read();
        } catch (IOException ex) {
            ErrorFrame erfframe = new ErrorFrame("De verbinding is verbroken voordat we de opcode van het packet konden lezen", "Herverbinden", IRIGui.ConnectionType);
        }
        System.out.println("Data received!!" + opcode);
        int opcodenr = Character.getNumericValue(opcode);
        char sensortype = '0';
        try {
            do {
                sensortype = (char) input.read();
            } while (sensortype == 0);

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
                System.out.println("Case 3: Connection thread");
                int amntofsensors = 0;
                try {
                    do {
                        amntofsensors = input.read();
                    } while (amntofsensors == 0);
                } catch (IOException ex) {
                    ErrorFrame erfframe = new ErrorFrame("De verbinding is verbroken voordat we het aantal sensoren van het packet konden lezen", "Herverbinden", IRIGui.ConnectionType);
                }
                int arrayspace = amntofsensors + 3;
                inputarray = new int[arrayspace];
                int index = 0;
                inputarray[index] = opcodenr;
                index++;
                inputarray[index] = sensortypenr;
                index++;
                inputarray[index] = amntofsensors;

                int temp;
                while (index <= (amntofsensors + 2)) {
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
                    amntofvalues = input.read();
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
                index++;
                
                while (index <= (amntofvalues + 2)) {
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

    }
}