import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CipherKeyTest {

    @Test
    void getPlain() {
        CipherKey key = new CipherKey("ABC", "XYZ");
        assertEquals("ABC", key.getPlain());
    }

    @Test
    void getCipher() {
        CipherKey key = new CipherKey("ABC", "XYZ");
        assertEquals("XYZ", key.getCipher());
    }
}