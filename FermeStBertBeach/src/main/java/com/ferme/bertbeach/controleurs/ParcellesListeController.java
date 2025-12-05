package com.ferme.bertbeach.controleurs;

import com.ferme.bertbeach.infrastructure.sqlite.ParcelleSQLiteDAO;
import com.ferme.bertbeach.parcelle.GestionParcellesService;
import com.ferme.bertbeach.parcelle.Parcelle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

/**
 * Contrôleur de la page de gestion des parcelles.
 * Implémente les cas d'utilisation CU01, CU02 et CU03.
 */
public class ParcellesListeController {

    /* -----------------------------
       RÉFÉRENCES AUX ÉLÉMENTS FXML
       ----------------------------- */

    @FXML private TextField champRecherche;

    @FXML private ListView<Parcelle> listeParcelles;

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

    @FXML private TextArea zoneDetails;
    @FXML private TextArea zoneHistorique;

    /* -----------------------------
       DONNÉES & SERVICES
       ----------------------------- */
    private GestionParcellesService service;
    private final ObservableList<Parcelle> parcellesObservable = FXCollections.observableArrayList();
    private Parcelle parcelleSelectionnee;

    /* -----------------------------
       INITIALISATION
       ----------------------------- */

    @FXML
    public void initialize() {

        // Désactive le message de succès au lancement
        messageSucces.setVisible(false);

        // Remplit les menus déroulants
        chargerMenusDeroulants();

        // Initialisation du service connecté à SQLite
        service = new GestionParcellesService(new ParcelleSQLiteDAO());

        // Sélection dans la liste
        listeParcelles.setOnMouseClicked(this::onSelectionParcelle);
        listeParcelles.setItems(parcellesObservable);

        // Mode lecture seule désactivé par défaut
        appliquerLectureSeule(false);

        // Boutons désactivés au début
        btnEnregistrer.setDisable(true);
        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);

        chargerParcellesDepuisBd();

        // Affichage initial vierge des zones de consultation
        viderZonesInformations();
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
       CHARGEMENT DES DONNÉES
       ----------------------------- */

    private void chargerParcellesDepuisBd() {
        parcellesObservable.setAll(service.listerParcelles());
        listeParcelles.refresh();
    }

    private void viderChamps() {
        champNom.clear();
        champSuperficie.clear();
        champLocalisation.clear();
        champCulture.getSelectionModel().clearSelection();
        typeSol.getSelectionModel().clearSelection();
        messageSucces.setVisible(false);
        viderZonesInformations();
    }

    private void viderZonesInformations() {
        zoneDetails.setText("Sélectionnez une parcelle puis cliquez sur Consulter pour afficher ses informations détaillées.");
        zoneHistorique.setText("Historique des traitements non disponible pour l'instant.");
    }


    /* -----------------------------
       SÉLECTION D’UNE PARCELLE
       ----------------------------- */

    private void onSelectionParcelle(MouseEvent event) {

        parcelleSelectionnee = listeParcelles.getSelectionModel().getSelectedItem();
        if (parcelleSelectionnee == null) return;

        champNom.setText(parcelleSelectionnee.getNom());
        champSuperficie.setText(String.valueOf(parcelleSelectionnee.getSuperficie()));
        champLocalisation.setText(parcelleSelectionnee.getLocalisation());

        // ComboBox → on sélectionne la valeur
        champCulture.getSelectionModel().select(parcelleSelectionnee.getCulture());
        typeSol.getSelectionModel().select(parcelleSelectionnee.getTypeSol());

        btnModifier.setDisable(false);
        btnSupprimer.setDisable(false);
        btnEnregistrer.setDisable(true);

        messageSucces.setVisible(false);
    }


    /* -----------------------------
       MÉTHODES DES BOUTONS
       ----------------------------- */

