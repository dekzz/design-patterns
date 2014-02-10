/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Competitor;

import datomala_zadaca_2.ConsoleTextColor;

/**
 *
 * @author dex
 */
public class WeakTeamState implements State{

    @Override
    public void strengthen(Team team) {
        team.setState(new NormalTeamState());
        team.setChanceCount(0);
    }

    @Override
    public void weaken(Team team) {
        team.setChanceCount(team.getChanceCount()+1);
    }

    @Override
    public void obliterate(Team team) {
        team.setState(new DisqualifiedTeamState());
        System.out.println(ConsoleTextColor.ANSI_RED + "X" + ConsoleTextColor.ANSI_RESET + "Team '" + team.getName() + "' is out of competition, ended on position: " + team.getPosition() + " with " + team.getPoints() + " points.");
    }

    
    
}
