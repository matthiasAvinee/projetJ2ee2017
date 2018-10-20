package hei.devweb.daos.mock.impl;

import hei.devweb.entities.Auteur;

import java.util.List;

public interface AuteurDao {

    public List<Auteur> listAuteur();

    public Auteur getAuteur(String pseudo);

    public Auteur addAuteur(Auteur auteur);
}
