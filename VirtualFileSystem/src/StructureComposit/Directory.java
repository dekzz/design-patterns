/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StructureComposit;

import java.util.ArrayList;
import datomala_zadaca_1.ConsoleTextColor;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author dex
 */
public class Directory implements AbstractFile {

    private int id;
    private int id_parent;
    private String name;
    private Path path;
    private long size;
    private String permissions;
    private ArrayList<AbstractFile> m_files = new ArrayList();

    public Directory(int id, int id_parent, String name, Path path, long size, String permissions) {
        this.id = id;
        this.id_parent = id_parent;
        this.name = name;
        this.path = path;
        this.size = size;
        this.permissions = permissions;
    }

    public void add(AbstractFile obj) {
        m_files.add(obj);
    }
    
    public void remove(int id) {
         for (int i = 0; i < this.m_files.size(); i++) {
            AbstractFile obj = (AbstractFile) m_files.get(i);
            if (obj.getId() == id) {
                m_files.remove(obj);
            } else if (Files.isDirectory(obj.getPath())) {
                Directory d = (Directory) obj;
                d.remove(id);
            }
        }

    }

    @Override
    public void ls() {
        System.out.println(Integer.toString(id) + " " + CompositeFSTree.indent + ConsoleTextColor.ANSI_YELLOW + name + ConsoleTextColor.ANSI_RESET + " (" + size + " B) | " + permissions);
        CompositeFSTree.indent.append("   ");
        for (int i = 0; i < m_files.size(); ++i) {
            AbstractFile obj = (AbstractFile) m_files.get(i);
            obj.ls();
        }
        CompositeFSTree.indent.setLength(CompositeFSTree.indent.length() - 3);
    }
    
    public AbstractFile getFileDirWithID(Directory dir, int id) {
        AbstractFile res;
        
        for(AbstractFile f : dir.m_files){
            java.io.File file = new java.io.File(f.getPath().toString());
            
            if(file.isDirectory() && f.getId() != id){
                Directory d = (Directory)f;
                res = d.getFileDirWithID(d, id);
                if(res != null) {
                    return res;
                }
            } else if (f.getId() == id) {
                return f;
            }
        }
        return null;
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

    public ArrayList getM_files() {
        return m_files;
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

    public void setM_files(ArrayList<AbstractFile> m_files) {
        this.m_files = m_files;
    }

}
