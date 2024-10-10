package edu.augustana;

import java.util.HashMap;
import java.util.Map;

public class MorseTranslator {

    private Map<String, String> morseCodeMap = new HashMap<>();

    // Constructor that initializes the Morse code map for the alphabet
    public MorseTranslator() {

        // Initialize the Morse code map for the alphabet
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
    }

    // Method to get Morse code for a given letter
    public String getMorseCode(String letter) {
        return morseCodeMap.getOrDefault(letter.toUpperCase(), "");
    }

    // Method to check if the user's Morse code matches the correct code for a letter
    public boolean validateMorseCode(String letter, String userInput) {
        String correctMorseCode = getMorseCode(letter);
        return correctMorseCode.equals(userInput);
    }
}
