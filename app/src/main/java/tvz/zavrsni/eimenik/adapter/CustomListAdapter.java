package tvz.zavrsni.eimenik.adapter;

import tvz.zavrsni.eimenik.helper.Ocjene;
import tvz.zavrsni.eimenik.helper.SQLiteHandler;
import tvz.zavrsni.eimenik.R;


import android.util.Log;
import android.widget.BaseAdapter;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import tvz.zavrsni.eimenik.helper.Ocjene;

/**
 * Created by Pero on 15.7.2015..
 */
public class CustomListAdapter extends BaseAdapter {
    private static final String TAG =CustomListAdapter.class.getSimpleName();
    private Activity activity;
    private LayoutInflater inflater;
    private List<Ocjene> ocjene;

    public CustomListAdapter(Activity activity, List<Ocjene> ocjene) {
        this.activity = activity;
        this.ocjene = ocjene;
    }

    public int getCount() {
        return ocjene.size();
    }

    @Override
    public Object getItem(int location) {
        return ocjene.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
                inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);
        TextView thumbnail = (TextView) convertView.findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);
        TextView genre = (TextView) convertView.findViewById(R.id.genre);



        Ocjene m = ocjene.get(position);
        thumbnail.setText(String.valueOf(m.GetOcjena()));
        title.setText(String.valueOf(m.GetPredmet()));
        rating.setText(String.valueOf(m.GetNazivRubrike()));
        year.setText(String.valueOf(m.GetDatumOocjene()));
        genre.setText(String.valueOf(m.GetKomentar()));
        return convertView;

        // getting movie data for the row
    }

}
