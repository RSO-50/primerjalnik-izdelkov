package si.rsvo.izdelki.lib;

import java.util.List;

public class izdelkiMetadata {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getCena() {
        return cena;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public String getTrgovina() { return trgovina; }

    public void setTrgovina(String trgovina) { this.trgovina = trgovina; }

    public Integer getOcena() { return ocena; }

    public void setOcena(Integer ocena) { this.ocena = ocena; }

    private Integer id;
    private String naziv;
    private Integer cena;
    private String trgovina;
    private Integer ocena;
}
