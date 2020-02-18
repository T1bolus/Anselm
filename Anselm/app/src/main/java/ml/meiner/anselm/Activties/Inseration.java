package ml.meiner.anselm.Activties;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import ml.meiner.anselm.DataBase.Chargingstation;
import ml.meiner.anselm.DataBase.FirestoreDatabase;
import ml.meiner.anselm.DataBase.FirestoreDatabaseChargingstationListener;
import ml.meiner.anselm.R;

public class Inseration extends FragmentActivity implements OnMapReadyCallback, FirestoreDatabaseChargingstationListener {

    //instanziert die SearchView und das MapFragment
    GoogleMap map;
    SupportMapFragment mapFragment;
    FirebaseUser user;
    PlacesClient plclient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ruft Activity auf
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inseration);

        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            Toast.makeText(getApplicationContext(), user.getDisplayName(), Toast.LENGTH_SHORT).show();
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Nicht angemeldet!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            finish();
            return;
        }

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googlemap);
        mapFragment.getMapAsync(this);

        FirestoreDatabase firestoreDatabase = FirestoreDatabase.getInstance();
        firestoreDatabase.fetchAllChargingStations(this);

        String apikey = "AIzaSyDYoQybddM6c-Daz0bHVe7h2tuyzxHmW1k";

        if(!Places.isInitialized()) {
            Places.initialize(getApplicationContext(),apikey);
        }

        plclient = Places.createClient(this);

        final AutocompleteSupportFragment autoFrag = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autoFrag.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME));

        autoFrag.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                final LatLng latlng = place.getLatLng();

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latlng, 16);
                map.animateCamera(cameraUpdate);
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                // Remove the marker
                //marker.remove();
            }
        });

        LatLng latLng = new LatLng(53, 8);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 6);
        map.animateCamera(cameraUpdate);
    }

    public void insertMark(android.view.View view) throws IOException {

        Geocoder geocoder;
        List<Address> addresses;

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
        goToInseration2(view, address, longitude, latitude);
    }

    public void goToInseration2(View view, String address, Double longitude, Double latitude) {
        Intent intent = new Intent(this, Inseration2.class);
        intent.putExtra("longitude", longitude);
        intent.putExtra("latitude", latitude);
        intent.putExtra("address", address);
        startActivity(intent);
    }

    @Override
    public void chargingStationsReady(ArrayList<Chargingstation> stations) {


        for (Chargingstation cs : stations) {
            LatLng pos = new LatLng(cs.getLatitude(), cs.getLongitude());

            // Creating a marker
            MarkerOptions markerOptions = new MarkerOptions();
            // Setting the position for the marker
            markerOptions.position(pos);
            //markerOptions.flat(true);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.new_mark));
            // Setting the title for the marker.
            // This will be displayed on taping the marker
            markerOptions.title(cs.getName() + ": " + pos.latitude + " : " + pos.longitude);

            // Placing a marker on the touched position
            map.addMarker(markerOptions);
        }
    }

}
