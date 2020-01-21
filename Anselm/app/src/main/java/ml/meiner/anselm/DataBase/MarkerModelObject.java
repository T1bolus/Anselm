package ml.meiner.anselm.DataBase;

import android.net.Uri;



public class MarkerModelObject {

    private int longitude;
    private int latitude;

    private String firstname;
    private String lastName;
    private String telnumber;

    // Um später snapshot vom Parkplatz zu übergeben
    public Uri photoID;

    public int getLongitude() {
        return longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTelnumber() {
        return telnumber;
    }

    public Uri getPhotoID() {
        return photoID;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setTelnumber(String telnumber) {
        this.telnumber = telnumber;
    }

    public void setPhotoID(Uri photoID) {
        this.photoID = photoID;
    }
}
