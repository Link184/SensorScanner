package md.fusionworks.sensorscanner.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PointsData implements Serializable {
    private List<Float> pointsCX = new ArrayList<Float>();
    private List<Float> pointsCY = new ArrayList<Float>();
    private List<Float> pointsCZ = new ArrayList<Float>();

    public PointsData() {
    }

    public List<Float> getPointsCX() {
        return pointsCX;
    }

    public List<Float> getPointsCY() {
        return pointsCY;
    }

    public List<Float> getPointsCZ() {
        return pointsCZ;
    }
}
