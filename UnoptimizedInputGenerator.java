import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

class UnoptimizedInputGenerator {

    private static final Random rand = new Random(); // randomness not really important

    private static final int WORD_LENGHT = 8;
    private static final int NR_OF_WORDS = 5_000_000;
    private static final int NR_OF_CHARACTERS = 62;

    private static final int[] CHAR_CODES = new int[NR_OF_CHARACTERS];

    public static void main(String[] args) throws IOException {

        int index = 0;

        // numbers
        index = fillArray(CHAR_CODES, index, 0x30, 0x39);

        // uppercase letters
        index = fillArray(CHAR_CODES, index, 0x41, 0x5a);

        // lowercase letters
        index = fillArray(CHAR_CODES, index, 0x61, 0x7a);

        // System.out.println(CHAR_CODES);
        // for (int i = 0; i < CHAR_CODES.length; i++) {
            // System.out.print((char)CHAR_CODES[i]);
        // }

        try (FileWriter fw = new FileWriter(args[0]);) {

            char[] word = new char[WORD_LENGHT + 1];
            word[WORD_LENGHT] = '\n';

            for (int j = 0; j < NR_OF_WORDS; j++) {
                for (int i = 0; i < WORD_LENGHT; i++) {
                    word[i] = generateChar();
                }
                fw.write(word);
            }

            fw.flush();
        }
    }

    private static int fillArray(int[] array, int startIndex, int startValue, int endValue) {
        for (int i = startValue; i <= endValue; i++) {
            array[startIndex] = i;
            startIndex++;
        }
        return startIndex;
    }

    private static char generateChar() {
        int n = rand.nextInt(NR_OF_CHARACTERS);
        // System.out.println(n);
        return (char) CHAR_CODES[n];
    }
}