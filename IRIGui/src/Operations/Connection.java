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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mitchell
 */
public class Connection implements Runnable {

    Socket connection;
    DataOutputStream output;
    DataInputStream input;
    private static Connection instance = null;
    String ip;
    int port;

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
        this.ip = ip;
        this.port = port;
        try {
            connection = new Socket(ip, port);
            output = new DataOutputStream(connection.getOutputStream());
            input = new DataInputStream(connection.getInputStream());


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

    public void sendRequest(final int request, String typeofsensor, String min, String max) throws IOException {
        if (request == 3) {
            output.write(request);
        }else if(request == 6){
            output.write(request);
            output.writeInt(typeofsensor.length());
            output.writeInt(Integer.parseInt(min));
            output.writeInt(Integer.parseInt(max));
        } else {
            System.out.println("Request: " + request);
            output.write(request);
            output.writeInt("Temperature1".length());
            output.writeBytes("Temperature1");
        }
    }

    @Override
    public void run() {
        while (instance.isConnected()) {
            ArrayList inputarray = null;
            char opcode = '0';
            try {
                opcode = (char)input.readByte();
            } catch (IOException ex) {
                ErrorFrame erfframe = new ErrorFrame("De verbinding is verbroken voordat we de opcode van het packet konden lezen", "Herverbinden", IRIGui.ConnectionType);
            }
            char sensortype = '0';
            try {
                do {
                    sensortype = (char) input.readByte();
                } while (sensortype == 0);

            } catch (IOException ex) {
                ErrorFrame erfframe = new ErrorFrame("De verbinding is verbroken voordat we de sensortype van het packet konden lezen", "Herverbinden", IRIGui.ConnectionType);
            }
            switch (opcode) {
                case 0:
                    System.out.println("Not defined yet, see Berend for details, Opcode = 0");
                    break;
                case 1:
                    System.out.println("Not defined yet, see Berend for details, Opcode = 1");
                    break;
                case 2:
                    try {
                        output.write(0x02);
                    } catch (IOException ex) {
                        ErrorFrame erfframe = new ErrorFrame("De verbinding is verbroken voordat we kunnen reageren op de ping packets", "Herverbinden", IRIGui.ConnectionType);
                    }
                    break;
                case 3:
                    System.out.println("Case 3: Connection thread");
                    inputarray = new ArrayList();
                    int amntofsensors = 0;
                    try {
                        do {
                            amntofsensors = input.readInt();
                        } while (amntofsensors == 0);
                    } catch (IOException ex) {
                        ErrorFrame erfframe = new ErrorFrame("De verbinding is verbroken voordat we het aantal sensoren van het packet konden lezen", "Herverbinden", IRIGui.ConnectionType);
                    }
                    int index = 0;
                    inputarray.add(opcode);
                    index++;
                    inputarray.add(sensortype);
                    index++;
                    inputarray.add(amntofsensors);
                    index++;
                    int temp;
                    while (index <= (amntofsensors + 2)) {
                        try {
                            do {
                                temp = input.readInt();
                            } while (temp == 0);
                            inputarray.add(temp);
                        } catch (IOException ex) {
                            ErrorFrame erfframe = new ErrorFrame("De verbinding is verbroken voordat we een waarde van het packet konden lezen", "Herverbinden", IRIGui.ConnectionType);
                        }

                        index++;
                    }
                    break;
                case 4:
				 System.out.println("got graph");
                    inputarray = new ArrayList();
                    int amntofvalues = 0;
                    try {
                        amntofvalues = input.readInt();
                    } catch (IOException ex) {
                        ErrorFrame erfframe = new ErrorFrame("De verbinding is verbroken voordat we het aantal sensoren van het packet konden lezen", "Herverbinden", IRIGui.ConnectionType);
                    }
                    index = 0;
                    inputarray.add(opcode);
                    index++;
                    inputarray.add(sensortype);
                    index++;
                    inputarray.add(amntofvalues);
                    index++;

                    while (index <= (amntofvalues + 2)) {
                        try {
                            do {
                                temp = input.readInt();
                            } while (temp == 0);
                            inputarray.add(temp);
                        } catch (IOException ex) {
                            ErrorFrame erfframe = new ErrorFrame("De verbinding is verbroken voordat we een waarde van het packet konden lezen", "Herverbinden", IRIGui.ConnectionType);
                        }

                        index++;
                    }
                    break;
                case 5:
                    inputarray = new ArrayList();
                    System.out.println("Alarm!!");
                    int value = 1;
                    try {
                        do {
                            value = input.readInt();
                        } while (value == 0);
                    } catch (IOException ex) {
                        Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    int counteractiontype = -1;
                    try {
                        do {
                            counteractiontype = input.readInt();
                        } while (counteractiontype == 0);
                    } catch (IOException ex) {
                        Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    int sensornr = 0;
                    try {
                        do {
                            sensornr = input.readInt();
                        } while (sensornr == 0);
                    } catch (IOException ex) {
                        Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    index = 0;
                    inputarray.add(opcode);
                    index++;
                    inputarray.add(sensortype);
                    index++;
                    inputarray.add(value);
                    index++;
                    inputarray.add(counteractiontype);
                    index++;
                    inputarray.add(sensornr);
                    break;
            }
            if (inputarray != null) {
                DataHandler.getInstance().handleData(inputarray.toArray());
            }

        }
        Connect(ip, port);
    }
}