package be.lsinf1225.julien.ezmeal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.view.View;
import android.support.design.widget.Snackbar;

import java.util.ArrayList;

import be.lsinf1225.julien.ezmeal.R;
import be.lsinf1225.julien.ezmeal.model.Recette;
import be.lsinf1225.julien.ezmeal.sql.DatabaseHelper;

/**
 * Created by merlin on 10/05/17.
 */

public class BrowseActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = BrowseActivity.this;

    private RadioGroup radioGroup;

    private RadioButton radioButton;

    private AppCompatButton appCompatButtonBrowse;

    private NestedScrollView nestedScrollView;

    //déclaration des objets
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initObjects();
        initViews();
        initListeners();

    }

    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollViewBrowser);
        appCompatButtonBrowse = (AppCompatButton) findViewById(R.id.appCompatButtonBrowse);
    }

    private void initListeners(){
        radioGroup = (RadioGroup) findViewById(R.id.radio_type);

        appCompatButtonBrowse.setOnClickListener(this);
    }

    private void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
    }

    public void onRadioButtonClicked(View v) {
        boolean checked = ((RadioButton) v).isChecked();

        // Reagrde quek type a été choisi
        switch(v.getId()) {
            case R.id.radio_starter:
                if (checked)
                    break;
            case R.id.radio_salad:
                if (checked)
                    break;
            case R.id.radio_dessert:
                if (checked)
                    break;
            case R.id.radio_poultry:
                if (checked)
                    break;
            case R.id.radio_meat:
                if (checked)
                    break;
            case R.id.radio_game:
                if (checked)
                    break;
            case R.id.radio_fish:
                if (checked)
                    break;
            case R.id.radio_pasta:
                if (checked)
                    break;
            case R.id.radio_cocktail:
                if (checked)
                    break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonBrowse:
                int selectedId = radioGroup.getCheckedRadioButtonId();

                radioButton = (RadioButton) findViewById(selectedId);

                int idx = radioGroup.indexOfChild(radioButton);

                ArrayList<Recette> recetteList = new ArrayList<Recette>();

                switch (idx) { // regarde quel type a été choisi
                    case -1: // sy aucun type n'a été choisi
                        Snackbar.make(nestedScrollView, getString(R.string.error_notype),  Snackbar.LENGTH_LONG).show();
                        break;
                    case 0:
                        recetteList = (ArrayList<Recette>)databaseHelper.searchType("entrées");
                        break;
                    case 1:
                        recetteList = (ArrayList<Recette>)databaseHelper.searchType("salades");
                        break;
                    case 2:
                        recetteList = (ArrayList<Recette>)databaseHelper.searchType("desserts");
                        break;
                    case 3:
                        recetteList = (ArrayList<Recette>)databaseHelper.searchType("poulets et volailles");
                        break;
                    case 4:
                        recetteList = (ArrayList<Recette>)databaseHelper.searchType("viandes");
                        break;
                    case 5:
                        recetteList = (ArrayList<Recette>)databaseHelper.searchType("gibiers");
                        break;
                    case 6:
                        recetteList = (ArrayList<Recette>)databaseHelper.searchType("poissons et fruits de mer");
                        break;
                    case 7:
                        recetteList = (ArrayList<Recette>)databaseHelper.searchType("pâtes");
                        break;
                    case 8:
                        recetteList = (ArrayList<Recette>)databaseHelper.searchType("cocktails");
                        break;
                }
                if (idx > -1 && idx < 9 && recetteList.size() != 0) { // si un type a été choisi
                            Intent listRecipeIntent = new Intent(activity, ListRecipeActivity.class);

                            listRecipeIntent.putExtra("ListRecette", recetteList);

                            startActivity(listRecipeIntent);
                }
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
                Intent searchIntent = new Intent(activity, SearchActivity.class);
                startActivity(searchIntent); //on lance l'activité
                return true;

            case R.id.action_account: //quand on appuie sur le bonhomme
                Intent profilIntent = new Intent(activity, ProfilActivity.class);
                startActivity(profilIntent); //on lance l'activité
                return true;

            case R.id.action_search: //quand on appuie sur la loupe
                Intent searchIntent1 = new Intent(activity, SearchActivity.class);
                startActivity(searchIntent1); //on lance l'activité de recherche
                return true;
            default:
                //Si l'action n'est pas reconnue on appelle la superclasse
                return super.onOptionsItemSelected(item);

        }
    }
}
