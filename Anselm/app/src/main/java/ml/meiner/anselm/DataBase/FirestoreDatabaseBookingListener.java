package ml.meiner.anselm.DataBase;

import java.util.ArrayList;


public interface FirestoreDatabaseBookingListener {
    void bookingsReady(ArrayList<Booking> bookings);
}
