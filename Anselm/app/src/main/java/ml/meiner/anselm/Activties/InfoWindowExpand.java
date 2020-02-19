package ml.meiner.anselm.Activties;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import ml.meiner.anselm.DataBase.Booking;
import ml.meiner.anselm.DataBase.Chargingstation;
import ml.meiner.anselm.DataBase.FirestoreDatabase;
import ml.meiner.anselm.Main.MainActivity;
import ml.meiner.anselm.R;


public class InfoWindowExpand extends AppCompatActivity {
    Chargingstation station;

    FirebaseUser user;

    public int selected_hourFrom;
    public int selected_minuteFrom;
    public int selected_hourTo;
    public int selected_minuteTo;

    Date realDateFrom;
    Date realDateTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_window_expand);


        station = (Chargingstation) getIntent().getSerializableExtra("station");
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (station != null) {
            //TODO: Muss noch angepasst und erweitert werden

            //Setting the picture form firebase of the owner of the station
            ImageView imageView = findViewById(R.id.userPicture);
            Picasso.get().load(station.getUsernamePicturePath()).into(imageView);

            TextView uView = findViewById(R.id.userNameView);
            uView.setText(user.getDisplayName());

            // Displaying the name of the owner of the station
            TextView userView = findViewById(R.id.stationOwnerTextView);
            userView.setText(station.getUsername());

            // Displaying the name of the station
            TextView stationNameView = findViewById(R.id.stationNameTextView);
            stationNameView.setText(station.getName());

            // Display address of station
            TextView addressView = findViewById(R.id.stationAddressTextView);
            addressView.setText(station.getAddress());

            // Display Price of station
            TextView priceView = findViewById(R.id.stationPriceTextView);
            priceView.setText(Float.toString(station.getPph()) + " €.");

            showPlugs(station);

            String pattern = "EEEE dd-MM HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            TextView startView = findViewById(R.id.startTextView);
            String date = simpleDateFormat.format(station.getFreeTimes().get(0));
            startView.setText(date);

            TextView endView = findViewById(R.id.endTextView);
            date = simpleDateFormat.format(station.getFreeTimes().get(1));
            endView.setText(date);


            // String freeString  = new SimpleDateFormat("MMM dd, yyyyy", Locale.getDefault()).format(station.getFreeTimes());
            // TextView freeView = findViewById(R.id.freeTimesView);
            //freeView.setText(freeString);

            Spinner dropdown = findViewById(R.id.planets_spinner3);
            String[] items = new String[]{"Mo", "Di", "Mi", "Do", "Fr", "Sa", "So"};
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
            dropdown.setAdapter(adapter);

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

    public static Date getWeekStartDate() {
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        return calendar.getTime();
    }

    public int giveDay(String day){
        int m_day=99;
        switch (day) {
            case ("Mo"):
                m_day=0;
                return m_day;
            case ("Di"):
                m_day=1;
                return m_day;
            case ("Mi"):
                m_day=2;
                return m_day;
            case ("Do"):
                m_day=3;
                return m_day;
            case ("Fr"):
                m_day=4;
                return m_day;
            case ("Sa"):
                m_day=5;
                return m_day;
            case ("So"):
                m_day=6;
                return m_day;
        }
        return m_day;
    }

    public void bookAStation(View view) {

        if (user == null) {

            Toast.makeText(this, "Please log in!", Toast.LENGTH_LONG).show();

            return;
        }

        station = (Chargingstation) getIntent().getSerializableExtra("station");

        Booking book = new Booking();

        book.setStation(station);
        book.setStationOwnerUid(station.getUid());
        book.setUsername(user.getDisplayName());
        book.setUid(user.getUid());
        book.setUsernamePicturePath(user.getPhotoUrl().toString());


        Spinner spinner = findViewById(R.id.planets_spinner3);
        int days = giveDay(spinner.getSelectedItem().toString());


        Date weekStart = getWeekStartDate();
        Date startDay = weekStart;

        //Increment startDay
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(startDay);
        calStart.add(Calendar.DATE, days);

        String giveDateFrom = calStart.get(Calendar.YEAR) + "-" + (calStart.get(Calendar.MONTH)+1) + "-" + calStart.get(Calendar.DAY_OF_MONTH);
        String giveTimeFrom = selected_hourFrom + ":" + selected_minuteFrom;
        String giveTimeTo = selected_minuteFrom + ":" + selected_minuteTo;
        Date realDateFrom = null;
        Date realDateTo = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            realDateFrom = formatter.parse(giveDateFrom + " " + giveTimeFrom);
            realDateTo = formatter.parse(giveDateFrom + " " + giveTimeTo);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if((selected_hourTo < selected_hourFrom) || (selected_hourTo == selected_hourFrom && selected_minuteTo < selected_minuteFrom) || (selected_hourTo == 0 && selected_hourFrom == 0 && selected_minuteTo == 0 && selected_minuteFrom == 0))
        {
            Toast.makeText(this, "Impossible Time!", Toast.LENGTH_LONG).show();
            return;
        }

        ArrayList<Date> bookedtimes = new ArrayList<>();
        bookedtimes.add(realDateFrom);
        bookedtimes.add(realDateTo);
        book.setBookedTimes(bookedtimes);



        FirestoreDatabase.getInstance().addBooking(this,book);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void changeTime(final View view) {
        Context m_context = this;
        Calendar calender = Calendar.getInstance();
        final int hour = calender.get(calender.HOUR_OF_DAY);
        final int min = calender.get(calender.MINUTE);
        TimePickerDialog timeDialog = new TimePickerDialog(m_context, R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                switch (view.getId()) {
                    case (R.id.time_spinner):
                        Button butt = findViewById(R.id.time_spinner);
                        butt.setText(i + " : " + i1);
                        selected_hourFrom = i;
                        selected_minuteFrom = i1;
                        break;
                    case (R.id.time_spinner2):
                        Button butt2 = findViewById(R.id.time_spinner2);
                        butt2.setText(i + " : " + i1);
                        selected_hourTo = i;
                        selected_minuteTo = i1;
                        break;
                }

            }
        }, hour, min, android.text.format.DateFormat.is24HourFormat(m_context));
        timeDialog.show();
    }

}
