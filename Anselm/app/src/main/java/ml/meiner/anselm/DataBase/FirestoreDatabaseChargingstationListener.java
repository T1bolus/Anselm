package ml.meiner.anselm.DataBase;

import java.util.ArrayList;

public interface FirestoreDatabaseChargingstationListener
{
    void chargingStationsReady(ArrayList<Chargingstation> stations);
}
