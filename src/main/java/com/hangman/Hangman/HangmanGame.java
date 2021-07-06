package com.hangman.Hangman;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class HangmanGame {
    //access words and stuff here
    private File wordListFile;
    private ArrayList<String> wordList = new ArrayList<>();
    private Scanner textScanner;
    //maybe we should make the wordList static , we don't want every user making us run this operation a lot
    //maybe make an init method that does the putting words into memory? honestly it should be done once.

    public HangmanGame() {
        try {
            //investigate classLoader later
//          wordlistFile = new File(ClassLoader.getSystemClassLoader().getResource("../../../wordlist.txt").getFile());
            wordListFile = new File(this.getClass().getResource("../../../wordlist.txt").getFile());
            textScanner = new Scanner(wordListFile);
            while(textScanner.hasNextLine()) {
                wordList.add(textScanner.nextLine());
            }
            String selectedWord = wordList.get((int)(Math.random()* wordList.size()));
            //when we have to reconstruct a game, we may have to extrapolate the next 2 lines into another method
            char[] textArray = selectedWord.toCharArray();
            char[] playerAnswers = new char[textArray.length];
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
