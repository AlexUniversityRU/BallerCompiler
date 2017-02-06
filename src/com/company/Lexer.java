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
    public Token nextToken(String input){
        String lexeme = input;
        TokenCode tokenCode = TokenCode.ERROR; // Error if not found
        int index = 0;
        int count = -1;

        // Regex's
        String rNumber = "\\b(^[1-9]+[0-9]*)";
        String rID = "\\d\\w+";

        // For rNumber, rID
        //  If the first index looks like a lexeme, check all further indexes
        //  recursively with checkToken and return the count of index's that
        //  fit within the regex
        // For Default
        //  Nothing found, send entire input back with ERROR
        boolean FOUND = false;
        int loop = 0;
        do{
            loop++;
            switch(loop){
                case 1:
                    // INT
                    if(input.matches(rNumber)){
                        tokenCode = TokenCode.INT;
                        FOUND = true;
                    }
                    break;
                case 3:
                    // Make sure words that could be taken as variables be found BEFORE the variable
                    // case is called.  Or don't allow variable to grab them in the regex somehow.
                    break;
                case 2:
                    // ID
                    if(input.matches(rID)){
                        tokenCode = TokenCode.ID;
                        FOUND = true;
                    }
                    break;
                default:
                    // ERROR
                    return new Token(lexeme, tokenCode);
            }

        } while(!FOUND);
        // /while(index + count != input.length());

        //System.out.print(tokenCode.toString()+ ", ");
        return new Token(lexeme, tokenCode);
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



