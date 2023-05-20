package BaseConverter;

public class Converter {
    private final String invalidChar = "!@#$%^&*()-_=+[{}];:'|/?.>,<";
    private final String originalString; //Keep the original string if 'text-based' is asked again
    private final int[] decimalValues; //Base calculation are based on their decimal representation, hence this array

    private StringBuilder result;

    
    //Class constructor, checks if there's an invalid char in the string
    public Converter(String baseString) {
        for (int i = 0; i < this.invalidChar.length(); i++){
            for (int j = 0; j < baseString.length(); j++){
                if (baseString.charAt(j) == invalidChar.charAt(i)){
                    System.out.println("Error: invalid char in base String, only alphabetical and numerical characters");
                    System.exit(2);
                }
            }
        }
        this.originalString = baseString;
        this.decimalValues = new int[this.originalString.length()];
    }





    /*Handles the base parameter then executes the corresponding base formula on each char for the result,
     they're all based on the decimal (or ascii) value hence the 'for loop' at the beginning of the function*/
    public StringBuilder DefineBase(String baseParameter) {
        result = new StringBuilder("'" + this.originalString + "' in " + baseParameter + ": ");
        for (int i = 0; i < this.originalString.length(); i++) {
            int currentChar = (int) this.originalString.charAt(i);
            this.decimalValues[i] = currentChar;
        }

        switch (baseParameter) {
            case "-h":
            case "hexadecimal":
                this.Base16();
                break;
            case "-o":
            case "octal":
                this.Base8();
                break;
            case "-d":
            case "decimal":
                this.Base10();
                break;
            case "-b":
            case "binary":
                this.Base2();
                break;
            case "-t":
            case "text":
                this.result.append(originalString);
                return this.result;
            case "e":
            case "exit":
                System.out.println("Program exit");
                System.exit(0);
            default:
                System.out.println("Error: Invalid base");
                System.exit(3);
        }
        return this.result;
    }



    
    private void Base10() {
        for (int i = 0; i < this.decimalValues.length; i++) {
            this.result.append(decimalValues[i] + " ");
        }
    }



    private void Base2() {
        int bitValue = 256; //For 8 bit
        for (int i = 0; i < this.decimalValues.length; i++) {
            String charBase2 = "";
            int currentBit = bitValue;

            while (currentBit != 1) {
                if (currentBit <= this.decimalValues[i]) {
                    this.decimalValues[i] -= currentBit;
                    charBase2 += '1';
                }
                else { charBase2 += '0'; }
                currentBit /= 2;
            }
            this.result.append(charBase2 + " ");
        }
    }



    private void Base16() {
        String hexaChar = "0123456789ABCDEF";
        for (int i = 0; i < this.decimalValues.length; i++) {
            String charBase16 = "";
            int currentValue = this.decimalValues[i];

            while (currentValue != 0) {
                if (currentValue % 16 != 0) {
                    int remainder = currentValue - ((int)(currentValue / 16) * 16);
                    charBase16 = hexaChar.charAt(remainder) + charBase16;
                }
                else { charBase16 = hexaChar.charAt((currentValue / 16)) + charBase16; }
                currentValue /= 16;
            }
            this.result.append(charBase16 + " ");
        }
    }



    private void Base8() {
        String octaChar = "01234567";
        for (int i = 0; i < this.decimalValues.length; i++) {
            String charBase8 = "";
            int currentValue = this.decimalValues[i];

            while (currentValue != 0) {
                int remainder = currentValue - ((int)(currentValue / 8) * 8);
                charBase8 = octaChar.charAt(remainder) + charBase8;
                currentValue /= 8;
            }
            this.result.append(charBase8 + " ");
        }
    }
}
