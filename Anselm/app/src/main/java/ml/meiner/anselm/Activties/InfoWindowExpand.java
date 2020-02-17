package ml.meiner.anselm.Activties;

import androidx.appcompat.app.AppCompatActivity;


import android.widget.Switch;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

            showPlugs(station);

            // String freeString  = new SimpleDateFormat("MMM dd, yyyyy", Locale.getDefault()).format(station.getFreeTimes());
            // TextView freeView = findViewById(R.id.freeTimesView);
            //freeView.setText(freeString);

        } else {
            finish();
            return;
        }
    }

    public void showPlugs(Chargingstation station) {

        Switch switchview1 = findViewById(R.id.typ1);
        if (station.isTyp1()) {
            switchview1.toggle();
        }

        Switch switchview2 = findViewById(R.id.typ2);
        if (station.isTyp2()) {
            switchview2.toggle();
        }

        Switch switchview3 = findViewById(R.id.ccs);
        if (station.isCcs()) {
            switchview3.toggle();
        }

        Switch switchview4 = findViewById(R.id.chademo);
        if (station.isChademo()) {
            switchview4.toggle();
        }

        Switch switchview5 = findViewById(R.id.schuko);
        if (station.isSchuko()) {
            switchview5.toggle();
        }

        Switch switchview6 = findViewById(R.id.cee_blue);
        if (station.isCee_blue()) {
            switchview6.toggle();
        }

        Switch switchview7 = findViewById(R.id.cee16);
        if (station.isCee16()) {
            switchview7.toggle();
        }

        Switch switchview8 = findViewById(R.id.cee32);
        if (station.isCee32()) {
            switchview8.toggle();
        }

    }

}
