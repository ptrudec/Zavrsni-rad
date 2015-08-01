package tvz.zavrsni.eimenik;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tvz.zavrsni.eimenik.app.AppConfig;
import tvz.zavrsni.eimenik.app.AppController;
import tvz.zavrsni.eimenik.helper.SQLiteHandler;
import tvz.zavrsni.eimenik.helper.SessionManager;

public class ChangePasswordActivity extends AppCompatActivity {

    private static final String TAG = ChangePasswordActivity.class.getSimpleName();

    private Button bttnChangePassword;
    private EditText oldPassword;
    private EditText newPassword1;
    private EditText newPassword2;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    private String varr_id = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldPassword = (EditText) findViewById(R.id.stara_lozinka);
        newPassword1 = (EditText) findViewById(R.id.nova_lozinka1);
        newPassword2 = (EditText) findViewById(R.id.nova_lozinka2);
        bttnChangePassword = (Button) findViewById(R.id.btnChange);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        bttnChangePassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String stara_lozinka = oldPassword.getText().toString();
                String nova_lozinka1 = newPassword1.getText().toString();
                String nova_lozinka2 = newPassword2.getText().toString();

                if (!stara_lozinka.isEmpty() && !nova_lozinka1.isEmpty() && !nova_lozinka2.isEmpty()) {
                    if (nova_lozinka1.equals(nova_lozinka2)) {
                        ChangePassword(stara_lozinka, nova_lozinka1);
                    }else{
                        Toast.makeText(getApplicationContext(),
                                "Nove lozinke moraju biti iste. Molimo ponovno unesite!", Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Molimo unesite staru i novu lozinku!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

    }
    private void ChangePassword(final String stara_lozinka, final String nova_lozinka1){
        varr_id = db.getId();
        String tag_string_req = "req_update";

        pDialog.setMessage("Promjena lozinke ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CHANGE_PASSWORD, new Response.Listener<String>() {

            public void onResponse(String response) {
                Log.d(TAG, "Change password Response: " + response.toString());
                hideDialog();
               try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        Toast.makeText(getApplicationContext(),
                                "Lozinka uspješno promjenjena!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                ChangePasswordActivity.this,
                                LoginActivity.class);
                        session.setLogin(false);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
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
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "change_password");
                params.put("id_ucenika", varr_id);
                params.put("stara_lozinka",stara_lozinka);
                params.put("nova_lozinka", nova_lozinka1);


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

