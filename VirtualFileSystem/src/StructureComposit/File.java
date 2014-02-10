/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StructureComposit;

import datomala_zadaca_1.ConsoleTextColor;
import java.nio.file.Path;
       
/**
 *
 * @author dex
 */
public class File implements AbstractFile {

    private int id;
    private int id_parent;
    private String name;
    private Path path;
    private long size;
    private String permissions;
    
    public File(int id, int id_parent, String name, Path path, long size, String permissions) {
        this.id = id;
        this.id_parent = id_parent;
        this.name = name;
        this.path = path;
        this.size = size;
        this.permissions = permissions;
    }

    @Override
    public void ls() {
        System.out.println(Integer.toString(id) + " " +CompositeFSTree.indent + ConsoleTextColor.ANSI_GREEN + name + ConsoleTextColor.ANSI_RESET + " (" + size + " B) | " + permissions);
    }

    public int getId() {
        return id;
    }

    public int getId_parent() {
        return id_parent;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public String getPermissions() {
        return permissions;
    }

    @Override
    public Path getPath() {
        return path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_parent(int id_parent) {
        this.id_parent = id_parent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
