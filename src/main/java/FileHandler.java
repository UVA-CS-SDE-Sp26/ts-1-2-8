import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHandler {

    private final Path dataDir;

    // Default constructor: expects ./data
    public FileHandler() {
        this(Paths.get("data"));
    }

    // Constructor for testing (lets tests inject a temp data folder)
    public FileHandler(Path dataDir) {
        this.dataDir = dataDir;
    }

    // Returns sorted .txt filenames (NOT full paths)
    public List<String> getAvailableFiles() throws IOException {
        if (!Files.exists(dataDir) || !Files.isDirectory(dataDir)) {
            return List.of(); // let ProgramController say "No files found."
        }

        try (Stream<Path> stream = Files.list(dataDir)) {
            return stream
                    .filter(Files::isRegularFile)
                    .map(p -> p.getFileName().toString())
                    .filter(name -> name.toLowerCase().endsWith(".txt"))
                    .sorted(Comparator.naturalOrder())
                    .collect(Collectors.toList());
        }
    }

    // Reads a file by filename inside dataDir
    public String readFile(String filename) throws IOException {
        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("Filename is blank.");
        }

        Path target = dataDir.resolve(filename).normalize();

        // Prevent path traversal like "../../secret"
        if (!target.startsWith(dataDir.normalize())) {
            throw new SecurityException("Invalid filename/path.");
        }

        if (!Files.exists(target) || !Files.isRegularFile(target)) {
            throw new NoSuchFileException("File not found: " + filename);
        }

        return Files.readString(target, StandardCharsets.UTF_8);
    }
}
