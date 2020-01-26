package ml.meiner.anselm.Activties;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
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

        if (user != null) //Only logged in User should be able to pass
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else
        {
            TextView userText = (TextView)findViewById(R.id.textView8);
            userText.setText(user.getDisplayName());
        }



    }


    public void insert(View view)
    {

    }


}



