package ml.meiner.anselm.DataBase;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;


public class CloudFirestore
{
    private FirebaseFirestore db;
    ArrayList<Chargingstation> stations = new ArrayList<>();

    private List<CloudFirestoreListener> listeners = new ArrayList<CloudFirestoreListener>();

    private static CloudFirestore instance = null;


    public static CloudFirestore getInstance()
    {
        if(instance == null)
        {
            instance = new CloudFirestore();
        }

        return instance;
    }

    //Firebase Cloud Realtime Database init
    private CloudFirestore()
    {
        db = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }


    public void addChargingStation(Chargingstation chargingstation)
    {

        db.collection("chargingstations").add(chargingstation).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("","DocumentSnapshot added with ID: " + documentReference.getId());
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("", "Error adding document", e);
                    }
                });


        //WITH NAME INSTEAD OF AUTO GENERATED ID
//        db.collection("chargingstations").document(Integer.toString(chargingstation.getId()))
//                .set(chargingstation)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d("", "DocumentSnapshot successfully written!");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("", "Error writing document", e);
//                    }
//                });
    }


    public void fetchAllChargingStations(CloudFirestoreListener listener)
    {
        if(listeners.contains(listener) == false) //add only new listeners
            listeners.add(listener);

        db.collection("chargingstations")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("", document.getId() + " => " + document.getData());
                                stations.add(document.toObject(Chargingstation.class));
                            }

                            //Inform all listeners
                            for (CloudFirestoreListener hl : listeners)
                                hl.chargingStationsReady(stations);

                        } else {
                            Log.d("", "Error getting documents: ", task.getException());

                        }
                    }
                });
    }

    public ArrayList<Chargingstation> getAllChargingStations()
    {
        return stations;
    }

}
