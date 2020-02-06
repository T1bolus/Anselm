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

        if(station != null)
        {
            //TODO: Muss noch angepasst und erweitert werden
            EditText editText = findViewById(R.id.editText);
            editText.setText(station.getName());
            TextView userView = findViewById(R.id.userTextView);
            userView.setText(station.getUsername());
            ImageView imageView = findViewById(R.id.imageView2);
            Picasso.get().load(station.getUsernamePicturePath()).into(imageView);
        }
        else
        {
            finish();
            return;
        }
    }


}
