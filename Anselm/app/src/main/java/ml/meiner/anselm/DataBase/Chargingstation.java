package ml.meiner.anselm.DataBase;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Chargingstation implements Serializable {

    private String id;
    private String name;
    private String username;
    private String usernamePicturePath;
    private String uid;
    private double longitude;
    private double latitude;
    private String address;
    private float pph; // Price per Houre in Euro
    //TODO: Datum für mögliche Reservierungen
    private boolean weeklyRepeat;
    private ArrayList<Date> freeTimes;
    private ArrayList<Date> bookedTimes;


    //plugs
    private boolean typ1;
    private boolean typ2;
    private boolean ccs;
    private boolean chademo;
    private boolean schuko;
    private boolean cee_blue;
    private boolean cee16;
    private boolean cee32;


    public Chargingstation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isSchuko() {
        return schuko;
    }

    public void setSchuko(boolean schuko) {
        this.schuko = schuko;
    }

    public boolean isTyp1() {
        return typ1;
    }

    public void setTyp1(boolean typ1) {
        this.typ1 = typ1;
    }

    public boolean isTyp2() {
        return typ2;
    }

    public void setTyp2(boolean typ2) {
        this.typ2 = typ2;
    }

    public boolean isCcs() {
        return ccs;
    }

    public void setCcs(boolean ccs) {
        this.ccs = ccs;
    }

    public boolean isChademo() {
        return chademo;
    }

    public void setChademo(boolean chademo) {
        this.chademo = chademo;
    }

    public boolean isCee_blue() {
        return cee_blue;
    }

    public void setCee_blue(boolean cee_blue) {
        this.cee_blue = cee_blue;
    }

    public boolean isCee16() {
        return cee16;
    }

    public void setCee16(boolean cee16) {
        this.cee16 = cee16;
    }

    public boolean isCee32() {
        return cee32;
    }

    public void setCee32(boolean cee32) {
        this.cee32 = cee32;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public float getPph() {
        return pph;
    }

    public void setPph(float pph) {
        this.pph = pph;
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

    public boolean isWeeklyRepeat() {
        return weeklyRepeat;
    }

    public void setWeeklyRepeat(boolean weeklyRepeat) {
        this.weeklyRepeat = weeklyRepeat;
    }

    public ArrayList<Date> getFreeTimes() {
        return freeTimes;
    }

    public void setFreeTimes(ArrayList<Date> freeTimes) {
        this.freeTimes = freeTimes;
    }

    public ArrayList<Date> getBookedTimes() {
        return bookedTimes;
    }

    public void setBookedTimes(ArrayList<Date> bookedTimes) {
        this.bookedTimes = bookedTimes;
    }

}
