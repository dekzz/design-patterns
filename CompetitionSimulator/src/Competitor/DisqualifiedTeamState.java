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
public class DisqualifiedTeamState implements State{

    @Override
    public void strengthen(Team team) {
        // raise
    }

    @Override
    public void weaken(Team team) {
        // purge
    }

    @Override
    public void obliterate(Team team) {
        // doomed
    }


    
}
