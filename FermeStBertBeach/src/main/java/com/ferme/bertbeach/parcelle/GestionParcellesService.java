package com.ferme.bertbeach.parcelle;

import com.ferme.bertbeach.infrastructure.ParcelleRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service applicatif centralisant les cas d'utilisation autour des parcelles.
 */
public class GestionParcellesService {

    private final ParcelleRepository repository;

    public GestionParcellesService(ParcelleRepository repository) {
        this.repository = repository;
    }

    /**
     * Cas d'utilisation CU01 : ajouter ou modifier une parcelle.
     * Lorsque l'identifiant est présent dans le référentiel, la parcelle est mise à jour,
     * sinon elle est ajoutée. Les validations minimales sont faites pour respecter les
     * préconditions décrites (données complètes et utilisateur authentifié en amont).
     */
    public Parcelle ajouterOuModifierParcelle(Parcelle parcelle) {
        if (parcelle.getNom() == null || parcelle.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom de la parcelle est obligatoire.");
        }
        if (parcelle.getSuperficie() <= 0) {
            throw new IllegalArgumentException("La superficie doit être positive.");
        }
        if (parcelle.getTypeSol() == null || parcelle.getTypeSol().isBlank()) {
            throw new IllegalArgumentException("Le type de sol doit être sélectionné.");
        }
        // Ici on suppose que l'authentification a déjà été réalisée par une couche supérieure.
        return repository.enregistrer(parcelle);
    }

    /**
     * Cas d'utilisation CU02 : supprimer une parcelle si elle existe.
     */
    public boolean supprimerParcelle(int identifiant) {
        return repository.supprimer(identifiant);
    }

    /**
     * Cas d'utilisation CU03 : consulter une parcelle.
     */
    public Optional<Parcelle> consulterParcelle(int identifiant) {
        return repository.rechercherParId(identifiant);
    }

    /**
     * Utilitaire pour la vue : liste toutes les parcelles afin d'afficher un tableau ou
     * une liste, notamment pour le scénario nominal du CU03.
     */
    public List<Parcelle> listerParcelles() {
        return repository.listerToutes();
    }
}
