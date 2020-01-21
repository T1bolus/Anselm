package ml.meiner.anselm.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;

import static xdroid.toaster.Toaster.toast;

public class UsersDatabaseAdapter {
    static final String DATABASE_NAME = "UsersDatabase.db";
    static final String TABLE_NAME = "USERS";
    static final int DATABASE_VERSION = 1;
    // SQL Statement to create a new database.
    //static final String DATABASE_CREATE = "create table " + TABLE_NAME + "( ID integer primary key autoincrement,user_name  text,user_phone  text,user_email text); ";
    static final String DATABASE_CREATE = "create table " + TABLE_NAME + "( ID integer primary key autoincrement,first_name  text,last_name  text,tel_number text, longitude integer, latidude integer); ";
    private static final String TAG = "UsersDatabaseAdapter:";

    // Variable to hold the database instance
    public static SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private static DataBaseHelper dbHelper;

    public UsersDatabaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method to open the Database
    public UsersDatabaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    // Method to close the Database
    public void close() {
        db.close();
    }

    // method returns an Instance of the Database
    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    // method to insert a record in Table
    public static String insertEntry(String first_name, String last_name, String tel_number, int longitude, int latitude) {

        try {
            ContentValues newValues = new ContentValues();
            // Assign values for each column.
            newValues.put("first_name", first_name);
            newValues.put("last_name", last_name);
            newValues.put("tel_number", tel_number);
            newValues.put("longitude", longitude);
            newValues.put("latidude", latitude);

            // Insert the row into your table
            db = dbHelper.getWritableDatabase();
            long result = db.insert(TABLE_NAME, null, newValues);
            toast("User Info Saved! Total Row Count is " + getRowCount());
            db.close();

        } catch (Exception ex) {
        }
        return "ok";
    }

    // method to get the password  of userName
    public static int getRowCount() {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        toast("Row Count is " + cursor.getCount());
        db.close();
        return cursor.getCount();
    }

}