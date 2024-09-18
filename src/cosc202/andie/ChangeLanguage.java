package cosc202.andie;

import java.util.Locale;
import java.util.prefs.Preferences;

/**
 * Settings Action to change the GUI language 
 */
public class ChangeLanguage {

    /**
     * <p>
     * Construct a ChangeLanguage using the specified language.
     * </p>
     * 
     * <p>
     * Changes the language of ANDIE's GUI using the parameter.
     * </p>
     * 
     * @param fullLanguage The language to change ANDIE's GUI to.
     */
    public ChangeLanguage(String fullLanguage) {

        String shortLanguage = shortenLanguage(fullLanguage);
        String country = getCountryFromLanguage(shortLanguage);

        Locale newLocale = new Locale(shortLanguage, country);

        updateLanguage(newLocale);
        Andie.changeLanguage(newLocale);
        Andie.getFrame().requestFocus();
    }

    /**
     * <p>
     * Updates the language of ANDIE's GUI using a specified locale.
     * </p>
     * 
     * <p>
     * The revelant language and country is taken from the locale parameter.
     * </p>
     * 
     * @param locale The locale to update ANDIE's GUI to, gives language and
     *               country.
     */
    private void updateLanguage(Locale locale) {
        Preferences prefs = Preferences.userNodeForPackage(Andie.class);
        prefs.put("language", locale.getLanguage());
        prefs.put("country", locale.getCountry());
    }

    /**
     * <p>
     * Gets the shortened version of a language name.
     * </p>
     * 
     * <p>
     * Gets the short version using the input string. Only supports maori and english at present.
     * </p>
     * 
     * @param fullLanguage The language name to shorten.
     */
    private static String shortenLanguage(String fullLanguage) {
        switch (fullLanguage.toLowerCase()) {
            case "english":
                return "en";
            case "maori":
                return "mi";
            default:
                // This should never be able to happen
                return "";
        }
    }

        /**
     * <p>
     * Gets the country from a language name.
     * </p>
     * 
     * <p>
     * Takes a shortened language name from shortenLanguage. Only supports maori and english at present.
     * </p>
     * 
     * @param fullLanguage The language name to shorten.
     */
    private static String getCountryFromLanguage(String language) {
        switch (language.toLowerCase()) {
            case "en":
                return "NZ";
            case "mi":
                return "NZ";
            default:
                // This should never be able to happen
                return "";
        }
    }

}
