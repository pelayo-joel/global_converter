package BaseConverter;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        //Checking if valid number of args, else print an error while quitting the program
        if (!(args.length == 2 || args.length == 4 || args.length == 6)) {
            System.out.println("Error: no or not enough arguments given\n" + 
                "Syntax: (while in the bin directory)\n" + 
                "    Default: java BaseConverter.Main 'base output' 'String to convert'\n" + 
                "    Optional arguments: -k 'key', -a 'encryption algorithm' (caesar algorithm if arg not used)");
            System.exit(1);
        }

        
        //Instantiate the 'Converter' class with the first argument given as a parameter, then print the base according to the second argument
        Converter result = new Converter(args[1]);
        System.out.println("\n" + result.DefineBase(args[0].toLowerCase()) + "\n");

        //Loop the input to ask each time if the user wants its string under a different base then print it
        Console input = System.console();
        while (true) {
            String newBase = input.readLine("Enter another base\n('-d' or 'decimal', '-h' or 'hexadecimal', '-o' or 'octal', '-b' or 'binary', '-t' or 'text', 'e' or 'exit' to quit): ");
            
            System.out.println("\n" + result.DefineBase(newBase.toLowerCase()) + "\n");

            input.readLine("Enter to continue...");
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

        }
    }
}