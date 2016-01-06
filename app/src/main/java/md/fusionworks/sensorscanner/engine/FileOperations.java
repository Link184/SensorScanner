package md.fusionworks.sensorscanner.engine;

import android.os.Environment;

import md.fusionworks.sensorscanner.data.PointsData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileOperations {
    public static final String FILE_PATH = Environment.getExternalStorageDirectory().toString() + "/PointCloudExport/";

    public static void saveToXYZ(List<Float> cx, List<Float> cy, List<Float> cz, String path, String fileName){
        new File(Environment.getExternalStorageDirectory() + "/PointCloudExport/").mkdir();
        new File(Environment.getExternalStorageDirectory() + "/PointCloudExport/" + path).mkdir();
        File file = new File(Environment.getExternalStorageDirectory() + "/PointCloudExport/" + path, fileName + ".xyz");
        StringBuffer fileContent = new StringBuffer("#Scan");
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

    public static boolean removeFile(String path, String fileName){
        File file = new File(Environment.getExternalStorageDirectory() + "/PointCloudExport/" + path, fileName + ".xyz");
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
