package tvz.zavrsni.eimenik.helper;

/**
 * Created by Pero on 12.7.2015..
 */
public class Ocjene {
    int ocjena;
    String datum_ocjene;
    String naziv_rubrike;
    String predmet;
    String komentar;

    // constructors
    public Ocjene() {
    }

    public Ocjene(int ocjena, String datum_ocjene, String naziv_rubrike, String predmet, String komentar) {
        this.ocjena = ocjena;
        this.datum_ocjene = datum_ocjene;
        this.naziv_rubrike = naziv_rubrike;
        this.predmet = predmet;
        this.komentar = komentar;
    }

    // setters
    public void setOcjena(int ocjena) {
        this.ocjena = ocjena;
    }

    public void setDatumOcjene(String datum_ocjene) {
        this.datum_ocjene = datum_ocjene;
    }

    public void setNazivRubrike(String naziv_rubrike) {
        this.naziv_rubrike = naziv_rubrike;
    }

    public void setPredmet (String predmet){
        this.predmet = predmet;
    }
    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    // getters
    public int GetOcjena() {
        return this.ocjena;
    }

    public String GetDatumOocjene() {
        return this.datum_ocjene;
    }

    public String GetNazivRubrike() {
        return this.naziv_rubrike;
    }
    public String GetPredmet() {
        return this.predmet;
    }
    public String GetKomentar() {
        return this.komentar;
    }
}
