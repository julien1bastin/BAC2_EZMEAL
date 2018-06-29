package be.lsinf1225.julien.ezmeal.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;
import java.util.Iterator;

import be.lsinf1225.julien.ezmeal.R;
import be.lsinf1225.julien.ezmeal.helpers.InputValidation;
import be.lsinf1225.julien.ezmeal.helpers.Utility;
import be.lsinf1225.julien.ezmeal.model.Evaluation;
import be.lsinf1225.julien.ezmeal.model.Necessite;
import be.lsinf1225.julien.ezmeal.model.Recette;
import be.lsinf1225.julien.ezmeal.sql.DatabaseHelper;

import static be.lsinf1225.julien.ezmeal.helpers.Utility.getBytePhoto;
import static be.lsinf1225.julien.ezmeal.helpers.Utility.getBytes;

/**
 * Created by julien on 30-04-17.
 */

public class RecipeActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = RecipeActivity.this;

    DatabaseHelper databaseHelper;

    NestedScrollView nestedScrollView;

    //recette à afficher
    private Recette recette;

    //View
    private AppCompatTextView textViewRecipe;
    private AppCompatTextView textViewDescription;
    private AppCompatTextView textViewType;
    private AppCompatTextView textViewSubtype;
    private AppCompatTextView textViewLevel;
    private AppCompatTextView textViewPreparation;
    private AppCompatTextView textViewCuisson;
    private AppCompatTextView textViewTempsTotal;
    private AppCompatTextView textViewSteps;
    private AppCompatTextView textViewMoyenne;

    private AppCompatTextView textViewCom1;
    private AppCompatTextView textViewCom2;
    private AppCompatTextView textViewCom3;
    private AppCompatImageView imageCom1;
    private AppCompatImageView imageCom2;
    private AppCompatImageView imageCom3;

    private AppCompatImageView imageRecipe;
    private AppCompatTextView textViewIngredients;

    private TextInputEditText textInputEditTextComment;
    private TextInputEditText textInputEditTextStars;
    private TextInputLayout textInputLayoutComment;
    private TextInputLayout textInputLayoutStars;

    private List<Necessite> listNecessite;
    private Necessite runner;

    private List<Evaluation> lastThreeEval;

    private AppCompatButton buttonEvaluer;
    private AppCompatButton buttonPrendrePhoto;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private InputValidation inputValidation;

    Evaluation evaluation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        //récupération de la recette à afficher
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        recette = (Recette) bundle.getSerializable("Recette");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initObjects();
        initViews();
        showComment();
        initListeners();

    }

    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollViewRecipe);

        imageRecipe = (AppCompatImageView) findViewById(R.id.imageRecipe);
        if(recette.getPhoto() != null){
            imageRecipe.setImageBitmap(getBytePhoto(recette.getPhoto()));
        }

        textViewCom1 = (AppCompatTextView) findViewById(R.id.textViewCom1);
        textViewCom2 = (AppCompatTextView) findViewById(R.id.textViewCom2);
        textViewCom3 = (AppCompatTextView) findViewById(R.id.textViewCom3);
        imageCom1 = (AppCompatImageView) findViewById(R.id.imageCom1);
        imageCom2 = (AppCompatImageView) findViewById(R.id.imageCom2);
        imageCom3 = (AppCompatImageView) findViewById(R.id.imageCom3);

        textViewRecipe = (AppCompatTextView) findViewById(R.id.textViewRecipe);
        textViewRecipe.setText(recette.getNom());

        textViewDescription = (AppCompatTextView) findViewById(R.id.textViewDescription);
        textViewDescription.setText(recette.getDescription());

        textViewType = (AppCompatTextView) findViewById(R.id.textViewType);
        textViewType.setText(recette.getType());

        textViewSubtype = (AppCompatTextView) findViewById(R.id.textViewSubtype);
        textViewSubtype.setText(recette.getSousType());

        textViewLevel = (AppCompatTextView) findViewById(R.id.textViewLevel);
        int temp = recette.getDifficulte();
        if(temp == 1) {
            textViewLevel.setText("très facile");
        }
        else if(temp == 2) {
            textViewLevel.setText("facile");
        }
        else if(temp == 3) {
            textViewLevel.setText("moyen");
        }
        else if(temp == 4) {
            textViewLevel.setText("difficile");
        }
        else if(temp == 5) {
            textViewLevel.setText("très difficle");
        }

        textViewPreparation = (AppCompatTextView) findViewById(R.id.textViewPreparation);
        textViewPreparation.setText(recette.getTempsPreparation() + " min");

        textViewCuisson = (AppCompatTextView) findViewById(R.id.textViewCuisson);
        textViewCuisson.setText(recette.getTempsCuisson() + " min");

        textViewTempsTotal = (AppCompatTextView) findViewById(R.id.textViewTempsTotal);
        temp = recette.getTempsPreparation()+recette.getTempsCuisson();
        textViewTempsTotal.setText(temp + " min");

        textViewSteps = (AppCompatTextView) findViewById(R.id.textViewSteps);
        textViewSteps.setText(recette.getInstruction());

        textViewMoyenne = (AppCompatTextView) findViewById(R.id.textViewMoyenne);
        textViewMoyenne.setText(recette.getMoyenne() + " / 5");

        runner = listNecessite.get(0);
        textViewIngredients = (AppCompatTextView) findViewById(R.id.textViewIngredients);
        textViewIngredients.setText("");
        Iterator it = listNecessite.iterator();
        while(it.hasNext()) {
            runner = (Necessite)it.next();
            if((runner.getUnite()).equals("0")) {
                textViewIngredients.append("\n" + "- " + runner.getLibelleIng());
            }
            else {
                textViewIngredients.append("\n" + "- " + runner.getQuantite() + " " + runner.getUnite() + " de " + runner.getLibelleIng());
            }

        }
        textInputEditTextComment = (TextInputEditText) findViewById(R.id.textInputEditTextComment);
        textInputEditTextStars = (TextInputEditText) findViewById(R.id.textInputEditTextStars);

        textInputLayoutComment = (TextInputLayout) findViewById(R.id.textInputLayoutComment);
        textInputLayoutStars = (TextInputLayout) findViewById(R.id.textInputLayoutStars);

        buttonEvaluer = (AppCompatButton) findViewById(R.id.buttonEvaluer);
        buttonPrendrePhoto = (AppCompatButton) findViewById(R.id.buttonPrendrePhoto);

    }

    /**
     * Fonction qui se charge de l'affichage des trois dernières évaluations données à la recette
     */
    private void showComment() {
        try{
            Evaluation temp = lastThreeEval.get(0);
            textViewCom1.setText(temp.getLogin() + ": note (" + temp.getNote() + "/5)" + "\n" + temp.getComment());
            if(temp.getImage() != null){
                imageCom1.setImageBitmap(getBytePhoto(temp.getImage()));
            }
        } catch(Exception e) {
            //s'il n'y a pas d'évaluation on affiche qu'il n'y a pas de commentaire
            textViewCom1.setText("No comment");
        }

        try{
            Evaluation temp = lastThreeEval.get(1);
            textViewCom2.setText(temp.getLogin() + ": note (" + temp.getNote() + "/5)" + "\n" + temp.getComment());
            if(temp.getImage() != null){
                imageCom2.setImageBitmap(getBytePhoto(temp.getImage()));
            }
        } catch(Exception e) {
            textViewCom2.setText("No comment");
        }

        try{
            Evaluation temp = lastThreeEval.get(2);
            textViewCom3.setText(temp.getLogin() + ": note (" + temp.getNote() + "/5)" + "\n" + temp.getComment());
            if(temp.getImage() != null){
                imageCom3.setImageBitmap(getBytePhoto(temp.getImage()));
            }
        } catch(Exception e) {
            textViewCom3.setText("No comment");
        }
    }

    private void initListeners(){
        buttonPrendrePhoto.setOnClickListener(this);
        buttonEvaluer.setOnClickListener(this);
    }

    private void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
        listNecessite = databaseHelper.getNecessite(recette.getNom());
        lastThreeEval = databaseHelper.getLastThreeEvaluation(recette.getNom());
        inputValidation = new InputValidation(activity);
        evaluation = new Evaluation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonPrendrePhoto:
                dispatchTakePictureIntent();
                break;
            case R.id.buttonEvaluer:
                verifyAndAddEvaluation();
                //on rafraichit la liste des trois dernieres evaluations
                lastThreeEval = databaseHelper.getLastThreeEvaluation(recette.getNom());
                //on affiche les nouveaux 3 dernieres evaluations
                showComment();
                break;
        }
    }

    /**
     * Fonction qui vérifie la forme de l'évaluation à ajouter à la recette
     * et l'ajoute dans la base de donnée si le format est correcte
     */
    private void verifyAndAddEvaluation(){

        if(!inputValidation.isInputEditTextFilled(textInputEditTextStars, textInputLayoutStars, getString(R.string.error_message_stars)))
        {
            return;
        }
        try {
            Integer.parseInt(textInputEditTextStars.getText().toString().trim());
        } catch(NumberFormatException e) {
            textInputLayoutStars.setError(getString(R.string.error_message_stars));
            return;
        } catch(NullPointerException e) {
            textInputLayoutStars.setError(getString(R.string.error_message_stars));
            return;
        }
        if(inputValidation.isInputEditTextFilled(textInputEditTextComment)){
            evaluation.setComment(textInputEditTextComment.getText().toString().trim());
        }
        evaluation.setNote(Integer.parseInt(textInputEditTextStars.getText().toString().trim()));
        evaluation.setLogin(databaseHelper.getUser(LoginActivity.emailUser).getLogin());
        evaluation.setDate(System.currentTimeMillis());
        evaluation.setNom(recette.getNom());

        if(!databaseHelper.checkEvaluation(evaluation)){
            databaseHelper.addEvaluation(evaluation);
            Snackbar.make(nestedScrollView, getString(R.string.eval_ok), Snackbar.LENGTH_LONG).show();
        }
        else{
            Snackbar.make(nestedScrollView, getString(R.string.error_message_eval_deja_la), Snackbar.LENGTH_LONG).show();
        }
        textInputEditTextStars.setText(null);
        textInputEditTextComment.setText(null);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent menuIntent = new Intent(activity, MenuActivity.class);
                //on commence l'activité de menu
                startActivity(menuIntent);
                return true;
            case R.id.action_account:
                Intent profilIntent = new Intent(activity, ProfilActivity.class);
                startActivity(profilIntent);
                return true;

            case R.id.action_search:
                Intent searchIntent = new Intent(activity, SearchActivity.class);
                startActivity(searchIntent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * Fonction qui ouvre l'application de l'appareil qui est charger de prendre des photos
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * Fonction qui est appelée automatiquement quand l'application de prise de photo se ferme et récupère la photo
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //récupération de la photo
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //on modifie la photo de la recette s'il n'y en a pas encore
            byte[] photo = Utility.getBytes(imageBitmap);
            if(recette.getPhoto() == null) {
                recette.setPhoto(photo);
                //on fait le changement dans la base de donnée
                databaseHelper.updateRecettePhoto(recette);
                //on affiche la photo
                imageRecipe.setImageBitmap(imageBitmap);
            }
            //on ajout la photo à l'évaliation
            evaluation.setImage(photo);
        }
    }

}
