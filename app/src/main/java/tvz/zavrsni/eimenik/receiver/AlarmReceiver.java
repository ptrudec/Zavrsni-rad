package tvz.zavrsni.eimenik.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tvz.zavrsni.eimenik.HomeFragment;
import tvz.zavrsni.eimenik.app.AppConfig;
import tvz.zavrsni.eimenik.app.AppController;
import tvz.zavrsni.eimenik.helper.SQLiteHandler;

/**
 * Created by Pero on 9.8.2015..
 */
public class AlarmReceiver extends BroadcastReceiver{

    private static final String TAG = "AlarmReceiver";
    private String var_dat = null;
    private String var_id = null;
    JSONArray ocj = null;
    private SQLiteHandler db;

    public Context mContext;
    //public Context context;


    public AlarmReceiver() { }
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Recurring alarm; requesting download service.");
        fetchGr();
        mContext=context;

    }


    public void fetchGr(){

        db = new SQLiteHandler(mContext);
        String tag_string_req = "req_update";

        var_dat = db.getLatestDate();
        var_id = db.getId();
        Log.d(TAG, "Variable datum: " + var_dat.toString());
        Log.d(TAG, "Variable id: " + var_id.toString());

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPDATE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Update Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    ocj = jObj.getJSONArray("ocjene");
                    if (!error) {
                        for (int i = 0; i < ocj.length(); i++) {
                            JSONObject oc = ocj.getJSONObject(i);
                            Integer redni_broj_ocjene = Integer.parseInt(oc.getString("redni_broj_ocjene"));
                            Integer ocjene_id_ucenika = Integer.parseInt(oc.getString("id_ucenika"));
                            Integer id_rubrike = Integer.parseInt(oc.getString("id_rubrike"));
                            Integer ocjene_redni_br_upisa = Integer.parseInt(oc.getString("redni_br_upisa"));
                            Integer ocjena = Integer.parseInt(oc.getString("ocjena"));
                            String datum_ocjene = oc.getString("datum_ocjene");
                            String ocjene_komentar = oc.getString("komentar");
                            db.addOcjene(redni_broj_ocjene, ocjene_id_ucenika, id_rubrike, ocjene_redni_br_upisa, ocjena, datum_ocjene, ocjene_komentar);

                        }


                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(mContext,
                                errorMsg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }


            }
        }
                , new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Update Error: " + error.getMessage());
                Toast.makeText(mContext,
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<String, String>();
                param.put("tag", "update");
                param.put("datum",var_dat);
                param.put("id_var",var_id);

                Log.d(TAG, "Update Response: " + param.toString());
                return param;
            }


        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    }

