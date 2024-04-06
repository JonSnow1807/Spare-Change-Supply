package umbcs680.filesys.test;

import umbcs680.filesys.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.time.LocalDateTime;


class FileSystemTest {
    private FileSystem fs;

    @BeforeEach
    void setUp() {
        // Initialize the FileSystem with the test structure 
        fs = TestFixtureInitializer.createFileSystemWithStructure();
    }

    @Test
    void singletonTest() {
        FileSystem anotherFs = FileSystem.getFileSystem();
        assertSame(fs, anotherFs, "FileSystem should be a singleton, only one instance should exist.");
    }

    @Test
    void appendAndRemoveRootDirTest() {
        Directory newRoot = new Directory(null, "newRoot", LocalDateTime.now());
        fs.appendRootDir(newRoot);
        assertTrue(fs.getRootDirs().contains(newRoot), "New root directory should be added.");

        fs.removeRootDir(newRoot);
        assertFalse(fs.getRootDirs().contains(newRoot), "Root directory should be removed.");
    }

    @Test
    void getRootDirsTest() {
        List<Directory> roots = fs.getRootDirs();
        assertNotNull(roots, "getRootDirs should not return null.");
        assertFalse(roots.isEmpty(), "getRootDirs should not return an empty list.");
    }

    @Test
    void listAllDirectoriesTest() {
        List<Directory> allDirs = fs.listAllDirectories();
        assertFalse(allDirs.isEmpty(), "listAllDirectories should not return an empty list.");
    }

    @Test
    void findDirectoryByNameTest() {
        Directory foundDir = fs.findDirectoryByName("src");
        assertNotNull(foundDir, "findDirectoryByName should return a directory when it exists.");
        assertEquals("src", foundDir.getName(), "The found directory should have the name 'src'.");
    }

    @Test
    void calculateTotalSizeTest() {
        int totalSize = fs.calculateTotalSize();
        assertNotEquals(0, totalSize, "calculateTotalSize should return a size greater than 0.");
    }

    @Test
    void findNonExistingDirectoryByNameTest() {
        Directory foundDir = fs.findDirectoryByName("nonExistingDir");
        assertNull(foundDir, "findDirectoryByName should return null for a non-existing directory.");
    }

    @Test
    void totalSizeAfterRemovingDirectoryTest() {
        int originalTotalSize = fs.calculateTotalSize();
        Directory dirToRemove = fs.getRootDirs().get(0).getSubDirectories().get(0);
        fs.getRootDirs().get(0).removeChild(dirToRemove);
        int newTotalSize = fs.calculateTotalSize();
        assertTrue(originalTotalSize > newTotalSize, "Total size should decrease after removing a directory.");
    }
}
