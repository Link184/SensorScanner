package md.fusionworks.sensorscanner.engine;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import md.fusionworks.sensorscanner.data.SensorData;

public class FileOperations {
    public static final String FILE_PATH = Environment.getExternalStorageDirectory().toString() + "/SensorData/";

    public static void saveToXYZ(List<Float> cx, List<Float> cy, List<Float> cz, String path, String fileName){
        new File(Environment.getExternalStorageDirectory() + "/SensorData/").mkdir();
        new File(Environment.getExternalStorageDirectory() + "/SensorData/" + path).mkdir();
        File file = new File(Environment.getExternalStorageDirectory() + "/SensorData/" + path, fileName + ".cvs");
        StringBuffer fileContent = new StringBuffer();
        for (int i = 0; i < cx.size(); i++) {
            fileContent.append("\n" + cx.get(i) + "\t" + cy.get(i) + "\t" + cz.get(i));
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(fileContent.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveToCVS(SensorData sensorData, String path, String fileName){
        new File(Environment.getExternalStorageDirectory() + "/SensorData/").mkdir();
        new File(Environment.getExternalStorageDirectory() + "/SensorData/" + path).mkdir();
        File file = new File(Environment.getExternalStorageDirectory() + "/SensorData/" + path, fileName + ".cvs");
        StringBuffer fileContent = new StringBuffer();
        for (Map.Entry<Long, String[]> entry: sensorData.getListAccelerometer().entrySet()) {
            fileContent.append("\n Time: " + entry.getKey() + ", Accel: " + Arrays.toString(entry.getValue()));
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(fileContent.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean removeFile(String path, String fileName){
        File file = new File(Environment.getExternalStorageDirectory() + "/SensorData/" + path, fileName + ".cvs");
        boolean delete = file.delete();
        return delete;
    }

    public static void removeFolder(String folder){
        File file = new File(FILE_PATH + folder);
        if (file.isDirectory())
        {
            String[] children = file.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(file, children[i]).delete();
            }
        }
        file.delete();
    }
}
