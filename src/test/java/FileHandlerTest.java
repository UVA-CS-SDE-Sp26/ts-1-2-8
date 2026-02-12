import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {

    @TempDir
    Path tempDir;

    @Test
    void getAvailableFiles_returnsOnlyTxt_sorted() throws Exception {
        Path data = tempDir.resolve("data");
        Files.createDirectories(data);

        Files.writeString(data.resolve("b.txt"), "B");
        Files.writeString(data.resolve("a.txt"), "A");
        Files.writeString(data.resolve("ignore.md"), "no");

        FileHandler fh = new FileHandler(data);
        List<String> files = fh.getAvailableFiles();

        assertEquals(List.of("a.txt", "b.txt"), files);
    }

    @Test
    void getAvailableFiles_missingFolder_returnsEmptyList() throws Exception {
        Path missing = tempDir.resolve("data"); // don't create it
        FileHandler fh = new FileHandler(missing);

        assertEquals(List.of(), fh.getAvailableFiles());
    }

    @Test
    void readFile_readsFullContents() throws Exception {
        Path data = tempDir.resolve("data");
        Files.createDirectories(data);

        Files.writeString(data.resolve("a.txt"), "Hello\nWorld\n");
        FileHandler fh = new FileHandler(data);

        assertEquals("Hello\nWorld\n", fh.readFile("a.txt"));
    }

    @Test
    void readFile_missingFile_throws() throws Exception {
        Path data = tempDir.resolve("data");
        Files.createDirectories(data);

        FileHandler fh = new FileHandler(data);
        assertThrows(Exception.class, () -> fh.readFile("nope.txt"));
    }

    @Test
    void readFile_blocksPathTraversal() throws Exception {
        Path data = tempDir.resolve("data");
        Files.createDirectories(data);

        FileHandler fh = new FileHandler(data);
        assertThrows(SecurityException.class, () -> fh.readFile("../evil.txt"));
    }
}
