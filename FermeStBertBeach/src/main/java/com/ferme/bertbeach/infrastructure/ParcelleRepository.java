package com.ferme.bertbeach.infrastructure;

import com.ferme.bertbeach.parcelle.Parcelle;

import java.util.List;
import java.util.Optional;

/**
 * Contrat de persistance pour les parcelles.
 */
public interface ParcelleRepository {
    Parcelle enregistrer(Parcelle parcelle);

    Optional<Parcelle> rechercherParId(int identifiant);

    List<Parcelle> listerToutes();

    boolean supprimer(int identifiant);
}
