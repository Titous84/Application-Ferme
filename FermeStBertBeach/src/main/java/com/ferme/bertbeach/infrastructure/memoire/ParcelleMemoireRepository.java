package com.ferme.bertbeach.infrastructure.memoire;

import com.ferme.bertbeach.parcelle.Parcelle;
import com.ferme.bertbeach.infrastructure.ParcelleRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implémentation simple en mémoire du dépôt de parcelles.
 *
 * Cette classe illustre le patron Repository et peut être remplacée par une
 * version connectée à une base de données sans impacter le reste du code.
 */
public class ParcelleMemoireRepository implements ParcelleRepository {

    private final Map<Integer, Parcelle> stockage = new HashMap<>();

    @Override
    public Parcelle enregistrer(Parcelle parcelle) {
        // Génère un identifiant simple basé sur la taille actuelle du stockage
        if (parcelle.getId() == null || parcelle.getId() == 0) {
            parcelle.setId(stockage.size() + 1);
        }
        stockage.put(parcelle.getId(), parcelle);
        return parcelle;
    }

    @Override
    public Optional<Parcelle> rechercherParId(int identifiant) {
        return Optional.ofNullable(stockage.get(identifiant));
    }

    @Override
    public List<Parcelle> listerToutes() {
        return new ArrayList<>(stockage.values());
    }

    @Override
    public boolean supprimer(int identifiant) {
        return stockage.remove(identifiant) != null;
    }
}
