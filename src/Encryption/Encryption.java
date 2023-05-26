package Encryption;

import BaseConverter.*;


/**
 * Instantiate an object which can crypt via different algorithms
 */
public class Encryption implements Bases {

    /* -------------------- Properties -------------------- */

    private final short[] DECIMAL_VALUES;
    private StringBuilder result;


    /* -------------------- Constructors -------------------- */

    
    /**
     * Keeps the default input which every algorithm will be based on.
     * 
     * @param DEFAULT_VALUES Default input as decimal values (ascii values)
     */
    public Encryption(short[] DEFAULT_VALUES) {
        this.DECIMAL_VALUES = DEFAULT_VALUES;
    }




    /* --------------- Public methods --------------- */

    /**
     * Performs the corresponding algorithm based on the given argument
     * 
     * 
     * @param algorithm Is the requested algorithm to crypt our String
     * @param key       Key is used only if we're using the caesar algorithm, it defines the offset
     * @return          the corresponding encrypted string
     */
    public StringBuilder Encrypt(String algorithm, String key) {
        this.result = new StringBuilder("");
        switch (algorithm) {
            case "-s", "--sha256":
                this.sha256();
                return this.result;
            case "-c", "--caesar":
                int keyValue = Integer.valueOf(key.substring(1, key.length())); 
                this.caesar(keyValue);
                return this.result; 
            default:
                System.out.println("Error: Invalid algorithm");
                System.exit(3);
                return null;
        }
    }




    /* --------------- Private methods --------------- */

    private void sha256() {
        StringBuilder stringSHA256 = new StringBuilder("");
        SHA256 encrypt = new SHA256(this.DECIMAL_VALUES);

        stringSHA256 = Bases.Base16(encrypt.decimalEncoding());
        
        for (int i = 0; i < stringSHA256.length(); i++) {
            if (stringSHA256.charAt(i) == ' ') {
                stringSHA256.replace(i, i+1, "");
            }
        }

        this.result.append("SHA256 Hash: " + stringSHA256);
    }



    private void caesar(int key) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String numeric = "0123456789";
        String caesarHash = "";

        for (int i = 0; i < this.DECIMAL_VALUES.length; i++) {
            char defaultChar = (char) this.DECIMAL_VALUES[i];
            char offsetChar;

            if (Character.isDigit(defaultChar)) {
                while (key < 0) { key += numeric.length(); }
                offsetChar = numeric.charAt((numeric.indexOf(defaultChar) + key) % numeric.length());
            }
            else {
                while (key < 0) { key += alphabet.length(); }
                offsetChar = alphabet.charAt((alphabet.indexOf(Character.toLowerCase(defaultChar)) + key) % alphabet.length());
            }

            if (Character.isUpperCase(defaultChar)) { offsetChar = Character.toUpperCase(offsetChar); }
            else if (defaultChar == ' ') { offsetChar = ' '; }
            caesarHash += offsetChar;
        }

        this.result.append("Caesar Hash [-k: " + key + "]: " + caesarHash);
    }
}
