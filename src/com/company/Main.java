package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static java.lang.System.in;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello!");

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String s;

        Lexer lexer = new Lexer();
        Queue<Token> tokenQ = new LinkedList<Token>();


        // Separates the input by spaces
        // Checks each word if it is a lexeme
        // Places the lexeme into the queue (even if the lexeme is ERROR)
        while ((s = in.readLine()) != null && s.length() != 0) {
            String[] splitted = s.split("\\s+");
            for (String part : splitted){
                tokenQ.add(lexer.nextToken(part));
            }
        }

        for(Token t : tokenQ) {
            System.out.println("lexeme: " + t.lexeme + " tCode: " + t.tCode);
        }


    }
}
