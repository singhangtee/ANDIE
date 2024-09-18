package cosc202.andie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.Locale;

/**
 * Tests for the formatting of changing languages.
 * 
 * @see ChangeLanguage
 * @see SettingsActions
 */
public class LanguageTest {

    @Test
    void test_with_language() {
        Locale.setDefault(new Locale("en", "EN"));
        Assertions.assertEquals("0.1", String.format("%.1f", 0.1));
        Locale.setDefault(new Locale("mi", "MI"));
        Assertions.assertEquals("0.1", String.format("%.1f", 0.1));
        //System.out.println(String.format("ok"));
        //Assertions.assertEquals("Pai", String.format("ok"));
    }
}