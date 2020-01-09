package ml.meiner.anselm.Activties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ml.meiner.anselm.Main.MainActivity;
import ml.meiner.anselm.R;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
    }

    public void gotoMap(View view)
    {
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
    }

    public void gotoRating(View view)
    {
        Intent intent = new Intent(this, Rating.class);
        startActivity(intent);
    }
}
