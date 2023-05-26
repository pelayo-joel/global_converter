import BaseConverter.*;
import Encryption.Encryption;


public class Main {
    public static void main(String[] args) throws Exception {
        //Checking if valid number of args, else prints an error
        if (!(args.length == 2 || args.length == 3)) {
            throw new IllegalArgumentException(
                "\nError: no or not enough arguments given\n" + 
                "Syntax: (while in the bin directory)\n" + 
                "    Default: java Main 'base output' 'String to convert'\n" + 
                "    Optional arguments: --sha256 or -s (to get sha256 encryption of the string)", null);
        }

        
        //Instantiate the 'Converter' class with the second argument (string to convert) given as a parameter
        Converter result = new Converter(args[1]);
        short[] inputDecimal = result.GetDecimalValues();
        
        //First prints the encryption if the argument is given, then the requested base according to the first argument
        if (args.length == 3) {
            Encryption crypted = new Encryption(inputDecimal);
            System.out.println("\n" + crypted.Encrypt(args[2]));
        }
        System.out.println("'" + args[1] + "' in [" + args[0] + "]: " + result.ConvertBase(args[0].toLowerCase()) + "\n");
    }
}