package tvz.zavrsni.eimenik;

/**
 * Created by Pero on 22.6.2015..
 */
import tvz.zavrsni.eimenik.app.AppConfig;
import tvz.zavrsni.eimenik.app.AppController;
import tvz.zavrsni.eimenik.helper.SessionManager;
import tvz.zavrsni.eimenik.helper.SQLiteHandler;

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

import com.android.volley.NoConnectionError;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new SQLiteHandler(getApplicationContext());

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


    }

    protected void onStart(){
        super.onStart();

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


        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RecoverPasswordActivity.class);
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

                        JSONObject user = jObj.getJSONObject("user");
                        String ime = user.getString("ime");
                        String prezime = user.getString("prezime");
                        String korisnicko_ime = user.getString("korisnicko_ime");
                        Integer id_ucenika = user.getInt("id_ucenika");

                        JSONObject upis = jObj.getJSONObject("upis");
                        Integer id_upisa=upis.getInt("id_upisa");
                        Integer id_razreda = upis.getInt("id_razreda");
                        String datum_upisa = upis.getString("datum_upisa");

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
                            Integer zavrsna_ocjena_predmeta=null;
                            String datum_zavrsne_ocjene=null;

                            if(up.getString("zavrsna_ocjena_predmeta")=="null") {
                            }
                            else {
                                zavrsna_ocjena_predmeta = Integer.parseInt(up.getString("zavrsna_ocjena_predmeta"));
                            }

                            if(up.getString("datum_zavrsne_ocjene")=="null") {
                            }
                            else {
                                datum_zavrsne_ocjene = up.getString("datum_zavrsne_ocjene");
                            }
                            db.addUpisaniPredmeti(redni_br_upisa, upisani_id_upisa, upisani_id_predmeta, upisani_datum_upisa, zavrsna_ocjena_predmeta, datum_zavrsne_ocjene);
                        }

                        predmeti = jObj.getJSONArray("predmeti");
                        for(int i=0; i<predmeti.length();i++){
                            JSONObject pr=predmeti.getJSONObject(i);
                            Integer id_predmeta = Integer.parseInt(pr.getString("id_predmeta"));
                            String naziv_predmeta = pr.getString("naziv_predmeta");
                            db.addPredmeti(id_predmeta, naziv_predmeta);
                        }

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

                        rubrike = jObj.getJSONArray("rubrike");
                        for(int i=0; i<rubrike.length();i++){
                            JSONObject rub=rubrike.getJSONObject(i);
                            Integer rubrike_id_rubrike = Integer.parseInt(rub.getString("id_rubrike"));
                            String naziv_rubrike=rub.getString("naziv_rubrike");
                            db.addRubrike(rubrike_id_rubrike, naziv_rubrike);

                        }

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
                        db.addUser(id_ucenika, ime, prezime, korisnicko_ime);
                        db.addUpis(id_upisa, id_ucenika, id_razreda, datum_upisa);
                        db.addRazredi(raz_id_razreda, godina, razred, godina_upisa);
                        db.addNastavnici(id_nastavnika, oib_nastavnika, nastavnici_ime, nastavnici_prezime);
                        db.addRaz_pred_nast(raz_pred_nast_id_razreda, raz_pred_nast_id_nastavnika, raz_pred_nast_redni_br_upisa, datum_od, datum_do);

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

                if(error instanceof NoConnectionError){
                    String error_msg="Nema internet veze, molimo uključite wifi ili mobilne podatke.";
                    Toast.makeText(getApplicationContext(),
                            error_msg, Toast.LENGTH_LONG).show();
                    hideDialog();
                }
                else {
                    Log.e(TAG, "Login Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();
                }
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
