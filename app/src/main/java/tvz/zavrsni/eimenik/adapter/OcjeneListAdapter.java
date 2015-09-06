package tvz.zavrsni.eimenik.adapter;

import tvz.zavrsni.eimenik.helper.Ocjene;
import tvz.zavrsni.eimenik.R;

import android.support.v7.widget.RecyclerView;
import android.widget.BaseAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class OcjeneListAdapter extends BaseAdapter {
    private static final String TAG =OcjeneListAdapter.class.getSimpleName();
    private Activity activity;
    private LayoutInflater inflater;
    private List<Ocjene> ocjene;

    public OcjeneListAdapter(Activity activity, List<Ocjene> ocjene) {
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
            convertView = inflater.inflate(R.layout.list_ocjene, null);
        TextView ocjena = (TextView) convertView.findViewById(R.id.ocjena);
        TextView naziv_predmeta = (TextView) convertView.findViewById(R.id.naziv_predmeta);
        TextView naziv_rubrike = (TextView) convertView.findViewById(R.id.naziv_rubrike);
        TextView datum_ocjene = (TextView) convertView.findViewById(R.id.datum_ocjene);
        TextView vrijeme_ocjene = (TextView) convertView.findViewById(R.id.vrijeme_ocjene);
        TextView komentar_ocjene = (TextView) convertView.findViewById(R.id.komentar_ocjene);



        Ocjene m = ocjene.get(position);
        ocjena.setText(String.valueOf(m.GetOcjena()));
        naziv_predmeta.setText(String.valueOf(m.GetPredmet()));
        naziv_rubrike.setText(String.valueOf(m.GetNazivRubrike()));

        String dat=String.valueOf(m.GetDatumOocjene());
        SimpleDateFormat input=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat output_datum=new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat output_vrijeme=new SimpleDateFormat("HH:mm");
        String datum=null;
        String vrijeme=null;
        try {
            Date date = input.parse(dat);
            datum = output_datum.format(date);
            vrijeme = output_vrijeme.format(date);
        }
        catch (ParseException e){
            e.printStackTrace();
        }

        datum_ocjene.setText(datum);
        vrijeme_ocjene.setText(vrijeme);
        komentar_ocjene.setText(String.valueOf(m.GetKomentar()));
        return convertView;

    }

}
