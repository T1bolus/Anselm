package ml.meiner.anselm.Main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import ml.meiner.anselm.Activties.History;
import ml.meiner.anselm.Activties.Inseration;
import ml.meiner.anselm.Activties.Map;
import ml.meiner.anselm.DataBase.Player;
import ml.meiner.anselm.DataBase.SQLiteDatabaseHandler;
import ml.meiner.anselm.R;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener
{

    private GoogleMap mMap;
    static int MY_LOCATION_REQUEST_CODE = 1339;
    private static final int RC_SIGN_IN = 1338;
    boolean logged_in = false;

    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    
    FirebaseUser user;

    private AdView mAdView;
    private SQLiteDatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googlemap);
        mapFragment.getMapAsync(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this); //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
        }

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // TESTAREA
        // create our sqlite helper class
        db = new SQLiteDatabaseHandler(this);
        // create some players
        Player player1 = new Player(1, "Lebron James", "F", 203);
        Player player2 = new Player(2, "Kevin Durant", "F", 208);
        Player player3 = new Player(3, "Rudy Gobert", "C", 214);
        // add them
        db.addPlayer(player1);
        db.addPlayer(player2);
        db.addPlayer(player3);
        // list all players
        List<Player> players = db.allPlayers();

        if (players != null) {
            String[] itemsNames = new String[players.size()];

            for (int i = 0; i < players.size(); i++) {
                itemsNames[i] = players.get(i).toString();
            }

            // display like string instances
            ListView list = (ListView) findViewById(R.id.list);
            list.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, itemsNames));

        }




    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        mMap.animateCamera(cameraUpdate);
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    public void onProviderEnabled(String provider) { }

    @Override
    public void onProviderDisabled(String provider) { }


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
        intent.putExtra("User", user);
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

    public void login(View view)
    {
        if(logged_in == false)
        {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    //new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build());

            // Create and launch sign-in intent
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);

        }
        else
        {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            // ...
                            logged_in = false;

                            Button btn = MainActivity.this.findViewById(R.id.buttonsignup);
                            btn.setText("Einloggen");

                            TextView nameLabel = MainActivity.this.findViewById((R.id.textView));
                            nameLabel.setText("");
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                user = FirebaseAuth.getInstance().getCurrentUser();
                Button btn = this.findViewById(R.id.buttonsignup);
                TextView nameLabel = this.findViewById(R.id.textView);
                if(user != null)
                {
                    btn.setText("Ausloggen");
                    nameLabel.setText("Hallo " + user.getDisplayName());
                    logged_in = true;
                }

                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                if(response != null)
                {
                    TextView nameLabel = this.findViewById((R.id.textView));
                    nameLabel.setText("Anmeldung fehlgeschlagen Fehler:" + response.getError().getMessage());
                }
            }
        }
    }
}
