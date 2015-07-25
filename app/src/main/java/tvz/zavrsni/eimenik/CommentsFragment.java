package tvz.zavrsni.eimenik;

/**
 * Created by Pero on 12.7.2015..
 */
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import tvz.zavrsni.eimenik.adapter.KomentariListAdapter;
import tvz.zavrsni.eimenik.helper.Komentari;
import tvz.zavrsni.eimenik.helper.SQLiteHandler;

public class CommentsFragment extends Fragment {
    private SQLiteHandler db;


    private List<Komentari> komentari=new ArrayList<>();
    private ListView listView2;
    private KomentariListAdapter adapter2;


    public CommentsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = new SQLiteHandler(getActivity().getApplicationContext());

        komentari = db.getAllKomentari();



        View rootView = inflater.inflate(R.layout.fragment_comments, container, false);


        listView2 = (ListView) rootView.findViewById(R.id.list_komentari);
        adapter2 = new KomentariListAdapter(CommentsFragment.this.getActivity(), komentari);
        listView2.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();

        return rootView;
    }
}