package be.lsinf1225.julien.ezmeal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import be.lsinf1225.julien.ezmeal.R;
import be.lsinf1225.julien.ezmeal.helpers.InputValidation;
import be.lsinf1225.julien.ezmeal.model.Recette;
import be.lsinf1225.julien.ezmeal.sql.DatabaseHelper;

/**
 * Created by julien on 30-04-17.
 */

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = SearchActivity.this;

    private AppCompatButton appCompatButtonGoToBrowse;

    //déclaration des objets
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;


    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutKeyword;
    private TextInputLayout textInputLayoutType;
    private TextInputLayout textInputLayoutSubtype;

    private TextInputEditText textInputEditTextKeyword;
    private TextInputEditText textInputEditTextType;
    private TextInputEditText textInputEditTextSubtype;

    private AppCompatButton appCompatButtonSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initObjects();
        initViews();
        initListeners();

    }

    private void initViews() {
        appCompatButtonGoToBrowse = (AppCompatButton) findViewById(R.id.appCompatButtonGoToBrowse);

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollViewSearch);

        textInputLayoutKeyword = (TextInputLayout) findViewById(R.id.textInputLayoutKeyword);
        textInputLayoutType = (TextInputLayout) findViewById(R.id.textInputLayoutType);
        textInputLayoutSubtype = (TextInputLayout) findViewById(R.id.textInputLayoutSubtype);

        textInputEditTextKeyword = (TextInputEditText) findViewById(R.id.textInputEditTextKeyword);
        textInputEditTextType = (TextInputEditText) findViewById(R.id.textInputEditTextType);
        textInputEditTextSubtype = (TextInputEditText) findViewById(R.id.textInputEditTextSubtype);

        appCompatButtonSearch = (AppCompatButton) findViewById(R.id.appCompatButtonSearch);
    }

    private void initListeners(){
        appCompatButtonGoToBrowse.setOnClickListener(this);
        appCompatButtonSearch.setOnClickListener(this);
    }

    private void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonGoToBrowse:
                Intent browseIntent = new Intent(activity, BrowseActivity.class);

                startActivity(browseIntent);
                break;
            case R.id.appCompatButtonSearch:
                checkSearch(); // lance la recherche
                break;
        }
    }

    /**
     * Cette méthode vérifie si l'utilisateur a entré au moins un critère puis lance la recherche
     */
    private void checkSearch() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextKeyword)
                && !inputValidation.isInputEditTextFilled(textInputEditTextType)
                && !inputValidation.isInputEditTextFilled(textInputEditTextSubtype)) { // vérifie si au moins un critère a été choisi
            Snackbar.make(nestedScrollView, getString(R.string.error_nocriteria),  Snackbar.LENGTH_LONG).show();
            return;
        }
        ArrayList<Recette> recetteList = new ArrayList<Recette>();

        recetteList = (ArrayList<Recette>)databaseHelper.search(textInputEditTextKeyword.getText().toString().trim(),
                textInputEditTextType.getText().toString().trim(),
                textInputEditTextSubtype.getText().toString().trim()); // lance la recherche

        if (recetteList.size() != 0) { // un élément a été trouvé
            Intent listRecipeIntent = new Intent(activity, ListRecipeActivity.class);

            listRecipeIntent.putExtra("ListRecette", recetteList);

            startActivity(listRecipeIntent);
        } else { // si aucun élément a été trouvé
            Snackbar.make(nestedScrollView, getString(R.string.error_message_liste_vide),  Snackbar.LENGTH_LONG).show();
            return;
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
                Intent menuIntent = new Intent(activity, MenuActivity.class);
                //on commence l'activité de menu
                startActivity(menuIntent);
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
