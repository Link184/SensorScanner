package md.fusionworks.sensorscanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import md.fusionworks.sensorscanner.adapter.ScanAdapter;
import md.fusionworks.sensorscanner.data.ScansDataView;
import md.fusionworks.sensorscanner.engine.Serialization;
import md.fusionworks.sensorscanner.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

public class ScansActivity extends AppCompatActivity {
    private static final int LAYOUT = R.layout.activity_scans;
    private static Context context;

    private static String tourName;
    private ScansDataView scansDataView;
    private RecyclerView recyclerView;
    private ScanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        context = getApplicationContext();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        scansDataView = (ScansDataView) Serialization.deserExternalData(Serialization.SCANS_FILE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                showInputDialog();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.scans_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ScanAdapter(initScansList());
        recyclerView.setAdapter(adapter);

    }

    private List<ScansDataView> initScansList() {
        Bundle extras = getIntent().getExtras();
        tourName = extras.getString("Tour");
        Log.d("EXTRAS", extras.getString("Tour"));
        List<ScansDataView> list = new ArrayList<ScansDataView>();
        if (scansDataView != null) {
            for (int i = 0; i < scansDataView.getNameListByKey(extras.getString("Tour")).size(); i++) {
                list.add(new ScansDataView(extras.getString("Tour"), scansDataView.getNameListByKey(extras.getString("Tour")).get(i)));
            }
        }else scansDataView = new ScansDataView();
        return list;
    }

    protected void showInputDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // get prompts.xml view
                LayoutInflater layoutInflater = LayoutInflater.from(ScansActivity.this);
                View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ScansActivity.this);
                alertDialogBuilder.setView(promptView);

                final EditText editText = (EditText) promptView.findViewById(R.id.xRangeEditText);
                // setup a dialog window
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Bundle extras = getIntent().getExtras();
                                scansDataView.setNameByKey(extras.getString("Tour"), editText.getText().toString());
                                Log.d("SCAN", "Size after add: " + scansDataView.getNameListByKey(extras.getString("Tour")).size());
                                Serialization.serExternalData(Serialization.SCANS_FILE, scansDataView);
                                Log.d("SER", "Scans was saved");
                                adapter = new ScanAdapter(initScansList());
                                recyclerView.setAdapter(adapter);
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(ScansActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                Log.d("BACK", "Back has pressed");
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ScansActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public static String getTourName() {
        return tourName;
    }

    public static Context getContext() {
        return context;
    }
}
