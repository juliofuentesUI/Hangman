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

    public HangmanGame() {
        try {
            //investigate classLoader later
//          wordlist = new File(ClassLoader.getSystemClassLoader().getResource("../../../wordlist.txt").getFile());
            wordListFile = new File(this.getClass().getResource("../../../wordlist.txt").getFile());
            textScanner = new Scanner(wordListFile);
            System.out.println("What words do we have here");
            while(textScanner.hasNextLine()) {
                wordList.add(textScanner.nextLine());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
