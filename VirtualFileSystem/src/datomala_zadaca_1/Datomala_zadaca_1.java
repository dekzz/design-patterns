/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datomala_zadaca_1;

import FileSystemFactory.FSFactory;
import FileSystemFactory.FileSystem;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author dex
 */
public class Datomala_zadaca_1 {

    public static int globalID = 1;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        FSFactory fsFactory = FSFactory.getFactory();
        
        String commandRegex = "^([a-z]+ ?[a-zA-Z\\-]* ?){1,1}([0-9]* ?){0,1}([A-Za-z0-9]*){0,1}$"; 
        String command;
        String param1;
        String param2;
        Pattern pattern;
        Matcher match;

        if (args.length == 1) {
            FileSystem fileSystem = fsFactory.createFileSystem(args[0]);
            String FSname = fileSystem.getFSName();
            Datomala_zadaca_1.help();
            while(true) {
                System.out.print("[" + ConsoleTextColor.ANSI_BLUE + FSname + ConsoleTextColor.ANSI_RESET + "]" + " > ");
                String cmd = System.console().readLine();
                pattern = Pattern.compile(commandRegex);
                match = pattern.matcher(cmd);
                if(match.matches()) {
                    command = match.group(1).trim();
                    param1 = match.group(2).trim();
                    param2 = match.group(3).trim();
                } else {
                    command = "";
                    param1 = "";
                    param2 = "";
                }
                
                switch(command){
                    case "cp":
                        if(!param1.isEmpty() && !param2.isEmpty())
                            fileSystem.copy(Integer.parseInt(param1), param2);
                        else
                            System.out.println("Incorrect parameters: enter file ID (number) and new name (text)!");
                        break;
                    case "mv":
                        if(!param1.isEmpty() && !param2.isEmpty())
                            fileSystem.move(Integer.parseInt(param1), Integer.parseInt(param2));
                        else
                            System.out.println("Incorrect parameters: enter file ID (number) and new destination ID (number)!");
                        break;
                    case "rm":
                        if(!param1.isEmpty())
                            fileSystem.delete(Integer.parseInt(param1));
                        else
                            System.out.println("Incorrect parameters: enter file ID (number)!");
                        break;
                    case "ls":
                        fileSystem.printTree();
                        break;
                    case "ls -l":
                        if(!param1.isEmpty())
                            fileSystem.printTree(Integer.parseInt(param1));
                        else
                            System.out.println("Incorrect parameters: enter file ID (number)!");
                        break;
                    case "ls -e":
                        if(!param1.isEmpty())
                            fileSystem.printElement(Integer.parseInt(param1));
                        else
                            System.out.println("Incorrect parameters: enter file ID (number)!");
                        break;
                    case "ls -P":
                        if(!param1.isEmpty())
                            fileSystem.printElementParents(Integer.parseInt(param1));
                        else
                            System.out.println("Incorrect parameters: enter file ID (number)!");
                        break;
                    case "help":
                        help();
                        break;
                    case "exit":
                        return;
                    case "":
                        break;
                    default:
                        System.out.println(ConsoleTextColor.ANSI_RED + "!" + ConsoleTextColor.ANSI_RESET+ " Unknown command");
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid number of arguments!");
        }

    }
      
    public static void help() {
        System.out.println(ConsoleTextColor.ANSI_RED + "\n|========================================================================================================================|\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_CYAN  + " List of commands:" + ConsoleTextColor.ANSI_RED + "                                                                                                      |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " help              ->  display help menu" + ConsoleTextColor.ANSI_RED + "                                                                                |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " cp id newName     ->  copy file/directory with 'id' identificator and sets to it a new name 'newName'" + ConsoleTextColor.ANSI_RED + "                  |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " mv id1 id2        ->  move file/directory with 'id1' identificator to another directory with identificator 'id2'" + ConsoleTextColor.ANSI_RED + "       |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " rm id             ->  delete file/directory with 'id' identificator" + ConsoleTextColor.ANSI_RED + "                                                    |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " ls                ->  print file system tree" + ConsoleTextColor.ANSI_RED + "                                                                           |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " ls -l id          ->  print tree of element with 'id' identificator" + ConsoleTextColor.ANSI_RED + "                                                    |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " ls -e id          ->  print details of element with 'id' identificator" + ConsoleTextColor.ANSI_RED + "                                                 |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " ls -P id          ->  print parent list of element with 'id' identificator" + ConsoleTextColor.ANSI_RED + "                                             |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " exit              ->  exit application" + ConsoleTextColor.ANSI_RED + "                                                                                 |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|========================================================================================================================| \n" + ConsoleTextColor.ANSI_RESET);
    }

}
