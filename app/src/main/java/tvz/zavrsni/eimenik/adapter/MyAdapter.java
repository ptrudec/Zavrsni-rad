package tvz.zavrsni.eimenik.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import tvz.zavrsni.eimenik.R;
import tvz.zavrsni.eimenik.helper.Ocjene;

/**
 * Created by Pero on 9.8.2015..
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Ocjene> ocjene;
    private Activity activity;


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView ocjena;
        public TextView naziv_predmeta;
        public TextView naziv_rubrike;
        public TextView datum_ocjene;
        public TextView vrijeme_ocjene;
        public TextView komentar_ocjene;
        public ViewHolder(View v){
            super (v);
            ocjena = (TextView) v.findViewById(R.id.ocjena);
             naziv_predmeta = (TextView) v.findViewById(R.id.naziv_predmeta);
             naziv_rubrike = (TextView) v.findViewById(R.id.naziv_rubrike);
             datum_ocjene = (TextView) v.findViewById(R.id.datum_ocjene);
             vrijeme_ocjene = (TextView) v.findViewById(R.id.vrijeme_ocjene);
             komentar_ocjene = (TextView) v.findViewById(R.id.komentar_ocjene);
        }
    }

    public MyAdapter(Activity activity, List<Ocjene> ocjene){
        this.activity = activity;
        this.ocjene = ocjene;
    }

    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ocjene, parent, false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }
    public void onBindViewHolder(ViewHolder holder, int position){
        Ocjene m = ocjene.get(position);
        holder.ocjena.setText(String.valueOf(m.GetOcjena()));
        holder.naziv_predmeta.setText(String.valueOf(m.GetPredmet()));
        holder.naziv_rubrike.setText(String.valueOf(m.GetNazivRubrike()));

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

        holder.datum_ocjene.setText(datum);
        holder.vrijeme_ocjene.setText(vrijeme);
        holder.komentar_ocjene.setText(String.valueOf(m.GetKomentar()));

    }


    public int getItemCount(){
        return ocjene.size();
    }





}
