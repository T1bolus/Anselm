package ml.meiner.anselm.Main;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ml.meiner.anselm.Activties.HistoryActivity;
import ml.meiner.anselm.Activties.MapActivity;
import ml.meiner.anselm.Activties.RatingActivity;
import ml.meiner.anselm.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changetoMap(View view)
    {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void changetoHistory(View view)
    {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void changetoRating(View view)
    {
        Intent intent = new Intent(this, RatingActivity.class);
        startActivity(intent);
    }

}
