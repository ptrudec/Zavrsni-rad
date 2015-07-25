package tvz.zavrsni.eimenik.adapter;

import tvz.zavrsni.eimenik.helper.Predmeti;
import tvz.zavrsni.eimenik.R;
import tvz.zavrsni.eimenik.GradesActivity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.BaseAdapter;
import android.app.Fragment;

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
 * Created by Pero on 24.7.2015..
 */
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

        //TextView zavrsna_ocjena_predmeta = (TextView) convertView.findViewById(R.id.zavrsna_ocjena_predmeta);
        //TextView vrijeme_zavrsne_ocjene = (TextView) convertView.findViewById(R.id.vrijeme_zavrsne_ocjene);
        //TextView datum_zavrsne_ocjene = (TextView) convertView.findViewById(R.id.datum_zavrsne_ocjene);
        TextView naziv_predmeta = (TextView) convertView.findViewById(R.id.naziv_predmeta_predmeta);
        //TextView zavrsna_ocjena_predmeta_text = (TextView) convertView.findViewById(R.id.zavrsna_ocjena_predmeta_text);


        final Predmeti m = predmeti.get(position);

        /*if((m.GetZavrsnaOcjena())!="null") {
            String var = "Zaključna ocjena:"+String.valueOf(m.GetZavrsnaOcjena());
            zavrsna_ocjena_predmeta_text.setText(var);
            //zavrsna_ocjena_predmeta.setText(String.valueOf(m.GetZavrsnaOcjena()));

        }
        else{
            zavrsna_ocjena_predmeta_text.setVisibility(View.GONE);
        }*/

        naziv_predmeta.setText(String.valueOf(m.GetNazivPredmeta()));


        /*String dat=String.valueOf(m.GetDatumZavrsneOcjene());
        if(dat!="null" && !dat.isEmpty()) {
            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat output_datum = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat output_vrijeme = new SimpleDateFormat("HH:mm");
            String datum = null;
            String vrijeme = null;
            try {
                Date date = input.parse(dat);
                datum = output_datum.format(date);
                vrijeme = output_vrijeme.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //datum_ocjene.setText(String.valueOf(m.GetDatumOocjene()));
            datum_zavrsne_ocjene.setText(datum);
            vrijeme_zavrsne_ocjene.setText(vrijeme);

        }*/

                //datum_zavrsne_ocjene.setText(dat);

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
