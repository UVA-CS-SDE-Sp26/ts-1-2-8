import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Cipher {


    public CipherKey loadKey(String keyPath) throws FileNotFoundException {
        File key = new File (keyPath);
        Scanner scanner = new Scanner(key);


        String plain = scanner.nextLine().trim();
        String cipher = scanner.nextLine().trim();
        scanner.close();


        if (plain.length() != cipher.length()) {
            throw new IllegalArgumentException("Lengths are different");
        }


        return new CipherKey(plain, cipher);
    }
    public String decipher(String input, CipherKey key) {
        String plain = key.getPlain();
        String cipher = key.getCipher();


        StringBuilder answer = new StringBuilder();


        for (char character : input.toCharArray()) {
            int position = cipher.indexOf(character);
            if (position >= 0) {
                answer.append(plain.charAt(position));
            } else {
                answer.append(character);
            }
        }


        return answer.toString();
    }
}
