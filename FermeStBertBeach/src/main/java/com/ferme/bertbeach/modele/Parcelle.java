package com.ferme.bertbeach.modele;

/**
 * Représente une parcelle de la ferme.
 * Cette classe s'inspire des informations décrites dans les cas d'utilisation
 * (identification, superficie, type de sol, culture, etc.).
 */
public class Parcelle {

    // Identifiant interne (clé primaire future en BD)
    private int id;

    // Nom lisible de la parcelle (ex. "Champ Ouest")
    private String nom;

    // Superficie en hectares
    private double superficieHa;

    // Type de sol principal (ex. "Limoneux", "Argileux")
    private String typeSol;

    // Culture actuellement en place (ex. "Maïs", "Foin", peut être vide)
    private String cultureActuelle;

    // Commentaires ou notes diverses sur la parcelle
    private String commentaires;

    /**
     * Constructeur complet.
     */
    public Parcelle(int id,
                    String nom,
                    double superficieHa,
                    String typeSol,
                    String cultureActuelle,
                    String commentaires) {
        this.id = id;
        this.nom = nom;
        this.superficieHa = superficieHa;
        this.typeSol = typeSol;
        this.cultureActuelle = cultureActuelle;
        this.commentaires = commentaires;
    }

    /**
     * Constructeur sans id (cas de création avant insertion BD).
     */
    public Parcelle(String nom,
                    double superficieHa,
                    String typeSol,
                    String cultureActuelle,
                    String commentaires) {
        this(0, nom, superficieHa, typeSol, cultureActuelle, commentaires);
    }

    // --- Getters / Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getSuperficieHa() {
        return superficieHa;
    }

    public void setSuperficieHa(double superficieHa) {
        this.superficieHa = superficieHa;
    }

    public String getTypeSol() {
        return typeSol;
    }

    public void setTypeSol(String typeSol) {
        this.typeSol = typeSol;
    }

    public String getCultureActuelle() {
        return cultureActuelle;
    }

    public void setCultureActuelle(String cultureActuelle) {
        this.cultureActuelle = cultureActuelle;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    @Override
    public String toString() {
        return "Parcelle{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", superficieHa=" + superficieHa +
                ", typeSol='" + typeSol + '\'' +
                ", cultureActuelle='" + cultureActuelle + '\'' +
                '}';
    }
}
