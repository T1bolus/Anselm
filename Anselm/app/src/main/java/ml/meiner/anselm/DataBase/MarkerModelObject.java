package ml.meiner.anselm.DataBase;

import android.net.Uri;



public class MarkerModelObject {

    private int longitude;
    private int latitude;

    private String first_name;
    private String last_name;
    private String tel_number;

    // Um später snapshot vom Parkplatz zu übergeben
    public Uri photoID;

    public int getLongitude() {
        return longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public String getFirstname() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getTelnumber() {
        return tel_number;
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
        this.first_name = firstname;
    }

    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    public void setTelnumber(String telnumber) {
        this.tel_number = telnumber;
    }

    public void setPhotoID(Uri photoID) {
        this.photoID = photoID;
    }
}
