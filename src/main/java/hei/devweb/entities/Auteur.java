package hei.devweb.entities;

public class Auteur {

    String pseudo;
    String mdp;
    Integer numerotel;
    String email;

    public Auteur(String pseudo, String mdp, Integer numerotel, String email) {
        this.pseudo = pseudo;
        this.mdp = mdp;
        this.numerotel = numerotel;
        this.email = email;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Integer getNumerotel() {
        return numerotel;
    }

    public void setNumerotel(Integer numerotel) {
        this.numerotel = numerotel;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
