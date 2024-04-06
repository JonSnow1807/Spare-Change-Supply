package umbcs680.filesys;

import java.time.LocalDateTime;

// File class extends FSElement
public class File extends FSElement {

    private boolean readable;
    private boolean writable;

    // Constructor
    public File(Directory parent, String name, int size, LocalDateTime creationTime, boolean readable, boolean writable) {
        super(parent, name, size, creationTime);
        this.readable = readable;
        this.writable = writable;
    }

    // Implement the isDirectory method to return false for File
    @Override
    public boolean isDirectory() {
        return false;
    }

    // Implement the getTotalSize method to return the size of the file
    @Override
    public int getTotalSize() {
        return getSize();
    }

    // Method to check if the file is readable
    public boolean isReadable() {
        return readable;
    }

    // Method to check if the file is writable
    public boolean isWritable() {
        return writable;
    }

    // Method to retrieve the file extension
    public String getExtension() {
        int lastIndexOfDot = getName().lastIndexOf(".");
        return lastIndexOfDot != -1 ? getName().substring(lastIndexOfDot + 1) : "";
    }
    
    public void setCreationTime(LocalDateTime newCreationTime) {
        this.creationTime = newCreationTime;
    }


}
