package md.fusionworks.sensorscanner.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import md.fusionworks.sensorscanner.R;
import md.fusionworks.sensorscanner.data.ScansDataView;
import md.fusionworks.sensorscanner.data.SensorData;
import md.fusionworks.sensorscanner.engine.FileOperations;
import md.fusionworks.sensorscanner.engine.Serialization;

public class SensorActivity extends AppCompatActivity implements SensorEventListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private static final int LAYOUT = R.layout.activity_sensor;

    private SensorManager mSensorManager;
    private ToggleButton scanToggleButton;
    private Button restartButton;
    private boolean isRestarted = false;

    private SensorData sensorData;
    private ScansDataView scansDataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        sensorData = new SensorData();
//        sensorData = (SensorData) Serialization.deserExternalData(Serialization.DATA_FILE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        scanToggleButton = (ToggleButton) findViewById(R.id.scan_toggle_button);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        scanToggleButton.setBackgroundColor(Color.parseColor("#259C25"));
        scanToggleButton.setOnCheckedChangeListener(this);
        restartButton = (Button) findViewById(R.id.restart_button);
        restartButton.setOnClickListener(this);
    }

    protected void showInputDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // get prompts.xml view
                LayoutInflater layoutInflater = LayoutInflater.from(SensorActivity.this);
                View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SensorActivity.this);
                alertDialogBuilder.setView(promptView);

                final EditText editText = (EditText) promptView.findViewById(R.id.xRangeEditText);
                // setup a dialog window
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String scanName = editText.getText().toString();
                                Bundle extras = getIntent().getExtras();
                                FileOperations.saveToCVS(sensorData, extras.getString("Scan"), scanName, true);

                                scansDataView = (ScansDataView) Serialization.deserExternalData(Serialization.SCANS_FILE);
                                scansDataView.setNameByKey(extras.getString("Scan"), scanName);
                                Serialization.serExternalData(Serialization.SCANS_FILE, scansDataView);
                                Log.d("CVS", "File " + scanName + " was saved");

                                Intent intent = new Intent(getApplicationContext(), ScansActivity.class);
                                intent.putExtra("Tour", extras.getString("Scan"));
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        Bundle extras = getIntent().getExtras();
                                        Intent intent = new Intent(getApplicationContext(), ScansActivity.class);
                                        intent.putExtra("Tour", extras.getString("Scan"));
                                        startActivity(intent);
                                    }
                                });
                // create an alert dialog
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Log.d("SETT", "Settings was selected");
                break;
            default:
                Log.e("Error", "Menu item is missing");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            String[] values = {String.valueOf(event.values[0]),
                    String.valueOf(event.values[1]),
                    String.valueOf(event.values[2]), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
            sensorData.getListSensorsHeap().put(System.nanoTime() / 1000, values);
            Log.d("SENSOR", "Is TYPE_ACCELEROMETER");
        }
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            String[] values = {"", "", "", String.valueOf(event.values[0]),
                    String.valueOf(event.values[1]),
                    String.valueOf(event.values[2]), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
            sensorData.getListSensorsHeap().put(System.nanoTime() / 1000, values);
            Log.d("SENSOR", "Is TYPE_GYROSCOPE");
        }
        if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            String[] values = {"", "", "", "", "", "", String.valueOf(event.values[0]),
                    String.valueOf(event.values[1]),
                    String.valueOf(event.values[2]), "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
            sensorData.getListSensorsHeap().put(System.nanoTime() / 1000, values);
            Log.d("SENSOR", "Is TYPE_GRAVITY");
        }
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            String[] values = {"", "", "", "", "", "", "", "", "", String.valueOf(event.values[0]),
                    String.valueOf(event.values[1]),
                    String.valueOf(event.values[2]), "", "", "", "", "", "", "", "", "", "", "", ""};
            sensorData.getListSensorsHeap().put(System.nanoTime() / 1000, values);
            Log.d("SENSOR", "Is TYPE_LINEAR_ACCELERATION");
        }
        if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
            String[] values = {"", "", "", "", "", "", "", "", "", "", "", "", String.valueOf(event.values[0]),
                    String.valueOf(event.values[1]),
                    String.valueOf(event.values[2]), "", "", "", "", "", "", "", "", ""};
            sensorData.getListSensorsHeap().put(System.nanoTime() / 1000, values);
            Log.d("SENSOR", "Is TYPE_PRESSURE");
        }
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            String[] values = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", String.valueOf(event.values[0]),
                    String.valueOf(event.values[1]),
                    String.valueOf(event.values[2]), "", "", "", "", "", "",};
            sensorData.getListSensorsHeap().put(System.nanoTime() / 1000, values);
            Log.d("SENSOR", "Is TYPE_ROTATION_VECTOR");
        }
        if (event.sensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR) {
            String[] values = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", String.valueOf(event.values[0]),
                    String.valueOf(event.values[1]),
                    String.valueOf(event.values[2]), "", "", "",};
            sensorData.getListSensorsHeap().put(System.nanoTime() / 1000, values);
            Log.d("SENSOR", "Is TYPE_ROTATION_VECTOR");
        }
        if (event.sensor.getType() == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) {
            String[] values = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", String.valueOf(event.values[0]),
                    String.valueOf(event.values[1]),
                    String.valueOf(event.values[2]),};
            sensorData.getListSensorsHeap().put(System.currentTimeMillis(), values);
            Log.d("SENSOR", "Is TYPE_ROTATION_VECTOR");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
//        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ALL), SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.scan_toggle_button:
                if (isChecked) {
                    buttonView.setChecked(true);
                    buttonView.setBackgroundColor(Color.parseColor("#FF0505")); //red

                    mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
                    mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_FASTEST);
                    mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_FASTEST);
                    mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_FASTEST);
                    mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_FASTEST);
                    mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_FASTEST);
                    mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_FASTEST);
                    mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE), SensorManager.SENSOR_DELAY_FASTEST);

                } else {
                    buttonView.setChecked(false);
                    buttonView.setBackgroundColor(Color.parseColor("#259C25")); //greed

                    mSensorManager.unregisterListener(this);

//                    Serialization.serExternalData(Serialization.DATA_FILE, sensorData);

                    if (!isRestarted) {
                        showInputDialog();
                        isRestarted = false;
                    }
                }
                break;
            default:
                Log.e("Error", "Toggle Button not found!");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.restart_button:
                isRestarted = true;
                scanToggleButton.setChecked(false);
                recreateActivityCompat(this);
                Toast.makeText(this, "App was restarted", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @SuppressLint("NewApi")
    public static final void recreateActivityCompat(final Activity a) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            a.recreate();
        } else {
            final Intent intent = a.getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            a.finish();
            a.overridePendingTransition(0, 0);
            a.startActivity(intent);
            a.overridePendingTransition(0, 0);
        }
    }
}
