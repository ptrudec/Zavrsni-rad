package tvz.zavrsni.eimenik.helper;

/**
 * Created by Pero on 22.6.2015..
 */
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "eimenik";

    // Login table name
    private static final String TABLE_UCENICI="ucenici";
    // Login Table Columns names
    private static final String KEY_ID_UCENIKA = "id_ucenika";
    private static final String KEY_NAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_EMAIL = "email";
    //private static final String KEY_UID = "uid";

    private static final String TABLE_UPIS="upis";
    private static final String KEY_ID_UPISA="id_upisa";
    private static final String KEY_ID_RAZREDA="id_razreda";
    private static final String KEY_DATUM_UPISA="datum_upisa";

    private static final String TABLE_RAZREDI="razredi";
    private static final String KEY_GODINA="godina";
    private static final String KEY_RAZRED="razred";
    private static final String KEY_GODINA_UPISA="godina_upisa";

    private static final String TABLE_UPISANI_PREDMETI="upisani_predmeti";
    private static final String KEY_REDNI_BR_UPISA="redni_br_upisa";
    private static final String KEY_ID_PREDMETA="id_predmeta";
    private static final String KEY_ZAVRSNA_OCJENA_PREDMETA="zavrsna_ocjena_predmeta";
    private static final String KEY_DATUM_ZAVRSNE_OCJENE="datum_zavrsne_ocjene";

    private static final String TABLE_PREDMETI="predmeti";
    private static final String KEY_NAZIV_PREDMETA="naziv_predmeta";

    private static final String TABLE_NASTAVNICI="nastavnici";
    private static final String KEY_ID_NASTAVNIKA="id_nastavnika";
    private static final String KEY_OIB_NASTAVNIKA="oib_nastavnika";


    private static final String TABLE_RAZ_PRED_NAST="raz_pred_nast";
    private static final String KEY_ID_DATUM_OD="datum_od";
    private static final String KEY_ID_DATUM_DO="datum_do";

    private static final String TABLE_OCJENE="ocjene";
    private static final String KEY_ID_REDNI_BR_OCJENE="redni_broj_ocjene";
    private static final String KEY_OCJENA="ocjena";
    private static final String KEY_DATUM_OCJENE="datum_ocjene";
    private static final String KEY_KOMENTAR="komentar";

    private static final String TABLE_RUBRIKE="rubrike";
    private static final String KEY_ID_RUBRIKE="id_rubrike";
    private static final String KEY_NAZIV_RUBRIKE="naziv_rubrike";

    private static final String TABLE_KOMENTAR="komentar";



    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_UCENICI + "("
                + KEY_ID_UCENIKA + " INTEGER PRIMARY KEY, " + KEY_NAME + " TEXT,"
                + KEY_SURNAME + " TEXT, " + KEY_EMAIL + " TEXT " + ")";
                //+ KEY_UID + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UCENICI);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    //public void addUser(String name, String surname, String email, String uid) {
        public void addUser(String name, String surname, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_SURNAME, surname); // Surname
        values.put(KEY_EMAIL, email); // Email
        //values.put(KEY_UID, uid);


        // Inserting Row
        long id = db.insert(TABLE_UCENICI, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM " + TABLE_UCENICI;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        // br = cursor.getCount();
        if (cursor.getCount() > 0) {
            //while (br-10 > br) {
                user.put("name", cursor.getString(1));
                user.put("surname", cursor.getString(2));
                user.put("email", cursor.getString(3));
                //user.put("uid", cursor.getString(4));

                //br --;
           //}
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Getting user login status return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT * FROM " + TABLE_UCENICI;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_UCENICI, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }



}
