package tvz.zavrsni.eimenik;

import tvz.zavrsni.eimenik.helper.SessionManager;
import tvz.zavrsni.eimenik.mail.BackgroundMail;
import tvz.zavrsni.eimenik.mail.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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

public class RecoverPasswordActivity extends AppCompatActivity {



    private static final String TAG = RecoverPasswordActivity.class.getSimpleName();
    private Button bttnReset;
    private EditText email;
    private ProgressDialog pDialog;
    private SessionManager session;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        email = (EditText) findViewById(R.id.email);
        bttnReset = (Button) findViewById(R.id.btnRecover);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        session = new SessionManager(getApplicationContext());

        if (session.isLoggedIn()) {
            Intent intent = new Intent(RecoverPasswordActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }

        bttnReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String mail = email.getText().toString();

                if (!mail.isEmpty()) {

                    checkEmail(mail);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Molimo unesite e-mail!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }


    private void checkEmail(final String mail){

        String tag_string_req = "req_recover";

        pDialog.setMessage("Provjera maila ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CHECK_MAIL, new Response.Listener<String>() {

            public void onResponse(String response) {
                Log.d(TAG, "Reset password Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        JSONObject hash = jObj.getJSONObject("promjena");
                        String hsh = hash.getString("hash");
                        BackgroundMail bm = new BackgroundMail(RecoverPasswordActivity.this);
                        bm.setGmailUserName("e-imenik@gmail.com");
                        bm.setGmailPassword("password");
                        bm.setMailTo(mail);
                        bm.setFormSubject("Eimenik reset lozinke");
                        bm.setFormBody("Molimo vas da na sljedećem linku resetirate lozinku: \r\n" + "http://localhost:8000/eimenik/reset_password.php?id=" +hsh +
                                "\r\n");
                        bm.send();

                        Toast.makeText(getApplicationContext(),
                                "Molimo provjerite e-mail pretinac!", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(
                                RecoverPasswordActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

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
                    Log.e(TAG, "Recover Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }
        }) {



            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "check_email");
                params.put("mail", mail);
                Log.e(TAG, "Params: " + params.toString());
                return params;
            }

        };

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
