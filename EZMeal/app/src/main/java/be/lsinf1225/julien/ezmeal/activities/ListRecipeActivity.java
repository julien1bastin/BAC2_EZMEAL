package be.lsinf1225.julien.ezmeal.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import be.lsinf1225.julien.ezmeal.R;
import be.lsinf1225.julien.ezmeal.adapter.RecetteAdapter;
import be.lsinf1225.julien.ezmeal.helpers.Utility;
import be.lsinf1225.julien.ezmeal.model.Recette;
import be.lsinf1225.julien.ezmeal.sql.DatabaseHelper;

/**
 * Created by julien on 30-04-17.
 */

public class ListRecipeActivity extends ListActivity {
    //on dit que l'activité courrante est une activité fille de AppCompatActivity
    private final ListActivity activity = ListRecipeActivity.this;

    private NestedScrollView nestedScrollView;

    private List<Recette> recetteList;

    DatabaseHelper databaseHelper;

    RecetteAdapter adapter;


    /**
     * Fonction de lancement de l'activité
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //on récupère la liste de recette à afficher
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        recetteList = (ArrayList<Recette>) bundle.getSerializable("ListRecette");

        //on appelle les fonctions d'initialisation
        initObjects();
        initViews();
    }


    /**
     * Initialisation des Views
     * On fait correspondre chaque objet View de la classe à une vue dans le layout
     */
    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollViewListRecipe);
    }

    /**
     * On initialise les objets nécessaires à la classe
     */
    private void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
        if(recetteList.size() == 0){
            //si la liste de recette est vide on renvoie un message d'erreur et on revient à l'activité précédente
            Snackbar.make(nestedScrollView, getString(R.string.error_message_liste_vide), Snackbar.LENGTH_LONG).show();
            finish();
        }
        //on trie la liste des recettes en fonction des moyennes
        Utility.sortRecipe(recetteList, databaseHelper);
        Recette[] listItems = new Recette[recetteList.size()];
        for(int i = 0; i < recetteList.size(); i++){
            Recette recette = recetteList.get(i);
            listItems[i] = recette;
        }
        //on appelle l'adapter qui se charge de display une recette
        adapter = new RecetteAdapter(this, listItems);
        setListAdapter(adapter);
    }

    /**
     * Fonction appelée automatiquement quand on appuie sur une recette
     * @param l
     * @param v
     * @param position
     * @param id
     */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //on lance l'activité de recette en lui passant la recette en information
        Recette selectedValue = (Recette) getListAdapter().getItem(position);
        Intent intent = new Intent(activity, RecipeActivity.class);
        intent.putExtra("Recette", selectedValue);
        startActivity(intent);
    }
}
