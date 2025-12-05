package com.ferme.bertbeach.parcelle;

import java.util.Objects;

/**
 * Modèle représentant une parcelle agricole persistée en base SQLite.
 * Les champs reflètent les besoins décrits dans les cas d'utilisation CU01, CU02 et CU03.
 */
public class Parcelle {

    private Integer id;
    private String nom;
    private double superficie;
    private String localisation;
    private String typeSol;
    private String culture; // champ optionnel déjà prévu dans l'interface FXML

    public Parcelle(Integer id, String nom, double superficie, String localisation, String typeSol, String culture) {
        this.id = id;
        this.nom = nom;
        this.superficie = superficie;
        this.localisation = localisation;
        this.typeSol = typeSol;
        this.culture = culture;
    }

    public Parcelle(String nom, double superficie, String localisation, String typeSol, String culture) {
        this(null, nom, superficie, localisation, typeSol, culture);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getTypeSol() {
        return typeSol;
    }

    public void setTypeSol(String typeSol) {
        this.typeSol = typeSol;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parcelle parcelle = (Parcelle) o;
        return Objects.equals(id, parcelle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return nom;
    }
}
