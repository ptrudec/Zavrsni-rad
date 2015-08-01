package tvz.zavrsni.eimenik.adapter;

import tvz.zavrsni.eimenik.helper.Predmeti;
import tvz.zavrsni.eimenik.R;
import tvz.zavrsni.eimenik.GradesActivity;

import java.util.List;

import android.content.Intent;
import android.widget.BaseAdapter;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PredmetiListAdapter extends BaseAdapter {

    private static final String TAG = PredmetiListAdapter.class.getSimpleName();
    private Activity activity;
    private LayoutInflater inflater;
    private List<Predmeti> predmeti;
    private Integer var_upis;

    public PredmetiListAdapter(Activity activity, List<Predmeti> predmeti) {
        this.activity = activity;
        this.predmeti = predmeti;
    }

    public int getCount() {
        return predmeti.size();
    }

    @Override
    public Object getItem(int location) {
        return predmeti.get(location);
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
            convertView = inflater.inflate(R.layout.list_predmeti, null);

        TextView naziv_predmeta = (TextView) convertView.findViewById(R.id.naziv_predmeta_predmeta);

        final Predmeti m = predmeti.get(position);
        naziv_predmeta.setText(String.valueOf(m.GetNazivPredmeta()));

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(activity, GradesActivity.class);
                        var_upis=Integer.parseInt(String.valueOf(m.GetRedniBrUpisa()));
                        intent.putExtra("var_upis", var_upis);
                        activity.startActivity(intent);

                    }
                });
        return convertView;

    }

}
