package edu.augustana.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;

class MorseTranslatorTest {
    MorseTranslator translator;
    @BeforeEach
    void setup(){
        translator = new MorseTranslator();

    }
    @Test
    void getMorseCodeForTextCorrect() {
        assertEquals(translator.getLetterForSingleMorseCode("-..."),"B");
        assertEquals(translator.getLetterForSingleMorseCode(".-"),"A");
    }

    @Test
    void getLetterForSingleMorseCodeException() {
        assertThrows(InputMismatchException.class, () ->translator.getLetterForSingleMorseCode(".-....."));
        assertThrows(InputMismatchException.class, () ->translator.getLetterForSingleMorseCode("......."));

    }

    @Test
    void getMorseCodeForText(){
        assertEquals(translator.getMorseCodeForText("testing everything"),"- . ... - .. -. --. % . ...- . .-. -.-- - .... .. -. --.");
    }

    @Test
    void getMorseCodeForTextNotText(){
        assertThrows(InputMismatchException.class,()->translator.getMorseCodeForText("#"));
    }

    @Test
    void translateMorseCode() {
        assertEquals(translator.translateMorseCode("- . ... - .. -. --. % . ...- . .-. -.-- - .... .. -. --."),"TESTING EVERYTHING");
        assertThrows(InputMismatchException.class,()->translator.translateMorseCode("- . ........ - .. -. --.   . ...- . .-. -.-- - .... .. -. --."));
    }
}