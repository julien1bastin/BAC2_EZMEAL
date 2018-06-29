package be.lsinf1225.julien.ezmeal.activities;

/**
 * Created by julien on 29-03-17.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import java.text.SimpleDateFormat;

import be.lsinf1225.julien.ezmeal.R;
import be.lsinf1225.julien.ezmeal.helpers.InputValidation;
import be.lsinf1225.julien.ezmeal.helpers.Utility;
import be.lsinf1225.julien.ezmeal.model.User;
import be.lsinf1225.julien.ezmeal.sql.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;
    private TextInputLayout textInputLayoutLogin;
    private TextInputLayout textInputLayoutFirstName;
    private TextInputLayout textInputLayoutBirthday;
    private TextInputLayout textInputLayoutSex;
    private TextInputLayout textInputLayoutLang;

    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;
    private TextInputEditText textInputEditTextLogin;
    private TextInputEditText textInputEditTextFirstName;
    private TextInputEditText textInputEditTextBirthday;
    private TextInputEditText textInputEditTextSex;
    private TextInputEditText textInputEditTextLang;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.textInputLayoutConfirmPassword);
        textInputLayoutLogin = (TextInputLayout) findViewById(R.id.textInputLayoutLogin);
        textInputLayoutFirstName = (TextInputLayout) findViewById(R.id.textInputLayoutFirstName);
        textInputLayoutBirthday = (TextInputLayout) findViewById(R.id.textInputLayoutBirthday);
        textInputLayoutSex = (TextInputLayout) findViewById(R.id.textInputLayoutSex);
        textInputLayoutLang = (TextInputLayout) findViewById(R.id.textInputLayoutLang);


        textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputEditTextName);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword = (TextInputEditText) findViewById(R.id.textInputEditTextConfirmPassword);
        textInputEditTextLogin = (TextInputEditText) findViewById(R.id.textInputEditTextLogin);
        textInputEditTextFirstName = (TextInputEditText) findViewById(R.id.textInputEditTextFirstName);
        textInputEditTextBirthday = (TextInputEditText) findViewById(R.id.textInputEditTextBirthday);
        textInputEditTextSex = (TextInputEditText) findViewById(R.id.textInputEditTextSex);
        textInputEditTextLang = (TextInputEditText) findViewById(R.id.textInputEditTextLang);

        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);

        appCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.appCompatTextViewLoginLink);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();

    }


    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;

            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextLogin, textInputLayoutLogin, getString(R.string.error_message_login))){
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextFirstName, textInputLayoutFirstName, getString(R.string.error_message_first_name))){
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextLang, textInputLayoutLang, getString(R.string.error_message_lang))) {
            return;
        }
        if(!inputValidation.isInputEditTextLanguage(textInputEditTextLang, textInputLayoutLang, getString(R.string.error_message_valid_langue))) {
            return;
        }
        if(inputValidation.isInputEditTextFilled(textInputEditTextBirthday)){
            if(!inputValidation.isInputEditTextBirthday(textInputEditTextBirthday, textInputLayoutBirthday, getString(R.string.error_message_valid_birthday))) {
                return;
            }
        }
        if(inputValidation.isInputEditTextFilled(textInputEditTextSex)) {
            if(!inputValidation.isInputEditTextSex(textInputEditTextSex, textInputLayoutSex, getString(R.string.error_message_valid_sex))){
                return;
            }
        }


        if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {

            user.setNom(textInputEditTextName.getText().toString().trim());
            user.setMail(textInputEditTextEmail.getText().toString().trim());
            user.setMdp(textInputEditTextPassword.getText().toString().trim());
            user.setLogin(textInputEditTextLogin.getText().toString().trim());
            user.setPrenom(textInputEditTextFirstName.getText().toString().trim());
            user.setLangue(textInputEditTextLang.getText().toString().trim());

            if(inputValidation.isInputEditTextFilled(textInputEditTextBirthday)){
                String s = textInputEditTextBirthday.getText().toString().trim();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                long l = Utility.getMillisDate(s, sdf);
                user.setNaissance(l);
            }
            if(inputValidation.isInputEditTextFilled(textInputEditTextSex)){
                user.setSexe(textInputEditTextSex.getText().toString().trim());
            }

            databaseHelper.addUser(user);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();

            Intent accountsIntent = new Intent(activity, LoginActivity.class);
            emptyInputEditText();
            startActivity(accountsIntent);


        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }


    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
        textInputEditTextLogin.setText(null);
        textInputEditTextFirstName.setText(null);
        textInputEditTextBirthday.setText(null);
        textInputEditTextSex.setText(null);
        textInputEditTextLang.setText(null);
    }
}
