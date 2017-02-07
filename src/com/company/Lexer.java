package com.company;

import javax.jws.soap.SOAPMessageHandlers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Lexer {

    // Plots ONE lexeme token, the caller sends each lexeme individually
    public ArrayList<Token> nextToken(String input){
        String lexeme = input;
        TokenCode tokenCode = TokenCode.ERROR; // Error if not found
        int index = 0;
        int count = -1;
        ArrayList<Token> tokenlist = new ArrayList<Token>();
        boolean ColonFound = false;

        // Comparisons
        String rINT = "\\b(^[1-9]+[0-9]*)";
        String rID = "\\d\\w+"; // Doesn't work
        String END = "end", PRINT = "print", ADD = "\\+", SUB = "-", LPAREN = "(", RPAREN = ")",
                MULT = "\\*", ASSIGN = "=";
        char SEMICOL = ';';


        if(input.charAt(input.length()-1) == SEMICOL){
            ColonFound = true;


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
                    tokenlist.add(0, new Token(lexeme, tokenCode));
                    return tokenlist;
                    //return new Token(lexeme, tokenCode);
            }

        } while(!FOUND);

        if(ColonFound){
            tokenlist.add(0, new Token(lexeme.substring(0, lexeme.length()-1), tokenCode));
            tokenlist.add(1, new Token(";", tokenCode.SEMICOL));
        } else {
            tokenlist.add(0, new Token(lexeme, tokenCode));
        }
        return tokenlist;
    }

    /** OBSOLETE BELOW **/

    // integer
    /*if (input.substring(index, index + 1).matches(rNumber)) {
        count = checkToken(input, 0, rNumber);
        tokenCode = TokenCode.INT;
     }*/
    // variable
     /*if (input.substring(index, index + 1).matches(rID)) {
        count = checkToken(input, 0, rID);
        tokenCode = TokenCode.ID;
      }*/

    // /while(index + count != input.length());

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


