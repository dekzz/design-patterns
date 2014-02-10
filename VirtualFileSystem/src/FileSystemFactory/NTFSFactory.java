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
public class NTFSFactory extends FSFactory {

    @Override
    public FileSystem createFileSystem(String root) {
        return new NTFSFileSystem(root);
    }

}
