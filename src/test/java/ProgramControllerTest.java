import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Manual Mock for FileHandler
class MockFileHandler extends FileHandler {
    private List<String> availableFiles;
    private String contentToReturn;
    private boolean throwExceptionOnList = false;
    private boolean throwExceptionOnRead = false;

    public void setAvailableFiles(List<String> files) {
        this.availableFiles = files;
    }

    public void setContentToReturn(String content) {
        this.contentToReturn = content;
    }

    public void setThrowExceptionOnList(boolean throwException) {
        this.throwExceptionOnList = throwException;
    }

    public void setThrowExceptionOnRead(boolean throwException) {
        this.throwExceptionOnRead = throwException;
    }

    @Override
    public List<String> getAvailableFiles() throws IOException {
        if (throwExceptionOnList) {
            throw new IOException("Mock IO Exception");
        }
        return availableFiles != null ? availableFiles : new ArrayList<>();
    }

    @Override
    public String readFile(String filename) throws IOException {
        if (throwExceptionOnRead) {
            throw new IOException("Mock IO Exception");
        }
        return contentToReturn;
    }
}

// Manual Mock for Cipher
class MockCipher extends Cipher {
    private String decipheredContent;
    
    public void setDecipheredContent(String content) {
        this.decipheredContent = content;
    }

    @Override
    public CipherKey loadKey(String keyPath) {
        return new CipherKey("mockPlain", "mockCipher");
    }

    @Override
    public String decipher(String input, CipherKey key) {
        return decipheredContent;
    }
}

class ProgramControllerTest {

    private ProgramController controller;
    private MockFileHandler mockFileHandler;
    private MockCipher mockCipher;

    @BeforeEach
    void setUp() {
        mockFileHandler = new MockFileHandler();
        mockCipher = new MockCipher();
        // Uses the dependency injection constructor we added
        controller = new ProgramController(mockFileHandler, mockCipher);
    }

    @Test
    void testListFiles_withMultipleFiles() {
        List<String> files = new ArrayList<>();
        files.add("filea.txt");
        files.add("fileb.txt");
        mockFileHandler.setAvailableFiles(files);

        String result = controller.listFiles();

        // Check if result contains formatted lines
        assertTrue(result.contains("01 filea.txt"));
        assertTrue(result.contains("02 fileb.txt"));
    }

    @Test
    void testListFiles_emptyList() {
        mockFileHandler.setAvailableFiles(new ArrayList<>());
        String result = controller.listFiles();
        assertEquals("No files found.", result);
    }

    @Test
    void testListFiles_handleException() {
        mockFileHandler.setThrowExceptionOnList(true);
        String result = controller.listFiles();
        assertEquals("ERROR: Unable to list files.", result);
    }

    @Test
    void testDisplayFile_noKey() {
        List<String> files = new ArrayList<>();
        files.add("filea.txt");
        mockFileHandler.setAvailableFiles(files);
        mockFileHandler.setContentToReturn("Raw Content");

        // Request file "1" (which maps to filea.txt) with null key
        String result = controller.displayFile("1", null);
        assertEquals("Raw Content", result);
    }

    @Test
    void testDisplayFile_withKey() {
        List<String> files = new ArrayList<>();
        files.add("filea.txt");
        mockFileHandler.setAvailableFiles(files);
        mockFileHandler.setContentToReturn("Raw Content");
        
        mockCipher.setDecipheredContent("Deciphered Content");

        // Request file "1" with a key path
        String result = controller.displayFile("1", "key.txt");
        assertEquals("Deciphered Content", result);
    }

    @Test
    void testDisplayFile_invalidFileNumber() {
        List<String> files = new ArrayList<>();
        files.add("filea.txt");
        mockFileHandler.setAvailableFiles(files);

        // "2" is out of bounds since we only have 1 file
        String result = controller.displayFile("2", null);
        assertEquals("ERROR: Invalid file number.", result);
    }

    @Test
    void testDisplayFile_nonNumericInput() {
        String result = controller.displayFile("abc", null);
        assertEquals("ERROR: File number must be a number.", result);
    }
}
