package ml.meiner.anselm.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import ml.meiner.anselm.Activties.History;
import ml.meiner.anselm.Activties.Inseration;
import ml.meiner.anselm.Activties.Map;
import ml.meiner.anselm.DataBase.DataHandlerActivity;
import ml.meiner.anselm.DataBase.UsersDatabaseAdapter;
import ml.meiner.anselm.R;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    UsersDatabaseAdapter usersDatabaseAdapter;

    GoogleMap map;
    SupportMapFragment mapFragment;
    private static final int RC_SIGN_IN = 1338;
    boolean logged_in = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googlemap);
        mapFragment.getMapAsync(this);

        usersDatabaseAdapter = new UsersDatabaseAdapter(getApplicationContext());


    }






    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        /*map.addMarker(new MarkerOptions()
                .position(latlng)
                .title("Marker"));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 10));
        */
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

    public void gotoTestActivity(View view) {
        Intent intent = new Intent(this, DataHandlerActivity.class);
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
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Button btn = this.findViewById(R.id.buttonsignup);
                btn.setText("Ausloggen");
                TextView nameLabel = this.findViewById((R.id.textView));
                nameLabel.setText("Hallo " + user.getDisplayName());
                logged_in = true;

                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                TextView nameLabel = this.findViewById((R.id.textView));
                nameLabel.setText("Anmeldung fehlgeschlagen Fehler:" + response.getError().getMessage());
            }
        }
    }
}
