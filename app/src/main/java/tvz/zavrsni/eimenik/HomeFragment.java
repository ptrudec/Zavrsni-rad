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

import tvz.zavrsni.eimenik.adapter.OcjeneListAdapter;
import tvz.zavrsni.eimenik.helper.Ocjene;
import tvz.zavrsni.eimenik.helper.SQLiteHandler;

public class HomeFragment extends Fragment {
    private SQLiteHandler db;


    private List<Ocjene> ocjene=new ArrayList<>();
    private ListView listView;
    private OcjeneListAdapter adapter1;


    public HomeFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = new SQLiteHandler(getActivity().getApplicationContext());

        ocjene = db.getZadnjeOcjene();

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        listView = (ListView) rootView.findViewById(R.id.list);
        adapter1 = new OcjeneListAdapter(HomeFragment.this.getActivity(), ocjene);
        listView.setAdapter(adapter1);
        //adapter1.notifyDataSetChanged();

        //db.close();

        return rootView;
    }
}
