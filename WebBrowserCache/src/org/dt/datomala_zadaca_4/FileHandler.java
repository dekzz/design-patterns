
package org.dt.datomala_zadaca_4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHandler {
    
    public static Object loadFile(String path, String fileName) {
        File file = new File(path.concat(fileName));
        if(file.exists() && file.isFile()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(path.concat(fileName));
                ObjectInputStream ois = new ObjectInputStream(fis);
                Object input = ois.readObject();
                ois.close();
                fis.close();
                System.out.println("! Loaded file: '" + fileName + "'  +");
                return input;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (    IOException | ClassNotFoundException ex) {
                Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fis.close();
                } catch (IOException ex) {
                    Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
        
    }
    
    public static void saveFile(String path, String fileName, Object o) {
        File file = new File(path);
        if(!file.exists()) 
            file.mkdirs();
        
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path.concat(fileName));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(o);
            oos.close();
            fos.close();
            System.out.println("! Saved file: '" + fileName + "'  +");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void deleteFile(String path) {
        File file = new File(path);
        if(file.exists() && file.isFile()) {
            file.delete();
        }
    }
    
    public static int size(String path) {
        int totalSize = 0;
        File dir = new File(path);
        for(File f : dir.listFiles()){
            if(f.isFile())
                totalSize += f.length();
            else
                totalSize += size(f.getAbsolutePath());
        }
        return totalSize;
    }
    
    public static void clean(String path) {
        File file = new File(path);
        if(file.isDirectory()) {
            for(File f : file.listFiles())
                clean(f.getAbsolutePath());
        }
        file.delete();
    }
    
}
