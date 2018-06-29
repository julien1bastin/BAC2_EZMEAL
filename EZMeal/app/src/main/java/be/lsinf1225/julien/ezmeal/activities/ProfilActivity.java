package be.lsinf1225.julien.ezmeal.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ViewSwitcher;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;

import be.lsinf1225.julien.ezmeal.R;
import be.lsinf1225.julien.ezmeal.helpers.InputValidation;
import be.lsinf1225.julien.ezmeal.helpers.Utility;
import be.lsinf1225.julien.ezmeal.model.User;
import be.lsinf1225.julien.ezmeal.sql.DatabaseHelper;

import static be.lsinf1225.julien.ezmeal.activities.LoginActivity.emailUser;
import static be.lsinf1225.julien.ezmeal.helpers.Utility.*;

/**
 * Created by julien on 30-04-17.
 */

public class ProfilActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = ProfilActivity.this;

    private NestedScrollView nestedScrollViewProfil;

    private AppCompatImageView imageUser;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private AppCompatImageView modify_name;
    private AppCompatImageView modify_first_name;
    private AppCompatImageView modify_email;
    private AppCompatImageView modify_password;
    private AppCompatImageView modify_birthday;
    private AppCompatImageView modify_sex;
    private AppCompatImageView modify_lang;
    private AppCompatImageView confirm_name;
    private AppCompatImageView confirm_first_name;
    private AppCompatImageView confirm_email;
    private AppCompatImageView confirm_password;
    private AppCompatImageView confirm_birthday;
    private AppCompatImageView confirm_sex;
    private AppCompatImageView confirm_lang;

    private AppCompatTextView photoModify;
    private AppCompatTextView textViewUserLogin;
    private AppCompatTextView textViewUserName;
    private AppCompatTextView textViewUserFirstName;
    private AppCompatTextView textViewUserEmail;
    private AppCompatTextView textViewUserPassword;
    private AppCompatTextView textViewUserBirthday;
    private AppCompatTextView textViewUserSex;
    private AppCompatTextView textViewUserLang;

    private ViewSwitcher switcher_name;
    private ViewSwitcher switcher_first_name;
    private ViewSwitcher switcher_email;
    private ViewSwitcher switcher_password;
    private ViewSwitcher switcher_birthday;
    private ViewSwitcher switcher_sex;
    private ViewSwitcher switcher_lang;
    private ViewSwitcher switcher_modify_name;
    private ViewSwitcher switcher_modify_first_name;
    private ViewSwitcher switcher_modify_email;
    private ViewSwitcher switcher_modify_password;
    private ViewSwitcher switcher_modify_birthday;
    private ViewSwitcher switcher_modify_sex;
    private ViewSwitcher switcher_modify_lang;

    private TextInputEditText textInputEditTextUserName;
    private TextInputEditText textInputEditTextUserFirstName;
    private TextInputEditText textInputEditTextUserEmail;
    private TextInputEditText textInputEditTextUserPassword;
    private TextInputEditText textInputEditTextUserBirthday;
    private TextInputEditText textInputEditTextUserSex;
    private TextInputEditText textInputEditTextUserLang;

    private DatabaseHelper databaseHelper;

    private InputValidation inputValidation;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initObjects();
        initViews();
        initListeners();

    }

    private void initViews() {
        nestedScrollViewProfil = (NestedScrollView) findViewById(R.id.nestedScrollViewProfil);

        imageUser = (AppCompatImageView) findViewById(R.id.imageUser);
        if(user.getPhoto() != null){
            imageUser.setImageBitmap(getBytePhoto(user.getPhoto()));
        }

        modify_name = (AppCompatImageView) findViewById(R.id.modify_name);
        modify_first_name = (AppCompatImageView) findViewById(R.id.modify_first_name);
        modify_email = (AppCompatImageView) findViewById(R.id.modify_email);
        modify_password = (AppCompatImageView) findViewById(R.id.modify_password);
        modify_birthday = (AppCompatImageView) findViewById(R.id.modify_birthday);
        modify_sex = (AppCompatImageView) findViewById(R.id.modify_sex);
        modify_lang = (AppCompatImageView) findViewById(R.id.modify_lang);

        confirm_name = (AppCompatImageView) findViewById(R.id.confirm_name);
        confirm_first_name = (AppCompatImageView) findViewById(R.id.confirm_first_name);
        confirm_email = (AppCompatImageView) findViewById(R.id.confirm_email);
        confirm_password = (AppCompatImageView) findViewById(R.id.confirm_password);
        confirm_birthday = (AppCompatImageView) findViewById(R.id.confirm_birthday);
        confirm_sex = (AppCompatImageView) findViewById(R.id.confirm_sex);
        confirm_lang = (AppCompatImageView) findViewById(R.id.confirm_lang);

        photoModify = (AppCompatTextView) findViewById(R.id.photoModify);
        textViewUserLogin = (AppCompatTextView) findViewById(R.id.textViewUserLogin);
        textViewUserLogin.setText(user.getLogin());
        textViewUserName = (AppCompatTextView) findViewById(R.id.textViewUserName);
        textViewUserName.setText(user.getNom());
        textViewUserFirstName = (AppCompatTextView) findViewById(R.id.textViewUserFirstName);
        textViewUserFirstName.setText(user.getPrenom());
        textViewUserEmail = (AppCompatTextView) findViewById(R.id.textViewUserEmail);
        textViewUserEmail.setText(emailUser);
        textViewUserPassword = (AppCompatTextView) findViewById(R.id.textViewUserPassword);
        textViewUserPassword.setText(user.getMdp());
        textViewUserBirthday = (AppCompatTextView) findViewById(R.id.textViewUserBirthday);
        textViewUserBirthday.setText(Utility.getFormattedDate(user.getNaissance(), new SimpleDateFormat("dd/MM/yyyy")));
        textViewUserSex = (AppCompatTextView) findViewById(R.id.textViewUserSex);
        textViewUserSex.setText(user.getSexe());
        textViewUserLang = (AppCompatTextView) findViewById(R.id.textViewUserLang);
        textViewUserLang.setText(user.getLangue());

        switcher_name = (ViewSwitcher) findViewById(R.id.switcher_name);
        switcher_first_name = (ViewSwitcher) findViewById(R.id.switcher_first_name);
        switcher_email = (ViewSwitcher) findViewById(R.id.switcher_email);
        switcher_password = (ViewSwitcher) findViewById(R.id.switcher_password);
        switcher_birthday = (ViewSwitcher) findViewById(R.id.switcher_birthday);
        switcher_sex = (ViewSwitcher) findViewById(R.id.switcher_sex);
        switcher_lang = (ViewSwitcher) findViewById(R.id.switcher_lang);

        switcher_modify_name = (ViewSwitcher) findViewById(R.id.switcher_modify_name);
        switcher_modify_first_name = (ViewSwitcher) findViewById(R.id.switcher_modify_first_name);
        switcher_modify_email = (ViewSwitcher) findViewById(R.id.switcher_modify_email);
        switcher_modify_password = (ViewSwitcher) findViewById(R.id.switcher_modify_password);
        switcher_modify_birthday = (ViewSwitcher) findViewById(R.id.switcher_modify_birthday);
        switcher_modify_sex = (ViewSwitcher) findViewById(R.id.switcher_modify_sex);
        switcher_modify_lang = (ViewSwitcher) findViewById(R.id.switcher_modify_lang);

        textInputEditTextUserName = (TextInputEditText) findViewById(R.id.textInputEditTextUserName);
        textInputEditTextUserFirstName = (TextInputEditText) findViewById(R.id.textInputEditTextUserFirstName);
        textInputEditTextUserEmail = (TextInputEditText) findViewById(R.id.textInputEditTextUserEmail);
        textInputEditTextUserPassword = (TextInputEditText) findViewById(R.id.textInputEditTextUserPassword);
        textInputEditTextUserBirthday = (TextInputEditText) findViewById(R.id.textInputEditTextUserBirthday);
        textInputEditTextUserSex = (TextInputEditText) findViewById(R.id.textInputEditTextUserSex);
        textInputEditTextUserLang = (TextInputEditText) findViewById(R.id.textInputEditTextUserLang);
    }

    private void initListeners(){
        photoModify.setOnClickListener(this);
        modify_name.setOnClickListener(this);
        modify_first_name.setOnClickListener(this);
        modify_email.setOnClickListener(this);
        modify_password.setOnClickListener(this);
        modify_birthday.setOnClickListener(this);
        modify_sex.setOnClickListener(this);
        modify_lang.setOnClickListener(this);
        confirm_name.setOnClickListener(this);
        confirm_first_name.setOnClickListener(this);
        confirm_email.setOnClickListener(this);
        confirm_password.setOnClickListener(this);
        confirm_birthday.setOnClickListener(this);
        confirm_sex.setOnClickListener(this);
        confirm_lang.setOnClickListener(this);
    }

    private void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
        user = databaseHelper.getUser(emailUser);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.photoModify:
                //on lance l'appareil photo
                dispatchTakePictureIntent();
                break;
            case R.id.modify_name:
                //on fait apparaite le second element du switcher
                switcher_name.showNext();
                switcher_modify_name.showNext();
                break;
            case R.id.modify_first_name:
                switcher_first_name.showNext();
                switcher_modify_first_name.showNext();
                break;
            case R.id.modify_email:
                switcher_email.showNext();
                switcher_modify_email.showNext();
                break;
            case R.id.modify_password:
                switcher_password.showNext();
                switcher_modify_password.showNext();
                break;
            case R.id.modify_birthday:
                switcher_birthday.showNext();
                switcher_modify_birthday.showNext();
                break;
            case R.id.modify_sex:
                switcher_sex.showNext();
                switcher_modify_sex.showNext();
                break;
            case R.id.modify_lang:
                switcher_lang.showNext();
                switcher_modify_lang.showNext();
                break;
            case R.id.confirm_name:
                //on vérifie si le champs est bien rempli
                if(!inputValidation.isInputEditTextFilled(textInputEditTextUserName, getString(R.string.error_message_name), nestedScrollViewProfil)){
                    break;
                }
                user.setNom(textInputEditTextUserName.getText().toString().trim()); //on change le nom de l'utilisateur
                databaseHelper.updateUser(user, "nom"); //on fait le changement également dans la base de donnée
                textViewUserName.setText(textInputEditTextUserName.getText().toString().trim()); //on modifie le texte
                switcher_name.showPrevious(); //on revient à l'élément précédent du switcher
                switcher_modify_name.showPrevious(); //idem
                break;
            case R.id.confirm_first_name:
                if(!inputValidation.isInputEditTextFilled(textInputEditTextUserFirstName, getString(R.string.error_message_first_name), nestedScrollViewProfil)){
                    break;
                }
                user.setPrenom(textInputEditTextUserFirstName.getText().toString().trim());
                databaseHelper.updateUser(user, "prenom");
                textViewUserFirstName.setText(textInputEditTextUserFirstName.getText().toString().trim());
                switcher_first_name.showPrevious();
                switcher_modify_first_name.showPrevious();
                break;
            case R.id.confirm_email:
                if(!inputValidation.isInputEditTextFilled(textInputEditTextUserEmail, getString(R.string.error_message_email), nestedScrollViewProfil)){
                    break;
                }
                if(!inputValidation.isInputEditTextEmail(textInputEditTextUserEmail, getString(R.string.error_message_email), nestedScrollViewProfil)){
                    break;
                }
                user.setMail(textInputEditTextUserEmail.getText().toString().trim());
                databaseHelper.updateUser(user, "mail");
                textViewUserEmail.setText(textInputEditTextUserEmail.getText().toString().trim());
                switcher_email.showPrevious();
                switcher_modify_email.showPrevious();
                break;
            case R.id.confirm_password:
                if(!inputValidation.isInputEditTextFilled(textInputEditTextUserPassword, getString(R.string.error_message_password), nestedScrollViewProfil)){
                    break;
                }
                user.setMdp(textInputEditTextUserPassword.getText().toString().trim());
                databaseHelper.updateUser(user, "mdp");
                textViewUserPassword.setText(textInputEditTextUserPassword.getText().toString().trim());
                switcher_password.showPrevious();
                switcher_modify_password.showPrevious();
                break;
            case R.id.confirm_birthday:
                if(!inputValidation.isInputEditTextFilled(textInputEditTextUserBirthday, getString(R.string.error_message_valid_birthday), nestedScrollViewProfil)){
                    break;
                }
                if(!inputValidation.isInputEditTextBirthday(textInputEditTextUserBirthday, getString(R.string.error_message_valid_birthday), nestedScrollViewProfil)){
                    break;
                }
                String s = textInputEditTextUserBirthday.getText().toString().trim();
                long l = Utility.getMillisDate(s, new SimpleDateFormat("dd/MM/yyyy"));
                user.setNaissance(l);
                databaseHelper.updateUser(user, "naissance");
                textViewUserBirthday.setText(textInputEditTextUserBirthday.getText().toString().trim());
                switcher_birthday.showPrevious();
                switcher_modify_birthday.showPrevious();
                break;
            case R.id.confirm_sex:
                if(!inputValidation.isInputEditTextFilled(textInputEditTextUserSex, getString(R.string.error_message_valid_sex), nestedScrollViewProfil)){
                    break;
                }
                if(!inputValidation.isInputEditTextSex(textInputEditTextUserSex, getString(R.string.error_message_valid_sex), nestedScrollViewProfil)){
                    break;
                }
                user.setSexe(textInputEditTextUserSex.getText().toString().trim());
                databaseHelper.updateUser(user, "sexe");
                textViewUserSex.setText(textInputEditTextUserSex.getText().toString().trim());
                switcher_sex.showPrevious();
                switcher_modify_sex.showPrevious();
                break;
            case R.id.confirm_lang:
                if(!inputValidation.isInputEditTextFilled(textInputEditTextUserLang, getString(R.string.error_message_lang), nestedScrollViewProfil)){
                    break;
                }
                if(!inputValidation.isInputEditTextLanguage(textInputEditTextUserLang, getString(R.string.error_message_valid_langue), nestedScrollViewProfil)){
                    break;
                }
                user.setLangue(textInputEditTextUserLang.getText().toString().trim());
                databaseHelper.updateUser(user, "Langue");
                textViewUserLang.setText(textInputEditTextUserLang.getText().toString().trim());
                switcher_lang.showPrevious();
                switcher_modify_lang.showPrevious();
                break;
        }
    }

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
            //on modifie la photo de l'utilisateur
            user.setPhoto(getBytes(imageBitmap));
            //on fait le changement dans la base de donnée
            databaseHelper.updateUser(user, "photo");
            //on affiche la photo
            imageUser.setImageBitmap(imageBitmap);
        }
    }

}
