package ml.meiner.anselm.Activties;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ml.meiner.anselm.R;
import ml.meiner.anselm.model.historyListDataHandler;

public class History extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<String> datalist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        datalist = new ArrayList<>();
        datalist.add("Auftrag X: Zu viel Geld bekommen und keins abgegeben");
        datalist.add("Irgendein anderer Text der mit der Datenbank aufgefüllt werden muss");
        datalist.add("Irgendein anderer Text der mit der Datenbank aufgefüllt werden muss");
        datalist.add("Irgendein anderer Text der mit der Datenbank aufgefüllt werden muss");
        datalist.add("Irgendein anderer Text der mit der Datenbank aufgefüllt werden muss");
        datalist.add("Irgendein anderer Text der mit der Datenbank aufgefüllt werden muss");
        datalist.add("Irgendein anderer Text der mit der Datenbank aufgefüllt werden muss");
        datalist.add("Irgendein anderer Text der mit der Datenbank aufgefüllt werden muss");
        datalist.add("Irgendein anderer Text der mit der Datenbank aufgefüllt werden muss");
        datalist.add("Irgendein anderer Text der mit der Datenbank aufgefüllt werden muss");




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
