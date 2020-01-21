package ml.meiner.anselm.DataBase;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ml.meiner.anselm.Main.MainActivity;
import ml.meiner.anselm.R;

import static xdroid.toaster.Toaster.toast;

// Für Datenbank

public class DataHandlerActivity extends AppCompatActivity {

    private TextView mUserName;
    private TextView mUserPhone;
    private TextView mUserEmail;
    private Button insertRowFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datahandleractivity);


        insertRowFrom = findViewById(R.id.insertRowFrom);
        mUserName = findViewById(R.id.userNameTxt);
        mUserPhone = findViewById(R.id.userPhoneTxt);
        mUserEmail = findViewById(R.id.userEmailTxt);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Hier bitte Daten eingeben");
        }
    }

    public void insertRow(View view) {

        TextView firstNameTxtView = findViewById(R.id.userNameTxt);
        TextView lastNameTxtView = findViewById(R.id.userPhoneTxt);
        TextView telnumberTxtView = findViewById(R.id.userEmailTxt);

        if(firstNameTxtView.getText().toString().trim().equals("")
                || lastNameTxtView.getText().toString().trim().equals("")
                || telnumberTxtView.getText().toString().trim().equals("")){
            toast("Bitte alle Felder ausfüllen!");
        }else{
            UsersDatabaseAdapter.insertEntry(firstNameTxtView.getText().toString().trim(),lastNameTxtView.getText().toString(),telnumberTxtView.getText().toString(),1,1);
            // Weg in die Main
            Intent myIntent = new Intent(DataHandlerActivity.this, MainActivity.class);
            DataHandlerActivity.this.startActivity(myIntent);
        }

    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}