import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MockProgramController extends ProgramController{
    @Override
    public String displayFile(String fileNumber, String keyPath) {
        if (fileNumber.equals("01") && keyPath == null) {
            return "Mock file 01 contents (default key)";
        } else if (fileNumber.equals("01") && "key.txt".equals(keyPath)) {
            return "Mock file 01 contents " + keyPath;
        } else if (!fileNumber.matches("\\d+")) {
            return "ERROR: File number must be a number.";
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
    @Test
    void testNoArgumentsGiven(){
        TopSecret testInterface = new TopSecret(new MockProgramController());
        String output = testInterface.logic(new String[]{});
        assertTrue(output.contains("filea.txt"), "Should list available files");

    }
}