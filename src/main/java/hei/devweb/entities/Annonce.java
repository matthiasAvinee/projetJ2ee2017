package hei.devweb.entities;

public class Annonce {

    Integer id;
    String titre;
    String description;
    Integer prix;
    Integer etat;
    private Instrument instrument;
    String auteur=null;



    public Annonce(Integer id, String titre, String description, Integer prix, Integer etat, String auteur, Instrument instrument) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.prix = prix;
        this.etat = etat;
        this.instrument = instrument;
        this.auteur=auteur;

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }


    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }
}
