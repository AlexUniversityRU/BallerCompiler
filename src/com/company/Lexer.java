import com.sun.deploy.util.StringUtils;

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

    private Queue<Token> tokens = new LinkedList<Token>();

    public Lexer(String input) {

        this.splitInput(input);

    }

    private void splitInput(String input) {

        String[] splitted = input.split("\\s+");

        for (String line : splitted) {
            String subString = "";
            boolean digitOrLetter = false;
            for (int i = 0; i < line.length(); i++) {
                char currentChar = line.charAt(i);
                if (Character.isLetter(currentChar) || Character.isDigit(currentChar)) {
                    subString += currentChar;
                    digitOrLetter = true;
                }  else {
                    if (digitOrLetter) {
                        digitOrLetter = false;
                        addToken(subString);
                        subString = "";
                    }
                    addToken((String.valueOf(currentChar)));
                }
            }
            if (!subString.isEmpty()) {
                addToken(subString);
                subString = "";
            }
        }



    }

    private void addToken(String subString) {

        System.out.println("Added token: " + subString);

        Token.TokenCode tokenCode;

        if (subString.matches("[0-9]+")) {
            tokenCode = Token.TokenCode.INT;
        } else if (subString.matches("(\bprint)")) {
            tokenCode = Token.TokenCode.PRINT;
        } else if (subString.matches("(\bend)")) {
            tokenCode = Token.TokenCode.END;
        } else if (subString.matches("[A-Za-z]+")) {
            tokenCode = Token.TokenCode.ID;
        } else if (subString.length() == 1) {
            if (subString == "*") {
                tokenCode = Token.TokenCode.MULT;
            } else if (subString.contentEquals("+")) {
                tokenCode = Token.TokenCode.ADD;
            } else if (subString.contentEquals("-")) {
                tokenCode = Token.TokenCode.SUB;
            } else if (subString.contentEquals("(")) {
                tokenCode = Token.TokenCode.LPAREN;
            } else if (subString.contentEquals(")")) {
                tokenCode = Token.TokenCode.RPAREN;
            } else if (subString.contentEquals(";")) {
                tokenCode = Token.TokenCode.SEMICOL;
            } else if (subString.contentEquals("=")) {
                tokenCode = Token.TokenCode.ASSIGN;
            } else {
                tokenCode = Token.TokenCode.ERROR;
            }
        } else {
            tokenCode = Token.TokenCode.ERROR;
        }


        this.tokens.add(new Token(subString, tokenCode));

    }



    // Plots ONE lexeme token, the caller sends each lexeme individually
    public ArrayList<Token> nextToken(String input){
        ArrayList<Token> tokenlist = new ArrayList<Token>();


        /*String lexeme = input;
        TokenCode tokenCode = TokenCode.ERROR; // Error if not found
        int index = 0;
        int count = -1;
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
        }*/


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
        /*boolean FOUND = false;
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
        }*/
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
      }

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
    }*/

}


