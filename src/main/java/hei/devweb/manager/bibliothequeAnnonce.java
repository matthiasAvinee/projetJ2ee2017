package hei.devweb.manager;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import hei.devweb.daos.AnnonceDaoImpl;
import hei.devweb.daos.AuteurDaoImpl;
import hei.devweb.entities.Annonce;
import hei.devweb.entities.Auteur;
import hei.devweb.entities.Condition;
import hei.devweb.entities.Instrument;


import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class bibliothequeAnnonce {

    private static final String IMG_DIRECTORY="http://annoncesjazz.com/img/pasimg.png";

    private static class AnnonceLibraryHolder {
        private final static bibliothequeAnnonce instance = new bibliothequeAnnonce();
    }

    public static bibliothequeAnnonce getInstance() {
        return AnnonceLibraryHolder.instance;
    }

    private AnnonceDaoImpl annonceDao = new AnnonceDaoImpl();
    private AuteurDaoImpl auteurDao=new AuteurDaoImpl();




    public List<Annonce> listAnnonce(Instrument filter, Condition condition) {
        if (filter==null){
            return annonceDao.listAnnonce();
        }else
        return annonceDao.listAnnoncesByInstrument(filter,condition);

    }


    public Annonce getAnnonce(Integer id) {
        return annonceDao.getAnnonce(id);
    }

    public List<Annonce> listMesAnnonces(String auteur) {
        return annonceDao.listMesAnnonces(auteur);
    }


    public Annonce addAnnonce(Annonce annonce, Path path) {


   //   Path picturePath=null;
   //   if(picture.getSubmittedFileName()!=null) {
   //       picturePath = Paths.get(IMG_DIRECTORY, picture.getSubmittedFileName());
   //       try {
   //           Files.copy(picture.getInputStream(),picturePath);
   //       } catch (IOException e) {
   //           picturePath = Paths.get(IMG_DIRECTORY, "pasimg.png");
   //           e.printStackTrace();
   //       }

   //   } else {  picturePath = Paths.get(IMG_DIRECTORY, "pasimg.png");
   //     }
        return annonceDao.addAnnonce(annonce, null);

    }



    public List<Auteur> listAuteur() {
        return auteurDao.listAuteur();
    }

    public Auteur getAuteur(String pseudo) {
        return auteurDao.getAuteur(pseudo);
    }




    public Auteur addAuteur(Auteur auteur) throws MySQLIntegrityConstraintViolationException {

        return auteurDao.addAuteur(auteur);
    }
public Map<String,String>listUtilisateursAutorises(){
        return auteurDao.listUtilisateursAutorises();
    }

    public List<Auteur> listAuteurChoisi(String pseudo) {
        return auteurDao.listAuteurChoisi(pseudo);
    }

    public void supprimerAnnonce(Integer id, String auteur){annonceDao.supprimerAnnonce(id,auteur);}


    public Path getAnnoncePicture( Integer annonceid){
        Path picturePath=annonceDao.getPicturePath(annonceid);
        if (picturePath == null) {
            try {
                picturePath = Paths.get(this.getClass().getClassLoader().getResource("C:/Users/perso/AvineeMatthias/src/main/webapp/img/pasimg.png").toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }
        return picturePath;

    }

    public void updateAnnonce(Integer id,String titre,String description, Integer prix, Integer etat, String instrument){
            annonceDao.updateAnnonce(id,titre,description,prix,etat,instrument);
    }

    public Integer compterAnnonce (Instrument instrument, Condition condition){
        System.out.println("Dans methode : compterAnnonce");

        try{
            return annonceDao.listAnnoncesByInstrument(instrument,condition).size();
        }catch (NullPointerException e){
            return annonceDao.listAnnonce().size();
        }
    }
}


