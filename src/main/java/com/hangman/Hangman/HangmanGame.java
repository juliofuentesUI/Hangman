package com.hangman.Hangman;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class HangmanGame {
    private File wordListFile;
    private static ArrayList<String> wordList = new ArrayList<>();
    private String[] availLetters = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private HashMap<String, Boolean> availLettersMap = new HashMap<String, Boolean>();
    private Scanner textScanner;

    private String currentWord;
    private Integer lives_remaining = 5;
    private Integer imageIndex = 0;
    private char[] textArray;
    private char[] playerAnswers;
    public boolean isGameOver = false;

    public HangmanGame() {
            loadWordListToMemory();
            initAvailableLetters();
            selectNextWord();
    }

    public void ResetGame() {
        initAvailableLetters();
        lives_remaining = 5;
        imageIndex = 0;
        selectNextWord();
    }

    private void loadWordListToMemory() {
        if (wordList.size() >= 1) return; //wordList is in memory
        try {
            wordListFile = new File(this.getClass().getResource("../../../wordlist.txt").getFile());
            textScanner = new Scanner(wordListFile);
            while(textScanner.hasNextLine()) {
                wordList.add(textScanner.nextLine());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void initAvailableLetters() {
        for(String availLetter: availLetters) {
            availLettersMap.put(availLetter, true);
        }
    }

    private void selectNextWord() {
        if (wordList.size() <= 0) return;
        currentWord = wordList.get((int)(Math.random()* wordList.size()));
        System.out.println("Currentword is : " + currentWord);
        textArray = currentWord.toCharArray();
        playerAnswers = new char[textArray.length];
        for(int i = 0; i < playerAnswers.length; i++) {
            playerAnswers[i] = "?".charAt(0);
        }
    }

    public Integer getImageIndex() {
        return imageIndex;
    }

    public String[] getAllLetters() {
        return availLetters;
    }

    public String getCurrentWord() {
        return currentWord;
    }

    public Integer getLivesRemaining() {
        return lives_remaining;
    }

    public HashMap getAvailLetterMap() {
        return availLettersMap;
    }

    public char[] getPlayerAnswers() {
        return playerAnswers;
    }

    public boolean checkGameWon() {
        for(char c: playerAnswers) {
            if (c != "?".charAt(0)) {
                continue;
                // break would be better , will break just break out of inner loop? who knows
            } else {
                return false;
            }
        }
        return true;
    }

    public Integer checkLetter(String letter) {
        char[] guessedLetter = letter.toCharArray();
        if (availLettersMap.containsKey(String.valueOf(guessedLetter))) {
            availLettersMap.put(String.valueOf(guessedLetter), false);
        }
        boolean found = false;
        for(int i = 0; i < textArray.length; i++) {
            if (guessedLetter[0] == textArray[i]) {
                found = true;
                playerAnswers[i] = guessedLetter[0];
            }
        }
        if (!found) {
            lives_remaining--;
            imageIndex++;
        }
        return lives_remaining;
    }
}
