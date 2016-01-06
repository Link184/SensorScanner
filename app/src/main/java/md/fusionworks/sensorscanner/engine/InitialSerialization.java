package md.fusionworks.sensorscanner.engine;

import md.fusionworks.sensorscanner.data.ScansDataView;
import md.fusionworks.sensorscanner.data.ToursDataView;

public class InitialSerialization {
    public static void initTourSer(){
        ToursDataView toursDataView = new ToursDataView();
        Serialization.serExternalData(Serialization.TOURS_FILE, toursDataView);
    }

    public static void initScanSer(){
        ScansDataView scansDataView = new ScansDataView();
        Serialization.serExternalData(Serialization.SCANS_FILE, scansDataView);
    }
}
