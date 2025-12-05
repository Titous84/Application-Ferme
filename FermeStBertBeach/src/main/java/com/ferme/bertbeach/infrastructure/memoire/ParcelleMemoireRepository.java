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

    private final Map<String, Parcelle> stockage = new HashMap<>();

    @Override
    public Parcelle enregistrer(Parcelle parcelle) {
        stockage.put(parcelle.getIdentifiant(), parcelle);
        return parcelle;
    }

    @Override
    public Optional<Parcelle> rechercherParId(String identifiant) {
        return Optional.ofNullable(stockage.get(identifiant));
    }

    @Override
    public List<Parcelle> listerToutes() {
        return new ArrayList<>(stockage.values());
    }

    @Override
    public boolean supprimer(String identifiant) {
        return stockage.remove(identifiant) != null;
    }
}
