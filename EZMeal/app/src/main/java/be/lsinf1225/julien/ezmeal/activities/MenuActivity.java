package be.lsinf1225.julien.ezmeal.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import be.lsinf1225.julien.ezmeal.R;
import be.lsinf1225.julien.ezmeal.helpers.Utility;
import be.lsinf1225.julien.ezmeal.model.Recette;
import be.lsinf1225.julien.ezmeal.model.User;
import be.lsinf1225.julien.ezmeal.sql.DatabaseHelper;

import static be.lsinf1225.julien.ezmeal.helpers.Utility.getBytePhoto;

/**
 * Created by julien on 27-04-17.
 */

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{
    //on dit que l'activité courrante est une activité fille de AppCompatActivity
    private final AppCompatActivity activity = MenuActivity.this;

    //déclaration des Views
    private NestedScrollView nestedScrollViewMenu;

    private AppCompatImageView imageTopWeek;
    private AppCompatImageView imageTopWeek2;
    private AppCompatImageView imageTopWeek3;

    private AppCompatImageView imageTop;
    private AppCompatImageView imageTop2;
    private AppCompatImageView imageTop3;

    private AppCompatImageView imageLast;
    private AppCompatImageView imageLast2;
    private AppCompatImageView imageLast3;

    private AppCompatTextView allRecipe;

    private AppCompatTextView textTopWeek1;
    private AppCompatTextView textTopWeek2;
    private AppCompatTextView textTopWeek3;

    private AppCompatTextView textTop1;
    private AppCompatTextView textTop2;
    private AppCompatTextView textTop3;

    private AppCompatTextView textLast1;
    private AppCompatTextView textLast2;
    private AppCompatTextView textLast3;

    private ProgressBar progressBar;

    //déclaration des objets
    private DatabaseHelper databaseHelper;

    //Variable de classe

    private Recette last1;
    private Recette last2;
    private Recette last3;

    private Recette topWeek1;
    private Recette topWeek2;
    private Recette topWeek3;

    private Recette top1;
    private Recette top2;
    private Recette top3;

    private Bitmap bitTop1 = null;
    private Bitmap bitTop2 = null;
    private Bitmap bitTop3 = null;

    private Bitmap bitWeek1 = null;
    private Bitmap bitWeek2 = null;
    private Bitmap bitWeek3 = null;

    private Bitmap bitLast1 = null;
    private Bitmap bitLast2 = null;
    private Bitmap bitLast3 = null;

    /**
     * Fonction de lancement de l'activité
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initObjects();
        //on lance en tâche de fond, dans un nouveau thread les activités à exécuter en arrière plan pour alléger le thread principal
        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute();
    }


    /**
     * Initialisation de la liste des 3 derniers recettes qui ont été ajoutées
     */
    private void initLast(){
        List<Recette> listLastRecette = databaseHelper.getLastThreeRecipe();
        last1 = listLastRecette.get(0);
        last2 = listLastRecette.get(1);
        last3 = listLastRecette.get(2);
    }

    /**
     * initialisation de la liste des trois recettes avec la meilleure moyenne de la dernière semaine écoulée
     */
    private void initTopWeek(){
        long currentTime = System.currentTimeMillis();
        List<Recette> listTopWeekRecette = databaseHelper.getLastWeekRecette(currentTime);
        Utility.sortRecipe(listTopWeekRecette, databaseHelper);
        topWeek1 = listTopWeekRecette.get(0);
        topWeek2 = listTopWeekRecette.get(1);
        topWeek3 = listTopWeekRecette.get(2);
    }

    /**
     * Initialisation de la liste des 3 meilleures recettes d'après les notes
     */
    private void initTop(){
        List<Recette> allRecettes = databaseHelper.getAllRecettes();
        Utility.sortRecipe(allRecettes, databaseHelper);
        top1 = allRecettes.get(0);
        top2 = allRecettes.get(1);
        top3 = allRecettes.get(2);
    }

    /**
     * Affichage des images des recettes si ces images existes
     */
    private void setImage(){
        if(bitTop1 != null){
            imageTop.setImageBitmap(bitTop1);
        }
        if(bitTop2 != null){
            imageTop2.setImageBitmap(bitTop2);
        }
        if(bitTop3 != null){
            imageTop3.setImageBitmap(bitTop3);
        }
        if(bitWeek1 != null){
            imageTopWeek.setImageBitmap(bitWeek1);
        }
        if(bitWeek2 != null){
            imageTopWeek2.setImageBitmap(bitWeek2);
        }
        if(bitWeek3 != null){
            imageTopWeek3.setImageBitmap(bitWeek3);
        }
        if(bitLast1 != null){
            imageLast.setImageBitmap(bitLast1);
        }
        if(bitLast2 != null){
            imageLast2.setImageBitmap(bitLast2);
        }
        if(bitLast3 != null){
            imageLast3.setImageBitmap(bitLast3);
        }
    }

    /**
     * Initialisation des views
     */
    private void initViews(){
        nestedScrollViewMenu =(NestedScrollView) findViewById(R.id.nestedScrollViewMenu);

        imageTop = (AppCompatImageView) findViewById(R.id.imageTop);
        imageTop2 = (AppCompatImageView) findViewById(R.id.imageTop2);
        imageTop3 = (AppCompatImageView) findViewById(R.id.imageTop3);

        imageTopWeek = (AppCompatImageView) findViewById(R.id.imageTopWeek);
        imageTopWeek2 = (AppCompatImageView) findViewById(R.id.imageTopWeek2);
        imageTopWeek3 = (AppCompatImageView) findViewById(R.id.imageTopWeek3);

        imageLast = (AppCompatImageView) findViewById(R.id.imageLast);
        imageLast2 = (AppCompatImageView) findViewById(R.id.imageLast2);
        imageLast3 = (AppCompatImageView) findViewById(R.id.imageLast3);

        allRecipe = (AppCompatTextView) findViewById(R.id.allRecipe);

        textTopWeek1 = (AppCompatTextView) findViewById(R.id.textTopWeek);
        textTopWeek2 = (AppCompatTextView) findViewById(R.id.textTopWeek2);
        textTopWeek3 = (AppCompatTextView) findViewById(R.id.textTopWeek3);

        textTop1 = (AppCompatTextView) findViewById(R.id.textTop);
        textTop2 = (AppCompatTextView) findViewById(R.id.textTop2);
        textTop3 = (AppCompatTextView) findViewById(R.id.textTop3);

        textLast1 = (AppCompatTextView) findViewById(R.id.textLast1);
        textLast2 = (AppCompatTextView) findViewById(R.id.textLast2);
        textLast3 = (AppCompatTextView) findViewById(R.id.textLast3);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    /**
     * Affichage du nom des recettes
     */
    private void setText() {
        textTopWeek1.setText(topWeek1.getNom());
        textTopWeek1.setHorizontallyScrolling(true);
        textTopWeek2.setText(topWeek2.getNom());
        textTopWeek2.setHorizontallyScrolling(true);
        textTopWeek3.setText(topWeek3.getNom());
        textTopWeek3.setHorizontallyScrolling(true);

        textTop1.setText(top1.getNom());
        textTop1.setHorizontallyScrolling(true);
        textTop2.setText(top2.getNom());
        textTop2.setHorizontallyScrolling(true);
        textTop3.setText(top3.getNom());
        textTop3.setHorizontallyScrolling(true);

        textLast1.setText(last1.getNom());
        textLast1.setHorizontallyScrolling(true);
        textLast2.setText(last2.getNom());
        textLast2.setHorizontallyScrolling(true);
        textLast3.setText(last3.getNom());
        textLast3.setHorizontallyScrolling(true);
    }

    /**
     * Initialisation des listeners
     */
    private void initListeners(){
        imageTop.setOnClickListener(this);
        imageTop2.setOnClickListener(this);
        imageTop3.setOnClickListener(this);

        imageTopWeek.setOnClickListener(this);
        imageTopWeek2.setOnClickListener(this);
        imageTopWeek3.setOnClickListener(this);

        imageLast.setOnClickListener(this);
        imageLast2.setOnClickListener(this);
        imageLast3.setOnClickListener(this);

        allRecipe.setOnClickListener(this);
    }

    /**
     * initialisation des objets
     */
    private void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
    }

    /**
     * Tâches à réaliser en tâche de fond
     */
    private class BackgroundTask extends AsyncTask<Void, Integer, Void>{

        /**
         * Fonction appelée lors de l'appel publishProgress()
         * @param values
         */
        @Override
        protected void onProgressUpdate(Integer... values){
            super.onProgressUpdate(values);
            // Mise à jour de la ProgressBar
            progressBar.setProgress(values[0]);
        }

        /**
         * Tâche à effectuer en tâche de fond
         * @param params
         * @return
         */
        @Override
        protected Void doInBackground(Void... params) {
            initViews();
            initListeners();
            publishProgress(10);
            initLast();
            publishProgress(30);
            initTopWeek();
            publishProgress(60);
            initTop();
            publishProgress(90);
            return null;
        }

        /**
         * Tâche à exécuter quand les tâches de fond sont terminées
         * @param result
         */
        @Override
        protected void onPostExecute(Void result) {
            setText();
            ImageDecode im = new ImageDecode();
            im.execute();
        }
    }

    /**
     * On décode les images en tâche de fond sur un thread différent pour ne pas alourdir le thread principal en calcul de décodage
     */
    private class ImageDecode extends  AsyncTask<Void, Integer, Void>{

        @Override
        protected void onProgressUpdate(Integer... values){
            super.onProgressUpdate(values);
            // Mise à jour de la ProgressBar
            progressBar.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... params) {
            if(top1.getPhoto() != null){
                bitTop1 = getBytePhoto(top1.getPhoto());
            }
            if(top2.getPhoto() != null){
                bitTop2 = getBytePhoto(top2.getPhoto());
            }
            if(top3.getPhoto() != null){
                bitTop3 = getBytePhoto(top3.getPhoto());
            }
            if(topWeek1.getPhoto() != null){
                bitWeek1 = getBytePhoto(topWeek1.getPhoto());
            }
            if(topWeek2.getPhoto() != null){
                bitWeek2 = getBytePhoto(topWeek2.getPhoto());
            }
            if(topWeek3.getPhoto() != null){
                bitWeek3 = getBytePhoto(topWeek3.getPhoto());
            }
            if(last1.getPhoto() != null){
                bitLast1 =getBytePhoto(last1.getPhoto());
            }
            if(last2.getPhoto() != null){
                bitLast2 = getBytePhoto(last2.getPhoto());
            }
            if(last3.getPhoto() != null){
                bitLast3 = getBytePhoto(last3.getPhoto());
            }
            publishProgress(100);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setImage();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, RecipeActivity.class);
        switch (v.getId()) {
            case R.id.imageTop:
                intent.putExtra("Recette", top1);
                startActivity(intent);
                break;
            case R.id.imageTop2:
                intent.putExtra("Recette", top2);
                startActivity(intent);
                break;
            case R.id.imageTop3:
                intent.putExtra("Recette", top3);
                startActivity(intent);
                break;
            case R.id.imageTopWeek:
                intent.putExtra("Recette", topWeek1);
                startActivity(intent);
                break;
            case R.id.imageTopWeek2:
                intent.putExtra("Recette", topWeek2);
                startActivity(intent);
                break;
            case R.id.imageTopWeek3:
                intent.putExtra("Recette", topWeek3);
                startActivity(intent);
                break;
            case R.id.imageLast:
                intent.putExtra("Recette", last1);
                startActivity(intent);
                break;
            case R.id.imageLast2:
                intent.putExtra("Recette", last2);
                startActivity(intent);
                break;
            case R.id.imageLast3:
                intent.putExtra("Recette", last3);
                startActivity(intent);
                break;
            case R.id.allRecipe:
                Intent listRecipeIntent = new Intent(activity, ListRecipeActivity.class);
                ArrayList<Recette> listAllRecettes = (ArrayList<Recette>)databaseHelper.getAllRecettes();
                listRecipeIntent.putExtra("ListRecette", listAllRecettes);
                startActivity(listRecipeIntent);
                break;
        }
    }

    /**
     * Fonction qui est appelée automatiquement, associe la bar de menu à un menu, ici menu_menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }


    /**
     * Fonction qui est appelee automatiquement quand il y a une interaction avec la bar de menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: //quand on appuie sur la flèche pour revenir en arrière
                Intent loginIntent = new Intent(activity, LoginActivity.class);
                //on commence l'activité de login
                startActivity(loginIntent);
                return true;

            case R.id.action_account: //quand on appuie sur le bonhomme
                Intent profilIntent = new Intent(activity, ProfilActivity.class);
                startActivity(profilIntent); //on lance l'activité
                return true;

            case R.id.action_search: //quand on appuie sur la loupe
                Intent searchIntent = new Intent(activity, SearchActivity.class);
                startActivity(searchIntent); //on lance l'activité de recherche
                return true;
            default:
                //Si l'action n'est pas reconnue on appelle la superclasse
                return super.onOptionsItemSelected(item);

        }
    }

}
