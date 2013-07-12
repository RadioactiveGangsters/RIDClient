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
    private ArrayList errors = new ArrayList();
    boolean exists = false;

    private DataHandler() {
    }

    public static DataHandler getInstance() {
        if (instance == null) {
            instance = new DataHandler();
        }
        return instance;
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
            index++;
            int sensortype = (int) dataarray[index];
            index++;
            int value = (int) dataarray[index];
            index++;
            int x = 0;
            WarningFrame warningframe;
            Iterator it = errors.iterator();
            if (dataarray[index] == 1) {
                while (it.hasNext()) {
                    Error err = (Error) it.next();
                    if (err.sensortype.equals("De temperatuursensor") && err.value == value) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    warningframe = new WarningFrame("De temperatuursensor", value, (int)dataarray[++index], "Koel af!");
                    errors.add(warningframe);
                }
            } else if (dataarray[index] == 2) {
                while (it.hasNext()) {
                    Error err = (Error) it.next();
                    if (err.sensortype.equals("De temperatuursensor") && err.value == value) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    warningframe = new WarningFrame("De temperatuursensor", value, (int)dataarray[++index], "Warm op!");
                    errors.add(warningframe);
                }
            } else if (dataarray[index] == 3) {
                while (it.hasNext()) {
                    Error err = (Error) it.next();
                    if (err.sensortype.equals("De snelheidssensor") && err.value == value) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    warningframe = new WarningFrame("De snelheidssensor", value, (int)dataarray[++index], "Verlaag de snelheid!");
                    errors.add(warningframe);
                }
            } else if (dataarray[index] == 4) {
                while (it.hasNext()) {
                    Error err = (Error) it.next();
                    if (err.sensortype.equals("De snelheidssensor") && err.value == value) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    warningframe = new WarningFrame("De snelheidssensor", value, (int)dataarray[++index], "Verhoog de snelheid!");
                    errors.add(warningframe);
                }
            } else if (dataarray[index] == 5) {
                while (it.hasNext()) {
                    Error err = (Error) it.next();
                    if (err.sensortype.equals("De vochtigheidssensor") && err.value == value) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    warningframe = new WarningFrame("De vochtigheidssensor", value, (int)dataarray[++index], "Verlaag de vochtigheid!");
                    errors.add(warningframe);
                }
            } else if (dataarray[index] == 6) {
                while (it.hasNext()) {
                    Error err = (Error) it.next();
                    if (err.sensortype.equals("De vochtigheidssensor") && err.value == value) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    warningframe = new WarningFrame("De vochtigheidssensor", value, (int)dataarray[++index], "Verhoog de vochtigheid!");
                    errors.add(warningframe);
                }
            } else if (dataarray[index] == 7) {
                while (it.hasNext()) {
                    Error err = (Error) it.next();
                    if (err.sensortype.equals("De vol/leeg sensor") && err.value == value) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    warningframe = new WarningFrame("De vol/leeg sensor", value, (int)dataarray[++index], "Leeg de tank");
                    errors.add(warningframe);
                }
            } else if (dataarray[index] == 8) {
                while (it.hasNext()) {
                    Error err = (Error) it.next();
                    if (err.sensortype.equals("De vol/leeg sensor") && err.value == value) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    warningframe = new WarningFrame("De vol/leeg sensor", value, (int)dataarray[++index], "Vul de tank");
                    errors.add(warningframe);
                }
            } else if (dataarray[index] == 9) {
                while (it.hasNext()) {
                    Error err = (Error) it.next();
                    if (err.sensortype.equals("De niveausensor") && err.value == value) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    warningframe = new WarningFrame("De niveausensor", value, (int)dataarray[++index], "Verlaag het niveau");
                    errors.add(warningframe);
                }
            } else if (dataarray[index] == 10) {
                while (it.hasNext()) {
                    Error err = (Error) it.next();
                    if (err.sensortype.equals("De niveausensor") && err.value == value) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    warningframe = new WarningFrame("De niveausensor", value, (int)dataarray[++index], "Verhoog het niveau");
                    errors.add(warningframe);
                }
            }
            exists = false;
        }

    }

    public void removeFromErrors(WarningFrame warning) {
        Iterator it = errors.iterator();
        Error err = new Error(warning.getText(), warning.getValue());
        errors.remove(err);
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
