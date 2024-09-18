package cosc202.andie;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Method;

/**
 * Test for ChangeLanguage class
 * 
 * With help from ChatGPT
 * 
 * @see ChangeLanguage
 */
public class ChangeLanguageTest {

    @Test
    void shortenLanguageTest() throws Exception {
        // Create accessable method because regular method is private
        Method shortenLanguageMethod = ChangeLanguage.class.getDeclaredMethod("shortenLanguage", String.class);
        shortenLanguageMethod.setAccessible(true);

        // Verify expected values
        Assertions.assertEquals("en", shortenLanguageMethod.invoke(null, "English"));
        Assertions.assertEquals("mi", shortenLanguageMethod.invoke(null, "Maori"));
        // Verify unexpected values
        Assertions.assertEquals("", shortenLanguageMethod.invoke(null, "oops"));
    }

    @Test
    void getCountryFromLanguageTest() throws Exception {
        // Create accessable method because regular method is private
        Method getCountryFromLanguagMethod = ChangeLanguage.class.getDeclaredMethod("getCountryFromLanguage", String.class);
        getCountryFromLanguagMethod.setAccessible(true);

        // Verify expected values
        Assertions.assertEquals("NZ", getCountryFromLanguagMethod.invoke(null, "en"));
        Assertions.assertEquals("NZ", getCountryFromLanguagMethod.invoke(null, "mi"));
        // Verify unexpected values
        Assertions.assertEquals("", getCountryFromLanguagMethod.invoke(null, "oops"));
    }
}
