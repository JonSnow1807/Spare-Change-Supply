package umbcs680.filesys.test;

import umbcs680.filesys.*;
import java.time.LocalDateTime;

public class TestFixtureInitializer {

    // Static method to create and initialize the FileSystem with test directories and files
    public static FileSystem createFileSystemWithStructure() {
        // Initialize FileSystem
        FileSystem fs = FileSystem.getFileSystem();

        // Create directories and files
        Directory repo = new Directory(null, "repo", LocalDateTime.now());
        Directory src = new Directory(repo, "src", LocalDateTime.now());
        Directory main = new Directory(src, "main", LocalDateTime.now());
        Directory test = new Directory(src, "test", LocalDateTime.now());

        File readme = new File(repo, "readme.md", 100, LocalDateTime.now(), true, true);
        File aJava = new File(main, "A.java", 200, LocalDateTime.now(), true, false);
        File bJava = new File(main, "B.java", 150, LocalDateTime.now(), true, false);
        File aTestJava = new File(test, "ATest.java", 120, LocalDateTime.now(), true, false);
        File bTestJava = new File(test, "BTest.java", 80, LocalDateTime.now(), true, false);

        // Add files to their respective directories
        repo.appendChild(readme);
        main.appendChild(aJava);
        main.appendChild(bJava);
        test.appendChild(aTestJava);
        test.appendChild(bTestJava);

        // Add directories to FileSystem
        fs.appendRootDir(repo); 
        
        return fs; // Return the initialized FileSystem
    }
}
