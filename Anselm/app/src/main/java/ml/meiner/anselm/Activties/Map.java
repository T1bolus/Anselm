package ml.meiner.anselm.Activties;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import ml.meiner.anselm.DataBase.Chargingstation;
import ml.meiner.anselm.DataBase.CloudFirestore;
import ml.meiner.anselm.DataBase.CloudFirestoreListener;
import ml.meiner.anselm.R;

public class Map extends AppCompatActivity implements GoogleMap.OnMyLocationClickListener, OnMapReadyCallback, LocationListener, CloudFirestoreListener {
    //Google Map Object
    private GoogleMap mMap;

    static int MY_LOCATION_REQUEST_CODE = 1337;

    private LocationManager locationManager;
    private static final long MIN_TIME = 4000;
    private static final float MIN_DISTANCE = 1000;
    boolean once = false;

    ArrayList<Chargingstation> stations = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //Activate ASync to enable Map interaction
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
        }

        CloudFirestore cloudFirestore = CloudFirestore.getInstance();
        cloudFirestore.fetchAllChargingStations(this);
    }


    @Override
    public void onLocationChanged(Location location)
    {
        if(once == false)
        {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 12);
            mMap.animateCamera(cameraUpdate);
            locationManager.removeUpdates(this);
            once = true;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    public void onProviderEnabled(String provider) { }

    @Override
    public void onProviderDisabled(String provider) { }

    public void gotoInseration(View view) {
        Intent intent = new Intent(this, Inseration.class);
        startActivity(intent);
    }

    public void gotoHistory(View view) {
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Before enabling the My Location layer, you must request
            // location permission from the user.
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationClickListener(this);

        } else //No Permissions
        {
            //Request GPS Permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    //Handler for Permission Result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //TODO: Locaction wird noch nicht angezeigt nach dem akzeptieren der Berechtigung, erst wenn die Activity neu aufgerufen wird
                mMap.setMyLocationEnabled(true);
                mMap.setOnMyLocationClickListener(this);

                //Reload to using new Permissions
                Intent intent = new Intent(this, Map.class);
                startActivity(intent);
                return;
            }
            Toast.makeText(this, "Location Permissions declined:\n", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void chargingStationsReady(ArrayList<Chargingstation> stations)
    {
        this.stations = stations;

        //TODO: MAKER Löschen

        for(Chargingstation cs: stations)
        {
            LatLng pos = new LatLng(cs.getLatitude(), cs.getLongitude());

            // Creating a marker
            MarkerOptions markerOptions = new MarkerOptions();
            // Setting the position for the marker
            markerOptions.position(pos);
            markerOptions.flat(true);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.new_mark));
            // Setting the title for the marker.
            // This will be displayed on taping the marker
            markerOptions.title("Ladestation: " + cs.getName() + " : " + pos.latitude + " : " + pos.longitude);

            // Placing a marker on the touched position
            mMap.addMarker(markerOptions);
        }
    }
}
