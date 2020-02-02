package ml.meiner.anselm.DataBase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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


public class FirestoreDatabase
{
    private FirebaseFirestore db;
    ArrayList<Chargingstation> stations = new ArrayList<>();
    ArrayList<Booking> bookings = new ArrayList<>();

    private List<FirestoreDatabaseChargingstationListener> chargingListeners = new ArrayList<>();
    private List<FirestoreDatabaseBookingListener> bookingListeners = new ArrayList<>();

    private static FirestoreDatabase instance = null;


    public static FirestoreDatabase getInstance()
    {
        if(instance == null)
        {
            instance = new FirestoreDatabase();
        }

        return instance;
    }

    //Firebase Cloud Realtime Database init
    private FirestoreDatabase()
    {
        db = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
    }


    public void addChargingStation(final Context context, Chargingstation chargingstation)
    {

        db.collection("chargingstations").add(chargingstation).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("","DocumentSnapshot added with ID: " + documentReference.getId());

                Toast.makeText(context, "Ladestation hinzugefügt!", Toast.LENGTH_LONG).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("", "Error adding document", e);

                        Toast.makeText(context, "Ladestation nicht hinzugefügt!", Toast.LENGTH_LONG).show();
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


    public void fetchAllChargingStations(FirestoreDatabaseChargingstationListener listener)
    {
        if(chargingListeners.contains(listener) == false) //add only new listeners
            chargingListeners.add(listener);

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
                            for (FirestoreDatabaseChargingstationListener hl : chargingListeners)
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


    public void fetchOwnBookings(FirestoreDatabaseBookingListener listener, String uid)
    {
        if(bookingListeners.contains(listener) == false) //add only new listeners
            bookingListeners.add(listener);


        db.collection("bookings")
                .whereEqualTo("stationOwnerUid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("", document.getId() + " => " + document.getData());
                                bookings.add(document.toObject(Booking.class));
                            }

                        } else {
                            //Log.d("", "Error getting documents: ", task.getException());

                        }
                    }
                });


        db.collection("bookings")
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("", document.getId() + " => " + document.getData());
                                bookings.add(document.toObject(Booking.class));
                            }

                            //Inform all listeners
                            for (FirestoreDatabaseBookingListener hl : bookingListeners)
                                hl.bookingsReady(bookings);
                        } else {
                            //Log.d("", "Error getting documents: ", task.getException());

                        }
                    }
                });

    }


    public ArrayList<Booking> getOwnBookings()
    {
        return bookings;
    }



    public void addBooking(final Context context, Booking booking) {

        db.collection("bookings").add(booking).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());

                Toast.makeText(context, "Buchung abgeschlossen!", Toast.LENGTH_LONG).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("", "Error adding document", e);

                        Toast.makeText(context, "Buchung fehlgeschlagen!", Toast.LENGTH_LONG).show();
                    }
                });

    }

}
