package tvz.zavrsni.eimenik.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private static final String KEY_ID_KOMENTARA="id_komentara";
    private static final String KEY_DATUM_KOMENTARA="datum_komentara";



    private static final String CREATE_TABLE_UCENICI = "CREATE TABLE " + TABLE_UCENICI + "("
            + KEY_ID_UCENIKA + " INTEGER PRIMARY KEY, " + KEY_IME + " TEXT,"
            + KEY_PREZIME + " TEXT, " + KEY_KORISNICKO_IME + " TEXT " + ")";

    private static final String CREATE_TABLE_UPIS = "CREATE TABLE " + TABLE_UPIS + "("
            + KEY_ID_UPISA + " INTEGER PRIMARY KEY, " + KEY_ID_UCENIKA + " INTEGER, "
            + KEY_ID_RAZREDA + " INTEGER, " + KEY_DATUM_UPISA + " DATETIME, "
            + " FOREIGN KEY (" + KEY_ID_UCENIKA + ") REFERENCES " + TABLE_UCENICI + "( " + KEY_ID_UCENIKA + "), "
            + " FOREIGN KEY (" + KEY_ID_RAZREDA + ") REFERENCES " + TABLE_RAZREDI + "( " + KEY_ID_RAZREDA + ")"
            + ")";

    private static final String CREATE_TABLE_RAZREDI = "CREATE TABLE " + TABLE_RAZREDI + "("
            + KEY_ID_RAZREDA + " INTEGER PRIMARY KEY, " + KEY_GODINA + " INTEGER,"
            + KEY_RAZRED + " TEXT, " + KEY_GODINA_UPISA + " DATETIME " + ")";

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
            + KEY_ID_KOMENTARA + " INTEGER PRIMARY KEY, " + KEY_ID_NASTAVNIKA + " INTEGER, " + KEY_ID_UCENIKA + " INTEGER,"
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
        Log.d(TAG, CREATE_TABLE_KOMENTAR);
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
        public void addUser(Integer id_ucenika, String ime, String prezime, String korisnicko_ime) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IME, ime); // Name
        values.put(KEY_PREZIME, prezime); // Surname
        values.put(KEY_KORISNICKO_IME, korisnicko_ime); // Email
        values.put(KEY_ID_UCENIKA, id_ucenika);

        // Inserting Row
        long id = db.insert(TABLE_UCENICI, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New ucenik inserted into sqlite: " + id);
    }

    public void addUpis(Integer id_upisa, Integer id_ucenika, Integer id_razreda, String datum_upisa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_UPISA, id_upisa);
        values.put(KEY_ID_UCENIKA, id_ucenika);
        values.put(KEY_ID_RAZREDA, id_razreda);
        values.put(KEY_DATUM_UPISA, datum_upisa);
        long id = db.insert(TABLE_UPIS, null, values);
        db.close();
        Log.d(TAG, "New upis inserted into sqlite: " + id);
    }

    public void addRazredi(Integer id_razreda, Integer godina, String razred, String godina_upisa) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_RAZREDA, id_razreda);
        values.put(KEY_GODINA, godina);
        values.put(KEY_RAZRED, razred);
        values.put(KEY_GODINA_UPISA, godina_upisa);
        // Inserting Row
        long id = db.insert(TABLE_RAZREDI, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New razredi inserted into sqlite: " + id);
    }

    public void addUpisaniPredmeti(Integer redni_br_upisa, Integer id_upisa, Integer id_predmeta, String datum_upisa, Integer zavrsna_ocjena_predmeta, String datum_zavrsne_ocjene) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_REDNI_BR_UPISA, redni_br_upisa);
        values.put(KEY_ID_UPISA, id_upisa);
        values.put(KEY_ID_PREDMETA, id_predmeta);
        values.put(KEY_DATUM_UPISA, datum_upisa);
        values.put(KEY_ZAVRSNA_OCJENA_PREDMETA, zavrsna_ocjena_predmeta);
        values.put(KEY_DATUM_ZAVRSNE_OCJENE, datum_zavrsne_ocjene);
        // Inserting Row
        long id = db.insert(TABLE_UPISANI_PREDMETI, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New upisani predmeti inserted into sqlite: " + id);
    }

    public void addPredmeti(Integer id_predmeta, String naziv_predmeta) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_PREDMETA, id_predmeta);
        values.put(KEY_NAZIV_PREDMETA, naziv_predmeta);
        // Inserting Row

        long id = db.insert(TABLE_PREDMETI, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New predmeti inserted into sqlite: " + id);
    }

    public void addNastavnici(Integer id_nastavnika, Integer oib_nastavnika, String ime, String prezime) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_NASTAVNIKA, id_nastavnika);
        values.put(KEY_OIB_NASTAVNIKA, oib_nastavnika);
        values.put(KEY_IME, ime);
        values.put(KEY_PREZIME, prezime);
        // Inserting Row

        long id = db.insert(TABLE_NASTAVNICI, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New nastavnici inserted into sqlite: " + id);
    }

    public void addRaz_pred_nast(Integer id_razreda, Integer id_nastavnika, Integer redni_br_upisa, String datum_od, String datum_do) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_RAZREDA, id_razreda);
        values.put(KEY_ID_NASTAVNIKA, id_nastavnika);
        values.put(KEY_REDNI_BR_UPISA, redni_br_upisa);
        values.put(KEY_ID_DATUM_DO, datum_od);
        values.put(KEY_ID_DATUM_DO, datum_do);
        // Inserting Row

        long id = db.insert(TABLE_RAZ_PRED_NAST, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New raz_pred_nast inserted into sqlite: " + id);
    }

    public void addOcjene(Integer redni_broj_ocjene, Integer id_ucenika, Integer id_rubrike, Integer redni_br_upisa, Integer ocjena, String datum_ocjene, String komentar) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

            values.put(KEY_ID_REDNI_BR_OCJENE, redni_broj_ocjene);
            values.put(KEY_ID_UCENIKA, id_ucenika);
            values.put(KEY_ID_RUBRIKE, id_rubrike);
            values.put(KEY_REDNI_BR_UPISA, redni_br_upisa);
            values.put(KEY_OCJENA, ocjena);
            values.put(KEY_DATUM_OCJENE, datum_ocjene);
            values.put(KEY_KOMENTAR, komentar);
            // Inserting Row

            long id= db.insert(TABLE_OCJENE, null, values);

        db.close(); // Closing database connection

        Log.d(TAG, "New ocjene inserted into sqlite: " + id);
    }

    public void addRubrike(Integer id_rubrike, String naziv_rubrike) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_RUBRIKE, id_rubrike);
        values.put(KEY_NAZIV_RUBRIKE, naziv_rubrike);
        // Inserting Row

        long id = db.insert(TABLE_RUBRIKE, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New rubrike inserted into sqlite: " + id);
    }

    public void addKomentar(Integer id_komentara, Integer id_nastavnika, Integer id_ucenika, Integer redni_br_upisa, String datum, String komentar) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_KOMENTARA, id_komentara);
        values.put(KEY_ID_NASTAVNIKA, id_nastavnika);
        values.put(KEY_ID_UCENIKA, id_ucenika);
        values.put(KEY_REDNI_BR_UPISA, redni_br_upisa);
        values.put(KEY_DATUM_KOMENTARA, datum);
        values.put(KEY_KOMENTAR, komentar);
        // Inserting Row

        long id = db.insert(TABLE_KOMENTAR, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New komentar inserted into sqlite: " + id);
    }

    public List<Ocjene> getZadnjeOcjene() {
        List<Ocjene> ocjene = new ArrayList<Ocjene>();
        String selectQuery = "SELECT " + KEY_OCJENA + ", " + KEY_DATUM_OCJENE + ", " + KEY_NAZIV_RUBRIKE + ", " + KEY_NAZIV_PREDMETA + "," + KEY_KOMENTAR + " FROM " + TABLE_OCJENE + " JOIN "
                + TABLE_RUBRIKE + " ON " + TABLE_OCJENE + "." + KEY_ID_RUBRIKE + "=" + TABLE_RUBRIKE + "." + KEY_ID_RUBRIKE + " JOIN " + TABLE_UPISANI_PREDMETI
                + " ON " + TABLE_OCJENE + "." + KEY_REDNI_BR_UPISA + "=" + TABLE_UPISANI_PREDMETI + "." + KEY_REDNI_BR_UPISA + " JOIN " + TABLE_PREDMETI
                + " ON " + TABLE_UPISANI_PREDMETI + "." + KEY_ID_PREDMETA + "=" + TABLE_PREDMETI + "." + KEY_ID_PREDMETA + " ORDER BY " + KEY_DATUM_OCJENE + " DESC LIMIT 10";

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Ocjene oc = new Ocjene();
                oc.setOcjena(c.getInt((c.getColumnIndex(KEY_OCJENA))));
                oc.setDatumOcjene((c.getString(c.getColumnIndex(KEY_DATUM_OCJENE))));
                oc.setNazivRubrike(c.getString(c.getColumnIndex(KEY_NAZIV_RUBRIKE)));
                oc.setPredmet(c.getString(c.getColumnIndex(KEY_NAZIV_PREDMETA)));
                oc.setKomentar(c.getString(c.getColumnIndex(KEY_KOMENTAR)));

                ocjene.add(oc);
            } while (c.moveToNext());
        }

        return ocjene;
    }
    public List<Ocjene> getAllOcjene(int id) {
        List<Ocjene> ocjene = new ArrayList<Ocjene>();
        String selectQuery = "SELECT " + KEY_OCJENA + ", " + KEY_DATUM_OCJENE + ", " + KEY_NAZIV_RUBRIKE + ", " + KEY_NAZIV_PREDMETA + "," + KEY_KOMENTAR +  " FROM " + TABLE_OCJENE + " JOIN "
                + TABLE_RUBRIKE + " ON " + TABLE_OCJENE + "." + KEY_ID_RUBRIKE + "=" + TABLE_RUBRIKE + "." + KEY_ID_RUBRIKE + " JOIN " + TABLE_UPISANI_PREDMETI
                + " ON " + TABLE_OCJENE + "." + KEY_REDNI_BR_UPISA + "=" + TABLE_UPISANI_PREDMETI + "." + KEY_REDNI_BR_UPISA + " JOIN " + TABLE_PREDMETI
                + " ON " + TABLE_UPISANI_PREDMETI + "." + KEY_ID_PREDMETA + "=" + TABLE_PREDMETI + "." + KEY_ID_PREDMETA + " WHERE " + TABLE_UPISANI_PREDMETI + "." + KEY_REDNI_BR_UPISA + "=" +id;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Ocjene oc = new Ocjene();
                oc.setOcjena(c.getInt((c.getColumnIndex(KEY_OCJENA))));
                oc.setDatumOcjene((c.getString(c.getColumnIndex(KEY_DATUM_OCJENE))));
                oc.setNazivRubrike(c.getString(c.getColumnIndex(KEY_NAZIV_RUBRIKE)));
                oc.setPredmet(c.getString(c.getColumnIndex(KEY_NAZIV_PREDMETA)));
                oc.setKomentar(c.getString(c.getColumnIndex(KEY_KOMENTAR)));

                ocjene.add(oc);
            } while (c.moveToNext());
        }

        return ocjene;
    }

    public List<Predmeti> getAllPredmeti() {
        List<Predmeti> predmeti = new ArrayList<Predmeti>();
        String selectQuery = "SELECT " + KEY_NAZIV_PREDMETA + ", " + KEY_REDNI_BR_UPISA + " FROM " + TABLE_UPISANI_PREDMETI + " JOIN "
                + TABLE_PREDMETI + " ON " + TABLE_UPISANI_PREDMETI + "." + KEY_ID_PREDMETA + "=" + TABLE_PREDMETI + "." + KEY_ID_PREDMETA ;

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Predmeti pr = new Predmeti();
                pr.setNazivPredmeta((c.getString(c.getColumnIndex(KEY_NAZIV_PREDMETA))));
                pr.setRedniBrUpisa(c.getInt(c.getColumnIndex(KEY_REDNI_BR_UPISA)));

                predmeti.add(pr);
            } while (c.moveToNext());
        }

        return predmeti;
    }

    public List<Komentari> getAllKomentari() {
        List<Komentari> komentari = new ArrayList<Komentari>();
        String selectQuery = "SELECT " + KEY_DATUM_KOMENTARA + ", " + KEY_KOMENTAR + ", " + KEY_NAZIV_PREDMETA +  " FROM " + TABLE_KOMENTAR + " JOIN "
                + TABLE_UPISANI_PREDMETI + " ON " + TABLE_KOMENTAR + "." + KEY_REDNI_BR_UPISA + "=" + TABLE_UPISANI_PREDMETI + "." + KEY_REDNI_BR_UPISA + " JOIN " + TABLE_PREDMETI
                + " ON " + TABLE_UPISANI_PREDMETI + "." + KEY_ID_PREDMETA + "=" + TABLE_PREDMETI + "." + KEY_ID_PREDMETA + " ORDER BY " + KEY_DATUM_KOMENTARA + " DESC";

        Log.e(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Komentari kom = new Komentari();
                kom.setDatumKomentara((c.getString(c.getColumnIndex(KEY_DATUM_KOMENTARA))));
                kom.setNaziv_predmeta(c.getString(c.getColumnIndex(KEY_NAZIV_PREDMETA)));
                kom.setKomentarKomentar(c.getString(c.getColumnIndex(KEY_KOMENTAR)));

                komentari.add(kom);
            } while (c.moveToNext());
        }

        return komentari;
    }

    public String getLatestDate() {
        String selectQuery = "SELECT " + KEY_DATUM_OCJENE + " FROM " + TABLE_OCJENE + " ORDER BY " + KEY_DATUM_OCJENE + " DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        String date = cursor.getString(cursor.getColumnIndex(KEY_DATUM_OCJENE));
        db.close();
        cursor.close();

        return date;
    }

    public String getLatestDateComments() {
        String selectQuery = "SELECT " + KEY_DATUM_KOMENTARA + " FROM " + TABLE_KOMENTAR + " ORDER BY " + KEY_DATUM_KOMENTARA + " DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        String date = cursor.getString(cursor.getColumnIndex(KEY_DATUM_KOMENTARA));
        db.close();
        cursor.close();

        return date;
    }

    public String getId() {
        String selectQuery = "SELECT " + KEY_ID_UCENIKA + " FROM " + TABLE_UCENICI;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        String id = cursor.getString(cursor.getColumnIndex(KEY_ID_UCENIKA));
        db.close();
        cursor.close();

        return id;
    }
    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_UCENICI, null, null);
        db.delete(TABLE_UPIS, null, null);
        db.delete(TABLE_RAZREDI, null, null);
        db.delete(TABLE_UPISANI_PREDMETI, null, null);
        db.delete(TABLE_PREDMETI, null, null);
        db.delete(TABLE_NASTAVNICI, null, null);
        db.delete(TABLE_RAZ_PRED_NAST, null, null);
        db.delete(TABLE_OCJENE, null, null);
        db.delete(TABLE_RUBRIKE, null, null);
        db.delete(TABLE_KOMENTAR, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }
}
