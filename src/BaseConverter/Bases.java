package BaseConverter;


public interface Bases {
    /**
     * The following methods converts based on the decimal value,
     * to a corresponding base.
     */
    public static StringBuilder Base2(short[] asciiDecimal) {
        StringBuilder base2 = new StringBuilder("");
        int bitValue = 128; //For up to 8 bit

        for (int i = 0; i < asciiDecimal.length; i++) {
            String charBase2 = "";
            int currentBit = bitValue;

            while (currentBit > 0) {
                if (currentBit <= asciiDecimal[i]) {
                    asciiDecimal[i] -= currentBit;
                    charBase2 += '1';
                }
                else { charBase2 += '0'; }
                currentBit /= 2;
            }
            base2.append(charBase2 + " ");
        }
        base2.setLength(base2.length() - 1);
        return base2;
    }



    public static StringBuilder Base8(short[] asciiDecimal) {
        StringBuilder base8 = new StringBuilder("");
        String octaChar = "01234567";

        for (int i = 0; i < asciiDecimal.length; i++) {
            String charBase8 = "";
            int currentValue = asciiDecimal[i];

            while (currentValue != 0) {
                int remainder = currentValue - ((int)(currentValue / 8) * 8);
                charBase8 = octaChar.charAt(remainder) + charBase8;
                currentValue /= 8;
            }
            base8.append("0" + charBase8 + " ");
        }
        base8.setLength(base8.length() - 1);
        return base8;
    }



    public static StringBuilder Base10(short[] asciiDecimal) {
        StringBuilder base10 = new StringBuilder("");
        for (int i = 0; i < asciiDecimal.length; i++) {
            base10.append(asciiDecimal[i] + " ");
        }
        base10.setLength(base10.length() - 1);
        return base10;
    }


    
    public static StringBuilder Base16(short[] asciiDecimal) {
        StringBuilder base16 = new StringBuilder("");
        String hexaChar = "0123456789abcdef";

        for (int i = 0; i < asciiDecimal.length; i++) {
            String charBase16 = "";
            int currentValue = asciiDecimal[i];

            while (currentValue != 0) {
                if (currentValue % 16 != 0) {
                    int remainder = currentValue - ((int)(currentValue / 16) * 16);
                    charBase16 = hexaChar.charAt(remainder) + charBase16;
                }
                else { charBase16 = hexaChar.charAt((currentValue / 16)) + charBase16; }
                currentValue /= 16;
            }
            base16.append(charBase16 + " ");
        }
        base16.setLength(base16.length() - 1);
        return base16;
    }

    

    public static StringBuilder TextBase(short[] asciiDecimal) {
        StringBuilder textBase = new StringBuilder("");

        for (int i = 0; i < asciiDecimal.length; i++) {
            textBase.append((char) asciiDecimal[i]);
        }
        return textBase;
    }





    /**
     * The following methods are all conversion formulas to get the decimal value,
     * note that we're directly iterating on the original string itself (via 'charPos')
     * meaning we're not splitting the string or whatsoever, even though the iteration
     * may vary a little from method to method.
     */
    public static short[] BinaryToDecimal(int decimalArrayLen, String defaultString) {
        short[] result = new short[decimalArrayLen];
        short charPos = 0;

        for (int i = 0; i < result.length; i++) {
            short bit = 128;
            
            while (defaultString.charAt(charPos) != ' ') {
                if (defaultString.charAt(charPos) == '1') { 
                    result[i] += bit;
                }
                bit /= 2; charPos++;
            }
            charPos++;
        }
        return result;
    }



    public static short[] OctaToDecimal(int decimalArrayLen, String defaultString) {
        short[] result = new short[decimalArrayLen];
        char[] octaChar = {'0', '1', '2', '3', '4', '5', '6', '7'};
        int charPos = 0;

        for (int i = 0; i < result.length; i++) {
            int octaPos = 1;

            while (defaultString.charAt(charPos) != ' ') {
                for (int j = 0; j < octaChar.length; j++) {
                    char currentChar = Character.toLowerCase(defaultString.charAt(charPos));
                    if (currentChar == octaChar[j]) {
                        result[i] += j * Math.pow(8, octaPos);
                        break;
                    }
                }
                octaPos--;
                charPos++;
            }
            charPos++;
        }
        return result;
    }



    public static short[] Decimal(int decimalArrayLen, String defaultString) {
        short[] result = new short[decimalArrayLen];
        int charPos = defaultString.length() - 2;

        for (int i = result.length - 1; i >= 0; i--) {
            int decaPos = 0;

            while (charPos >= 0 && defaultString.charAt(charPos) != ' ') {
                int digitValue = Character.getNumericValue(defaultString.charAt(charPos));
                result[i] += digitValue * Math.pow(10, decaPos);
                decaPos++;
                charPos--;
            }
            charPos--;
        }
        return result;
    }



    public static short[] HexaToDecimal(int decimalArrayLen, String defaultString) {
        short[] result = new short[decimalArrayLen];
        char[] hexaChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        int charPos = 0;

        for (int i = 0; i < result.length; i++) {
            int hexaPos = 1;

            while (defaultString.charAt(charPos) != ' ') {
                for (int j = 0; j < hexaChar.length; j++) {
                    char currentChar = Character.toLowerCase(defaultString.charAt(charPos));
                    
                    if (currentChar == hexaChar[j]) {
                        result[i] += j * Math.pow(16, hexaPos);
                        break;
                    }
                }
                hexaPos--;
                charPos++;
            }
            charPos++;
        }
        return result;
    }
}
