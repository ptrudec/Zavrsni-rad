package tvz.zavrsni.eimenik;

/**
 * Created by Pero on 22.6.2015..
 */
import tvz.zavrsni.eimenik.app.AppConfig;
import tvz.zavrsni.eimenik.app.AppController;
import tvz.zavrsni.eimenik.helper.SessionManager;
import tvz.zavrsni.eimenik.helper.SQLiteHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class LoginActivity extends Activity {
    // LogCat tag
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    JSONArray ocj = null;
    JSONArray rubrike = null;
    JSONArray komentar = null;
    JSONArray upisani_predmeti = null;
    JSONArray predmeti = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        db = new SQLiteHandler(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String korisnicko_ime = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                // Check for empty data in the form
                if (korisnicko_ime.trim().length() > 0 && password.trim().length() > 0) {
                    // login user
                    checkLogin(korisnicko_ime, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Molimo unesite korisničko ime i lozinku!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String korisnicko_ime, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                    // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                       // String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String ime = user.getString("ime");
                        String prezime = user.getString("prezime");
                        String korisnicko_ime = user.getString("korisnicko_ime");
                        Integer id_ucenika = user.getInt("id_ucenika");

                        JSONObject upis = jObj.getJSONObject("upis");
                        Integer id_upisa=upis.getInt("id_upisa");
                        Integer id_razreda = upis.getInt("id_razreda");
                        String datum_upisa = upis.getString("datum_upisa");
                        /*SimpleDateFormat dupisa = new SimpleDateFormat("yyyy-MM-dd");
                        Date datum_u = null;
                        try {
                            datum_u = dupisa.parse(datum_upisa);
                        }catch(ParseException e){
                            e.printStackTrace();
                        }*/

                        JSONObject razredi = jObj.getJSONObject("razredi");
                        Integer raz_id_razreda=razredi.getInt("id_razreda");
                        Integer godina = razredi.getInt("godina");
                        String razred=razredi.getString("razred");
                        String godina_upisa = razredi.getString("godina_upisa");

                        upisani_predmeti = jObj.getJSONArray("upisani_predmeti");
                        for(int i=0; i<upisani_predmeti.length();i++){
                            JSONObject up=upisani_predmeti.getJSONObject(i);
                            Integer redni_br_upisa=Integer.parseInt(up.getString("redni_br_upisa"));
                            Integer upisani_id_upisa=Integer.parseInt(up.getString("id_upisa"));
                            Integer upisani_id_predmeta = Integer.parseInt(up.getString("id_predmeta"));
                            String upisani_datum_upisa=up.getString("datum_upisa");
                            Integer zavrsna_ocjena_predmeta=0;
                            String datum_zavrsne_ocjene=up.getString("datum_zavrsne_ocjene");
                            db.addUpisaniPredmeti(redni_br_upisa, upisani_id_upisa, upisani_id_predmeta, upisani_datum_upisa, zavrsna_ocjena_predmeta, datum_zavrsne_ocjene);
                        }

                        predmeti = jObj.getJSONArray("predmeti");
                        for(int i=0; i<predmeti.length();i++){
                            JSONObject pr=predmeti.getJSONObject(i);
                            Integer id_predmeta = Integer.parseInt(pr.getString("id_predmeta"));
                            String naziv_predmeta = pr.getString("naziv_predmeta");
                            db.addPredmeti(id_predmeta, naziv_predmeta);
                        }
/*
                        JSONObject upisani_predmeti = jObj.getJSONObject("upisani_predmeti");
                        Integer redni_br_upisa=upisani_predmeti.getInt("redni_br_upisa");
                        Integer upisani_id_upisa=upisani_predmeti.getInt("id_upisa");
                        Integer upisani_id_predmeta = upisani_predmeti.getInt("id_predmeta");
                        String upisani_datum_upisa=upisani_predmeti.getString("datum_upisa");
                        /*Integer zavrsna_ocjena_predmeta=upisani_predmeti.getInt("zavrsna_ocjena_predmeta");*/
                       /* Integer zavrsna_ocjena_predmeta=0;/*upisani_predmeti.getInt("zavrsna_ocjena_predmeta");*/
                      /*  String datum_zavrsne_ocjene=upisani_predmeti.getString("datum_zavrsne_ocjene");*/

                        /*
                        JSONObject predmeti = jObj.getJSONObject("predmeti");
                        Integer id_predmeta = predmeti.getInt("id_predmeta");
                        String naziv_predmeta = predmeti.getString("naziv_predmeta");*/

                        JSONObject nastavnici = jObj.getJSONObject("nastavnici");
                        Integer id_nastavnika = nastavnici.getInt("id_nastavnika");
                        Integer oib_nastavnika = nastavnici.getInt("oib_nastavnika");
                        String nastavnici_ime = nastavnici.getString("ime");
                        String nastavnici_prezime = nastavnici.getString("prezime");

                        JSONObject raz_pred_nast = jObj.getJSONObject("raz_pred_nast");
                        Integer raz_pred_nast_id_razreda = raz_pred_nast.getInt("id_razreda");
                        Integer raz_pred_nast_id_nastavnika=raz_pred_nast.getInt("id_nastavnika");
                        Integer raz_pred_nast_redni_br_upisa = raz_pred_nast.getInt("redni_br_upisa");
                        String datum_od = raz_pred_nast.getString("datum_od");
                        String datum_do=raz_pred_nast.getString("datum_do");


                        //JSONObject ocjene = jObj.getJSONObject("ocjene");
                        //JSONObject ocjene= new JSONObject("ocjene");
                        /*redni_broj_ocjene=ocjene.getInt("redni_broj_ocjene");
                        Integer ocjene_id_ucenika=ocjene.getInt("id_ucenika");
                        Integer id_rubrike=ocjene.getInt("id_rubrike");
                        Integer ocjene_redni_br_upisa=ocjene.getInt("redni_br_upisa");
                        Integer ocjena=ocjene.getInt("ocjena");
                        String datum_ocjene=ocjene.getString("datum_ocjene");
                        String ocjene_komentar=ocjene.getString("komentar");*/

                        ocj = jObj.getJSONArray("ocjene");

                        for(int i=0; i<ocj.length();i++){
                            JSONObject oc=ocj.getJSONObject(i);
                            Integer redni_broj_ocjene = Integer.parseInt(oc.getString("redni_broj_ocjene"));
                            Integer ocjene_id_ucenika=Integer.parseInt(oc.getString("id_ucenika"));
                            Integer id_rubrike=Integer.parseInt(oc.getString("id_rubrike"));
                            Integer ocjene_redni_br_upisa=Integer.parseInt(oc.getString("redni_br_upisa"));
                            Integer ocjena=Integer.parseInt(oc.getString("ocjena"));
                            String datum_ocjene=oc.getString("datum_ocjene");
                            String ocjene_komentar=oc.getString("komentar");
                            db.addOcjene(redni_broj_ocjene, ocjene_id_ucenika, id_rubrike, ocjene_redni_br_upisa, ocjena, datum_ocjene, ocjene_komentar);

                        }


                        /*
                        JSONObject rubrike = jObj.getJSONObject("rubrike");
                        Integer rubrike_id_rubrike=rubrike.getInt("id_rubrike");
                        String naziv_rubrike=rubrike.getString("naziv_rubrike");
                        */

                        rubrike = jObj.getJSONArray("rubrike");
                        for(int i=0; i<rubrike.length();i++){
                            JSONObject rub=rubrike.getJSONObject(i);
                            Integer rubrike_id_rubrike = Integer.parseInt(rub.getString("id_rubrike"));
                            String naziv_rubrike=rub.getString("naziv_rubrike");
                            db.addRubrike(rubrike_id_rubrike, naziv_rubrike);

                        }

                        /*
                        JSONObject komentar = jObj.getJSONObject("komentar");
                        Integer komentar_id_nastavnika=komentar.getInt("id_nastavnika");
                        Integer komentar_id_ucenika=komentar.getInt("id_ucenika");
                        Integer komentar_redni_br_upisa=komentar.getInt("redni_br_upisa");
                        String datum=komentar.getString("datum");
                        String komentar_komentar=komentar.getString("komentar");

                        */

                        komentar = jObj.getJSONArray("komentar");
                        for(int i=0; i<komentar.length();i++){
                            JSONObject kom=komentar.getJSONObject(i);
                            Integer id_komentara=Integer.parseInt(kom.getString("id_komentara"));
                            Integer komentar_id_nastavnika=Integer.parseInt(kom.getString("id_nastavnika"));
                            Integer komentar_id_ucenika=Integer.parseInt(kom.getString("id_ucenika"));
                            Integer komentar_redni_br_upisa=Integer.parseInt(kom.getString("redni_br_upisa"));
                            String datum=kom.getString("datum");
                            String komentar_komentar=kom.getString("komentar");
                            db.addKomentar(id_komentara, komentar_id_nastavnika, komentar_id_ucenika, komentar_redni_br_upisa, datum, komentar_komentar);
                        }

                        // Inserting row in users table
                        //db.addUser(name, surname, email, uid);
                        db.addUser(id_ucenika, ime, prezime, korisnicko_ime);
                        db.addUpis(id_upisa, id_ucenika, id_razreda, datum_upisa);
                        db.addRazredi(raz_id_razreda, godina, razred, godina_upisa);
                        //db.addUpisaniPredmeti(redni_br_upisa, upisani_id_upisa, upisani_id_predmeta, upisani_datum_upisa, zavrsna_ocjena_predmeta, datum_zavrsne_ocjene);
                        //db.addPredmeti(id_predmeta, naziv_predmeta);
                        db.addNastavnici(id_nastavnika, oib_nastavnika, nastavnici_ime, nastavnici_prezime);
                        db.addRaz_pred_nast(raz_pred_nast_id_razreda, raz_pred_nast_id_nastavnika, raz_pred_nast_redni_br_upisa, datum_od, datum_do);
                        //db.addOcjene(redni_broj_ocjene, ocjene_id_ucenika, id_rubrike, ocjene_redni_br_upisa, ocjena, datum_ocjene, ocjene_komentar);
                        //db.addRubrike(rubrike_id_rubrike, naziv_rubrike);
                        //db.addKomentar(komentar_id_nastavnika, komentar_id_ucenika, komentar_redni_br_upisa, datum, komentar_komentar);

                        // Launch main activity
                        Intent intent = new Intent(LoginActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "login");
                params.put("korisnicko_ime", korisnicko_ime);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