    @FXML
    private void onAjouterParcelle() {
        listeParcelles.getSelectionModel().clearSelection();
        parcelleSelectionnee = null;
        viderChamps();
        appliquerLectureSeule(false);
        btnEnregistrer.setDisable(false);
        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);
    }

    @FXML
    private void onConsulterParcelle() {
        if (parcelleSelectionnee == null) {
            afficherAlerte(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner une parcelle à consulter.");
            return;
        }
        Optional<Parcelle> parcelle = service.consulterParcelle(parcelleSelectionnee.getId());
        if (parcelle.isEmpty()) {
            afficherAlerte(Alert.AlertType.ERROR, "Parcelle introuvable", "La parcelle sélectionnée n'existe plus en base de données.");
            chargerParcellesDepuisBd();
            return;
        }
        Parcelle p = parcelle.get();
        afficherDetailsDansVue(p);
    }

    @FXML
    private void onModifierParcelle() {
        if (parcelleSelectionnee == null) {
            afficherAlerte(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner une parcelle à modifier.");
            return;
        }
        appliquerLectureSeule(false);
        btnEnregistrer.setDisable(false);
        messageSucces.setVisible(false);
    }

    @FXML
    private void onSupprimerParcelle() {
        if (parcelleSelectionnee == null) {
            afficherAlerte(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner une parcelle à supprimer.");
            return;
        }
        String messageConfirmation = "Voulez-vous vraiment supprimer la parcelle \"" + parcelleSelectionnee.getNom() +
                "\" ?\nCette action est irréversible. Vous pouvez aussi choisir l'option d'archivage.";
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, messageConfirmation, ButtonType.OK, ButtonType.CANCEL);
        confirmation.setHeaderText("Suppression de parcelle");
        confirmation.showAndWait().ifPresent(reponse -> {
            if (reponse == ButtonType.OK) {
                try {
                    service.supprimerParcelle(parcelleSelectionnee.getId());
                    chargerParcellesDepuisBd();
                    viderChamps();
                    btnModifier.setDisable(true);
                    btnSupprimer.setDisable(true);
                    afficherMessageSucces("Parcelle supprimée avec succès.");
                } catch (RuntimeException e) {
                    afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Impossible de supprimer la parcelle : " + e.getMessage());
                }
            }
        });
    }

    @FXML
    private void onEnregistrerParcelle() {
        try {
            Parcelle parcelle = construireParcelleDepuisFormulaire();
            Parcelle persistee = service.ajouterOuModifierParcelle(parcelle);
            parcelleSelectionnee = persistee;
            afficherMessageSucces("Parcelle enregistrée avec succès.");
            chargerParcellesDepuisBd();
            // Réappliquer la sélection si modification
            if (persistee.getId() != null) {
                selectionnerParcelleDansListe(persistee.getId());
            }
            btnEnregistrer.setDisable(true);
            btnModifier.setDisable(false);
            btnSupprimer.setDisable(false);
            afficherDetailsDansVue(persistee);
        } catch (IllegalArgumentException e) {
            afficherAlerte(Alert.AlertType.WARNING, "Validation", e.getMessage());
        } catch (RuntimeException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue : " + e.getMessage());
        }
    }

    private Parcelle construireParcelleDepuisFormulaire() {
        String nom = champNom.getText();
        String superficieTexte = champSuperficie.getText();
        String localisation = champLocalisation.getText();
        String culture = champCulture.getSelectionModel().getSelectedItem();
        String sol = typeSol.getSelectionModel().getSelectedItem();

        double superficie;
        try {
            superficie = Double.parseDouble(superficieTexte.replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La superficie doit être un nombre valide.");
        }

        Parcelle parcelle;
        if (parcelleSelectionnee != null) {
            parcelle = new Parcelle(parcelleSelectionnee.getId(), nom, superficie, localisation, sol, culture);
        } else {
            parcelle = new Parcelle(nom, superficie, localisation, sol, culture);
        }
        return parcelle;
    }

    private void selectionnerParcelleDansListe(int id) {
        for (Parcelle p : parcellesObservable) {
            if (p.getId() != null && p.getId() == id) {
                listeParcelles.getSelectionModel().select(p);
                parcelleSelectionnee = p;
                break;
            }
        }
    }

    private void afficherDetailsDansVue(Parcelle p) {
        String details = "Nom : " + p.getNom() + "\n" +
                "Superficie : " + p.getSuperficie() + " ha\n" +
                "Localisation : " + p.getLocalisation() + "\n" +
                "Culture : " + (p.getCulture() == null ? "-" : p.getCulture()) + "\n" +
                "Type de sol : " + p.getTypeSol();
        zoneDetails.setText(details);

        // La gestion des traitements n'étant pas persistée, on affiche une note informative.
        zoneHistorique.setText("Historique des traitements :\n- Aucun traitement enregistré pour cette parcelle pour le moment.");
    }

    private void afficherAlerte(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.setHeaderText(titre);
        alert.showAndWait();
    }

    private void afficherMessageSucces(String message) {
        messageSucces.setText(message);
        messageSucces.setVisible(true);
    }
}
