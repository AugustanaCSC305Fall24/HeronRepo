package edu.augustana;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Set;

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
    }

    // Method to get Morse code for a given letter
    public String getMorseCode(String letter) {
        return morseCodeMap.getOrDefault(letter.toUpperCase(), "");
    }
    public String getLetter(String morseCode){
        for(Map.Entry<String, String> set : morseCodeMap.entrySet()){
            if (set.getValue().equals(morseCode) ){
                return set.getKey();
            }
        }
        throw new InputMismatchException(morseCode + " Invalid morse code");
    }
    // Method to check if the user's Morse code matches the correct code for a letter
    public boolean validateMorseCode(String letter, String userInput) {
        String correctMorseCode = getMorseCode(letter);
        return correctMorseCode.equals(userInput);
    }
}
