package be.lsinf1225.julien.ezmeal.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;

import be.lsinf1225.julien.ezmeal.R;
import be.lsinf1225.julien.ezmeal.helpers.Utility;

/**
 * Created by julien on 06-04-17.
 */

public class Recette implements Serializable{

    private String nom;
    private String description;
    private String instruction;
    private int tempsPreparation;
    private int tempsCuisson;
    private String type;
    private int difficulte;
    private long dateAjout;
    private byte[] photo = null;
    private String createur;
    private String sousType;
    private double moyenne;

    public Recette(){

    }

    public Recette(String nom, String description, String instruction, int tempsPreparation, int tempsCuisson, String type, int difficulte, String dateAjout, String photo, String createur, String sousType) {
        this.nom = nom;
        this.description = description;
        this.instruction = instruction;
        this.tempsPreparation = tempsPreparation;
        this.tempsCuisson = tempsCuisson;
        this.type = type;
        this.difficulte = difficulte;
        this.dateAjout = Utility.getMillisDate(dateAjout, new SimpleDateFormat("dd/MM/yyyy"));
        this.photo = null;
        this.createur = createur;
        this.sousType = sousType;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public double getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(double moyenne) {
        this.moyenne = moyenne;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public int getTempsPreparation() {
        return tempsPreparation;
    }

    public void setTempsPreparation(int tempsPreparation) {
        this.tempsPreparation = tempsPreparation;
    }

    public int getTempsCuisson() {
        return tempsCuisson;
    }

    public void setTempsCuisson(int tempsCuisson) {
        this.tempsCuisson = tempsCuisson;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(int difficulte) {
        this.difficulte = difficulte;
    }

    public long getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(long dateAjout) {
        this.dateAjout = dateAjout;
    }

    public String getCreateur() {
        return createur;
    }

    public void setCreateur(String createur) {
        this.createur = createur;
    }

    public String getSousType() {
        return sousType;
    }

    public void setSousType(String sousType) {
        this.sousType = sousType;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public static Comparator<Recette> recetteComparatorMoyenne = new Comparator<Recette>() {
        @Override
        public int compare(Recette o1, Recette o2) {
            return Double.compare(o1.getMoyenne(), o2.getMoyenne());
        }
    };
}
