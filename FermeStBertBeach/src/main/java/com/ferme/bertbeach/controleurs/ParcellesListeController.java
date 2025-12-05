package com.ferme.bertbeach.controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Contrôleur de la page de gestion des parcelles.
 * Étape 2 : Mise en place de la structure, lecture seule,
 * sélection et liaison interface.
 */
public class ParcellesListeController {

    /* -----------------------------
       RÉFÉRENCES AUX ÉLÉMENTS FXML
       ----------------------------- */

    @FXML private TextField champRecherche;

    @FXML private ListView<String> listeParcelles;

    @FXML private TextField champNom;
    @FXML private TextField champSuperficie;
    @FXML private TextField champLocalisation;
    @FXML private TextField champCulture;
    @FXML private TextArea champHistorique;

    @FXML private CheckBox caseLectureSeule;

    @FXML private Button btnConsulter;
    @FXML private Button btnModifier;
    @FXML private Button btnSupprimer;
    @FXML private Button btnEnregistrer;

    @FXML private Label messageSucces;


    /* -----------------------------
       DONNÉES TEMPORAIRES (étape 2)
       En étape 4, on remplacera par
       une vraie classe Parcelle + BD.
       ----------------------------- */

    private final List<ParcelleTemp> parcelles = new ArrayList<>();


    /**
     * Classe temporaire pour tester l'interface.
     * (sera remplacée plus tard par ton modèle réel)
     */
    private static class ParcelleTemp {
        SimpleStringProperty nom = new SimpleStringProperty();
        SimpleStringProperty superficie = new SimpleStringProperty();
        SimpleStringProperty localisation = new SimpleStringProperty();
        SimpleStringProperty culture = new SimpleStringProperty();
        SimpleStringProperty historique = new SimpleStringProperty();
    }


    /* -----------------------------
       INITIALISATION
       ----------------------------- */

    @FXML
    public void initialize() {

        // Désactive le message de succès au lancement
        messageSucces.setVisible(false);

        // Charge quelques données de test pour vérifier la liste
        chargerParcellesDeTest();

        // Clic sur la liste → charger les informations dans le formulaire
        listeParcelles.setOnMouseClicked(this::onSelectionParcelle);

        // Mode lecture seule désactivé par défaut
        appliquerLectureSeule(false);

        // Désactivation initiale des boutons
        btnEnregistrer.setDisable(true);
        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);
    }


    /* -----------------------------
       GESTION DU MODE LECTURE SEULE
       ----------------------------- */

    @FXML
    private void onBasculerLectureSeule() {
        boolean lectureSeule = caseLectureSeule.isSelected();
        appliquerLectureSeule(lectureSeule);
    }

    /**
     * Active ou désactive tous les champs de formulaire.
     */
    private void appliquerLectureSeule(boolean lectureSeule) {

        champNom.setDisable(lectureSeule);
        champSuperficie.setDisable(lectureSeule);
        champLocalisation.setDisable(lectureSeule);
        champCulture.setDisable(lectureSeule);
        champHistorique.setDisable(lectureSeule);

        // Le bouton enregistrer devient inutilisable en mode lecture seule
        btnEnregistrer.setDisable(lectureSeule);
    }


    /* -----------------------------
       CHARGEMENT DES DONNÉES TEST
       ----------------------------- */

    private void chargerParcellesDeTest() {
        ParcelleTemp p1 = new ParcelleTemp();
        p1.nom.set("Parcelle Nord");
        p1.superficie.set("12.5");
        p1.localisation.set("Zone Nord");
        p1.culture.set("Maïs");
        p1.historique.set("Bonne production 2024.");

        ParcelleTemp p2 = new ParcelleTemp();
        p2.nom.set("Parcelle Est");
        p2.superficie.set("7.2");
        p2.localisation.set("Zone Est");
        p2.culture.set("Blé");
        p2.historique.set("Rotation récente.");

        parcelles.add(p1);
        parcelles.add(p2);

        // Affiche seulement les noms dans la liste
        for (ParcelleTemp p : parcelles) {
            listeParcelles.getItems().add(p.nom.get());
        }
    }


    /* -----------------------------
       SÉLECTION D'UNE PARCELLE
       ----------------------------- */

    private void onSelectionParcelle(MouseEvent event) {

        int index = listeParcelles.getSelectionModel().getSelectedIndex();

        // Rien sélectionné → on ignore
        if (index < 0) {
            return;
        }

        ParcelleTemp p = parcelles.get(index);

        // Charge les champs
        champNom.setText(p.nom.get());
        champSuperficie.setText(p.superficie.get());
        champLocalisation.setText(p.localisation.get());
        champCulture.setText(p.culture.get());
        champHistorique.setText(p.historique.get());

        // Active les boutons maintenant que quelque chose est sélectionné
        btnModifier.setDisable(false);
        btnSupprimer.setDisable(false);
        btnEnregistrer.setDisable(true); // par défaut

        messageSucces.setVisible(false);
    }


    /* -----------------------------
       MÉTHODES DES BOUTONS (VIDES)
       Étape 3 : on les implémente
       ----------------------------- */

    @FXML
    private void onAjouterParcelle() {
        // Étape 3 : à implémenter
    }

    @FXML
    private void onConsulterParcelle() {
        // Étape 3
    }

    @FXML
    private void onModifierParcelle() {
        // Étape 3
    }

    @FXML
    private void onSupprimerParcelle() {
        // Étape 3
    }

    @FXML
    private void onEnregistrerParcelle() {
        // Étape 3
    }

}
