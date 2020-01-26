package ml.meiner.anselm.DataBase;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Chargingstation {

    private String id;
    private String name;
    private FirebaseUser user;
    private String uid;
    private String position; //Blödsinn
    private int height; //Blödsinn
    private double longitude;
    private double latitude;
    private String address;
    private float pph; // Price per Houre in Euro

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

    public Chargingstation(String id, String name, String position, int height) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.height = height;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return name + " - " + position + " - " + height + " cm";
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

    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
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
}
