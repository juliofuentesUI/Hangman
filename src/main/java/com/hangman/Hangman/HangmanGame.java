package com.hangman.Hangman;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class HangmanGame {
    //access words and stuff here
    private File wordListFile;
    private static ArrayList<String> wordList = new ArrayList<>();
    private Scanner textScanner;

    private String currentWord;
    private Integer lives_remaining;
    private char[] textArray;
    private char[] playerAnswers;
    public boolean isGameOver = false;
    public boolean isGameWon = false;

    //maybe we should make the wordList static , we don't want every user making us run this operation a lot
    //maybe make an init method that does the putting words into memory? honestly it should be done once.

    public HangmanGame() {
            LoadWordListToMemory();
            SelectNextWord(); //loads nextWord into memory along with charArray
    }

    public HangmanGame(Object gameState) {
        // in the case we have to load an existing game;
    }

    private void LoadWordListToMemory() {
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

    private void SelectNextWord() {
        if (wordList.size() <= 0) return;
        currentWord = wordList.get((int)(Math.random()* wordList.size()));
        //when we have to reconstruct a game, we may have to extrapolate the next 2 lines into another method
        textArray = currentWord.toCharArray();
        playerAnswers = new char[textArray.length];
    }

    public boolean CheckLetter(String letter) {
        //convert to char
        char[] guessedLetter = letter.toCharArray();
        boolean found = false;
        //check to see if letter exists in textArray
        for(int i = 0; i < textArray.length; i++) {
            if (guessedLetter[0] == textArray[i]) {
                //that means this DOES exists
                //they guessed correctly
                found = true;
                playerAnswers[i] = guessedLetter[0];
                //this for loop below checks to see if game is over
                // if it is, just stop the game
                for(char c: playerAnswers) {
                    if (playerAnswers[i] == 0) {
                        //that means this value still hasn't been filled in
                        //game is still on
                        continue;
                    } else {
                        isGameWon = true;
                        return true;
                    }
                }
            }
        }
        if (!found) {
            lives_remaining--;
            return false;
            //lose a life, update hangman image from 0 to 1 and so fourth.
            //return true that this letter exists
            //then if the caller see's true, they can just access our getter methods to see
            // the current playerAnswers array
        }
        return true;
    }
}
