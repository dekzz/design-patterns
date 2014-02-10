/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileSystemFactory;

import StructureComposit.AbstractFile;
import StructureComposit.CompositeFSTree;
import StructureComposit.Directory;
import FileManager.DeleteFileVisitor;
import FileManager.CopyFileVisitor;
import datomala_zadaca_1.ConsoleTextColor;
import datomala_zadaca_1.Datomala_zadaca_1;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dex
 */
public class EXT4FileSystem extends FileSystem {

    File dir;
    Directory rootDir;

    public EXT4FileSystem(String rootPath) {
        dir = new File(rootPath);
        rootDir = new Directory(Datomala_zadaca_1.globalID, 0, dir.getName(), Paths.get(rootPath), StructureComposit.CompositeFSTree.folderSize(dir), StructureComposit.CompositeFSTree.getPermissions(dir));
        StructureComposit.CompositeFSTree.addTree(rootPath, rootDir);
    }

    @Override
    public String getFSName() {
        return "EXT4";
    }
    
    @Override
    public void copy(int id, String newName) {
        AbstractFile file = rootDir.getFileDirWithID(rootDir, id);
        if(file != null) {
            Path filePath = file.getPath();
            try {
                Files.walkFileTree(filePath, new CopyFileVisitor(filePath, Paths.get(filePath.getParent().resolve(newName).toString())));
                Directory parentDir;
                AbstractFile copy;
                if(Files.isDirectory(filePath)){
                    copy = new Directory(++Datomala_zadaca_1.globalID, file.getId_parent(), newName, filePath.getParent().resolve(newName), file.getSize(), file.getPermissions());
                } else {
                    copy = new StructureComposit.File(++Datomala_zadaca_1.globalID, file.getId_parent(), newName, filePath.getParent().resolve(newName), file.getSize(), file.getPermissions());
                }
                int parentID = file.getId_parent();
                if(parentID <= rootDir.getId()){
                    parentDir = rootDir;
                } else {
                    parentDir = (Directory)rootDir.getFileDirWithID(rootDir, parentID);
                }
                parentDir.add(copy);
                System.out.println("File: '" + filePath.getFileName() + "' copied as: '" + newName + "'!");
            } catch (IOException ex) {
                Logger.getLogger(EXT4FileSystem.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Failed to copy file: '" + filePath.getFileName() + "' as '" + newName + "'!");
            }
        } else {
            System.out.println(ConsoleTextColor.ANSI_RED + "!" + ConsoleTextColor.ANSI_RESET + " Invalid parameter(s)");
        }
    }

    @Override
    public void move(int id, int id_parent) {
        AbstractFile parent = rootDir.getFileDirWithID(rootDir, id_parent);
        if(id_parent <= rootDir.getId()){
            parent = rootDir;
        }
        if(parent != null) {
            if(Files.isDirectory(parent.getPath())) {
                Directory parentDir = (Directory)parent;
                AbstractFile child = rootDir.getFileDirWithID(rootDir, id);
                Path source = child.getPath();
                Path destination = parentDir.getPath();
                if(child != null) {
                    try {
                        Files.move(source, destination.resolve(source.getFileName()));                
                        rootDir.remove(child.getId());
                        child.setId_parent(id_parent);
                        child.setPath(destination.resolve(source.getFileName()));
                        parentDir.add(child);
                        System.out.println("Moved file: '" + source.getFileName() + "' to new location: '" + destination.toString() + "'!");
                    } catch (IOException ex) {
                        Logger.getLogger(EXT4FileSystem.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("Failed to move file: '" + source.getFileName() + "' to new location: '" + destination.toString() + "'!");
                    }
                }
            } else {
                System.out.println("File: '" + parent.getPath() + "' with ID: '" + id_parent + "' is not a directory!");
            }
        } else {
            System.out.println(ConsoleTextColor.ANSI_RED + "!" + ConsoleTextColor.ANSI_RESET + " Invalid parameter(s)");
        }
    }

    @Override
    public void delete(int id) {
        AbstractFile file = rootDir.getFileDirWithID(rootDir, id);
        if(file != null) {
            Path filePath = file.getPath();
            try {
                Files.walkFileTree(filePath, new DeleteFileVisitor());
                rootDir.remove(file.getId());
                System.out.println("File/directory: '" + filePath.getFileName() + "' deleted!");
            } catch (IOException ex) {
                Logger.getLogger(EXT4FileSystem.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Failed to delete '" + filePath.getFileName() + "'!");
            }
        } else {
            System.out.println(ConsoleTextColor.ANSI_RED + "!" + ConsoleTextColor.ANSI_RESET + " Invalid parameter(s)");
        }
    }

    @Override
    public void printTree() {
        rootDir.ls();
    }

    @Override
    public void printTree(int id) {
        AbstractFile tree = rootDir.getFileDirWithID(rootDir, id);
        if(tree != null) {
            tree.ls();
        } else {
            System.out.println(ConsoleTextColor.ANSI_RED + "!" + ConsoleTextColor.ANSI_RESET + " Invalid parameter(s)");
        }
    }
    
    @Override
    public void printElement(int id) {
        AbstractFile node = rootDir.getFileDirWithID(rootDir, id);
        String color;
        String nrChildren = " | ";
        Directory dir;
        if(node != null) {
            if(Files.isDirectory(node.getPath())) {
                color = ConsoleTextColor.ANSI_YELLOW;
                dir = (Directory)node;
                nrChildren += String.valueOf(dir.getM_files().size()) + " sub element(s)";
            } else {
                color = ConsoleTextColor.ANSI_GREEN;
            }
            System.out.println(node.getId() + " " + CompositeFSTree.indent + color + node.getName() + ConsoleTextColor.ANSI_RESET + " (" + node.getSize() + " B) | " + node.getPermissions() + nrChildren);
        } else {
            System.out.println(ConsoleTextColor.ANSI_RED + "!" + ConsoleTextColor.ANSI_RESET + " Invalid parameter(s)");
        }
    }

    @Override
    public void printElementParents(int id) {
        AbstractFile obj = rootDir.getFileDirWithID(rootDir, id);
        if(id == rootDir.getId()){            
            obj = rootDir;
        } else if (id < rootDir.getId()) {
            return;
        }
        if(obj != null) {
            String color;
            if(Files.isDirectory(obj.getPath())) {
                color = ConsoleTextColor.ANSI_YELLOW;
            } else {
               color = ConsoleTextColor.ANSI_GREEN;
            }
            System.out.println(obj.getId() + " " + CompositeFSTree.indent + color + obj.getName() + ConsoleTextColor.ANSI_RESET + " (" + obj.getSize() + " B) | " + obj.getPermissions());
            CompositeFSTree.indent.append("   ");
            printElementParents(obj.getId_parent());
            CompositeFSTree.indent.setLength(CompositeFSTree.indent.length() - 3);
        } else {
            System.out.println(ConsoleTextColor.ANSI_RED + "!" + ConsoleTextColor.ANSI_RESET + " Invalid parameter(s)");
        }
    }

}
