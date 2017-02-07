/**
 * Created by Owner on 05-Feb-17.
 */
public class Compiler {


    public Lexer myLexer;
    public Parser myParser;

    public Compiler(String input) {
        this.myLexer = new Lexer(input);
        this.myParser = new Parser(myLexer);



    }
}
