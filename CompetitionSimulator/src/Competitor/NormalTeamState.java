/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Competitor;

/**
 *
 * @author dex
 */
public class NormalTeamState implements State{
    
    @Override
    public void strengthen(Team team) {
        // IT'S OVER 9000 !!1
    }

    @Override
    public void weaken(Team team) {
        team.setState(new WeakTeamState());
        team.setChanceCount(team.getChanceCount()+1);
    }

    @Override
    public void obliterate(Team team) {
        // mercy...
    }
    
}
