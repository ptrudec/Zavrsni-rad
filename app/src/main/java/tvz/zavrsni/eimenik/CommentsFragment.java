package tvz.zavrsni.eimenik;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import tvz.zavrsni.eimenik.adapter.KomentariListAdapter;
import tvz.zavrsni.eimenik.app.AppConfig;
import tvz.zavrsni.eimenik.app.AppController;
import tvz.zavrsni.eimenik.helper.Komentari;
import tvz.zavrsni.eimenik.helper.SQLiteHandler;

public class CommentsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SQLiteHandler db;
    private static final String TAG = CommentsFragment.class.getSimpleName();
    JSONArray kom = null;

    private String var_dat = null;
    private String var_id = null;

    private List<Komentari> komentari=new ArrayList<>();
    private ListView listView2;
    private KomentariListAdapter adapter2;

    private SwipeRefreshLayout swipeRefreshLayout;


    public CommentsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = new SQLiteHandler(getActivity().getApplicationContext());

        komentari.addAll(db.getAllKomentari());
        View rootView = inflater.inflate(R.layout.fragment_comments, container, false);

        listView2 = (ListView) rootView.findViewById(R.id.list_komentari);
        adapter2 = new KomentariListAdapter(CommentsFragment.this.getActivity(), komentari);

        TextView emptyText = (TextView) rootView.findViewById(android.R.id.empty);
        listView2.setEmptyView(emptyText);

        listView2.setAdapter(adapter2);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh1_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        fetchComments();

                                    }
                                }
        );

        return rootView;
    }

    public void onRefresh() {

        swipeRefreshLayout.setRefreshing(true);
        fetchComments();
        Toast.makeText(getActivity().getApplicationContext(),
                "Komentari ažurirani", Toast.LENGTH_LONG).show();
    }

    public void fetchComments(){

        String tag_string_req = "req_update";

        var_dat = db.getLatestDateComments();
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

                    kom = jObj.getJSONArray("komentari");
                    if (!error) {
                        for (int i = 0; i < kom.length(); i++) {
                            JSONObject oc = kom.getJSONObject(i);
                            Integer id_komentara = Integer.parseInt(oc.getString("id_komentara"));
                            Integer id_nastavnika = Integer.parseInt(oc.getString("id_nastavnika"));
                            Integer id_ucenika = Integer.parseInt(oc.getString("id_ucenika"));
                            Integer redni_br_upisa = Integer.parseInt(oc.getString("redni_br_upisa"));
                            String datum = oc.getString("datum");
                            String komentar = oc.getString("komentar");
                            db.addKomentar(id_komentara, id_nastavnika, id_ucenika, redni_br_upisa, datum, komentar);

                        }


                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getActivity().getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
                komentari.clear();
                komentari.addAll(db.getAllKomentari());
                adapter2.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);


            }
        }
                , new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Update Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<String, String>();
                param.put("tag", "update_comments");
                param.put("datum",var_dat);
                param.put("id_var",var_id);

                Log.d(TAG, "Comments Response: " + param.toString());
                return param;
            }


        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    }
