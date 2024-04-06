package umbcs680.filesys;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Directory extends FSElement {
    private LinkedList<FSElement> children;

    public Directory(Directory parent, String name, LocalDateTime creationTime) {
        super(parent, name, 0, creationTime);
        children = new LinkedList<>();
    }

    public void appendChild(FSElement child) {
        if (child != null) {
            children.add(child);
            child.setParent(this);
        }
    }

    public boolean removeChild(FSElement child) {
        return children.remove(child);
    }

    public int countChildren() {
        return children.size();
    }

    public LinkedList<Directory> getSubDirectories() {
        LinkedList<Directory> subDirectories = new LinkedList<>();
        for (FSElement element : children) {
            if (element.isDirectory()) {
                subDirectories.add((Directory) element);
            }
        }
        return subDirectories;
    }

    public LinkedList<File> getFiles() {
        LinkedList<File> files = new LinkedList<>();
        for (FSElement element : children) {
            if (!element.isDirectory()) {
                files.add((File) element);
            }
        }
        return files;
    }

    // Method to find an FSElement by name
    public FSElement findElement(String name) {
        for (FSElement element : children) {
            if (element.getName().equals(name)) {
                return element;
            }
        }
        return null; // Return null if not found
    }

    // Method to get the directory structure as a string
    public String getDirectoryTree() {
        StringBuilder sb = new StringBuilder();
        buildDirectoryTree(sb, 0);
        return sb.toString();
    }

    // Helper method to build the directory tree
    private void buildDirectoryTree(StringBuilder sb, int level) {
        for (int i = 0; i < level; i++) {
            sb.append("  "); // indentation
        }
        sb.append(this.getName()).append("/\n");
        for (FSElement element : children) {
            if (element.isDirectory()) {
                ((Directory)element).buildDirectoryTree(sb, level + 1);
            } else {
                for (int i = 0; i <= level; i++) {
                    sb.append("  "); // indentation
                }
                sb.append(element.getName()).append("\n");
            }
        }
    }

    // Method to get the last modified time within this directory
    public LocalDateTime getLastModifiedTime() {
        LocalDateTime lastModified = this.getCreationTime();
        for (FSElement element : children) {
            if (element.isDirectory()) {
                LocalDateTime dirLastModified = ((Directory)element).getLastModifiedTime();
                if (dirLastModified.isAfter(lastModified)) {
                    lastModified = dirLastModified;
                }
            } else if (element.getCreationTime().isAfter(lastModified)) {
                lastModified = element.getCreationTime();
            }
        }
        return lastModified;
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public int getSize() {
        return 0; // Directory size is defined to be 0
    }

    @Override
    public int getTotalSize() {
        int totalSize = 0;
        for (FSElement element : children) {
            totalSize += element.getTotalSize();
        }
        return totalSize;
    }

    public LinkedList<FSElement> getChildren() {
        return children;
    }

}
