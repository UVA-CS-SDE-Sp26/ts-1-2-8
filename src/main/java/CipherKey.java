public class CipherKey {
    private final String plain;
    private final String cipher;


    public CipherKey(String plain, String cipher) {
        this.plain = plain;
        this.cipher = cipher;
    }


    public String getPlain() {
        return plain;
    }


    public String getCipher() {
        return cipher;
    }
}
