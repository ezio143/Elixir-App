package com.example.dell.elixir.Activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.elixir.R;
import com.example.dell.elixir.SQL_db.SContract;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    GoogleMap m_map;
    boolean mapReady = false;
    MapFragment mapFragment;
    Button btn_map, btn_hybrid, btn_satellite, btn_go;
    LatLng mLatLng = new LatLng(12.9634856,77.5037206);
    MarkerOptions DrAit,sensor1;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_map = (Button) findViewById(R.id.btn_1);
        btn_hybrid = (Button) findViewById(R.id.btn_3);
        btn_satellite = (Button) findViewById(R.id.btn_2);

        DrAit = new MarkerOptions().position(new LatLng(12.963268,77.506189)).title("Dr-Ait").icon(BitmapDescriptorFactory.fromResource(R.drawable.white_marker));

        btn_map.setOnClickListener(this);
        btn_satellite.setOnClickListener(this);
        btn_hybrid.setOnClickListener(this);

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapReady = true;
        m_map = googleMap;
        m_map.clear();
        m_map.addMarker(DrAit);
        m_map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        try{
        cursor = getContentResolver().query(SContract.MAPS_Entry.CONTENT_URI,new String[]{"*"},null,null,null);
           if(cursor.moveToFirst()) {
                double latx = cursor.getDouble(cursor.getColumnIndex(SContract.MAPS_Entry.COLUMN_LAT));
                double longx = cursor.getDouble(cursor.getColumnIndex(SContract.MAPS_Entry.COLUMN_LONG));
                latx = Math.round(latx * 1000)/1000.0;
                longx = Math.round(longx * 1000)/1000.0;
                mLatLng = new LatLng(latx, longx);
                sensor1 = new MarkerOptions().position(mLatLng).title("sensor 1").icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_marker));
                m_map.addMarker(sensor1);
                Toast.makeText(getBaseContext(), "location found "+latx+" , "+longx, Toast.LENGTH_SHORT).show();
            }

        }catch(Exception e){
            Toast.makeText(getBaseContext(), "location error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


        CameraPosition target = CameraPosition.builder().target(mLatLng).zoom(14).bearing(50).tilt(60).build();
        flyto(target);

    }

    private void flyto(CameraPosition target) {
        m_map.animateCamera(CameraUpdateFactory.newCameraPosition(target), 10000, null);
    }

    @Override
    public void onClick(View view) {


         if (mapReady) {
            if (view == btn_map) {
                m_map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            } else if (view == btn_hybrid) {
                m_map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            } else if (view == btn_satellite) {
                m_map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        }
    }
}
