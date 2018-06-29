package be.lsinf1225.julien.ezmeal.activities;

/**
 * Created by julien on 29-03-17.
 */

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import be.lsinf1225.julien.ezmeal.R;
import be.lsinf1225.julien.ezmeal.helpers.InputValidation;
import be.lsinf1225.julien.ezmeal.helpers.Utility;
import be.lsinf1225.julien.ezmeal.model.Recette;
import be.lsinf1225.julien.ezmeal.model.User;
import be.lsinf1225.julien.ezmeal.sql.DatabaseHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //on dit que l'activité courrante est une activité fille de AppCompatActivity
    private final AppCompatActivity activity = LoginActivity.this;

    //Variable globale
    public static String emailUser;

    //déclaration des Views
    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;

    //déclaration des objets
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    /**
     * Fonction de lancement de l'activité
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //on attribue un layout à l'activité
        setContentView(R.layout.activity_login);
        //on enlève la barre d'activité
        getSupportActionBar().hide();


        //on appelle les fonctions d'initialisation
        initViews();
        initListeners();
        initObjects();

    }

    /**
     * Cette méthode initialise les views et les fait correspondre aux views du layout
     */
    private void initViews() {

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);


    }

    /**
     * cette méthode initialise les listeners
     */
    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    /**
     * cette méthode initialise les objets à utiliser
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);

    }

    /**
     * Cette méthode est appelée quand on clique sur un listener
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                // On commence l'activité d'enregistrement
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    /**
     * Cette méthode vérifie si l'email est valide et si l'email et le mot de passe correspondent
     */
    private void verifyFromSQLite() {
        //vérification de la validité
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return;
        }
        //vérification de la correspondance
        if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {

            emailUser = textInputEditTextEmail.getText().toString().trim();
            //si tous les tests passent, on lance l'activité de menu principale et on vide les champs
            Intent menuIntent = new Intent(activity, MenuActivity.class);
            emptyInputEditText();
            startActivity(menuIntent);


        } else {
            // Si un des test ne passe pas, un message apparait à l'écran
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Cette méthode vide tous les champs
     */
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}