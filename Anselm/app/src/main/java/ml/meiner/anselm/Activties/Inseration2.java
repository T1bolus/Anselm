package ml.meiner.anselm.Activties;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import ml.meiner.anselm.DataBase.Chargingstation;
import ml.meiner.anselm.DataBase.FirestoreDatabase;
import ml.meiner.anselm.Main.MainActivity;
import ml.meiner.anselm.R;

import static android.app.PendingIntent.getActivity;

public class Inseration2 extends AppCompatActivity  {

    FirestoreDatabase firestoreDatabase = FirestoreDatabase.getInstance();
    FirebaseUser user;
    Calendar calendar;
    double longitude = 0;
    double latitude = 0;
    public int selected_hourFrom;
    public int selected_minuteFrom;
    public int selected_hourTo;
    public int selected_minuteTo;
    public int current_month = calendar.get(Calendar.MONTH);
    public int current_year = calendar.get(Calendar.YEAR);
    public String from_Day;
    public String to_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inseration2);
        Spinner dropdown = findViewById(R.id.planets_spinner);
        Spinner dropdown2 = findViewById(R.id.planets_spinner2);
        String[] items = new String[]{"Mo", "Di", "Mi", "Do", "Fr", "Sa", "So"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown2.setAdapter(adapter);
        //Get information
        Intent i = getIntent();
        String address = i.getStringExtra("address");
        longitude = i.getDoubleExtra("longitude", 0);
        latitude = i.getDoubleExtra("latitude", 0);



        TextView tv = findViewById(R.id.addressBox);
        tv.setText(address);


        //FIREBASE LOGIN
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) //Only logged in User should be able to pass
        {
            Toast.makeText(this, "Please Login to add charging station!", Toast.LENGTH_LONG).show();

            finish();
            return;
        }
        else
        {
            TextView userText = findViewById(R.id.textView8);
            userText.setText(user.getDisplayName());
        }
    }
    public void changeTime(final View view){
        Context m_context = this;
        Calendar calender = Calendar.getInstance();
        final int hour = calender.get(calender.HOUR_OF_DAY);
        final int min = calender.get(calender.MINUTE);
        TimePickerDialog timeDialog = new TimePickerDialog(m_context, R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                switch (view.getId()) {
                    case (R.id.time_spinner):
                        Button butt = findViewById(R.id.time_spinner);
                        butt.setText(i + " : " + i1);
                        selected_hourFrom=i;
                        selected_minuteFrom=i1;
                        break;
                    case (R.id.time_spinner2):
                        Button butt2 = findViewById(R.id.time_spinner2);
                        butt2.setText(i + " : " + i1);
                        selected_hourTo=i;
                        selected_minuteTo=i1;
                        break;
                }

            }
        }, hour, min, android.text.format.DateFormat.is24HourFormat(m_context));
        timeDialog.show();
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
        typ1 = switchv.isChecked();

        switchv = findViewById(R.id.typ2);
        typ2 = switchv.isChecked();

        switchv = findViewById(R.id.ccs);
        ccs = switchv.isChecked();

        switchv = findViewById(R.id.chademo);
        chademo = switchv.isChecked();

        switchv = findViewById(R.id.schuko);
        schuko = switchv.isChecked();

        switchv = findViewById(R.id.cee_blue);
        cee_blue = switchv.isChecked();

        switchv = findViewById(R.id.cee16);
        cee16 = switchv.isChecked();

        switchv = findViewById(R.id.cee32);
        cee32 = switchv.isChecked();
        String giveDateFrom = current_year + "-" + current_month + "-" + from_Day;
        String giveDateTo = current_year + "-" + current_month + "-" + to_day;
        String giveTimeFrom = selected_hourFrom + ":" + selected_minuteFrom;
        String giveTimeTo = selected_minuteFrom + ":" + selected_minuteTo;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd HH:mm");
        try {

            Date realDateFrom = formatter.parse(giveDateFrom + " " + giveTimeFrom);
            Date realDateTo = formatter.parse(giveDateTo + " " + giveTimeTo);

        } catch (ParseException e) {
            e.printStackTrace();
        }

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
        station.setUsernamePicturePath(user.getPhotoUrl().toString());
        station.setUid(user.getUid());
        station.setId(""); //TODOOO



        firestoreDatabase.addChargingStation(this, station);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}



