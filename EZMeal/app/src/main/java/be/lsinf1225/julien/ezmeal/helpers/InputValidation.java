package be.lsinf1225.julien.ezmeal.helpers;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.util.regex.Pattern;


/**
 * Created by julien on 29-03-17.
 */

public class InputValidation {
    private Context context;

    /**
     * Constructeur
     *
     * @param context
     */
    public InputValidation(Context context){
        this.context = context;
    }

    /**
     * méthode qui regarde si le champ a du texte rempli
     *
     * @param textInputEditText
     * @param textInputLayout
     * @param message
     * @return
     */
    public boolean isInputEditTextFilled(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message) {
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty()) {
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    /**
     * méthode qui regarde si le champ a du texte rempli
     * @param textInputEditText
     * @param message
     * @param nestedScrollView
     * @return
     */
    public boolean isInputEditTextFilled(TextInputEditText textInputEditText, String message, NestedScrollView nestedScrollView) {
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty()) {
            Snackbar.make(nestedScrollView, message, Snackbar.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    /**
     * méthode qui regarde si le champ a du texte rempli
     * @param textInputEditText
     * @return
     */
    public boolean isInputEditTextFilled(TextInputEditText textInputEditText) {
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty()){
            return false;
        }
        return true;
    }

    /**
     * methode qui regarde si InputEditText est un sexe valide (M ou F)
     *
     * @param textInputEditText
     * @param textInputLayout
     * @param message
     * @return
     */
    public boolean isInputEditTextSex(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message) {
        String value = textInputEditText.getText().toString().trim();
        if(!(value.equalsIgnoreCase("M") || value.equalsIgnoreCase("F"))) {
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return false;
        }
        else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * methode qui regarde si InputEditText est un sexe valide (M ou F)
     *
     * @param textInputEditText
     * @param message
     * @param nestedScrollView
     * @return
     */
    public boolean isInputEditTextSex(TextInputEditText textInputEditText, String message, NestedScrollView nestedScrollView) {
        String value = textInputEditText.getText().toString().trim();
        if(!(value.equalsIgnoreCase("M") || value.equalsIgnoreCase("F"))) {
            Snackbar.make(nestedScrollView, message, Snackbar.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * methode qui regarde si InputEditText a une langue valide (FR ou EN ou ES)
     *
     * @param textInputEditText
     * @param textInputLayout
     * @param message
     * @return
     */
    public boolean isInputEditTextLanguage(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message) {
        String value = textInputEditText.getText().toString().trim();
        if(!(value.equalsIgnoreCase("FR") || value.equalsIgnoreCase("EN") || value.equalsIgnoreCase("ES"))) {
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return false;
        }
        else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }
    /**
     * methode qui regarde si InputEditText a une langue valide (FR ou EN ou ES)
     *
     * @param textInputEditText
     * @param message
     * @param nestedScrollView
     * @return
     */
    public boolean isInputEditTextLanguage(TextInputEditText textInputEditText, String message, NestedScrollView nestedScrollView) {
        String value = textInputEditText.getText().toString().trim();
        if(!(value.equalsIgnoreCase("FR") || value.equalsIgnoreCase("EN") || value.equalsIgnoreCase("ES"))) {
            Snackbar.make(nestedScrollView, message, Snackbar.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * méthode qui regarde si textInputEditText est bien une date de naissance valide
     *
     * @param textInputEditText
     * @param textInputLayout
     * @param message
     * @return
     */
    public boolean isInputEditTextBirthday(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message) {
        String value = textInputEditText.getText().toString().trim();
        if(!Pattern.matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)", value)) {
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return false;
        }
        else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * méthode qui regarde si textInputEditText est bien une date de naissance valide
     *
     * @param textInputEditText
     * @param message
     * @param nestedScrollView
     * @return
     */
    public boolean isInputEditTextBirthday(TextInputEditText textInputEditText, String message, NestedScrollView nestedScrollView) {
        String value = textInputEditText.getText().toString().trim();
        if(!Pattern.matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)", value)) {
            Snackbar.make(nestedScrollView, message, Snackbar.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * methode qui regarde si InputEditText a un email valide
     *
     * @param textInputEditText
     * @param textInputLayout
     * @param message
     * @return
     */
    public boolean isInputEditTextEmail(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message) {
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * methode qui regarde si InputEditText a un email valide
     * @param textInputEditText
     * @param message
     * @param nestedScrollView
     * @return
     */
    public boolean isInputEditTextEmail(TextInputEditText textInputEditText, String message, NestedScrollView nestedScrollView) {
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            Snackbar.make(nestedScrollView, message, Snackbar.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * methode qui regarde si les deux InputEditText sont les mêmes
     * @param textInputEditText1
     * @param textInputEditText2
     * @param textInputLayout
     * @param message
     * @return
     */
    public boolean isInputEditTextMatches(TextInputEditText textInputEditText1, TextInputEditText textInputEditText2, TextInputLayout textInputLayout, String message) {
        String value1 = textInputEditText1.getText().toString().trim();
        String value2 = textInputEditText2.getText().toString().trim();
        if (!value1.contentEquals(value2)) {
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText2);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * methode qui fait disparaitre le clavier
     *
     * @param view
     */
    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
