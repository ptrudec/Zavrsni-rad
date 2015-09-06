package tvz.zavrsni.eimenik;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import tvz.zavrsni.eimenik.adapter.OcjeneListAdapter;
import tvz.zavrsni.eimenik.app.AppConfig;
import tvz.zavrsni.eimenik.app.AppController;
import tvz.zavrsni.eimenik.helper.Ocjene;
import tvz.zavrsni.eimenik.helper.SQLiteHandler;


public class GradesActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = GradesActivity.class.getSimpleName();
    private SQLiteHandler db;
    JSONArray ocj = null;

    private String var_dat = null;
    private String var_id = null;

    private List<Ocjene> ocjene=new ArrayList<>();
    private ListView listView4;
    private OcjeneListAdapter adapter4;
    private Integer var;
    private SwipeRefreshLayout swipeRefreshLayout;

    public GradesActivity(){};


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);
        Bundle bundle = getIntent().getExtras();

        var=bundle.getInt("var_upis");

        db = new SQLiteHandler(this.getApplicationContext());
        ocjene = db.getAllOcjene(var);

        listView4 = (ListView) findViewById(R.id.list_ocj);
        adapter4 = new OcjeneListAdapter(this, ocjene);

        TextView emptyText = (TextView)findViewById(android.R.id.empty);
        listView4.setEmptyView(emptyText);
        listView4.setAdapter(adapter4);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout2);
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        fetchGrades();
                                    }
                                }
        );

        adapter4.notifyDataSetChanged();

    }

    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        fetchGrades();

        Toast.makeText(GradesActivity.this.getApplicationContext(),
                "Ocjene ažurirane", Toast.LENGTH_LONG).show();

    }
    public void fetchGrades() {

        String tag_string_req = "req_grades";
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
                        Toast.makeText(GradesActivity.this.getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
                ocjene.clear();
                ocjene.addAll(db.getAllOcjene(var));
                adapter4.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);


            }
        }
                , new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Update Error: " + error.getMessage());
                Toast.makeText(GradesActivity.this.getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<String, String>();
                param.put("tag", "update");
                param.put("datum",var_dat);
                param.put("id_var",var_id);

                Log.d(TAG, "Grade Response: " + param.toString());
                return param;
            }


        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
