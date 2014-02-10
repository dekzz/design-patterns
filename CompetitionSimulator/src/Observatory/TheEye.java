/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Observatory;

import Competitor.Team;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author dex
 */
public class TheEye implements Subject {
    
    private List observers = new ArrayList();

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void calculateEfficiency() {
        double efficiency = 0;
        Iterator i = observers.iterator();
        
        while(i.hasNext()) {
            Observer o = (Observer) i.next();
            if(o instanceof Team) {
                Team team = (Team) o;
                if(team.getRoundsPlayed() != 0) {
                    efficiency = (double)team.getPoints() / team.getRoundsPlayed();
                    if(efficiency != team.getEfficiency())
                        team.updateEfficiency(efficiency);
                }
            }
        }
    }
    
    public ArrayList<Team> getObservers() {
        ArrayList<Team> obs = new ArrayList<>();
        Iterator i = observers.iterator();
        
        while(i.hasNext()) {
            Observer o = (Observer) i.next();
            if(o instanceof Team) {
                Team team = (Team) o;
                obs.add(team);
            }
        }
        
        return obs;
    }
    
}
