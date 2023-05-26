package Encryption;

import BaseConverter.*;


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
     * @param algorithm
     * @return          the corresponding encrypted string
     */
    public StringBuilder Encrypt(String algorithm) {
        this.result = new StringBuilder("");
        switch (algorithm) {
            case "-s", "--sha256":
                this.sha256();
                return this.result;
            case "-c", "--caesar":
                this.result.append("Caesar: At a later date... :P");
                return this.result; 
            default:
                System.out.println("Error: Invalid base");
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



    private void caesar() {
        //Maybe :D
    }
}
