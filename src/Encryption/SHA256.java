package Encryption;


/**
 * Re-creates the SHA256 algorithm, we're directly working on the decimal
 * representation of every value wether it's initial hash values, kConstants
 * or whatsoever.
 */
public class SHA256 {

    /* -------------------- Properties -------------------- */

    private final long[] K_CONSTANTS = { 
        1116352408, 1899447441, 3049323471L, 3921009573L, 961987163, 1508970993, 2453635748L, 2870763221L, 3624381080L, 310598401,
        607225278, 1426881987, 1925078388, 2162078206L, 2614888103L, 3248222580L, 3835390401L, 4022224774L, 264347078, 604807628,
        770255983, 1249150122, 1555081692, 1996064986, 2554220882L, 2821834349L, 2952996808L, 3210313671L, 3336571891L, 3584528711L,
        113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, 2177026350L, 2456956037L, 
        2730485921L, 2820302411L, 3259730800L, 3345764771L, 3516065817L, 3600352804L, 4094571909L, 275423344, 430227734, 506948616,
        659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, 2227730452L, 2361852424L, 
        2428436474L, 2756734187L, 3204031479L, 3329325298L 
    }; 
    private long[] HASH_VALUES = { 1779033703, 3144134277L, 1013904242, 2773480762L, 1359893119, 2600822924L, 528734635, 1541459225 };

    private short BLOCK_LENGTH = 56;
    private short[] DEFAULT_INPUT_BLOCK = new short[BLOCK_LENGTH];
    private long[] MESSAGE_SCHEDULE = new long[64];


    /* -------------------- Constructors -------------------- */


    /**
     * Initialize in three steps the default block:
     *  - It first checks if the input is higher than the default block length 
     * (512 - 64 bits -> array of 56 values, our inputs are all under 8 bits 
     * so it should work) and adds 64 (or 512 bits) to the default block.
     *  - Fills the default block with our input (each char from our input)
     * with the last one being 128 (10000000).
     *  - Then puts the default block into the message schedule as well as the
     * length of our input in the 16th row.
     * 
     * It then goes on to the algorithm, separated in two phase (DefineMessageSchedule -> UpdateHashValues).
     * 
     * 
     * @param inputAsciiValues Default input which is an array of decimal values representing our chars
     */
    public SHA256(short[] inputAsciiValues) {
        //Step 1
        while (inputAsciiValues.length >= BLOCK_LENGTH) {
            BLOCK_LENGTH += 64;
            DEFAULT_INPUT_BLOCK = new short[BLOCK_LENGTH];
        }

        //Step 2
        for (int i = 0; i < inputAsciiValues.length; i++) {
            DEFAULT_INPUT_BLOCK[i] = inputAsciiValues[i];
            DEFAULT_INPUT_BLOCK[i+1] = 128;
        }

        //Step 3
        int BYTE_BLOCK = 3;
        for (int i = 0; i < 14; i++) {
            MESSAGE_SCHEDULE[i] = (DEFAULT_INPUT_BLOCK[BYTE_BLOCK - 3] << 24) | (DEFAULT_INPUT_BLOCK[BYTE_BLOCK - 2] << 16) | (DEFAULT_INPUT_BLOCK[BYTE_BLOCK - 1] << 8) | DEFAULT_INPUT_BLOCK[BYTE_BLOCK];
            BYTE_BLOCK += 4;
        }
        MESSAGE_SCHEDULE[15] = 8 * inputAsciiValues.length;

        //Main algorithm
        this.DefineMessageSchedule(); //Phase 1
        this.UpdateHashValues();      //Phase 2
    }




    /* --------------- Public methods --------------- */

