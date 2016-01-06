package md.fusionworks.sensorscanner.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ScansDataView implements Serializable{
//    private ImageView icon;
    private String tourName;
    private String name;
    private List<String> nameList = new ArrayList<String>();
    private Map<String, List<String>> nameMap = new LinkedHashMap<String, List<String>>();

    public ScansDataView() {
    }

    public ScansDataView(String name) {
        this.name = name;
        nameList.add(name);
    }

    public ScansDataView(String tourName, String name) {
        this.name = name;
        this.tourName = tourName;
        setNameByKey(tourName, name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public Map<String, List<String>> getNameMap() {
        return nameMap;
    }

    public List<String> getNameListByKey(String key){
        List<String> tmpList = new ArrayList<String>();
        if (nameMap.containsKey(key)) {
            tmpList.addAll(nameMap.get(key));
        }
        return tmpList;
    }

    public void setNameByKey(String key, String value) {
        List<String> tmpList = new ArrayList<String>();
        if (!nameMap.containsKey(key)) {
            nameMap.put(key, tmpList);
        }
        for(Map.Entry<String, List<String>> entry: nameMap.entrySet()){
            if (entry.getKey().equals(key)){
                tmpList.addAll(entry.getValue());
                tmpList.add(value);
                nameMap.put(key, tmpList);
            }
        }
    }

    public void removeNameByKey(String key, String value){
        List<String> tmpList = new ArrayList<String>();
        for(Map.Entry<String, List<String>> entry: nameMap.entrySet()){
            if (entry.getKey().equals(key)){
                tmpList.addAll(entry.getValue());
                tmpList.remove(value);
                nameMap.put(key, tmpList);
            }
        }
    }

    public void setNameMap(Map<String, List<String>> nameMap) {
        this.nameMap = nameMap;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }
}
