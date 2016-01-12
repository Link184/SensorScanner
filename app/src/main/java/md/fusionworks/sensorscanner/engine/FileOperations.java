package md.fusionworks.sensorscanner.engine;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import md.fusionworks.sensorscanner.data.SensorData;

public class FileOperations {
    public static final String FILE_PATH = Environment.getExternalStorageDirectory().toString() + "/SensorData/";

    public static void saveToCVS(SensorData sensorData, String path, String fileName, boolean toZIP){
        new File(Environment.getExternalStorageDirectory() + "/SensorData/").mkdir();
        new File(Environment.getExternalStorageDirectory() + "/SensorData/" + path).mkdir();
        File file = new File(Environment.getExternalStorageDirectory() + "/SensorData/" + path, fileName + ".cvs");
        StringBuffer fileContent = new StringBuffer("Time,AccX,AccY,AccZ,GyroX,GyroY,GyroZ,GravX,GravY,GravZ,LAccX,LAccY,LAccZ,PresX,PresY,PresZ,RotVX,RotVY,RotVZ,RotGX,RotGY,RotGZ,RotGRX,RotGRY,RotGRZ");
        for (Map.Entry<Long, String[]> entry : sensorData.getListSensorsHeap().entrySet()) {
            fileContent.append("\n" + entry.getKey() + "," + prepereArray(entry.getValue()));
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(fileContent.toString());
            bufferedWriter.flush();
            bufferedWriter.close();

            byte[] buffer = new byte[1024];
            if (toZIP){
                long begin = System.nanoTime();
                FileOutputStream fileOutputStream = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/SensorData/" + path, fileName + ".zip"));
                ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
                ZipEntry zipEntry = new ZipEntry(fileName + ".cvs");
                zipOutputStream.putNextEntry(zipEntry);

                FileInputStream fileInputStream = new FileInputStream(file);
                int len;
                while ((len = fileInputStream.read(buffer)) > 0) {
                    zipOutputStream.write(buffer, 0, len);
                }

                fileInputStream.close();
                zipOutputStream.closeEntry();
                zipOutputStream.close();
                long end = System.nanoTime();
                Log.d("ZIP", "Archiving time: " + (end - begin));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String prepereArray(String[] array) {
        if (array == null) {
            return "";
        }
        if (array.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(array.length * 7);
        sb.append(array[0]);
        for (int i = 1; i < array.length; i++) {
            sb.append(",");
            sb.append(array[i]);
        }
        return sb.toString();
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
