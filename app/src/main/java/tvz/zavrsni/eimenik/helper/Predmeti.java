package tvz.zavrsni.eimenik.helper;

public class Predmeti {

    String datum_zavrsne_ocjene;
    String zavrsna_ocjena;
    String naziv_predmeta;
    Integer redni_br_upisa;

    // constructors
    public Predmeti() {
    }

    public Predmeti(String datum_zavrsne_ocjene, String zavrsna_ocjena, String naziv_predmeta, Integer redni_br_upisa) {
        this.datum_zavrsne_ocjene = datum_zavrsne_ocjene;
        this.zavrsna_ocjena = zavrsna_ocjena;
        this.naziv_predmeta = naziv_predmeta;
        this.redni_br_upisa=redni_br_upisa;
    }


    public void setNazivPredmeta(String naziv_predmeta) {
        this.naziv_predmeta = naziv_predmeta;
    }

    public void setRedniBrUpisa(Integer redni_br_upisa){
        this.redni_br_upisa=redni_br_upisa;
    }

    public String GetNazivPredmeta() {
        return this.naziv_predmeta;
    }
    public Integer GetRedniBrUpisa(){
        return  this.redni_br_upisa;
    }

}
