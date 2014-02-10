/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datomala_zadaca_2;

import Competition.Competition;

/**
 *
 * @author dex
 */
public class ResumeCommand implements Command {
    
    Competition competition;

    public ResumeCommand(Competition competition) {
        this.competition = competition;
    }

    @Override
    public void execute() {
        competition.resumeCompetition();
    }

    @Override
    public void undo() {
        competition.pauseCompetition();
    }
    
}
