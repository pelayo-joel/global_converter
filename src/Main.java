import BaseConverter.*;


public class Main {
    public static void main(String[] args) throws Exception {
        //Checking if valid number of args, else prints an error
        if (!(args.length == 2 || args.length == 4 || args.length == 6)) {
            throw new IllegalArgumentException(
                "\nError: no or not enough arguments given\n" + 
                "Syntax: (while in the bin directory)\n" + 
                "    Default: java Main 'base output' 'String to convert'\n" + 
                "    Optional arguments: --sha256 or -s (to get sha256 encryption of the string)", null);
        }

        
        //Instantiate the 'Converter' class with the second argument (string to convert) given as a parameter, then prints the base according to the first argument (requested base)
        Converter result = new Converter(args[1]);
        System.out.println("\n" + "'" + args[1] + "' in " + args[0] + ": " + result.ConvertBase(args[0].toLowerCase()) + "\n");
    }
}