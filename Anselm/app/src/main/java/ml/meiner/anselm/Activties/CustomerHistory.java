package ml.meiner.anselm.Activties;

import androidx.appcompat.app.AppCompatActivity;

import ml.meiner.anselm.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CustomerHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerhistory);
    }

    public void gotoMap(View view)
    {
        Intent intent = new Intent(this, Map.class);
        startActivity(intent);
    }

    public void gotoProviderHistory(View view)
    {
        Intent intent = new Intent(this, ProviderHistory.class);
        startActivity(intent);
    }
}
