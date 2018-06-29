package be.lsinf1225.julien.ezmeal.model;

import android.graphics.drawable.Drawable;

import java.sql.Blob;
import java.util.Date;

/**
 * Created by julien on 06-04-17.
 */

public class Evaluation {

    private String login;
    private String nom;
    private int note;
    private String comment;
    private byte[] image = null;
    private long date;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
