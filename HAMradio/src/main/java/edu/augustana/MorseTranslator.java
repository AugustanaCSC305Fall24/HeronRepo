package edu.augustana;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class MorseTranslator {

    private final Map<String, String> morseCodeMap = new HashMap<>();

    // Constructor that initializes the Morse code map for the alphabet
    public MorseTranslator() {
        morseCodeMap.put("A", ".-");
        morseCodeMap.put("B", "-...");
        morseCodeMap.put("C", "-.-.");
        morseCodeMap.put("D", "-..");
        morseCodeMap.put("E", ".");
        morseCodeMap.put("F", "..-.");
        morseCodeMap.put("G", "--.");
        morseCodeMap.put("H", "....");
        morseCodeMap.put("I", "..");
        morseCodeMap.put("J", ".---");
        morseCodeMap.put("K", "-.-");
        morseCodeMap.put("L", ".-..");
        morseCodeMap.put("M", "--");
        morseCodeMap.put("N", "-.");
        morseCodeMap.put("O", "---");
        morseCodeMap.put("P", ".--.");
        morseCodeMap.put("Q", "--.-");
        morseCodeMap.put("R", ".-.");
        morseCodeMap.put("S", "...");
        morseCodeMap.put("T", "-");
        morseCodeMap.put("U", "..-");
        morseCodeMap.put("V", "...-");
        morseCodeMap.put("W", ".--");
        morseCodeMap.put("X", "-..-");
        morseCodeMap.put("Y", "-.--");
        morseCodeMap.put("Z", "--..");
        morseCodeMap.put("0", "-----");
        morseCodeMap.put("1", ".----");
        morseCodeMap.put("2", "..---");
        morseCodeMap.put("3", "...--");
        morseCodeMap.put("4", "....-");
        morseCodeMap.put("5", ".....");
        morseCodeMap.put("6", "-....");
        morseCodeMap.put("7", "--...");
        morseCodeMap.put("8", "---..");
        morseCodeMap.put("9", "----.");
        morseCodeMap.put("?", "..--..");
    }

    // Translates from English to Morse Code
    public String getMorseCodeForText(String text) {
        StringBuilder morseCodeBuilder = new StringBuilder();
        for (char c : text.toUpperCase().toCharArray()) {
            if (c == ' ') {  // Double space to separate words
                morseCodeBuilder.append("  ");
            } else {
                String morseCode = morseCodeMap.getOrDefault(String.valueOf(c).toUpperCase(), "");
                if (!morseCode.isEmpty()) {
                    morseCodeBuilder.append(morseCode).append(" ");
                }
            }
        }
        return morseCodeBuilder.toString().trim();
    }

    // Helper method to get a single letter for Morse code
    public String getLetterForSingleMorseCode(String morseCode) {
        for (Map.Entry<String, String> set : morseCodeMap.entrySet()) {
            if (set.getValue().equals(morseCode)) {
                return set.getKey();
            }
        }
        throw new InputMismatchException(morseCode + " Invalid morse code");
    }


}
