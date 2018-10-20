package hei.devweb.entities;

public enum Condition {
    Aucun("id DESC"),
    Date("date"),
    Prix("prix"),
    NomAuteur("auteur"),
    NomAnnonce("titre"),
    Etat ("etat");


    String libelle;


    Condition(String libelle) {
        this.libelle= libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
