package com.company;


public class Token {
    String lexeme;
    TokenCode tCode;

    public Token(String lexeme, TokenCode tCode){
        this.lexeme = lexeme;
        this.tCode = tCode;
    }

}

enum TokenCode{ID, ASSIGN, SEMICOL, INT, ADD, SUB,
                MULT, LPAREN, RPAREN, PRINT, END, ERROR};
