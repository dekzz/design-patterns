/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datomala_zadaca_2;

import java.util.Stack;

/**
 *
 * @author dex
 */
public class Invoker {
    Stack<Command> commands;
    Command startCommand;
    Command pauseCommand;
    Command resumeCommand;
    Command stopCommand;

    public Invoker() {
        commands = new Stack<Command>();
    }

    public void setStartCommand(Command startCommand) {
        this.startCommand = startCommand;
    }

    public void setPauseCommand(Command pauseCommand) {
        this.pauseCommand = pauseCommand;
    }

    public void setResumeCommand(Command resumeCommand) {
        this.resumeCommand = resumeCommand;
    }

    public void setStopCommand(Command stopCommand) {
        this.stopCommand = stopCommand;
    }

    public void executeStart() {
        startCommand.execute();
        commands.add(startCommand);
    }
    
    public void executePause() {
        pauseCommand.execute();
        commands.add(pauseCommand);
    }
    
    public void executeResume() {
        resumeCommand.execute();
        commands.add(resumeCommand);
    }
    
    public void executeStop() {
        stopCommand.execute();
        commands.add(stopCommand);
    }
    
    public void undoAll() {
        Command cmd = null;
        while (!commands.empty()) {
            cmd = commands.pop();
            cmd.undo();
        }
    }
    
    public void commit() {
        commands = new Stack<Command>();
    }
}
