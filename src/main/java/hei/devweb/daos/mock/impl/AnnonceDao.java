package hei.devweb.daos.mock.impl;

import hei.devweb.entities.Annonce;

import java.nio.file.Path;
import java.util.List;

public interface AnnonceDao {

    public List<Annonce> listAnnonce();

    public Annonce getAnnonce(Integer id);

    public Annonce addAnnonce(Annonce annonce, Path picturePath);

}
