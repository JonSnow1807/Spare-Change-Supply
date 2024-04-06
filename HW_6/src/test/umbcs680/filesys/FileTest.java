package umbcs680.filesys.test;

import umbcs680.filesys.*;
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class FileTest {
    private FileSystem fs;
    private File readme, aJava, bJava, aTestJava, bTestJava;

    // Utility method to convert file properties to a string array for assertion checks
    private String[] fileInfoToArray(File file) {
        return new String[]{
            file.getName(),
            Integer.toString(file.getSize()),
            file.getCreationTime().toString(),
            Boolean.toString(file.isReadable()),
            Boolean.toString(file.isWritable())
        };
    }

    @BeforeEach
    void setUp() {
        fs = TestFixtureInitializer.createFileSystemWithStructure();

        Directory repo = fs.getRootDirs().get(0);
        Directory main = repo.getSubDirectories().get(0);
        Directory test = repo.getSubDirectories().get(1);

        readme = repo.getFiles().get(0);
        aJava = main.getFiles().get(0);
        bJava = main.getFiles().get(1);
        aTestJava = test.getFiles().get(0);
        bTestJava = test.getFiles().get(1);
    }

    @Test
    void verifyFileEquality() {
        File copyOfReadme = new File(readme.getParent(), readme.getName(), readme.getSize(), readme.getCreationTime(), readme.isReadable(), readme.isWritable());
        assertArrayEquals(fileInfoToArray(readme), fileInfoToArray(copyOfReadme), "File instances with the same properties should be equal");
    }

    @Test
    void verifyReadableWritableFlags() {
        assertTrue(readme.isReadable(), "readme should be readable");
        assertTrue(readme.isWritable(), "readme should be writable");
    }

    @Test
    void checkFileExtension() {
        assertEquals("md", readme.getExtension(), "File extension should be md for the readme file");
        assertEquals("java", aJava.getExtension(), "File extension should be java for the A.java file");
    }

    @Test
    void verifyFileSize() {
        assertEquals(100, readme.getSize(), "Size of the readme file should be 100 bytes");
    }

    @Test
    void testTotalSizeCalculation() {
        // Add files to a new directory to isolate total size calculation
        Directory newDir = new Directory(null, "newDir", LocalDateTime.now());
        newDir.appendChild(new File(newDir, "file1.txt", 100, LocalDateTime.now(), true, true));
        newDir.appendChild(new File(newDir, "file2.txt", 200, LocalDateTime.now(), true, true));

        assertEquals(300, newDir.getTotalSize(), "Total size of files in newDir should be 300 bytes");
    }

    @Test
    void removeFileFromDirectory() {
        assertTrue(aTestJava.getParent().removeChild(aTestJava), "ATest.java should be removed successfully");
        assertNull(aTestJava.getParent(), "ATest.java should not have a parent directory after being removed");
    }
}
