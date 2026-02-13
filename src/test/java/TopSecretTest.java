import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MockProgramController extends ProgramController{
    @Override
    public String displayFile(String fileNumber, String keyPath) {
        if (fileNumber.equals("01") && keyPath == null) {
            return "Mock file 01 contents (default key)";
        } else if (fileNumber.equals("01") && "key.txt".equals(keyPath)) {
            return "Mock file 01 contents (custom key)";
        } else {
            return "ERROR: Invalid file number.";
        }
    }

    @Override
    public String listFiles() {
        return "01 filea.txt\n02 fileb.txt\n03 filec.txt";
    }
}

public class TopSecretTest{
    /**************
     *MOCKITO TEST*
     **************/

    @Test
    void testNoArgumentsGiven(){
        TopSecret testInterface = new TopSecret(new MockProgramController());
        String output = testInterface.logic(new String[]{});
        assertTrue(output.contains("filea.txt"), "Should list available files");
    }

    @Test
    void testOneArgument(){
        TopSecret testInterface = new TopSecret(new MockProgramController());
        String output = testInterface.logic(new String[]{"01"});
        assertTrue(output.contains("default key"));
    }

    @Test
    void testTwoArgument(){
        TopSecret testInterface = new TopSecret(new MockProgramController());
        String output = testInterface.logic(new String[]{"01", "key.txt"});
        assertTrue(output.contains("custom key"));
    }

    @Test
    void testManyArgument(){
        TopSecret testInterface = new TopSecret(new MockProgramController());
        String output = testInterface.logic(new String[]{"01", "key.txt", "third argument"});
        assertTrue(output.contains("Invalid"));
    }


    /*****************
    *INTEGRATION TEST*
    ******************/

    @Test
    void stringArgument(){
        TopSecret testInterface = new TopSecret();
        String output = testInterface.logic(new String[]{"string"});
        assertTrue(output.contains("ERROR: File number"));
    }

    @Test
    void largeFileNumberArgument(){
        TopSecret testInterface = new TopSecret();
        String output = testInterface.logic(new String[]{"100"});
        assertTrue(output.contains("ERROR: Invalid"));
    }

    @Test
    void intSecondArgument(){
        TopSecret testInterface = new TopSecret();
        String output = testInterface.logic(new String[]{"01", "01"});
        assertTrue(output.contains("FileNotFound"));
    }

    @Test
    void noFilesAvailable(){
        TopSecret testInterface = new TopSecret();
        String output = testInterface.logic(new String[]{});
        assertTrue(output.contains("No files found."));
    }

    @Test
    void validWithKey(){
        TopSecret testInterface = new TopSecret();
        String output = testInterface.logic(new String[]{"01", "key.txt"});
        assertTrue(!output.isEmpty());
    }

    @Test
    void validWithoutKey(){
        TopSecret testInterface = new TopSecret();
        String output = testInterface.logic(new String[]{"01"});
        assertTrue(!output.isEmpty());
    }
}