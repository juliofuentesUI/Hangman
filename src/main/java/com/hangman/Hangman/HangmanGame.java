package com.hangman.Hangman;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class HangmanGame {
    //access words and stuff here
    private File wordListFile;
    private static ArrayList<String> wordList = new ArrayList<>();
    private String[] availLetters = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private HashMap<String, Boolean> availLettersMap = new HashMap<String, Boolean>();
    private Scanner textScanner;

    private String currentWord;
    private Integer lives_remaining = 5;
    private char[] textArray;
    private char[] playerAnswers;
    public boolean isGameOver = false;

    //maybe we should make the wordList static , we don't want every user making us run this operation a lot
    //maybe make an init method that does the putting words into memory? honestly it should be done once.

    public HangmanGame() {
            loadWordListToMemory();
            initAvailableLetters();
            selectNextWord(); //loads nextWord into memory along with charArray
    }

    public HangmanGame(Object gameState) {
        // in the case we have to load an existing game;
    }

    private void loadWordListToMemory() {
        if (wordList.size() >= 1) return; //wordList is in memory
        try {
//          wordlistFile = new File(ClassLoader.getSystemClassLoader().getResource("../../../wordlist.txt").getFile());
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
        //when we have to reconstruct a game, we may have to extrapolate the next 2 lines into another method
        textArray = currentWord.toCharArray();
        playerAnswers = new char[textArray.length];
        for(int i = 0; i < playerAnswers.length; i++) {
            playerAnswers[i] = "?".charAt(0);
        }
    }

    public void restartGame() {
        //restart game logic here
        //reset arrays, find new word, reset lives remaining, and reset isGameWon /over booleans
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
        //this for loop below checks to see if game is over
        // if it is, just stop the game
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
        //convert to char
        char[] guessedLetter = letter.toCharArray();
        if (availLettersMap.containsKey(String.valueOf(guessedLetter))) {
            availLettersMap.put(String.valueOf(guessedLetter), false);
        }
        boolean found = false;
        for(int i = 0; i < textArray.length; i++) {
            if (guessedLetter[0] == textArray[i]) {
                //that means this DOES exists
                //they guessed correctly
                found = true;
                playerAnswers[i] = guessedLetter[0];
            }
        }
        if (!found) {
            lives_remaining--;
            //lose a life, update hangman image from 0 to 1 and so fourth.
            //return true that this letter exists
            //then if the caller see's true, they can just access our getter methods to see
            // the current playerAnswers array
        }
        return lives_remaining;
    }
}
