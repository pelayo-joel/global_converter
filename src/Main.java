import BaseConverter.*;
import Encryption.Encryption;


public class Main {
    public static void main(String[] args) throws Exception {
        //Checking if valid number of args, else prints an error
        if (!(args.length == 2 || (args.length == 3 && (args[2].charAt(0) == '-')) || (args.length == 4 && (args[3].charAt(0) == '-')))) {
            throw new IllegalArgumentException(
                "\nError: invalid arguments or syntax given\n" + 
                "Syntax: (while in the bin directory)\n" + 
                "    Default: java Main 'base output' 'String to convert'\n" + 
                "    Optional arguments (encryption): \n" + 
                "       --sha256 or -s,\n" +
                "       --caesar or -c followed by -k (k being any signed integer)", null);
        }

        
        //Instantiate the 'Converter' class with the second argument (string to convert) given as a parameter
        Converter result = new Converter(args[1]);
        short[] inputDecimal = result.GetDecimalValues();
        
        //First prints the encryption if the argument is given, then the requested base according to the first argument
        System.out.println();
        if (args.length > 2) {
            Encryption crypted = new Encryption(inputDecimal);

            if (args.length == 4) { System.out.println(crypted.Encrypt(args[2], args[3])); }
            else { System.out.println(crypted.Encrypt(args[2], "-0")); }
        }
        System.out.println("'" + args[1] + "' in [" + args[0] + "]: " + result.ConvertBase(args[0].toLowerCase()) + "\n");
    }
}