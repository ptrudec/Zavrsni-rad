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
                        Integer id_ucenika=user.getInt("id_ucenika");

                        JSONObject upis = jObj.getJSONObject("upis");
                        Integer id_upisa=upis.getInt("id_upisa");
                        Integer id_razreda=upis.getInt("id_razreda");
                        String datum_upisa=upis.getString("datum_upisa");
                        /*SimpleDateFormat dupisa = new SimpleDateFormat("yyyy-MM-dd");
                        Date datum_u = null;
                        try {
                            datum_u = dupisa.parse(datum_upisa);
                        }catch(ParseException e){
                            e.printStackTrace();
                        }*/

                        JSONObject razredi = jObj.getJSONObject("razredi");
                        Integer id_razreda=razredi.getInt("id_razreda");
                        Integer godina=razredi.getInt("godina");
                        Integer razred=razredi.getInt("razred");
                        String godina_upisa=razredi.getString("godina_upisa");

                        // Inserting row in users table
                        //db.addUser(name, surname, email, uid);
                        db.addUser(id_ucenika, ime, prezime, korisnicko_ime);
                        db.addUpis(id_upisa, id_ucenika, id_razreda, datum_upisa);
                        db.addRazredi(id_razreda, godina, razred, godina_upisa);

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
