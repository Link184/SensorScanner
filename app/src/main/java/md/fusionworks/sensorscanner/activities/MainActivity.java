package md.fusionworks.sensorscanner.activities;

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

import java.util.ArrayList;
import java.util.List;

import md.fusionworks.sensorscanner.R;
import md.fusionworks.sensorscanner.activities.settings.SettingsActivity;
import md.fusionworks.sensorscanner.adapter.TourAdapter;
import md.fusionworks.sensorscanner.data.ToursDataView;
import md.fusionworks.sensorscanner.engine.Serialization;

public class MainActivity extends AppCompatActivity {
    private static final int LAYOUT = R.layout.activity_main;
    private static Context context;

    private ToursDataView toursDataView;
    private RecyclerView recyclerView;
    private TourAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = getApplicationContext();

        toursDataView = (ToursDataView) Serialization.deserExternalData(Serialization.TOURS_FILE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new TourAdapter(initTourList());
        recyclerView.setAdapter(adapter);
    }

    private List<ToursDataView> initTourList() {
        List<ToursDataView> list = new ArrayList<ToursDataView>();
        if (toursDataView != null) {
            for (int i = 0; i < toursDataView.getNameList().size(); i++) {
                list.add(new ToursDataView(toursDataView.getDateList().get(i), toursDataView.getNameList().get(i)));
            }
            Log.d("CARD", "Tours: " + list.size());
        } else {
            toursDataView = new ToursDataView();
        }
        return list;
    }

    protected void showInputDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // get prompts.xml view
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setView(promptView);

                final EditText editText = (EditText) promptView.findViewById(R.id.xRangeEditText);
                // setup a dialog window
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                toursDataView.setName(editText.getText().toString());
                                adapter = new TourAdapter(initTourList());
                                recyclerView.setAdapter(adapter);
                                Serialization.serExternalData(Serialization.TOURS_FILE, toursDataView);
                                Log.d("CARD", "Size after add: " + toursDataView.getNameList().size());
                                Log.d("SER", "Tours was saved");
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                Log.e("Err", "Missing element");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    public static Context getContext() {
        return context;
    }
}
