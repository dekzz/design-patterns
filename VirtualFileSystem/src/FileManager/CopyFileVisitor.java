/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileManager;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.CONTINUE;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import static java.nio.file.StandardCopyOption.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * @author dex
 */
public class CopyFileVisitor extends SimpleFileVisitor<Path> {

    private Path source;
    private Path target;

    public CopyFileVisitor(Path source, Path target) {
        this.source = source;
        this.target = target;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        //System.out.println(source);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        CopyOption[] opt = new CopyOption[]{COPY_ATTRIBUTES};
        System.out.println("Source Directory " + dir);
        Path newDirectory = target.resolve(source.relativize(dir));
        System.out.println("Target Directory " + newDirectory);
        try {
            System.out.println("Creating directory tree " + Files.copy(dir, newDirectory, opt));
        } catch (FileAlreadyExistsException x) {
        } catch (IOException x) {
            return FileVisitResult.SKIP_SUBTREE;
        }

        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        //System.out.println("results");
        System.out.println("Copying file:" + file);
        copy(file, target.resolve(source.relativize(file)));
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return CONTINUE;
    }
    
    public static void copy(Path source,Path target) throws IOException{
        CopyOption[] options = new CopyOption[]{COPY_ATTRIBUTES};
        System.out.println("Copied file "+Files.copy(source, target, options));
    }
}