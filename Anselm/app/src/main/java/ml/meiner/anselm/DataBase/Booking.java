package ml.meiner.anselm.DataBase;

import java.util.ArrayList;
import java.util.Date;

public class Booking
{
    private String id;
    private Chargingstation station;
    private String username;
    private String usernamePicturePath;
    private String uid;
    private String stationOwnerUid;

    ArrayList<Date> bookedTimes;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Chargingstation getStation() {
        return station;
    }

    public void setStation(Chargingstation station) {
        this.station = station;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernamePicturePath() {
        return usernamePicturePath;
    }

    public void setUsernamePicturePath(String usernamePicturePath) {
        this.usernamePicturePath = usernamePicturePath;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ArrayList<Date> getBookedTimes() {
        return bookedTimes;
    }

    public void setBookedTimes(ArrayList<Date> bookedTimes) {
        this.bookedTimes = bookedTimes;
    }

    public String getStationOwnerUid() {
        return stationOwnerUid;
    }

    public void setStationOwnerUid(String stationOwnerUid) {
        this.stationOwnerUid = stationOwnerUid;
    }
}
