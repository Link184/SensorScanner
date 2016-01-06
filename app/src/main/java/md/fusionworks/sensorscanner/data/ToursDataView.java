package md.fusionworks.sensorscanner.data;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ToursDataView implements Serializable{
    private String name;
    private DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm");
    private List<Date> dateList = new ArrayList<Date>();
    private List<String> nameList = new ArrayList<String>();

    public ToursDataView() {
    }

    public ToursDataView(Date date, String name) {
        this.name = name;
        nameList.add(name);
        dateList.add(date);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        nameList.add(name);
        dateList.add(new Date());
    }

    public List<String> getNameList() {
        return nameList;
    }

    public String getStringDate(int index) {
        Date date = dateList.get(index);
        return df.format(date);
    }

    public List<Date> getDateList() {
        return dateList;
    }
}
