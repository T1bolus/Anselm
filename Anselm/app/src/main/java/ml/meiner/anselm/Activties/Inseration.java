package ml.meiner.anselm.Activties;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.fragment.app.FragmentActivity;
import ml.meiner.anselm.R;

public class Inseration extends FragmentActivity implements OnMapReadyCallback {

    //instanziert die SearchView und das MapFragment
    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;
    Marker markerCenter;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.isEmailVerified())
        {
            TextView view = this.findViewById(R.id.textView2);
            if(user.getDisplayName() != null)
            {
                view.setText("User: " + user.getDisplayName());
            }
            view.setText("User: FAILED");

            // User is signed in
        }
        else {
            TextView view = this.findViewById(R.id.textView2);
            view.setText("Nicht angemeldet!");
            // No user is signed in
        }
         */





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
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18));

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
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                // Remove the marker
                marker.remove();
            }
        });
    }

    public void insertMark(android.view.View view) throws IOException {


       GoogleMap mMap = map;
        Geocoder geocoder;
        List<Address> addresses;


        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }

        LatLng lol = map.getCameraPosition().target;
        double longitude = lol.longitude;
        double latitude = lol.latitude;
        geocoder = new Geocoder(this, Locale.getDefault());

        addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.draggable(true);
        markerOptions.flat(true);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.new_mark));
        markerOptions.position(map.getCameraPosition().target);
        markerOptions.title("Ladestation: " + address);
        map.addMarker(markerOptions);
    }
}



