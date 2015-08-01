package tvz.zavrsni.eimenik.helper;

public class Komentari {
    String datum_komentara;
    String naziv_predmeta;
    String komentar;

    // constructors
    public Komentari() {
    }

    public Komentari(String datum_komentara, String naziv_predmeta, String komentar) {
        this.datum_komentara = datum_komentara;
        this.naziv_predmeta = naziv_predmeta;
        this.komentar = komentar;
    }

    // setters
    public void setDatumKomentara(String datum_komentara) {
        this.datum_komentara = datum_komentara;
    }

    public void setNaziv_predmeta(String naziv_predmeta) {
        this.naziv_predmeta = naziv_predmeta;
    }

    public void setKomentarKomentar(String komentar) {
        this.komentar = komentar;
    }

    // getters

    public String GetDatumKomentara() {
        return this.datum_komentara;
    }

    public String GetNaziv_predmeta() {
        return this.naziv_predmeta;
    }
    public String GetKomentarKomentar() {
        return this.komentar;
    }

}
