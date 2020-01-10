package ml.meiner.anselm.Activties;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import ml.meiner.anselm.R;

public class Map extends AppCompatActivity implements GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback
{
    //Google Map Object
    private GoogleMap mMap;

    static int MY_LOCATION_REQUEST_CODE = 1337;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //Activate ASync to enable Map interaction
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void gotoHistory(View view)
    {
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }

    public void gotoRating(View view)
    {
        Intent intent = new Intent(this, Rating.class);
        startActivity(intent);
    }


    @Override
    public void onMapReady(GoogleMap map)
    {
        mMap = map;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Before enabling the My Location layer, you must request
            // location permission from the user.
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationClickListener(this);

        }
        else //No Permissions
        {
            //Request GPS Permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_REQUEST_CODE);
        }
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                // Clears the previously touched position
                mMap.clear();

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);
            }
        });


        // Add a marker
        // and move the map's camera to the same location.
        LatLng oldenburg = new LatLng(53.1267, 8.2384);
        map.addMarker(new MarkerOptions().position(oldenburg)
                .title("Marker in Oldenburg"));
        map.moveCamera(CameraUpdateFactory.newLatLng(oldenburg));
    }


    @Override
    public void onMyLocationClick(@NonNull Location location)
    {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    //Handler for Permission Result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //TODO: Locaction wird noch nicht angezeigt nach dem akzeptieren der Berechtigung, erst wenn die Activity neu aufgerufen wird
                mMap.setMyLocationEnabled(true);
                mMap.setOnMyLocationClickListener(this);
                return;
            }
            Toast.makeText(this, "Location Permissions declined:\n", Toast.LENGTH_LONG).show();
        }
    }
}
