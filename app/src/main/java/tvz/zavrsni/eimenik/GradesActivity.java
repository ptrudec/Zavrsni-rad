package tvz.zavrsni.eimenik;

/**
 * Created by Pero on 25.7.2015..
 */

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


import tvz.zavrsni.eimenik.adapter.OcjeneListAdapter;
import tvz.zavrsni.eimenik.helper.Ocjene;
import tvz.zavrsni.eimenik.helper.SQLiteHandler;


/**
 * Created by Pero on 25.7.2015..
 */
public class GradesActivity extends AppCompatActivity {
//AppCompatActivity {


    private SQLiteHandler db;


    private List<Ocjene> ocjene=new ArrayList<>();
    private ListView listView4;
    private OcjeneListAdapter adapter4;
    private Integer var;

    public GradesActivity(){};


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);
        Bundle bundle = getIntent().getExtras();
        var=bundle.getInt("var_upis");



        //db = new SQLiteHandler(this.getApplicationContext());

        db = new SQLiteHandler(this.getApplicationContext());
        ocjene = db.getAllOcjene(var);




        listView4 = (ListView) findViewById(R.id.list_ocj);
        adapter4 = new OcjeneListAdapter(this, ocjene);
        listView4.setAdapter(adapter4);

        db.close();
        adapter4.notifyDataSetChanged();

    }




}
