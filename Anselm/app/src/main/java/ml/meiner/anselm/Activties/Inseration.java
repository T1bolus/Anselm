package ml.meiner.anselm.Activties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ml.meiner.anselm.R;

public class Inseration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inseration);
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
