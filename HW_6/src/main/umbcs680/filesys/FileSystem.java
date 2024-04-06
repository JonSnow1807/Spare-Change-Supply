package umbcs680.filesys;

import java.util.LinkedList;
import java.util.List;

public class FileSystem {
    private final LinkedList<Directory> rootDirs;

    // Private constructor
    private FileSystem() {
        rootDirs = new LinkedList<>();
    }

    // Helper class for the Initialization-on-demand Holder idiom
    private static class Holder {
        static final FileSystem INSTANCE = new FileSystem();
    }

    // Public method to get the singleton instance of FileSystem
    public static FileSystem getFileSystem() {
        return Holder.INSTANCE;
    }

    // Method to get the list of root directories as an unmodifiable list to preserve immutability
    public List<Directory> getRootDirs() {
        return List.copyOf(rootDirs); 
    }

    // Method to add a new root directory
    public synchronized void appendRootDir(Directory root) {
        if (root != null && !rootDirs.contains(root)) {
            rootDirs.add(root);
        }
    }

    // Method to remove a root directory
    public synchronized void removeRootDir(Directory root) {
        rootDirs.remove(root);
    }

    // Method to list all directories recursively
    public List<Directory> listAllDirectories() {
        List<Directory> allDirs = new LinkedList<>();
        for (Directory rootDir : rootDirs) {
            addAllDirectories(rootDir, allDirs);
        }
        return allDirs;
    }

    // Helper method to recursively add directories to a list
    private void addAllDirectories(Directory dir, List<Directory> allDirs) {
        allDirs.add(dir);
        for (Directory subDir : dir.getSubDirectories()) {
            addAllDirectories(subDir, allDirs);
        }
    }

    // Method to find a directory by name
    public Directory findDirectoryByName(String name) {
        for (Directory rootDir : rootDirs) {
            Directory found = findDirectoryByNameRecursive(rootDir, name);
            if (found != null) {
                return found;
            }
        }
        return null; // Return null if not found
    }

    // Helper method to recursively find a directory by name
    private Directory findDirectoryByNameRecursive(Directory dir, String name) {
        if (dir.getName().equals(name)) {
            return dir;
        }
        for (Directory subDir : dir.getSubDirectories()) {
            Directory found = findDirectoryByNameRecursive(subDir, name);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    // Method to calculate the total size used by all files
    public int calculateTotalSize() {
        int totalSize = 0;
        for (Directory rootDir : rootDirs) {
            totalSize += rootDir.getTotalSize();
        }
        return totalSize;
    }

}
