package hei.devweb.daos;

import hei.devweb.daos.mock.impl.AuteurDao;
import hei.devweb.entities.Auteur;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuteurDaoImpl implements AuteurDao {

    @Override
    public List<Auteur> listAuteur() {
        List<Auteur> auteurs = new ArrayList<Auteur>();

        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM auteur ORDER BY pseudo")) {
            while (resultSet.next()) {
                auteurs.add(
                        new Auteur(resultSet.getString("pseudo"), resultSet.getString("mdp"), resultSet.getInt("numerotel"), resultSet.getString("email")));
            }
        } catch (SQLException e) {

        }

        return auteurs;
    }

    public String getMotdePasse(String pseudo) {
        String query = "SELECT mdp FROM auteur WHERE pseudo=?";
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, pseudo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("mdp");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Auteur getAuteur(String pseudo) {
        String query = "SELECT * FROM auteur WHERE pseudo=?";
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, pseudo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Auteur(resultSet.getString("pseudo"), resultSet.getString("mdp"), resultSet.getInt("numerotel"), resultSet.getString("email"));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Auteur addAuteur(Auteur auteur) {
        String query = "INSERT INTO auteur(pseudo, numerotel, email, mdp) VALUES(?, ?, ?, ?)";
        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, auteur.getPseudo());
            statement.setInt(2, auteur.getNumerotel());
            statement.setString(3, auteur.getEmail());
            statement.setString(4, auteur.getMdp());
            statement.executeUpdate();


            return auteur;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return auteur;
    }

    public Map<String, String> listUtilisateursAutorises() {
        Map<String, String> list = new HashMap<>();

        try (Connection connection = DataSourceProvider.getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT pseudo,mdp FROM auteur")) {

            while (resultSet.next()) {

                list.put(resultSet.getString("pseudo"), resultSet.getString("mdp"));
            }
        } catch (SQLException e) {

        }

        return list;
    }

    public List<Auteur> listAuteurChoisi(String pseudoCherche) {

        List<Auteur> list = new ArrayList<>();
        try (Connection connection = DataSourceProvider.getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM auteur WHERE pseudo=?")) {

                statement.setString(1, pseudoCherche);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        list.add(new Auteur(resultSet.getString("pseudo"), resultSet.getString("mdp"), resultSet.getInt("numerotel"), resultSet.getString("email")));
                        return list;
                    }
                    return list;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } return list;
    }


}