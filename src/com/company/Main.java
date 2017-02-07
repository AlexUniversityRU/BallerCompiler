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
        String input1 = "vasr = 3;\nb = 4 * (7-var);\nprint b;\nend";
        String input2 = "var = 3 + ;\n" +
                "print var;\n" +
                "end";
        String input = "var = 3 ! ;\n" +
                "print var;\n" +
                "end";
        Compiler comp = new Compiler(input);





    }
}
