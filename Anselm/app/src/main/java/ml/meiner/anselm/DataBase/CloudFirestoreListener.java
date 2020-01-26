package ml.meiner.anselm.DataBase;

import java.util.ArrayList;

public interface CloudFirestoreListener
{
    void chargingStationsReady(ArrayList<Chargingstation> stations);
}
