package com.example.dell.elixir.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.elixir.Adapters.My_home_Adapter;
import com.example.dell.elixir.R;
import com.example.dell.elixir.SQL_db.SContract;
import com.example.dell.elixir.Ubidots_sync.Ubidot_Client;
import com.example.dell.elixir.sensorData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    Cursor c1, c2, c3;
    public static float temp_value = 0;
    public static float pH_value = 0;
    public static float Turb_value = 0;
    public static int choice = 0;
    View view_temp, view_ph, view_turb, view_status;
    private TextView prog_phtext, prog_temptext, prog_turbtext, status_text;
    ProgressDialog dialog;
    ProgressBar prog_ph, prog_temp, prog_turb;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] XMEN = {R.drawable.e1min_low,R.drawable.p3, R.drawable.e2min_low, R.drawable.p2, R.drawable.e4min_low, R.drawable.e5min_low};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    private FloatingActionButton fab;

    ContentValues map_values = new ContentValues();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);+
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //new views
        view_ph = (View) findViewById(R.id.view_ph);
        view_temp = (View) findViewById(R.id.view_temp);
        view_turb = (View) findViewById(R.id.view_turb);


        view_ph.setOnClickListener(this);
        view_turb.setOnClickListener(this);
        view_temp.setOnClickListener(this);


        prog_phtext = (TextView) findViewById(R.id.progress_text_ph);
        prog_temptext = (TextView) findViewById(R.id.progress_text_temp);
        prog_turbtext = (TextView) findViewById(R.id.progress_text_turb);

        prog_turb = (ProgressBar) findViewById(R.id.progressBar_turb);
        prog_temp = (ProgressBar) findViewById(R.id.progressBar_temp);
        prog_ph = (ProgressBar) findViewById(R.id.progressBar_ph);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("sensorData");


        dialog = new ProgressDialog(HomeActivity.this);
        Sync_db_task task = new Sync_db_task(getBaseContext());
        task.execute();

        init();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
         if (id == R.id.action_refresh) {
            Sync_db_task task = new Sync_db_task(getBaseContext());
                task.execute();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(getBaseContext(),HistoryActivity.class));

        }  else if (id == R.id.nav_share) {
            Intent intent=new Intent(android.content.Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

// Add data to the intent, the receiving app will decide what to do with it.
            intent.putExtra(Intent.EXTRA_SUBJECT,"measured Sensor Values are .. ");
            intent.putExtra(Intent.EXTRA_TEXT, "Temperature : "+temp_value+" Turbidity : "+Turb_value+" pH : "+pH_value);

            startActivity(Intent.createChooser(intent, "How do you want to share?"));

        } else if (id == R.id.nav_about) {

            Dialog dialog = new Dialog(HomeActivity.this);
            dialog.setTitle("About Us..");
            dialog.setContentView(R.layout.about);
            dialog.show();

        }


         else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //for view pager init function
    private void init() {
        for (int i = 0; i < XMEN.length; i++)
            XMENArray.add(XMEN[i]);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new My_home_Adapter(HomeActivity.this, XMENArray));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == XMEN.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3500, 2500);
    }


    @Override
    public void onClick(View view) {
        if (view.equals(view_ph)) {
            choice = 0;
            startActivity(new Intent(this, Ubidots_Activity.class));

        } else if (view.equals(view_temp)) {
            choice = 1;
            startActivity(new Intent(this, Ubidots_Activity.class));

        } else if (view.equals(view_turb)) {
            choice = 2;
            startActivity(new Intent(this, Ubidots_Activity.class));
        } else if (view == fab) {
            //  Cursor mapcursor = getContentResolver().query(SContract.MAPS_Entry.CONTENT_URI,null,null,null,null);
            //  if(mapcursor.moveToFirst()) {
            //      Bundle b = new Bundle();
            //      b.putFloat("Longitude", mapcursor.getString());

            //}
            startActivity(new Intent(HomeActivity.this, MapActivity.class));
            //Snackbar.make(view, "Map action", Snackbar.LENGTH_LONG.setAction("Action", null).show();
        } else if (view == view_status) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Water Status\n\n Pure : Drinkable \n\n Good : Usable \n\n  Bad: not Usable");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }




    //sync the database with the cloud values
    private class Sync_db_task extends AsyncTask<URL, Integer, Boolean> {

        Context mContext;

        public Sync_db_task(Context context) {
            mContext = context;
        }


        private String API_KEY = "A1E-7WPueEXH3Vl9CerPfXXOZV80L0TiKL";
        private String tempVarId = "5a97b0a2c03f97663590ea57";
        private String pHVarId = "5a97b0abc03f9766c1f88b22";
        private String turbidityId = "5a97b0b4c03f97663046222f";
        private String longitudeId = "5a97b073c03f9765641cf28f";
        private String latitudeId = "5a9789b77625426b4478f1b6";


        Float val;
        List<Ubidot_Client.Value> db_data;

        private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        boolean res = false;


        private void fetchData() {

            try {
                c2 = getContentResolver().query(SContract.TEMP_Entry.CONTENT_URI, null, null, null, null);
                if (c2.moveToFirst())
                    temp_value = c2.getFloat(c2.getColumnIndex(SContract.TEMP_Entry.COLUMN_TEMP));


                c1 = getContentResolver().query(SContract.PH_Entry.CONTENT_URI, null, null, null, null);
                if (c1.moveToFirst())
                    pH_value = c1.getFloat(c1.getColumnIndex(SContract.PH_Entry.COLUMN_PH));


                c3 = getContentResolver().query(SContract.TURB_Entry.CONTENT_URI, null, null, null, null);
                if (c3.moveToFirst())
                    Turb_value = c3.getFloat(c3.getColumnIndex(SContract.TURB_Entry.COLUMN_TURB));


                sensorData data = new sensorData(temp_value, pH_value, Turb_value);
                myRef.push().setValue(data);

                //Toast.makeText(getBaseContext(), "fetch success", Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                return;
            } finally {
                if (c1 != null) c1.close();
                if (c2 != null) c2.close();
                if (c3 != null) c3.close();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setTitle("Sensor Data");
            dialog.setMessage("Fetching...");
            dialog.show();
        }

        protected Boolean doInBackground(URL... urls) {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Toast.makeText(mContext, "thread error", Toast.LENGTH_SHORT).show();
            }

            Ubidot_Client uc = new Ubidot_Client();

            (uc).handleUbidots(tempVarId, API_KEY, new Ubidot_Client.UbiListener() {
                @Override
                public void onDataReady(List<Ubidot_Client.Value> result) {
                    List<Entry> entries = new ArrayList();
                    List<String> labels = new ArrayList<String>();
                    ContentValues values = new ContentValues();

                    if (result.size() > 0) {
                        mContext.getContentResolver().delete(SContract.TEMP_Entry.CONTENT_URI, null, null);


                        // Convert timestamp to date
                        Date d = new Date(result.get(0).timestamp);

                        // Create Labels
                        labels.add(sdf.format(d));

                        values.put(SContract.TEMP_Entry.COLUMN_TEMP, result.get(0).value);
                        values.put(SContract.TEMP_Entry.Date_Time, sdf.format(d));
                        mContext.getContentResolver().insert(SContract.TEMP_Entry.CONTENT_URI, values);


                    }

                    //Toast.makeText(getActivity().getBaseContext(), "temp:"+val, Toast.LENGTH_SHORT).show();
                }
            });


            // Pressure
            (uc).handleUbidots(pHVarId, API_KEY, new Ubidot_Client.UbiListener() {
                @Override
                public void onDataReady(List<Ubidot_Client.Value> result) {
                    Log.d("Chart", "======== On data Ready ===========");

                    if (result.size() > 0) {
                        mContext.getContentResolver().delete(SContract.PH_Entry.CONTENT_URI, null, null);


                        // Convert timestamp to date
                        Date d = new Date(result.get(0).timestamp);

                        ContentValues values = new ContentValues();
                        values.put(SContract.PH_Entry.COLUMN_PH, result.get(0).value);
                        values.put(SContract.PH_Entry.Date_Time, sdf.format(d));
                        mContext.getContentResolver().insert(SContract.PH_Entry.CONTENT_URI, values);


                    }


                }
            });


            (uc).handleUbidots(turbidityId, API_KEY, new Ubidot_Client.UbiListener() {
                @Override
                public void onDataReady(List<Ubidot_Client.Value> result) {
                    Log.d("Chart", "======== On data Ready ===========");
                    List<BarEntry> entries = new ArrayList();
                    List<String> labels = new ArrayList<String>();
                    if (result.size() > 0) {
                        mContext.getContentResolver().delete(SContract.TURB_Entry.CONTENT_URI, null, null);


                        // Convert timestamp to date
                        Date d = new Date(result.get(0).timestamp);
                        // Create Labels
                        labels.add(sdf.format(d));


                        ContentValues values = new ContentValues();
                        values.put(SContract.TURB_Entry.COLUMN_TURB, result.get(0).value);
                        values.put(SContract.TURB_Entry.Date_Time, sdf.format(d)); //result.get(i).timestamp);
                        mContext.getContentResolver().insert(SContract.TURB_Entry.CONTENT_URI, values);


                    }


                }
            });

            (uc).handleUbidots(latitudeId, API_KEY, new Ubidot_Client.UbiListener() {
                @Override
                public void onDataReady(List<Ubidot_Client.Value> result) {
                    List<Entry> entries = new ArrayList();
                    List<String> labels = new ArrayList<String>();
                    ContentValues values = new ContentValues();

                    if (result.size() > 0) {
                        mContext.getContentResolver().delete(SContract.MAPS_Entry.CONTENT_URI, null, null);

                        map_values.put(SContract.MAPS_Entry.COLUMN_LAT, result.get(0).value);


                    }


                    //Toast.makeText(getActivity().getBaseContext(), "temp:"+val, Toast.LENGTH_SHORT).show();
                }
            });


            (uc).handleUbidots(longitudeId, API_KEY, new Ubidot_Client.UbiListener() {
                @Override
                public void onDataReady(List<Ubidot_Client.Value> result) {


                    // Convert timestamp to date
                    Date d = new Date(result.get(0).timestamp);

                    // Create Labels

                    map_values.put(SContract.MAPS_Entry.COLUMN_LONG, result.get(0).value);
                    map_values.put(SContract.MAPS_Entry.Date_Time, sdf.format(d));
                    mContext.getContentResolver().insert(SContract.MAPS_Entry.CONTENT_URI, map_values);


                    res = true; //Toast.makeText(getActivity().getBaseContext(), "temp:"+val, Toast.LENGTH_SHORT).show();
                }
            });


            return res;
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Boolean result) {


            fetchData();
            prog_ph.setProgress((int) ((pH_value / 14.0) * 100));
            prog_temp.setProgress((int) (temp_value));
            prog_turb.setProgress((int) ((Turb_value / 50.0) * 100));
            prog_phtext.setText(pH_value + "");
            prog_temptext.setText(temp_value + "");
            prog_turbtext.setText(Turb_value + "");
            dialog.dismiss();
            Toast.makeText(getBaseContext(), "Fetch Complete", Toast.LENGTH_SHORT).show();


        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
