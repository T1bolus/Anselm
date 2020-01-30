package ml.meiner.anselm.Activties;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ml.meiner.anselm.DataBase.Booking;
import ml.meiner.anselm.DataBase.FirestoreDatabase;
import ml.meiner.anselm.DataBase.FirestoreDatabaseBookingListener;
import ml.meiner.anselm.Main.MainActivity;
import ml.meiner.anselm.R;
import ml.meiner.anselm.model.historyListDataHandler;

public class History extends AppCompatActivity implements FirestoreDatabaseBookingListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Booking> datalist = new ArrayList<>();


    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null)
        {
            Toast.makeText(this, "Bitte einloggen um Buchungen zu sehen!", Toast.LENGTH_LONG).show();

            finish();
            return;
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

        FirestoreDatabase firestoreDatabase = FirestoreDatabase.getInstance();
        firestoreDatabase.fetchOwnBookings(this, user.getUid());

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


    @Override
    public void bookingsReady(ArrayList<Booking> bookings)
    {
        datalist.clear();

        for(Booking b: bookings)
        {
            datalist.add(b);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
