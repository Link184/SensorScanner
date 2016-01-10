package md.fusionworks.sensorscanner.data;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class SensorData implements Serializable {
    private Map<Long, String[]> listAccelerometer = new LinkedHashMap<Long, String[]>();
    private Map<Long, String[]> listGyroscope = new LinkedHashMap<Long, String[]>();
    private Map<Long, String[]> listGravity = new LinkedHashMap<Long, String[]>();
    private Map<Long, String[]> listLinearAcceleration = new LinkedHashMap<Long, String[]>();

    public Map<Long, String[]> getListAccelerometer() {
        return listAccelerometer;
    }

    public Map<Long, String[]> getListGyroscope() {
        return listGyroscope;
    }

    public Map<Long, String[]> getListGravity() {
        return listGravity;
    }

    public Map<Long, String[]> getListLinearAcceleration() {
        return listLinearAcceleration;
    }
}
