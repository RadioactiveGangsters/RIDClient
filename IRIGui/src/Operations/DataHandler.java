/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Operations;

import irigui.MainScreen;
import irigui.WarningFrame;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author user
 */
//This Class will handle the distribution of data among the different SensorPages
public class DataHandler {

    String data;
    private static DataHandler instance = null;
    boolean error = false;

    private DataHandler() {
    }

    public static DataHandler getInstance() {
        if (instance == null) {
            instance = new DataHandler();
        }
        return instance;
    }
    
    public void setError(){
        error = false;
    }

    public void handleData(Object[] dataarray) {
        int index = 0;
        if (dataarray[index] == 0 || dataarray[index] == 1 || dataarray[index] == 2) {
            System.out.println("Opcode = 0, 1 of 2");
        } else if (dataarray[index] == 3) {
            index++;
            int sensortype = (int) dataarray[index];
            index++;
            int amountofsensors = (int) dataarray[index];
            index++;

            while (index <= (dataarray.length - 1)) {
                int temp = (int) dataarray[index];
                MainScreen.getInstance().updateAllSensors(sensortype, index - 3, temp);
                index++;
            }
            MainScreen.getInstance().refreshSensors(sensortype);
        } else if (dataarray[index] == 4) {
            index++;
            int sensortype = (int) dataarray[index];
            index++;
            int amountofvalues = (int) dataarray[index];
            index++;
            int[] temparray = new int[dataarray.length - 3];
            while (index <= (dataarray.length - 1)) {
                temparray[index - 3] = (int) dataarray[index];
                index++;
            }
            MainScreen.getInstance().addValuesToGraph(sensortype, temparray.length, temparray);
        } else if (dataarray[index] == 5) {
            if (!error) {
                error = true;
                index++;
                int sensortype = (int) dataarray[index];
                index++;
                int value = (int) dataarray[index];
                index++;
                int x = 0;
                WarningFrame warningframe;
                if (dataarray[index] == 1) {
                    warningframe = new WarningFrame("De temperatuursensor", value, (int) dataarray[++index], "Koel af!", "Temperature");
                } else if (dataarray[index] == 2) {
                    warningframe = new WarningFrame("De temperatuursensor", value, (int) dataarray[++index], "Warm op!", "Temperature");
                } else if (dataarray[index] == 3) {
                    warningframe = new WarningFrame("De Flowsensor", value, (int) dataarray[++index], "Verlaag de flow", "Flow");
                } else if (dataarray[index] == 4) {
                    warningframe = new WarningFrame("De Flowsensor", value, (int) dataarray[++index], "Verhoog de flow", "Flow");
                } else if (dataarray[index] == 5) {
                    warningframe = new WarningFrame("De Druksensor", value, (int) dataarray[++index], "Verlaag de druk", "Pressure");
                } else if (dataarray[index] == 6) {
                    warningframe = new WarningFrame("De Druksensor", value, (int) dataarray[++index], "Verhoog de druk", "Pressure");
                } else if (dataarray[index] == 7) {
                    warningframe = new WarningFrame("De vol/leeg sensor", value, (int) dataarray[++index], "Leeg de tank", "Fullness");
                } else if (dataarray[index] == 8) {
                    warningframe = new WarningFrame("De vol/leeg sensor", value, (int) dataarray[++index], "Vul de tank", "Fullness");
                } else if (dataarray[index] == 9) {
                    warningframe = new WarningFrame("De Stralingssensor", value, (int) dataarray[++index], "Verlaag", "Radiation");
                } else if (dataarray[index] == 10) {
                    warningframe = new WarningFrame("De Stralingssensor", value, (int) dataarray[++index], "Verhoog", "Radiation");
                }
            }

        }

    }


    public class Error {

        String sensortype;
        int value;

        public Error(String sensortype, int value) {
            this.sensortype = sensortype;
            this.value = value;
        }
    }
}
