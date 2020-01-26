package ml.meiner.anselm.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "OurDb";
    private static final String TABLE_NAME = "Chargingstations";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_POSITION = "position";
    private static final String KEY_HEIGHT = "height";
    private static final String[] COLUMNS = { KEY_ID, KEY_NAME, KEY_POSITION,
            KEY_HEIGHT };

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE Chargingstations ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT, "
                + "position TEXT, " + "height INTEGER )";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void deleteOne(Chargingstation chargingstation) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[] { String.valueOf(chargingstation.getId()) });
        db.close();
    }

    public Chargingstation getPlayer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();

        Chargingstation chargingstation = new Chargingstation();
        chargingstation.setId(cursor.getString(0));
        chargingstation.setName(cursor.getString(1));
        chargingstation.setPosition(cursor.getString(2));
        chargingstation.setHeight(Integer.parseInt(cursor.getString(3)));

        return chargingstation;
    }

    public List<Chargingstation> allChargingstations() {

        List<Chargingstation> chargingstations = new LinkedList<Chargingstation>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Chargingstation chargingstation = null;

        if (cursor.moveToFirst()) {
            do {
                chargingstation = new Chargingstation();
                chargingstation.setId(cursor.getString(0));
                chargingstation.setName(cursor.getString(1));
                chargingstation.setPosition(cursor.getString(2));
                chargingstation.setHeight(Integer.parseInt(cursor.getString(3)));
                chargingstations.add(chargingstation);
            } while (cursor.moveToNext());
        }

        return chargingstations;
    }

    public void addChargingstation(Chargingstation chargingstation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, chargingstation.getName());
        values.put(KEY_POSITION, chargingstation.getPosition());
        values.put(KEY_HEIGHT, chargingstation.getHeight());
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public int updateChargingstation(Chargingstation chargingstation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, chargingstation.getName());
        values.put(KEY_POSITION, chargingstation.getPosition());
        values.put(KEY_HEIGHT, chargingstation.getHeight());

        int i = db.update(TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[] { String.valueOf(chargingstation.getId()) });

        db.close();

        return i;
    }

}
