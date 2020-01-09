package ml.meiner.anselm.Activties;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ml.meiner.anselm.Main.MainActivity;
import ml.meiner.anselm.R;

public class Map extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    public void gotoHistory(View view)
    {
        Intent intent = new Intent(this, History.class);
        startActivity(intent);
    }

    public void gotoRating(View view)
    {
        Intent intent = new Intent(this, Rating.class);
        startActivity(intent);
    }
}
