package be.lsinf1225.julien.ezmeal.model;

/**
 * Created by julien on 06-04-17.
 */

public class Ingredients {
    private String libelle;
    private double prixParKg;


    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getPrixParKg() {
        return prixParKg;
    }

    public void setPrixParKg(double prixParKg) {
        this.prixParKg = prixParKg;
    }
}
