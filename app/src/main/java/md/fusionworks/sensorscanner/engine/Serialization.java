package md.fusionworks.sensorscanner.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serialization implements Serializable {
    public static final String TOURS_FILE = "ToursData.ss";
    public static final String SCANS_FILE = "ScansData.ss";
    public static final String DATA_FILE = "Data.ss";

    private static File file;
    private static boolean serFinished = false;

    public static void serData(Context context, String fileName, Object obj) {
        new File(String.valueOf(context.getCacheDir() + "/SensorCache")).mkdir();
        file = new File(context.getCacheDir() + "/SensorCache", fileName);
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(obj);
            fileOut.close();
            out.close();
            Log.d("SER", "Success!" + file.toString());
            serFinished = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object deserData(Context context, String fileName) {
        file = new File(context.getCacheDir() + "/SensorCache", fileName);
        Object retObj = null;
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            retObj = in.readObject();
            fileIn.close();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return retObj;
    }

    public static void serExternalData(String fileName, Object obj) {
        new File(String.valueOf(Environment.getExternalStorageDirectory() + "/SensorScans")).mkdir();
        file = new File(Environment.getExternalStorageDirectory() + "/SensorScans", fileName);
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(obj);
            fileOut.close();
            out.close();
            Log.d("SER", "Success!" + file.toString());
            serFinished = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object deserExternalData(String fileName) {
        file = new File(Environment.getExternalStorageDirectory() + "/SensorScans", fileName);
        Object retObj = null;
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            retObj = in.readObject();
            fileIn.close();
            in.close();
        } catch (FileNotFoundException e) {
            Log.d("SER", "Error, File not found, will create other empty file");
            switch (fileName) {
                case TOURS_FILE:
                    InitialSerialization.initTourSer();
                    break;
                case SCANS_FILE:
                    InitialSerialization.initScanSer();
                    break;
                case DATA_FILE:
                    InitialSerialization.initData();
                    break;
                default:
                    Log.e("SER", "File Error");
            }
            Log.d("SER", "Empty File was created");
            deserExternalData(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return retObj;
    }

    private Bitmap saveIcon(String fileName){
        file = new File(Environment.getExternalStorageDirectory() + "/SensorCache", fileName);
        Bitmap icon = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
        try {
            FileOutputStream out = new FileOutputStream(file);
            icon.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return icon;
    }

    public static boolean isSerFinished() {
        return serFinished;
    }

    public static void setSerFinished(boolean serFinished) {
        Serialization.serFinished = serFinished;
    }
}
