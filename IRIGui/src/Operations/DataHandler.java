/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Operations;

import irigui.MainScreen;
import irigui.WarningFrame;



/**
 *
 * @author user
 */
//This Class will handle the distribution of data among the different SensorPages

public class DataHandler{

    String data;
    private static DataHandler instance = null;

    private DataHandler(){
    }

    public static DataHandler getInstance() {
        if (instance == null) {
            instance = new DataHandler();
        }
        return instance;
    }

    public void handleData(int []dataarray){
        int index = 0;
        if(dataarray[index] == 0||dataarray[index] == 1|| dataarray[index] == 2){
            System.out.println("Opcode = 0, 1 of 2");
        }else if(dataarray[index] == 3){
            index++;
            int sensortype = dataarray[index];
            index++;
            int amountofsensors = dataarray[index];
            index++;
            
            while(index<=(dataarray.length-1)){
                int temp = dataarray[index];
                MainScreen.getInstance().updateAllSensors(sensortype, index-3, temp);
                index++;
            }
            MainScreen.getInstance().refreshSensors(sensortype);
        }else if(dataarray[index] == 4){
            index++;
            int sensortype = dataarray[index];
            index++;
            int amountofvalues = dataarray[index];
            index++;
            int[]temparray = new int[dataarray.length - 3];
            while(index <= (dataarray.length-1)){
                temparray[index-3] = dataarray[index];
                index++;
            }
            MainScreen.getInstance().addValuesToGraph(sensortype, temparray.length, temparray);
            System.out.println("Opcode = 4");
            System.out.println("Get all the data neede to build the graph for the selected sensor");
        }else if(dataarray[index] == 5){
            index++;
            int sensortype = dataarray[index];
            index++;
            int value = dataarray[index];
            index++;
            WarningFrame warningframe;
            if(dataarray[index] == 1){
                warningframe = new WarningFrame("De temperatuursensor", value, dataarray[++index], "Koel af!");
            }else if(dataarray[index] == 2){
                warningframe = new WarningFrame("De temperatuursensor", value, dataarray[++index], "Warm op!");
            }else if(dataarray[index] == 3){
                warningframe = new WarningFrame("De snelheidssensor", value, dataarray[++index], "Verlaag de snelheid!");
            }else if(dataarray[index] == 4){
                warningframe = new WarningFrame("De snelheidssensor", value, dataarray[++index], "Verhoog de snelheid!");
            }else if(dataarray[index] == 5){
                warningframe = new WarningFrame("De vochtigheidssensor", value, dataarray[++index], "Verlaag de vochtigheid!");
            }else if(dataarray[index] == 6){
                warningframe = new WarningFrame("De vochtigheidssensor", value, dataarray[++index], "Verhoog de vochtigheid!");
            }else if(dataarray[index] == 7){
                warningframe = new WarningFrame("De vol/leeg sensor", value, dataarray[++index], "Leeg de tank");
            }else if(dataarray[index] == 8){
                warningframe = new WarningFrame("De vol/leeg sensor", value, dataarray[++index], "Vul de tank");
            }else if(dataarray[index] == 9){
                warningframe = new WarningFrame("De niveausensor", value, dataarray[++index], "Verlaag het niveau");
            }else if(dataarray[index] == 10){
                warningframe = new WarningFrame("De niveausensor", value, dataarray[++index], "Verhoog het niveau");
            }
            System.out.println("Opcode = 5");
            System.out.println("ALARMMMMMM!!!!");
        }
        
    }

}