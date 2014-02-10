/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StructureComposit;

import datomala_zadaca_1.Datomala_zadaca_1;
import java.nio.file.Paths;

/**
 *
 * @author dex
 */
public class CompositeFSTree {

    public static StringBuffer indent = new StringBuffer();

    public static void addTree(String path, Directory parent) {
        java.io.File dir = new java.io.File(path);
        java.io.File[] children = dir.listFiles();

        for (java.io.File child : children) {
            if (child.isDirectory()) {
                Directory newDir = new Directory(++Datomala_zadaca_1.globalID, parent.getId(), child.getName(), Paths.get(child.getAbsolutePath()), folderSize(child), getPermissions(child));
                parent.add(newDir);
                addTree(child.getAbsolutePath(), newDir);
            } else if (child.isFile()) {
                File file = new File(++Datomala_zadaca_1.globalID, parent.getId(), child.getName(), Paths.get(child.getAbsolutePath()), child.length(), getPermissions(child));
                parent.add(file);
            }
        }
    }

    public static long folderSize(java.io.File directory) {
        long length = 0;
        for (java.io.File file : directory.listFiles()) {
            if (file.isFile()) {
                length += file.length();
            } else {
                length += folderSize(file);
            }
        }
        return length;
    }

    public static String getPermissions(java.io.File file) {
        String permissions = "";
        if (file.canRead()) {
            permissions += "r";
        } else {
            permissions += "-";
        }
        if (file.canWrite()) {
            permissions += "w";
        } else {
            permissions += "-";
        }
        if (file.canExecute()) {
            permissions += "x";
        } else {
            permissions += "-";
        }
        return permissions;
    }

}
