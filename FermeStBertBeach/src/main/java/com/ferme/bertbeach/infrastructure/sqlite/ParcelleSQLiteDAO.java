package com.ferme.bertbeach.infrastructure.sqlite;

import com.ferme.bertbeach.infrastructure.ParcelleRepository;
import com.ferme.bertbeach.parcelle.Parcelle;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO SQLite responsable de la persistance des parcelles.
 */
public class ParcelleSQLiteDAO implements ParcelleRepository {

    private static final String NOM_FICHIER = "ferme.db";
    private final String urlConnexion;

    public ParcelleSQLiteDAO() {
        Path chemin = Paths.get(NOM_FICHIER).toAbsolutePath();
        urlConnexion = "jdbc:sqlite:" + chemin;
        initialiserSchema();
    }

    private void initialiserSchema() {
        String sqlCreation = "CREATE TABLE IF NOT EXISTS parcelles (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nom TEXT NOT NULL," +
                "superficie REAL NOT NULL," +
                "localisation TEXT," +
                "type_sol TEXT NOT NULL," +
                "culture TEXT" +
                ")";
        try (Connection conn = DriverManager.getConnection(urlConnexion);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlCreation);
        } catch (SQLException e) {
            throw new RuntimeException("Impossible d'initialiser la base SQLite", e);
        }
    }

    @Override
    public Parcelle enregistrer(Parcelle parcelle) {
        if (parcelle.getId() == null || parcelle.getId() == 0) {
            return inserer(parcelle);
        }
        return mettreAJour(parcelle);
    }

    private Parcelle inserer(Parcelle parcelle) {
        String sql = "INSERT INTO parcelles (nom, superficie, localisation, type_sol, culture) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(urlConnexion);
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            remplirChamps(parcelle, ps);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    parcelle.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion de la parcelle", e);
        }
        return parcelle;
    }

    private Parcelle mettreAJour(Parcelle parcelle) {
        String sql = "UPDATE parcelles SET nom = ?, superficie = ?, localisation = ?, type_sol = ?, culture = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(urlConnexion);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            remplirChamps(parcelle, ps);
            ps.setInt(6, parcelle.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la parcelle", e);
        }
        return parcelle;
    }

    @Override
    public Optional<Parcelle> rechercherParId(int identifiant) {
        String sql = "SELECT * FROM parcelles WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(urlConnexion);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, identifiant);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(hydraterParcelle(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche de la parcelle", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Parcelle> listerToutes() {
        List<Parcelle> resultat = new ArrayList<>();
        String sql = "SELECT * FROM parcelles ORDER BY nom";
        try (Connection conn = DriverManager.getConnection(urlConnexion);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                resultat.add(hydraterParcelle(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du chargement des parcelles", e);
        }
        return resultat;
    }

    @Override
    public boolean supprimer(int identifiant) {
        String sql = "DELETE FROM parcelles WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(urlConnexion);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, identifiant);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de la parcelle", e);
        }
    }

    private static void remplirChamps(Parcelle parcelle, PreparedStatement ps) throws SQLException {
        ps.setString(1, parcelle.getNom());
        ps.setDouble(2, parcelle.getSuperficie());
        ps.setString(3, parcelle.getLocalisation());
        ps.setString(4, parcelle.getTypeSol());
        ps.setString(5, parcelle.getCulture());
    }

    private Parcelle hydraterParcelle(ResultSet rs) throws SQLException {
        return new Parcelle(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getDouble("superficie"),
                rs.getString("localisation"),
                rs.getString("type_sol"),
                rs.getString("culture")
        );
    }
}
