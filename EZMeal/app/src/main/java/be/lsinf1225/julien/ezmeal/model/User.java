package be.lsinf1225.julien.ezmeal.model;

import android.graphics.Picture;
import android.graphics.drawable.Drawable;

import java.sql.Blob;


/**
 * Created by julien on 29-03-17.
 */

public class User {
    private String login;
    private String nom;
    private String prenom;
    private String mail;
    private String mdp;
    private long naissance;
    private String sexe;
    private byte[] photo;
    private String langue;


   public byte[] getPhoto() {
        return this.photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getLogin(){
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNom(){
        return this.nom;
    }

    public void setNom(String nom){
        this.nom=nom;
    }

    public String getPrenom(){
        return this.prenom;
    }

    public void setPrenom(String prenom){
        this.prenom=prenom;
    }

    public String getMail(){
        return this.mail;
    }

    public void setMail(String mail){
        this.mail=mail;
    }

    public String getMdp(){
        return this.mdp;
    }

    public void setMdp(String mdp){
        this.mdp=mdp;
    }

    public long getNaissance(){
        return this.naissance;
    }

    public void setNaissance(long naissance){
        this.naissance=naissance;
    }

    public String getSexe(){
        return this.sexe;
    }

    public void setSexe(String sexe){
        this.sexe=sexe;
    }

    public String getLangue(){
        return this.langue;
    }

    public void setLangue(String langue){
        this.langue=langue;
    }

}
