package com.company;

import javax.jws.soap.SOAPMessageHandlers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Lexer {

    private final int START = 0;
    private int ENDWORD = 0;
    private final String rINT = "\\b(^[1-9]+[0-9]*)";
    private final int CONTINUE = 1, FINISHED = 0, ERROR = 2;

    public Lexer() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String s;
        String input = "";

        while ((s = in.readLine()) != null && s.length() != 0) {
            input = s;
        }

        ENDWORD = input.length()-1;
        int ENDLOOP = input.length();
        int OFFSET = 0;
        for(int i=0; i<ENDLOOP; i++) {
            switch(nextToken(input, i)){
                case 0:
                    // FINISHED
                    String sToken = input.substring(OFFSET,i+1);
                    TokenCode tc = identifyToken(sToken);
                    Token token = new Token(sToken, tc);
                    OFFSET = i;
                    System.out.println("lexeme: "+ token.lexeme + " tCode: " + token.tCode);
                    break;
                default:
                    // CONTINUE
                    //System.out.println("Continuing");
                    break;
            }
        }
    }

    private TokenCode identifyToken(String token){
        if(token.matches(";")){
            return TokenCode.SEMICOL;
        }
        if(token.matches(rINT)){
            return TokenCode.INT;
        }

        else return TokenCode.ERROR;
    }

    private int nextToken(String input, int INDEX){
        //System.out.println("INDEX: " + INDEX + " END: " + ENDWORD);
        if(INDEX == ENDWORD){
            //System.out.println("END");
            return FINISHED;
        }

        if(input.substring(INDEX, INDEX+1).matches(rINT)){
            //System.out.println("CONT");
            return CONTINUE;
        }


        return FINISHED;
    }














    // Plots lexeme tokens, the caller sends each connected string w/out spaces
    public ArrayList<Token> Tokenizer(String input){
        String lexeme = input;
        TokenCode tokenCode = TokenCode.ERROR; // Error if not founn
        ArrayList<Token> tokenlist = new ArrayList<Token>();
        boolean SemiColonFound = false;

        // Comparisons
        String rINT = "\\b(^[1-9]+[0-9]*)";
        String rID = "\\d\\w+"; // Doesn't work
        String END = "end", PRINT = "print", ADD = "\\+", SUB = "-", LPAREN = "(", RPAREN = ")",
                MULT = "\\*", ASSIGN = "=";
        char SEMICOL = ';';



        if(input.charAt(input.length()-1) == SEMICOL){
            SemiColonFound = true;
            input = input.substring(0, input.length()-1);
        }

        // For Default
        //  Nothing found, send entire input back with ERROR
        //
        // Make sure words that could be taken as variables be found BEFORE the variable
        // case is called.  Or don't allow variable to grab them in the regex somehow.
        //
        // LPAREN, RPAREN, SEMICOL special cases
        // Search for end of word
        // Send word-1 through nextToken again
        // return set of tokens T = {word-1, end of word)
        boolean FOUND = false;
        int loop = 0;
        do{
            loop++;
            switch(loop){
                case 1:
                    // INT
                    if(input.matches(rINT)){
                        tokenCode = TokenCode.INT;
                        FOUND = true;
                    }
                    break;
                case 2:
                    // END
                    if(input.matches(END)){
                        tokenCode = TokenCode.END;
                        FOUND = true;
                    }
                    break;
                case 3:
                    // PRINT
                    if(input.matches(PRINT)){
                        tokenCode = TokenCode.PRINT;
                        FOUND = true;
                    }
                    break;
                case 4:
                    // ADD
                    if(input.matches(ADD)){
                        tokenCode = TokenCode.ADD;
                        FOUND = true;
                    }
                    break;
                case 5 :
                    // SUB
                    if(input.matches(SUB)){
                        tokenCode = TokenCode.SUB;
                        FOUND = true;
                    }
                    break;
                case 6:
                    // MULT
                    if(input.matches(MULT)){
                        tokenCode = TokenCode.MULT;
                        FOUND = true;
                    }
                    break;
                case 7:
                    // ASSIGN
                    if(input.matches(ASSIGN)){
                        tokenCode = TokenCode.ASSIGN;
                        FOUND = true;
                    }
                    break;
                case 10:
                    // ID
                    if(input.matches(rID)){
                        tokenCode = TokenCode.ID;
                        FOUND = true;
                    }
                    break;
                default:
                    // ERROR
                    tokenlist.add(new Token(lexeme, tokenCode));
                    return tokenlist;
                    //return new Token(lexeme, tokenCode);
            }

        } while(!FOUND);

        if(SemiColonFound){
            tokenlist.add(new Token(lexeme.substring(0, lexeme.length()-1), tokenCode));
            tokenlist.add(new Token(";", tokenCode.SEMICOL));
        } else {
            tokenlist.add(new Token(lexeme, tokenCode));
        }
        return tokenlist;
    }

    /** OBSOLETE BELOW **/

    // Recursively checks how many characters long the token is
    // Makes sure each index follows the regex
    private static int checkToken(String sub, int index, String regex){

        if(sub.length() <= index) {
            return 0;
        }

        if(sub.substring(index, index+1).matches(regex)){
            return checkToken(sub, index+1, regex) + 1;
        }

        return 0;
    }

}


