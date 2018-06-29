package be.lsinf1225.julien.ezmeal.model;

/**
 * Created by julien on 06-04-17.
 */

public class Necessite {
    private String nom;
    private String libelleIng;
    private double quantite;
    private String unite;


    public String getIdRecette() {
        return nom;
    }

    public void setIdRecette(String nom) {
        this.nom = nom;
    }

    public String getLibelleIng() {
        return libelleIng;
    }

    public void setLibelleIng(String libelleIng) {
        this.libelleIng = libelleIng;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }
}
