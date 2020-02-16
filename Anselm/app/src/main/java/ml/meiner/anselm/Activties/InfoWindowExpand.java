package ml.meiner.anselm.Activties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import ml.meiner.anselm.DataBase.Chargingstation;
import ml.meiner.anselm.R;


public class InfoWindowExpand extends AppCompatActivity {
    Chargingstation station;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_window_expand);


        station = (Chargingstation) getIntent().getSerializableExtra("station");

        if (station != null) {
            //TODO: Muss noch angepasst und erweitert werden

            // Setting the picture form firebase of the owner of the station
            ImageView imageView = findViewById(R.id.imageView);
            Picasso.get().load(station.getUsernamePicturePath()).into(imageView);

            // Displaying the name of the owner of the station
            TextView userView = findViewById(R.id.stationOwnerTextView);
            userView.setText(station.getUsername());

            // Displaying the name of the station
            TextView stationNameView = findViewById(R.id.stationNameTextView);
            stationNameView.setText("Name: " + station.getName());

            // Display address of station
            TextView addressView = findViewById(R.id.stationAddressTextView);
            addressView.setText("Addresse: " + station.getAddress());

            // Display Price of station
            TextView priceView = findViewById(R.id.stationPriceTextView);
            priceView.setText("Preis pro Stunde: " + Float.toString(station.getPph()) + " €.");

            TextView plugView = findViewById(R.id.plugTextView);
            plugView.setText(getAllPlugs(station));


        } else {
            finish();
            return;
        }
    }

    public String getAllPlugs(Chargingstation station) {
        // TODO: Leeren
        String s = "Test";

        if (!station.isTyp1()) {
            s.concat("Typ 1 + ");
        }

        if (station.isTyp2()) {
            s.concat("Typ 2 + ");
        }

        if (station.isCcs()) {
            s.concat("CCs + ");
        }

        if (station.isChademo()) {
            s.concat("Chademo + ");
        }

        if (station.isSchuko()) {
            s.concat("Schuko + ");
        }

        if (station.isCee_blue()) {
            s.concat("Cee_blue + ");
        }

        if (station.isCee16()) {
            s.concat("Cee16 + ");
        }

        if (station.isCee32()) {
            s.concat("Cee32");
        }

        return s;

    }


}
