package hei.devweb.daos;

import hei.devweb.daos.mock.impl.AnnonceDao;
import hei.devweb.entities.Annonce;
import hei.devweb.entities.Condition;
import hei.devweb.entities.Instrument;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnnonceDaoImpl implements AnnonceDao {


    public List<Annonce> listAnnonce() {
        List<Annonce> annonces = new ArrayList<Annonce>();

        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM annonce ORDER BY id DESC ")) {
            while (resultSet.next()) {
                annonces.add(
                        new Annonce(resultSet.getInt("id"),
                                resultSet.getString("titre"),
                                resultSet.getString("description"),
                                resultSet.getInt("prix"),
                                resultSet.getInt("etat"),
                                resultSet.getString("auteur"),
                                Instrument.valueOf(resultSet.getString("instrumentId"))));
            }
        } catch (SQLException e) {

        }
        return annonces;
    }


    @Override
    public Annonce getAnnonce(Integer id) {
        String query = "SELECT * FROM annonce WHERE id=?";
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Annonce(resultSet.getInt("id"),
                            resultSet.getString("titre"),
                            resultSet.getString("description"),
                            resultSet.getInt("prix"),
                            resultSet.getInt("etat"),
                            resultSet.getString("auteur"),
                            Instrument.valueOf(resultSet.getString("instrumentId")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Path getPicturePath(Integer annonceid) {
        String query = "SELECT picture FROM annonce WHERE id=?";
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, annonceid);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String picturePath = resultSet.getString("picture");
                    if (picturePath != null) {
                        return Paths.get(picturePath);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Annonce addAnnonce(Annonce annonce, Path picturePath) {
        String query = "INSERT INTO annonce(titre, description, prix, etat,auteur,instrumentId,picture) VALUES(?, ?, ?, ?,?,?,?)";
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, annonce.getTitre());
            statement.setString(2, annonce.getDescription());
            statement.setInt(3, annonce.getPrix());
            statement.setInt(4, annonce.getEtat());
            statement.setString(5, annonce.getAuteur());
            statement.setString(6, annonce.getInstrument().name());
            if (picturePath != null) {
                statement.setString(7, picturePath.toString());
            } else {
                statement.setString(7, "http://annoncesjazz.com/img/pasimg.png");
            }
            statement.executeUpdate();

            try (ResultSet ids = statement.getGeneratedKeys()) {
                if (ids.next()) {
                    int generatedId = ids.getInt(1);
                    annonce.setId(generatedId);
                    return annonce;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Annonce> listMesAnnonces(String auteur) {

        List<Annonce> list = new ArrayList<>();
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM annonce WHERE auteur=?")) {

                statement.setString(1, auteur);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        list.add(new Annonce(resultSet.getInt("id"),
                                resultSet.getString("titre"),
                                resultSet.getString("description"),
                                resultSet.getInt("prix"),
                                resultSet.getInt("etat"),
                                resultSet.getString("auteur"),
                                Instrument.valueOf(resultSet.getString("instrumentId"))));
                    }
                    return list;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void supprimerAnnonce(Integer id, String auteur) {
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "delete from annonce where id=? AND auteur=?")) {
                statement.setInt(1, id);
                statement.setString(2, auteur);
                statement.executeUpdate();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Annonce> listAnnoncesByInstrument(Instrument instrument, Condition condition) {

        List<Annonce> list = new ArrayList<>();
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM annonce WHERE instrumentId=? ORDER BY " + condition.getLibelle())) {

                statement.setString(1, instrument.name());


                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        list.add(new Annonce(resultSet.getInt("id"),
                                resultSet.getString("titre"),
                                resultSet.getString("description"),
                                resultSet.getInt("prix"),
                                resultSet.getInt("etat"),
                                resultSet.getString("auteur"),
                                Instrument.valueOf(resultSet.getString("instrumentId"))));
                    }
                    return list;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateAnnonce(Integer id, String titre, String description, Integer prix, Integer etat, String instrument) {

        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE annonce SET titre = ?, description = ?, prix=?, etat=?, instrumentId=? WHERE id=?")) {
                statement.setString(1, titre);
                statement.setString(2, description);
                statement.setInt(3, prix);
                statement.setInt(4, etat);
                statement.setString(5, instrument);
                statement.setInt(6, id);
                statement.executeUpdate();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    }








