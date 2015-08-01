package tvz.zavrsni.eimenik;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tvz.zavrsni.eimenik.adapter.PredmetiListAdapter;
import tvz.zavrsni.eimenik.helper.Predmeti;
import tvz.zavrsni.eimenik.helper.SQLiteHandler;

public class GradesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SQLiteHandler db;


    private List<Predmeti> predmeti=new ArrayList<>();
    private ListView listView3;
    private PredmetiListAdapter adapter3;

    private SwipeRefreshLayout swipeRefreshLayout;

    public GradesFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = new SQLiteHandler(getActivity().getApplicationContext());

        predmeti = db.getAllPredmeti();

        View rootView = inflater.inflate(R.layout.fragment_grades, container, false);

        listView3 = (ListView) rootView.findViewById(R.id.list_predmeti);
        adapter3 = new PredmetiListAdapter(GradesFragment.this.getActivity(), predmeti);
        listView3.setAdapter(adapter3);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh2_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        /*swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //swipeRefreshLayout.setRefreshing(true);

                                    }
                                }
        );*/

        db.close();
        adapter3.notifyDataSetChanged();


        return rootView;
    }
    public void onRefresh() {

        Toast.makeText(getActivity().getApplicationContext(),
                "Ocjene ažurirane", Toast.LENGTH_LONG).show();
    }
}