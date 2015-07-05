package tvz.zavrsni.eimenik.helper;

/**
 * Created by Pero on 22.6.2015..
 */
import java.util.Date;
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
    private static final String KEY_IME = "ime";
    private static final String KEY_PREZIME = "prezime";
    private static final String KEY_KORISNICKO_IME = "korisnicko_ime";
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
    private static final String KEY_DATUM_KOMENTARA="datum_komentara";


    private static final String CREATE_TABLE_UCENICI = "CREATE TABLE " + TABLE_UCENICI + "("
            + KEY_ID_UCENIKA + " INTEGER PRIMARY KEY, " + KEY_IME + " TEXT,"
            + KEY_PREZIME + " TEXT, " + KEY_KORISNICKO_IME + " TEXT " + ")";
    //+ KEY_UID + " TEXT" + ")";

    private static final String CREATE_TABLE_UPIS = "CREATE TABLE " + TABLE_UPIS + "("
            + KEY_ID_UPISA + " INTEGER PRIMARY KEY, " + KEY_ID_UCENIKA + " INTEGER, "
            + KEY_ID_RAZREDA + " INTEGER, " + KEY_DATUM_UPISA + " DATETIME, "
            + " FOREIGN KEY (" + KEY_ID_UCENIKA + ") REFERENCES " + TABLE_UCENICI + "( " + KEY_ID_UCENIKA + "), "
            + " FOREIGN KEY (" + KEY_ID_RAZREDA + ") REFERENCES " + TABLE_RAZREDI + "( " + KEY_ID_RAZREDA + ")"
            + ")";

    private static final String CREATE_TABLE_RAZREDI = "CREATE TABLE " + TABLE_RAZREDI + "("
            + KEY_ID_RAZREDA + " INTEGER PRIMARY KEY, " + KEY_GODINA + " INTEGER,"
            + KEY_RAZRED + " INTEGER, " + KEY_GODINA_UPISA + " DATETIME " + ")";

    private static final String CREATE_TABLE_UPISANI_PREDMETI = "CREATE TABLE " + TABLE_UPISANI_PREDMETI + "("
            + KEY_REDNI_BR_UPISA + " INTEGER PRIMARY KEY, " + KEY_ID_UPISA + " INTEGER, "
            + KEY_ID_PREDMETA + " INTEGER, " + KEY_DATUM_UPISA + " DATETIME, "
            + KEY_ZAVRSNA_OCJENA_PREDMETA + " INTEGER, " + KEY_DATUM_ZAVRSNE_OCJENE + " DATETIME, "
            + " FOREIGN KEY (" + KEY_ID_UPISA + ") REFERENCES " + TABLE_UPIS + "( " + KEY_ID_UPISA + "), "
            + " FOREIGN KEY (" + KEY_ID_PREDMETA + ") REFERENCES " + TABLE_PREDMETI + "( " + KEY_ID_PREDMETA + ")"
            + ")";

    private static final String CREATE_TABLE_PREDMETI = "CREATE TABLE " + TABLE_PREDMETI + "("
            + KEY_ID_PREDMETA + " INTEGER PRIMARY KEY, " + KEY_NAZIV_PREDMETA + " TEXT " + ")";

    private static final String CREATE_TABLE_NASTAVNICI = "CREATE TABLE " + TABLE_NASTAVNICI + "("
            + KEY_ID_NASTAVNIKA + " INTEGER PRIMARY KEY, " + KEY_OIB_NASTAVNIKA + " INTEGER,"
            + KEY_IME + " TEXT, " + KEY_PREZIME + " TEXT " + ")";

    private static final String CREATE_TABLE_RAZ_PRED_NAST = "CREATE TABLE " + TABLE_RAZ_PRED_NAST + "("
            + KEY_ID_RAZREDA + " INTEGER, " + KEY_ID_NASTAVNIKA + " INTEGER, "
            + KEY_REDNI_BR_UPISA + " INTEGER, " + KEY_ID_DATUM_OD + " DATETIME, " + KEY_ID_DATUM_DO + " DATETIME, "
            + " FOREIGN KEY (" + KEY_ID_RAZREDA + ") REFERENCES " + TABLE_RAZREDI + "( " + KEY_ID_RAZREDA + "), "
            + " FOREIGN KEY (" + KEY_ID_NASTAVNIKA + ") REFERENCES " + TABLE_NASTAVNICI + "( " + KEY_ID_NASTAVNIKA + "), "
            + " FOREIGN KEY (" + KEY_REDNI_BR_UPISA + ") REFERENCES " + TABLE_UPISANI_PREDMETI + "( " + KEY_REDNI_BR_UPISA + ")"
            + ")";

    private static final String CREATE_TABLE_OCJENE = "CREATE TABLE " + TABLE_OCJENE + "("
            + KEY_ID_REDNI_BR_OCJENE + " INTEGER PRIMARY KEY, " + KEY_ID_UCENIKA + " INTEGER, "
            + KEY_ID_RUBRIKE + " INTEGER, " + KEY_REDNI_BR_UPISA + " INTEGER, " + KEY_OCJENA + " INTEGER, "
            + KEY_DATUM_OCJENE + " DATETIME, " + KEY_KOMENTAR + " TEXT, "
            + " FOREIGN KEY (" + KEY_ID_UCENIKA + ") REFERENCES " + TABLE_UCENICI + "( " + KEY_ID_UCENIKA + "), "
            + " FOREIGN KEY (" + KEY_ID_RUBRIKE + ") REFERENCES " + TABLE_RUBRIKE + "( " + KEY_ID_RUBRIKE + "), "
            + " FOREIGN KEY (" + KEY_REDNI_BR_UPISA + ") REFERENCES " + TABLE_UPISANI_PREDMETI+ "(" + KEY_REDNI_BR_UPISA + ")"
            + ")";

    private static final String CREATE_TABLE_RUBRIKE = "CREATE TABLE " + TABLE_RUBRIKE + "("
            + KEY_ID_RUBRIKE + " INTEGER PRIMARY KEY, " + KEY_NAZIV_RUBRIKE + " TEXT" + ")";

    private static final String CREATE_TABLE_KOMENTAR = "CREATE TABLE " + TABLE_KOMENTAR + "("
            + KEY_ID_NASTAVNIKA + " INTEGER, " + KEY_ID_UCENIKA + " INTEGER,"
            + KEY_REDNI_BR_UPISA + " INTEGER, " + KEY_DATUM_KOMENTARA + " DATETIME,"
            + KEY_KOMENTAR + " TEXT, "
            + " FOREIGN KEY (" + KEY_ID_NASTAVNIKA + ") REFERENCES " + TABLE_NASTAVNICI + "( " + KEY_ID_NASTAVNIKA + "), "
            + " FOREIGN KEY (" + KEY_ID_UCENIKA + ") REFERENCES " + TABLE_UCENICI + "( " + KEY_ID_UCENIKA + "), "
            + " FOREIGN KEY (" + KEY_REDNI_BR_UPISA + ") REFERENCES " + TABLE_UPISANI_PREDMETI+ "( " + KEY_REDNI_BR_UPISA + ")"
            + ")";





    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_UCENICI);
        db.execSQL(CREATE_TABLE_UPIS);
        db.execSQL(CREATE_TABLE_RAZREDI);
        db.execSQL(CREATE_TABLE_UPISANI_PREDMETI);
        db.execSQL(CREATE_TABLE_PREDMETI);
        db.execSQL(CREATE_TABLE_NASTAVNICI);
        db.execSQL(CREATE_TABLE_RAZ_PRED_NAST);
        db.execSQL(CREATE_TABLE_OCJENE);
        db.execSQL(CREATE_TABLE_RUBRIKE);
        db.execSQL(CREATE_TABLE_KOMENTAR);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UCENICI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UPIS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RAZREDI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UPISANI_PREDMETI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREDMETI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NASTAVNICI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RAZ_PRED_NAST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OCJENE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RUBRIKE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KOMENTAR);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    //public void addUser(String name, String surname, String email, String uid) {
        public void addUser(Integer id_ucenika, String ime, String prezime, String korisnicko_ime) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IME, ime); // Name
        values.put(KEY_PREZIME, prezime); // Surname
        values.put(KEY_KORISNICKO_IME, korisnicko_ime); // Email
        values.put(KEY_ID_UCENIKA, id_ucenika);
        //values.put(KEY_UID, uid);


        // Inserting Row
        long id = db.insert(TABLE_UCENICI, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New ucenik inserted into sqlite: " + id);
    }

    public void addUpis(Integer id_upisa, Integer id_ucenika, Integer id_razreda, String datum_upisa) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_UPISA, id_upisa); // Name
        values.put(KEY_ID_UCENIKA, id_ucenika); // Surname
        values.put(KEY_ID_RAZREDA, id_razreda); // Email
        values.put(KEY_DATUM_UPISA, datum_upisa);
        // Inserting Row
        long id = db.insert(TABLE_UPIS, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New upis inserted into sqlite: " + id);
    }

    public void addRazredi(Integer id_razreda, Integer godina, Integer razred, String godina_upisa) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_RAZREDA, id_razreda); // Name
        values.put(KEY_GODINA, godina); // Surname
        values.put(KEY_RAZRED, razred); // Email
        values.put(KEY_GODINA_UPISA, godina_upisa);
        // Inserting Row
        long id = db.insert(TABLE_RAZREDI, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New razredi inserted into sqlite: " + id);
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
                user.put("ime", cursor.getString(1));
                user.put("prezime", cursor.getString(2));
                user.put("korisnicko_ime", cursor.getString(3));

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
        db.delete(TABLE_UPIS, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }



}
