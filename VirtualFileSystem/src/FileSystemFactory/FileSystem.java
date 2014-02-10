/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package FileSystemFactory;

/**
 *
 * @author dex
 */
public abstract class FileSystem {
    
    public abstract String getFSName();
    public abstract void copy(int id, String newName);
    public abstract void move(int id, int id_parent);
    public abstract void delete(int id);
    public abstract void printTree();
    public abstract void printTree(int id);
    public abstract void printElement(int id);
    public abstract void printElementParents(int id);
    
}
