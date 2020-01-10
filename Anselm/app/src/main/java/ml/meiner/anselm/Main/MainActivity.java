package ml.meiner.anselm.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ml.meiner.anselm.Activties.ProviderHistory;
import ml.meiner.anselm.Activties.Map;
import ml.meiner.anselm.Activties.CustomerHistory;
import ml.meiner.anselm.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void gotoCustomerHistory(View view)
    {
        Intent intent = new Intent(this, CustomerHistory.class);
        startActivity(intent);
    }

}
