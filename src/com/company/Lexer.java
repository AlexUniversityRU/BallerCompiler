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

        //System.out.println("Added token: " + subString);

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
            } else if (subString.contentEquals("*")) {
                tokenCode = Token.TokenCode.MULT;
            }else {
                tokenCode = Token.TokenCode.ERROR;
            }
        } else {
            tokenCode = Token.TokenCode.ERROR;
        }


        this.tokens.add(new Token(subString, tokenCode));

    }


    public Token nextToken(){


        Token temp =  this.tokens.remove();
        System.out.println("Lex: " + temp.lexeme + ", tCode: " + temp.tCode);
        return temp;

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


