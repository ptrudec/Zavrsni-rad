package tvz.zavrsni.eimenik;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tvz.zavrsni.eimenik.adapter.KomentariListAdapter;
import tvz.zavrsni.eimenik.helper.Komentari;
import tvz.zavrsni.eimenik.helper.SQLiteHandler;

public class CommentsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SQLiteHandler db;


    private List<Komentari> komentari=new ArrayList<>();
    private ListView listView2;
    private KomentariListAdapter adapter2;

    private SwipeRefreshLayout swipeRefreshLayout;


    public CommentsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = new SQLiteHandler(getActivity().getApplicationContext());

        komentari = db.getAllKomentari();



        View rootView = inflater.inflate(R.layout.fragment_comments, container, false);


        listView2 = (ListView) rootView.findViewById(R.id.list_komentari);
        adapter2 = new KomentariListAdapter(CommentsFragment.this.getActivity(), komentari);

        //dodavanje teksta ako nema komentara
        TextView emptyText = (TextView) rootView.findViewById(android.R.id.empty);
        listView2.setEmptyView(emptyText);

        listView2.setAdapter(adapter2);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh1_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

       /* swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //swipeRefreshLayout.setRefreshing(true);

                                    }
                                }
        );*/
        adapter2.notifyDataSetChanged();

        return rootView;
    }

    public void onRefresh() {
        Toast.makeText(getActivity().getApplicationContext(),
                "Ocjene ažurirane", Toast.LENGTH_LONG).show();
    }
}