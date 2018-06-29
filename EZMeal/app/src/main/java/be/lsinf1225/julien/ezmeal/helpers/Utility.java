package be.lsinf1225.julien.ezmeal.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import be.lsinf1225.julien.ezmeal.model.Recette;
import be.lsinf1225.julien.ezmeal.sql.DatabaseHelper;

import static be.lsinf1225.julien.ezmeal.model.Recette.recetteComparatorMoyenne;

/**
 * Created by julien on 01-05-17.
 */

public class Utility {

    // converti bitmap en byte[]
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // converti byte[] en bitmap
    public static Bitmap getBytePhoto(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    //converti une date de milliseconde en String dans le format dateformat
    public static String getFormattedDate(long milliSeconds, SimpleDateFormat dateFormat)
    {
        DateFormat formatter = dateFormat;


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    //converti une date de string en millisecondes
    public static long getMillisDate(String date, SimpleDateFormat simpleDateFormat){
        long timeInMilliseconds = 0;
        try {
            Date mDate = simpleDateFormat.parse(date);
            timeInMilliseconds = mDate.getTime();

        } catch(ParseException e){
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }


    /**
     *
     * @param recetteList: liste de recettes
     * @return la liste de recette triée en fonction des notes des recettes
     */
    public static void sortRecipe(List<Recette> recetteList, DatabaseHelper databaseHelper){
        for(int i = 0; i<recetteList.size(); i++){
            setMoyenneRecette(recetteList.get(i), databaseHelper.allNotes(recetteList.get(i)));
        }
        Collections.sort(recetteList, recetteComparatorMoyenne);
    }

    public static void setMoyenneRecette(Recette recette, List<Integer> listNote){
        double moy = 0.0;
        for(int i = 0; i<listNote.size(); i++){
            moy+=listNote.get(i);
        }
        moy = moy / (double)listNote.size();
        recette.setMoyenne(moy);
    }




}
