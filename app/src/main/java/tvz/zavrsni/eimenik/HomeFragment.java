package tvz.zavrsni.eimenik;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

import tvz.zavrsni.eimenik.adapter.MyAdapter;
import tvz.zavrsni.eimenik.adapter.OcjeneListAdapter;
import tvz.zavrsni.eimenik.app.AppConfig;
import tvz.zavrsni.eimenik.app.AppController;
import tvz.zavrsni.eimenik.helper.Ocjene;
import tvz.zavrsni.eimenik.helper.SQLiteHandler;
import tvz.zavrsni.eimenik.receiver.AlarmReceiver;


public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SQLiteHandler db;
    private static final String TAG = HomeFragment.class.getSimpleName();

    JSONArray ocj = null;

    private String var_dat = null;
    private String var_id = null;

    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Ocjene> ocjene = new ArrayList<>();

    private ListView listView;
    private OcjeneListAdapter adapter1;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context mContext;


    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //mContext = getActivity();
        db = new SQLiteHandler(getActivity().getApplicationContext());
        //ocjene = db.getZadnjeOcjene();
        ocjene.addAll(db.getZadnjeOcjene());

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        listView = (ListView) rootView.findViewById(R.id.list_posljednje_ocjene);
        adapter1 = new OcjeneListAdapter(HomeFragment.this.getActivity(), ocjene);
        listView.setAdapter(adapter1);

/*
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_posljednje_ocjene);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter= new MyAdapter(HomeFragment.this.getActivity(), ocjene);
        mRecyclerView.setAdapter(mAdapter);
*/
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);


        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        fetchGrades();
                                    }
                                }
        );

        //setRecurringAlarm(getActivity().getApplicationContext());
        return rootView;
    }


    private void setRecurringAlarm(Context context) {

        // we know mobiletuts updates at right around 1130 GMT.
        // let's grab new stuff at around 11:45 GMT, inexactly
        Calendar updateTime = Calendar.getInstance();
        updateTime.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        updateTime.set(Calendar.HOUR_OF_DAY, 20);
        updateTime.set(Calendar.MINUTE, 36);

        //updateTime.add(Calendar.SECOND, 30);

        Intent downloader = new Intent(context, AlarmReceiver.class);
        PendingIntent recurringDownload = PendingIntent.getBroadcast(context,
                0, downloader, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarms = (AlarmManager) getActivity().getSystemService(
                Context.ALARM_SERVICE);
        alarms.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                updateTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY,
                recurringDownload);
    }


    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        fetchGrades();
        //ocjene.clear();
        //ocjene.addAll(db.getZadnjeOcjene());

        /*new Handler().post(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();

            }
        });*/




        //adapter1=null;
        //adapter1 = new OcjeneListAdapter(HomeFragment.this.getActivity(), ocjene);
        //listView.setAdapter(adapter1);
        //ocjene=db.getZadnjeOcjene();
       // Log.d(TAG, "Ocjene2 list: " + ocjene.toString());
        //

        //listView.invalidateViews();
        //adapter1.notifyDataSetChanged();


        Toast.makeText(getActivity().getApplicationContext(),
                "Ocjene ažurirane", Toast.LENGTH_LONG).show();



    }




    public void fetchGrades() {

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
                        Toast.makeText(getActivity().getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
                ocjene.clear();
                ocjene.addAll(db.getZadnjeOcjene());
                adapter1.notifyDataSetChanged();
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
