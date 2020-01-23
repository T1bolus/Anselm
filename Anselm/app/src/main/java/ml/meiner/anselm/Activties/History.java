package ml.meiner.anselm.Activties;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ml.meiner.anselm.DataBase.Chargingstation;
import ml.meiner.anselm.DataBase.SQLiteDatabaseHandler;
import ml.meiner.anselm.R;
import ml.meiner.anselm.model.historyListDataHandler;

public class History extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private SQLiteDatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ArrayList<String> datalist = new ArrayList<>();

        // TESTAREA
        // create our sqlite helper class
        db = new SQLiteDatabaseHandler(this);
        // create some chargingstations
        Chargingstation chargingstation1 = new Chargingstation(1, "Lebron James", "F", 203);
        Chargingstation chargingstation2 = new Chargingstation(2, "Kevin Durant", "F", 208);
        Chargingstation chargingstation3 = new Chargingstation(3, "Rudy Gobert", "C", 214);
        // add them
        db.addChargingstation(chargingstation1);
        db.addChargingstation(chargingstation2);
        db.addChargingstation(chargingstation3);

        // list all chargingstations
        List<Chargingstation> chargingstations = db.allChargingstations();

        if (chargingstations != null) {
            String[] itemsNames = new String[chargingstations.size()];

            for (int i = 0; i < chargingstations.size(); i++) {
                itemsNames[i] = chargingstations.get(i).toString();
                datalist.add(itemsNames[i]);
            }

            // display in a simple list
            // ListView list = (ListView) findViewById(R.id.list);
            // list.setAdapter(new ArrayAdapter<String>(this,
            //         android.R.layout.simple_list_item_1, android.R.id.text1, itemsNames));

        }


        recyclerView = findViewById(R.id.recycleView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new historyListDataHandler(datalist);
        recyclerView.setAdapter(mAdapter);

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
