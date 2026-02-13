import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


class CipherTest {

    private final Cipher cipher = new Cipher();

    @Test
    void loadKey(@TempDir Path tempDir) throws IOException {

        Path keyFile = tempDir.resolve("key.txt");
        Files.writeString(keyFile, "ABC\nXYZ");

        CipherKey key = cipher.loadKey(keyFile.toString());

        assertEquals("ABC", key.getPlain());
        assertEquals("XYZ", key.getCipher());
    }
    @Test
    void decipher() {
        CipherKey key = new CipherKey("ABC", "XYZ");

        String result = cipher.decipher("XYZA! ", key);

        assertEquals("ABCA! ", result);
    }
}