package ml.meiner.anselm.Main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import ml.meiner.anselm.Activties.History;
import ml.meiner.anselm.Activties.Inseration;
import ml.meiner.anselm.Activties.Map;
import ml.meiner.anselm.DataBase.Booking;
import ml.meiner.anselm.DataBase.Chargingstation;
import ml.meiner.anselm.DataBase.FirestoreDatabase;
import ml.meiner.anselm.DataBase.FirestoreDatabaseChargingstationListener;
import ml.meiner.anselm.R;





public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, FirestoreDatabaseChargingstationListener {

    private GoogleMap mMap;
    static int MY_LOCATION_REQUEST_CODE = 1339;
    private static final int RC_SIGN_IN = 1338;

    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;

    ArrayList<Chargingstation> stations = new ArrayList<>();


    FirebaseUser user;

    private AdView mAdView;
    private static final String TAG = "GoogleActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            TextView nameLabel = this.findViewById(R.id.textView);
            nameLabel.setText("Hallo " + user.getDisplayName());

            ImageView imageView = findViewById(R.id.buttonsignup);
            Picasso.get().load(user.getPhotoUrl().toString()).into(imageView);
        }
        else
        {
            TextView nameLabel = this.findViewById(R.id.textView);
            nameLabel.setText("Nicht angemeldet!");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googlemap);
        mapFragment.getMapAsync(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
        }



        FirestoreDatabase firestoreDatabase = FirestoreDatabase.getInstance();
        firestoreDatabase.fetchAllChargingStations(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 12);
        mMap.animateCamera(cameraUpdate);
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }


    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Before enabling the My Location layer, you must request
            // location permission from the user.
            mMap.setMyLocationEnabled(true);


        } else //No Permissions
        {
            //Request GPS Permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_REQUEST_CODE);
        }

        LatLng latLng = new LatLng(53, 8);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);
        mMap.animateCamera(cameraUpdate);
    }

    //Handler for Permission Result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //TODO: Locaction wird noch nicht angezeigt nach dem akzeptieren der Berechtigung, erst wenn die Activity neu aufgerufen wird
                mMap.setMyLocationEnabled(true);

                return;
            }
            Toast.makeText(this, "Location Permissions declined:\n", Toast.LENGTH_LONG).show();
        }
    }

    public void gotoInseration(View view) {

        Intent intent = new Intent(this, Inseration.class);
        startActivity(intent);
    }


    public void gotoHistory(View view) {
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }

    public void gotoMap(View view) {
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
    }

    public void login(View view) {
        if(user == null)
            signIn();
        else
            signOut();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                user = FirebaseAuth.getInstance().getCurrentUser();

                TextView textView = findViewById(R.id.textView);
                if(textView != null)
                    textView.setText("Hallo " + user.getDisplayName());

                Toast.makeText(MainActivity.this, "Hallo " + user.getDisplayName(), Toast.LENGTH_LONG).show();

                ImageView imageView = findViewById(R.id.buttonsignup);
                Picasso.get().load(user.getPhotoUrl().toString()).into(imageView);
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...

                Toast.makeText(MainActivity.this, "Login failed: " + response.getError().getMessage(), Toast.LENGTH_LONG).show();

            }
        }
    }


    private void signIn() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }


    private void signOut() {

        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "Erfolgreich abgemeldet!", Toast.LENGTH_LONG).show();

                        TextView textView = findViewById(R.id.textView);
                        if(textView != null)
                            textView.setText("Nicht angemeldet!");

                        ImageView imageView = findViewById(R.id.buttonsignup);
                        imageView.setImageURI(null);
                        imageView.setImageURI(Uri.parse("android.resource://ml.meiner.anselm/"+R.drawable.user));

                        user = null;
                    }
                });

    }

    @Override
    public void chargingStationsReady(ArrayList<Chargingstation> stations)
    {
        this.stations = stations;

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
           // markerOptions.title("Ladestation: " + cs.getName() + " : " + pos.latitude + " : " + pos.longitude);

            // Placing a marker on the touched position
            mMap.addMarker(markerOptions);
        }


//        Booking booking = new Booking();
//        booking.setUid(user.getUid());
//        booking.setStation(new stations.get(0));
//        booking.setUsername(user.getDisplayName());
//        booking.setStationOwnerUid(stations.get(0).getUid());
//        booking.setUsernamePicturePath(user.getPhotoUrl().toString());
//
//        FirestoreDatabase firestoreDatabase = FirestoreDatabase.getInstance();
//        firestoreDatabase.addBooking(this, booking);
    }
}