    /**
     * Stores each hexa values as decimals (determined by unpacking each hash values as 4 values of 8 bit
     * which forms when in hexadecimal the final sha256 hash)
     * 
     * @return  The hash values as decimals (will be converted to hexa in the 'Converter' class)
     */
    public short[] decimalEncoding() {
        short[] decimalSHA256 = new short[HASH_VALUES.length * 4];

        for (int i = 0; i < HASH_VALUES.length; i++) {
            decimalSHA256[(i*4)+3] = (short) (HASH_VALUES[i] & 0xff);
            decimalSHA256[(i*4)+2] = (short) ((HASH_VALUES[i] >> 8) & 0xff);
            decimalSHA256[(i*4)+1] = (short) ((HASH_VALUES[i] >> 16) & 0xff);
            decimalSHA256[(i*4)] = (short) ((HASH_VALUES[i] >> 24) & 0xff);
        }

        return decimalSHA256;
    }




    /* --------------- Private methods --------------- */

    private void DefineMessageSchedule() {
        for (int i = 16; i < MESSAGE_SCHEDULE.length; i++) {
            long sigma1 = ((MESSAGE_SCHEDULE[i-15] >>> 7) | (MESSAGE_SCHEDULE[i-15] << (Integer.SIZE - 7))) ^ ((MESSAGE_SCHEDULE[i-15] >>> 18) | (MESSAGE_SCHEDULE[i-15] << (Integer.SIZE - 18))) ^ 
                ((MESSAGE_SCHEDULE[i-15] >>> 3) | (MESSAGE_SCHEDULE[i-15] << (Integer.SIZE - 3)));
            long sigma2 = ((MESSAGE_SCHEDULE[i-2] >>> 17) | (MESSAGE_SCHEDULE[i-2] << (Integer.SIZE - 17))) ^ ((MESSAGE_SCHEDULE[i-2] >>> 19) | (MESSAGE_SCHEDULE[i-2] << (Integer.SIZE - 19))) ^ 
                ((MESSAGE_SCHEDULE[i-2] >>> 10) | (MESSAGE_SCHEDULE[i-2] << (Integer.SIZE - 10)));

            MESSAGE_SCHEDULE[i] = MESSAGE_SCHEDULE[i-16] + sigma1 + MESSAGE_SCHEDULE[i-7] + sigma2;
        }
    }



    private void UpdateHashValues() {
        for (int i = 0; i < K_CONSTANTS.length; i++) {
            long SIGMA0 = ((HASH_VALUES[0] >>> 2) | (HASH_VALUES[0] << (Integer.SIZE - 2))) ^ ((HASH_VALUES[0] >>> 13) | (HASH_VALUES[0] << (Integer.SIZE - 13))) ^ 
                ((HASH_VALUES[0] >>> 22) | (HASH_VALUES[0] << (Integer.SIZE - 22)));
            long SIGMA1 = ((HASH_VALUES[4] >>> 6) | (HASH_VALUES[4] << (Integer.SIZE - 6))) ^ ((HASH_VALUES[4] >>> 11) | (HASH_VALUES[4] << (Integer.SIZE - 11))) ^ 
                ((HASH_VALUES[4] >>> 25) | (HASH_VALUES[4] << (Integer.SIZE - 25)));
            long CHOICE = (HASH_VALUES[4] & HASH_VALUES[5]) ^ (~(HASH_VALUES[4]) & HASH_VALUES[6]);
            long MAJ = (HASH_VALUES[0] & HASH_VALUES[1]) ^ (HASH_VALUES[0] & HASH_VALUES[2]) ^ (HASH_VALUES[1] & HASH_VALUES[2]);

            long tmp1 = HASH_VALUES[7] + SIGMA1 + CHOICE + K_CONSTANTS[i] + MESSAGE_SCHEDULE[i], tmp2 = SIGMA0 + MAJ;
            
            HASH_VALUES[7] = HASH_VALUES[6]; 
            HASH_VALUES[6] = HASH_VALUES[5]; 
            HASH_VALUES[5] = HASH_VALUES[4]; 
            HASH_VALUES[4] = HASH_VALUES[3] + tmp1;
            HASH_VALUES[3] = HASH_VALUES[2]; 
            HASH_VALUES[2] = HASH_VALUES[1]; 
            HASH_VALUES[1] = HASH_VALUES[0]; 
            HASH_VALUES[0] = tmp1 + tmp2;
        }
    }
}
