package umbcs680.filesys;

import java.time.LocalDateTime;

// Abstract class for File System Elements
public abstract class FSElement {
    protected String name; // name of the file or directory
    protected int size; // size in bytes
    protected LocalDateTime creationTime; // creation date and time
    private Directory parent; // reference to the parent directory

    public FSElement(Directory parent, String name, int size, LocalDateTime creationTime) {
        this.parent = parent;
        this.name = name;
        this.size = 0; // Assuming the size of directory is 0 
        this.creationTime = creationTime != null ? creationTime : LocalDateTime.now();
    }

    // Public getters for name, size, and creation time
    public String getName() {
        return name;
    }

    // Size for a directory is zero
    public int getSize() {
        return isDirectory() ? 0 : size;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    // Protected setters for name and size
    protected void setName(String name) {
        this.name = name;
    }

    protected void setSize(int size) {
        this.size = size;
    }

    // Public setter and getter for the parent directory
    public void setParent(Directory parent) {
        this.parent = parent;
    }

    public Directory getParent() {
        return parent;
    }

    // Abstract method to check if this FSElement is a directory - implemented by subclasses
    public abstract boolean isDirectory();

    // Abstract method to get total size of the element - to be implemented by subclasses
    // If it's a directory, it should be the total size of all elements in the directory
    public abstract int getTotalSize();
}
