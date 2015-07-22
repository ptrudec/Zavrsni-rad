package tvz.zavrsni.eimenik.adapter;

import tvz.zavrsni.eimenik.helper.Komentari;
import tvz.zavrsni.eimenik.R;


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
/**
 * Created by Pero on 22.7.2015..
 */
public class KomentariListAdapter extends BaseAdapter {

    private static final String TAG = OcjeneListAdapter.class.getSimpleName();
    private Activity activity;
    private LayoutInflater inflater;
    private List<Komentari> komentari;

    public KomentariListAdapter(Activity activity, List<Komentari> komentari) {
        this.activity = activity;
        this.komentari = komentari;
    }

    public int getCount() {
        return komentari.size();
    }

    @Override
    public Object getItem(int location) {
        return komentari.get(location);
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
            convertView = inflater.inflate(R.layout.list_komentari, null);
        TextView datum_komentara = (TextView) convertView.findViewById(R.id.datum_komentara);
        TextView vrijeme_komentara = (TextView) convertView.findViewById(R.id.vrijeme_komentara);
        TextView naziv_predmeta = (TextView) convertView.findViewById(R.id.naziv_predmeta_komentara);
        TextView komentar = (TextView) convertView.findViewById(R.id.komentar_komentar);


        Komentari m = komentari.get(position);
        naziv_predmeta.setText(String.valueOf(m.GetNaziv_predmeta()));
        komentar.setText(String.valueOf(m.GetKomentarKomentar()));

        String dat=String.valueOf(m.GetDatumKomentara());
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

        //datum_ocjene.setText(String.valueOf(m.GetDatumOocjene()));
        datum_komentara.setText(datum);
        vrijeme_komentara.setText(vrijeme);
        return convertView;
    }
}
