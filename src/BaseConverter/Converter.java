package BaseConverter;

import java.util.regex.*;


public class Converter implements Bases {
    
    /* -------------------- Properties -------------------- */

    private final String INVALID_CHAR = "!@#$%^&*()-_=+[{}];:'|/?.>,<";
    private final String ORIGINAL_STRING; //Keeps the original string
    private short[] decimalValues; //Base calculation are based on their decimal representation, hence this array

    private StringBuilder result;


    /* -------------------- Constructors -------------------- */


    /**
     * Class constructor: First checks for any invalid char, 
     * then determines in what base the string is written in.
     * 
     * @param baseString Original string to convert given in the command arguments
     */
    public Converter(String baseString) {
        this.ORIGINAL_STRING = baseString;

        for (int i = 0; i < this.INVALID_CHAR.length(); i++){
            for (int j = 0; j < baseString.length(); j++){
                if (baseString.charAt(j) == INVALID_CHAR.charAt(i)){
                    System.out.println("Error: invalid char in base String, only alphabetical and numerical characters");
                    System.exit(2);
                }
            }
        }

        this.DefineBase(); //Defines in what base the original string is written in
    }




    /* --------------- Public methods --------------- */

    /**
     * Depending on the requested base, this method uses the 'Bases' interface
     * methods to convert the original string based on the decimal (or ascii) value
     * of each char in the string. 
     * 
     * @param baseParameter Requested base given in the command arguments
     * @return              The converted string in the requested base
     */
    public StringBuilder ConvertBase(String baseParameter) {
        this.result = new StringBuilder();
        switch (baseParameter) {
            case "-h", "--hexadecimal":
                this.result.append(Bases.Base16(this.decimalValues));
                return this.result;
            case "-o", "--octal":
                this.result.append(Bases.Base8(this.decimalValues));
                return this.result;
            case "-d", "--decimal":
                this.result.append(Bases.Base10(this.decimalValues));
                return this.result;
            case "-b", "--binary":
                this.result.append(Bases.Base2(this.decimalValues));
                return this.result;
            case "-t", "--text":
                this.result.append(Bases.TextBase(this.decimalValues));
                return this.result;
            default:
                System.out.println("Error: Invalid base");
                System.exit(3);
                return null;
        }
    }



    public short[] GetDecimalValues() {
        return this.decimalValues;
    }




    /* --------------- Private methods --------------- */

    /**
     * This method uses regular expressions to determine the base of the
     * original string, it then initialize 'decimalValues' length which is needed
     * for all conversion.
     */
    private void DefineBase() {
        Pattern[] patterns = {Pattern.compile("^([0-1]{8}\s)*$"), Pattern.compile("^([0][0-7]{3}\s)*$"), 
            Pattern.compile("^([0-9]+\s)*$"), Pattern.compile("^([0-9|A-F]{2}\s)*$", Pattern.CASE_INSENSITIVE)};
        byte patternSelector = 0;

        for (byte i = 1; i <= patterns.length; i++) {
            Matcher baseMatcher = patterns[i-1].matcher(this.ORIGINAL_STRING);
            if (baseMatcher.find()) {
                patternSelector = i;
                break;
            }
        }

        //Initialize the length of the decimals array
        if (patternSelector != 0) {
            int baseStringLen = 0;

            for (int i = 0; i < this.ORIGINAL_STRING.length(); i++) {
                if (this.ORIGINAL_STRING.charAt(i) == ' ') {
                    baseStringLen++;
                }
            }
            this.decimalValues = new short[baseStringLen];
        }
        else { this.decimalValues = new short[this.ORIGINAL_STRING.length()]; }

        this.DefineDecimal(patternSelector);
    }



    /**
     * Since all conversions are based on their decimal value, we use this method
     * to know which conversion we need to use to get the decimal value of each char
     * depending on the base defined by the previous method, else if we just have plain text
     * we simply cast the char to an int which returns its ascii value. It also uses methods in the
     * 'Bases' interface.
     * 
     * @param pattern The determined base which is given by the method 'DefineBase'
     */
    private void DefineDecimal(short pattern) {
        if (pattern == 1) {
            this.decimalValues = Bases.BinaryToDecimal(this.decimalValues.length, this.ORIGINAL_STRING);
        }
        else if (pattern == 2) {
            this.decimalValues = Bases.OctaToDecimal(this.decimalValues.length, this.ORIGINAL_STRING);
        }
        else if (pattern == 3) {
            this.decimalValues = Bases.Decimal(this.decimalValues.length, this.ORIGINAL_STRING);
        }
        else if (pattern == 4) {
            this.decimalValues = Bases.HexaToDecimal(this.decimalValues.length, this.ORIGINAL_STRING);
        }
        else {
            for (int i = 0; i < this.ORIGINAL_STRING.length(); i++) {
                short currentChar = (short) this.ORIGINAL_STRING.charAt(i);
                this.decimalValues[i] = currentChar;
            }
        }
    }
}
