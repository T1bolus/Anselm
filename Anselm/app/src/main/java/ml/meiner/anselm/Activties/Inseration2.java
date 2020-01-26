package ml.meiner.anselm.Activties;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import ml.meiner.anselm.DataBase.Chargingstation;
import ml.meiner.anselm.DataBase.CloudFirestore;
import ml.meiner.anselm.Main.MainActivity;
import ml.meiner.anselm.R;

public class Inseration2 extends AppCompatActivity {

    CloudFirestore cloudFirestore = CloudFirestore.getInstance();
    FirebaseUser user;

    double longitude = 0;
    double latitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inseration2);

        //Get information
        Intent i = getIntent();
        String address = i.getStringExtra("address");
        longitude = i.getDoubleExtra("longitude", 0);
        latitude = i.getDoubleExtra("latitude", 0);




        TextView tv = (TextView)findViewById(R.id.addressBox);
        tv.setText(address);


        //FIREBASE LOGIN
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) //Only logged in User should be able to pass
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else
        {
            TextView userText = findViewById(R.id.textView8);
            userText.setText(user.getDisplayName());
        }

    }


    public void insert(View view)
    {
        EditText textView;
        Switch switchv;

        Chargingstation station = new Chargingstation();

        String firstName;
        String lastName;
        String address;
        float pph;
        boolean typ1;
        boolean typ2;
        boolean ccs;
        boolean chademo;
        boolean schuko;
        boolean cee_blue;
        boolean cee16;
        boolean cee32;

        textView = findViewById(R.id.firstName);
        firstName = textView.getText().toString();

        textView = findViewById(R.id.lastName);
        lastName = textView.getText().toString();

        textView = findViewById(R.id.addressBox);
        address = textView.getText().toString();

        textView = findViewById(R.id.pph);
        pph = Float.valueOf(textView.getText().toString());

        switchv = findViewById(R.id.typ1);
        typ1 = switchv.getShowText();

        switchv = findViewById(R.id.typ2);
        typ2 = switchv.getShowText();

        switchv = findViewById(R.id.ccs);
        ccs = switchv.getShowText();

        switchv = findViewById(R.id.chademo);
        chademo = switchv.getShowText();

        switchv = findViewById(R.id.schuko);
        schuko = switchv.getShowText();

        switchv = findViewById(R.id.cee_blue);
        cee_blue = switchv.getShowText();

        switchv = findViewById(R.id.cee16);
        cee16 = switchv.getShowText();

        switchv = findViewById(R.id.cee32);
        cee32 = switchv.getShowText();


        station.setName(firstName + " " + lastName);
        station.setAddress(address);
        station.setPph(pph);
        station.setTyp1(typ1);
        station.setTyp2(typ2);
        station.setCcs(ccs);
        station.setChademo(chademo);
        station.setSchuko(schuko);
        station.setCee_blue(cee_blue);
        station.setCee16(cee16);
        station.setCee32(cee32);
        station.setLongitude(longitude);
        station.setLatitude(latitude);
        station.setUsername(user.getDisplayName());
        station.setUsernamePicturePath(user.getPhotoUrl().getPath());
        station.setUid(user.getUid());
        station.setId(""); //TODOOO


        cloudFirestore.addChargingStation(station);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}



