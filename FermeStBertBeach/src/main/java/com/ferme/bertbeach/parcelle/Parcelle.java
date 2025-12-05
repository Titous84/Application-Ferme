package com.ferme.bertbeach.parcelle;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Représente une parcelle agricole. Les noms de variables et les commentaires
 * restent en français afin de faciliter le lien avec les cas d'utilisation
 * décrits dans le document d'analyse.
 */
public class Parcelle {
    private final String identifiant;
    private String nom;
    private double superficieHectares;
    private String localisation;
    private String cultureActive;
    private String historiqueCulture;
    private LocalDate derniereMiseAJour;

    public Parcelle(String nom, double superficieHectares, String localisation,
                    String cultureActive, String historiqueCulture) {
        this(UUID.randomUUID().toString(), nom, superficieHectares, localisation, cultureActive, historiqueCulture,
                LocalDate.now());
    }

    public Parcelle(String identifiant, String nom, double superficieHectares, String localisation,
                    String cultureActive, String historiqueCulture, LocalDate derniereMiseAJour) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.superficieHectares = superficieHectares;
        this.localisation = localisation;
        this.cultureActive = cultureActive;
        this.historiqueCulture = historiqueCulture;
        this.derniereMiseAJour = derniereMiseAJour;
    }

    /**
     * Applique une modification en masse tout en tenant la date de mise à jour à jour.
     */
    public void mettreAJour(String nom, double superficieHectares, String localisation,
                            String cultureActive, String historiqueCulture) {
        this.nom = nom;
        this.superficieHectares = superficieHectares;
        this.localisation = localisation;
        this.cultureActive = cultureActive;
        this.historiqueCulture = historiqueCulture;
        this.derniereMiseAJour = LocalDate.now();
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public String getNom() {
        return nom;
    }

    public double getSuperficieHectares() {
        return superficieHectares;
    }

    public String getLocalisation() {
        return localisation;
    }

    public String getCultureActive() {
        return cultureActive;
    }

    public String getHistoriqueCulture() {
        return historiqueCulture;
    }

    public LocalDate getDerniereMiseAJour() {
        return derniereMiseAJour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parcelle parcelle = (Parcelle) o;
        return Objects.equals(identifiant, parcelle.identifiant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifiant);
    }

    @Override
    public String toString() {
        return "Parcelle{" +
                "identifiant='" + identifiant + '\'' +
                ", nom='" + nom + '\'' +
                ", superficieHectares=" + superficieHectares +
                ", localisation='" + localisation + '\'' +
                ", cultureActive='" + cultureActive + '\'' +
                ", historiqueCulture='" + historiqueCulture + '\'' +
                ", derniereMiseAJour=" + derniereMiseAJour +
                '}';
    }
}
