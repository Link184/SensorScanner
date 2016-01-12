package md.fusionworks.sensorscanner.data;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class SensorData implements Serializable {
    private Map<Long, String[]> listAccelerometer = new LinkedHashMap<Long, String[]>();
    private Map<Long, String[]> listGyroscope = new LinkedHashMap<Long, String[]>();
    private Map<Long, String[]> listGravity = new LinkedHashMap<Long, String[]>();
    private Map<Long, String[]> listLinearAcceleration = new LinkedHashMap<Long, String[]>();
    private Map<Long, String[]> listPressure = new LinkedHashMap<Long, String[]>();
    private Map<Long, String[]> listRotationVector = new LinkedHashMap<Long, String[]>();
    private Map<Long, String[]> listRotationGame = new LinkedHashMap<Long, String[]>();
    private Map<Long, String[]> listRotationGeomagneticRotation = new LinkedHashMap<Long, String[]>();

    private Map<Long, String[]> listSensorsHeap = new LinkedHashMap<Long, String[]>();

    public Map<Long, String[]> getListSensorsHeap() {
        return listSensorsHeap;
    }
}
