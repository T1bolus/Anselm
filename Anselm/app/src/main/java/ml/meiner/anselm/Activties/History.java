package ml.meiner.anselm.Activties;

import androidx.appcompat.app.AppCompatActivity;

import ml.meiner.anselm.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

    public void gotoInseration(View view)
    {
        Intent intent = new Intent(this, Inseration.class);
        startActivity(intent);
    }
}
