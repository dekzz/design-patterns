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
public abstract class FSFactory {
    
    public static FSFactory getFactory() {
        
        FATFactory FAT = null;
        NTFSFactory NTFS = null;
        EXT4Factory EXT4 = null;
        
        String FStype = System.getenv("DS_TIP"); // FS_TYPE !!1
        switch (FStype) {
            case "FAT":
                if (FAT == null) synchronized(FSFactory.class) {
                    if (FAT == null)
                        FAT = new FATFactory();
                }
                return FAT;
            case "NTFS":
                if (NTFS == null) synchronized(FSFactory.class) {
                    if (NTFS == null)
                        NTFS = new NTFSFactory();
                }
                return NTFS;
            case "EXT4":
                if (EXT4 == null) synchronized(FSFactory.class) {
                    if (EXT4 == null)
                        EXT4 = new EXT4Factory();
                }
                return EXT4;
            default:
                throw new IllegalArgumentException("Unsupported File System!");
        }
    }
    
    public abstract FileSystem createFileSystem(String root);
}
