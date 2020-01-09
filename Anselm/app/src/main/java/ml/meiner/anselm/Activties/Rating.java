package ml.meiner.anselm.Activties;

import androidx.appcompat.app.AppCompatActivity;

import ml.meiner.anselm.Main.MainActivity;
import ml.meiner.anselm.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Rating extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
    }

    public void gotoMap(View view)
    {
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
    }

    public void gotoHistory(View view)
    {
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }
}
