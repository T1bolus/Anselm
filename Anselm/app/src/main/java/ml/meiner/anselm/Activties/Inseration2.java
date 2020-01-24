package ml.meiner.anselm.Activties;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import ml.meiner.anselm.R;

public class Inseration2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inseration2);
        Intent i = getIntent();
        String address = i.getStringExtra("address");
        TextView tv = (TextView)findViewById(R.id.addressBox);
        tv.setText(address);
    }


}



