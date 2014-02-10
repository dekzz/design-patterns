/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package StructureComposit;

import java.nio.file.Path;

/**
 *
 * @author dex
 */
public interface AbstractFile {

    public void ls();
    public int getId();
    public int getId_parent();
    public String getName();
    public Path getPath();
    public long getSize();
    public String getPermissions();
    public void setId(int id);
    public void setId_parent(int id_parent);
    public void setName(String name);
    public void setPath(Path path);
    public void setSize(long size);
    public void setPermissions(String permission);
    
}
