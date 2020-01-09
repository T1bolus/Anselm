package ml.meiner.anselm.Acitivies;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import ml.meiner.anselm.R;

public class MapActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.FirstMap);

    }
    public class MapsMarkerActivity extends AppCompatActivity
            implements OnMapReadyCallback {
        // Include the OnCreate() method here too, as described above.
        @Override
        public void onMapReady(GoogleMap googleMap) {
            // Add a marker in Sydney, Australia,
            // and move the map's camera to the same location.
            LatLng sydney = new LatLng(-33.852, 151.211);
            googleMap.addMarker(new MarkerOptions().position(sydney)
                    .title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }
}
