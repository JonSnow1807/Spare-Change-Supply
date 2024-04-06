package umbcs680.filesys.test;

import umbcs680.filesys.*;
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;


class DirectoryTest {
    private FileSystem fs;
    private Directory root, src, main, test;
    private File readme, aJava, bJava, aTestJava, bTestJava;

    @BeforeEach
    void setUp() {
        fs = TestFixtureInitializer.createFileSystemWithStructure();
        root = fs.getRootDirs().get(0);
        src = root.getSubDirectories().get(0);
        main = src.getSubDirectories().get(0);
        test = src.getSubDirectories().get(1);
        readme = root.getFiles().get(0);
        aJava = main.getFiles().get(0);
        bJava = main.getFiles().get(1);
        aTestJava = test.getFiles().get(0);
        bTestJava = test.getFiles().get(1);
    }

    // Equality check for Directory instances
    @Test
    void verifyDirectoryEqualityRoot() {
        String[] expected = {"repo", "0", root.getCreationTime().toString()};
        String[] actual = dirToStringArray(root);
        assertArrayEquals(expected, actual, "Root directory details do not match expected values.");
    }

    @Test
    void verifyDirectoryEqualitySrc() {
        String[] expected = {"src", "0", src.getCreationTime().toString()};
        String[] actual = dirToStringArray(src);
        assertArrayEquals(expected, actual, "Source directory details do not match expected values.");
    }

    // Additional utility method for equality checks
    private String[] dirToStringArray(Directory dir) {
        return new String[]{
            dir.getName(),
            String.valueOf(dir.getSize()),
            dir.getCreationTime().toString()
        };
    }

    // Test to ensure the correct number of subdirectories
    @Test
    void subDirectoryCountIsCorrect() {
        assertEquals(2, src.getSubDirectories().size(), "Src should have 2 subdirectories.");
    }

    // Test to ensure the correct number of files in directories
    @Test
    void filesCountInMainIsCorrect() {
        assertEquals(2, main.getFiles().size(), "Main should have 2 files.");
    }

    // Test to find a specific file
    @Test
    void findSpecificFile() {
        assertEquals(aJava, main.findElement("A.java"), "Should find A.java in main directory.");
    }

    // Test for total size calculation
    @Test
    void verifyTotalSizeCalculation() {
        int expectedTotalSize = readme.getSize() + aJava.getSize() + bJava.getSize();
        assertEquals(expectedTotalSize, root.getTotalSize(), "Total size should be the sum of all file sizes.");
    }

    // Test for last modified time
    @Test
    void verifyLastModifiedTime() {
        LocalDateTime latest = LocalDateTime.now();
        // Simulating a later modified time for a file
        aJava.setCreationTime(latest);
        assertEquals(latest, root.getLastModifiedTime(), "Last modified time should match the latest time.");
    }

    // Test for removing a child
    @Test
    void removeChildFromDirectory() {
        assertTrue(main.removeChild(aJava), "A.java should be removed successfully from main.");
        assertFalse(main.getFiles().contains(aJava), "A.java should no longer be present in main.");
    }

    // Test for directory tree structure
    @Test
    void directoryTreeStructure() {
        String expectedStructure = "repo/\n  src/\n    main/\n      A.java\n      B.java\n    test/\n      ATest.java\n      BTest.java\n  readme.md\n";
        assertEquals(expectedStructure, root.getDirectoryTree(), "Directory structure string should match expected format.");
    }

 
}
