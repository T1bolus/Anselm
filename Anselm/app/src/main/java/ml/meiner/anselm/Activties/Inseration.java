package ml.meiner.anselm.Activties;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.fragment.app.FragmentActivity;
import ml.meiner.anselm.DataBase.Booking;
import ml.meiner.anselm.DataBase.Chargingstation;
import ml.meiner.anselm.DataBase.FirestoreDatabase;
import ml.meiner.anselm.DataBase.FirestoreDatabaseChargingstationListener;
import ml.meiner.anselm.Main.MainActivity;
import ml.meiner.anselm.R;

public class Inseration extends FragmentActivity implements OnMapReadyCallback, FirestoreDatabaseChargingstationListener {

    //instanziert die SearchView und das MapFragment
    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // ruft Actinivty auf
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inseration);


        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null)
        {
            Toast.makeText(getApplicationContext(), user.getDisplayName(), Toast.LENGTH_SHORT).show();
        }
        else
        {
            Context context = getApplicationContext();
            CharSequence text = "Nicht angemeldet!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();


            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

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


        FirestoreDatabase firestoreDatabase = FirestoreDatabase.getInstance();
        firestoreDatabase.fetchAllChargingStations(this);

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

        LatLng latLng = new LatLng(53, 8);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 6);
        map.animateCamera(cameraUpdate);
    }

    public void insertMark(android.view.View view) throws IOException {

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
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.draggable(true);
//        markerOptions.flat(true);
//        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.new_mark));
//        markerOptions.position(map.getCameraPosition().target);
//        markerOptions.title("Ladestation: " + address);
//        map.addMarker(markerOptions);
        goToInseration2(view, address, longitude, latitude);
    }

    public void goToInseration2(View view, String address, Double longitude, Double latitude ){
        Intent intent = new Intent(this, Inseration2.class);
        intent.putExtra("longitude", longitude);
        intent.putExtra("latitude", latitude);
        intent.putExtra("address", address);
        startActivity(intent);
    }


    @Override
    public void chargingStationsReady(ArrayList<Chargingstation> stations)
    {

        //TODO: MARKER Löschen

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
            markerOptions.title(cs.getName() + ": " + pos.latitude + " : " + pos.longitude);

            // Placing a marker on the touched position
            map.addMarker(markerOptions);
        }
    }
}
