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

    /* ---- Champs convertis en MENU DÉROULANT ---- */
    @FXML private ComboBox<String> champCulture;
    @FXML private ComboBox<String> typeSol;

    @FXML private CheckBox caseLectureSeule;

    @FXML private Button btnConsulter;
    @FXML private Button btnModifier;
    @FXML private Button btnSupprimer;
    @FXML private Button btnEnregistrer;

    @FXML private Label messageSucces;


    /* -----------------------------
       DONNÉES TEMPORAIRES (étape 2)
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
        SimpleStringProperty sol = new SimpleStringProperty();
    }


    /* -----------------------------
       INITIALISATION
       ----------------------------- */

    @FXML
    public void initialize() {

        // Désactive le message de succès au lancement
        messageSucces.setVisible(false);

        // Remplit les menus déroulants
        chargerMenusDeroulants();

        // Charge quelques données de test
        chargerParcellesDeTest();

        // Sélection dans la liste
        listeParcelles.setOnMouseClicked(this::onSelectionParcelle);

        // Mode lecture seule désactivé par défaut
        appliquerLectureSeule(false);

        // Boutons désactivés au début
        btnEnregistrer.setDisable(true);
        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);
    }

    /**
     * Remplit les menus déroulants
     */
    private void chargerMenusDeroulants() {

        champCulture.getItems().setAll(
                "Maïs", "Blé", "Soya", "Luzerne", "Avoine"
        );

        typeSol.getItems().setAll(
                "Argileux", "Sableux", "Limon", "Organique"
        );
    }


    /* -----------------------------
       MODE LECTURE SEULE
       ----------------------------- */

    @FXML
    private void onBasculerLectureSeule() {
        appliquerLectureSeule(caseLectureSeule.isSelected());
    }

    private void appliquerLectureSeule(boolean lectureSeule) {

        champNom.setDisable(lectureSeule);
        champSuperficie.setDisable(lectureSeule);
        champLocalisation.setDisable(lectureSeule);
        champCulture.setDisable(lectureSeule);
        typeSol.setDisable(lectureSeule);

        btnEnregistrer.setDisable(lectureSeule);
    }


    /* -----------------------------
       DONNÉES TEST
       ----------------------------- */

    private void chargerParcellesDeTest() {

        ParcelleTemp p1 = new ParcelleTemp();
        p1.nom.set("Parcelle Nord");
        p1.superficie.set("12.5");
        p1.localisation.set("Zone Nord");
        p1.culture.set("Maïs");
        p1.sol.set("Limon");

        ParcelleTemp p2 = new ParcelleTemp();
        p2.nom.set("Parcelle Est");
        p2.superficie.set("7.2");
        p2.localisation.set("Zone Est");
        p2.culture.set("Blé");
        p2.sol.set("Argileux");

        parcelles.add(p1);
        parcelles.add(p2);

        for (ParcelleTemp p : parcelles) {
            listeParcelles.getItems().add(p.nom.get());
        }
    }


    /* -----------------------------
       SÉLECTION D’UNE PARCELLE
       ----------------------------- */

    private void onSelectionParcelle(MouseEvent event) {

        int index = listeParcelles.getSelectionModel().getSelectedIndex();
        if (index < 0) return;

        ParcelleTemp p = parcelles.get(index);

        champNom.setText(p.nom.get());
        champSuperficie.setText(p.superficie.get());
        champLocalisation.setText(p.localisation.get());

        // ComboBox → on sélectionne la valeur
        champCulture.getSelectionModel().select(p.culture.get());
        typeSol.getSelectionModel().select(p.sol.get());

        btnModifier.setDisable(false);
        btnSupprimer.setDisable(false);
        btnEnregistrer.setDisable(true);

        messageSucces.setVisible(false);
    }


    /* -----------------------------
       MÉTHODES DES BOUTONS
       ----------------------------- */

    @FXML
    private void onAjouterParcelle() {}

    @FXML
    private void onConsulterParcelle() {}

    @FXML
    private void onModifierParcelle() {}

    @FXML
    private void onSupprimerParcelle() {}

    @FXML
    private void onEnregistrerParcelle() {}

}
