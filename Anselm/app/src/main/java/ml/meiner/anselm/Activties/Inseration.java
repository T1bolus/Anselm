package ml.meiner.anselm.Activties;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.SearchView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.List;

import ml.meiner.anselm.R;

public class Inseration extends FragmentActivity implements OnMapReadyCallback {

    //instanziert die SearchView und das MapFragment
    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;
    Marker markerCenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // ruft Actinivty auf
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inseration);

        searchView = findViewById(R.id.sv_location);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googlemap);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(Inseration.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latlng = new LatLng(address.getLatitude(), address.getLongitude());
                    //map.addMarker(new MarkerOptions().position(latlng).title(location));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 10));

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(map.getCameraPosition().target);
        markerCenter = map.addMarker(markerOptions);

        map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            public void onCameraMove() {
                markerCenter.setPosition(map.getCameraPosition().target);
            }
        });
    }
}



