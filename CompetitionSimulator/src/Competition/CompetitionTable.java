/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Competition;

import java.util.ArrayList;

/**
 *
 * @author dex
 */
public class CompetitionTable { // memento originator
    
    private ArrayList<CompetitorStatus> table;
    private int round;

    public CompetitionTable(ArrayList<CompetitorStatus> table, int round) {
        this.table = new ArrayList<>(table);
        this.round = round;
    }
    
    public ArrayList<CompetitorStatus> getTable() {
        return table;
    }

    public void setTable(ArrayList<CompetitorStatus> table) {
        this.table = table;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
    
    public boolean changed(ArrayList<CompetitorStatus> table) {   
        if(this.table.size() != table.size()) {
            return false;
        }
        else {
            for(int i = 0; i < table.size(); i++) {
                if(this.table.get(i).getPosition() != table.get(i).getPosition()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public Object saveToMemento() {
        return new Memento(table, round);
    }
    
    public void restoreFromMemento(Object m) {
        if(m instanceof Memento) {
            Memento memento = (Memento) m;
            table = memento.getTable();
            round = memento.getRound();
        }
    }
    
    private static class Memento {
        
        private ArrayList<CompetitorStatus> table;
        private int round;

        public Memento(ArrayList<CompetitorStatus> table, int round) {
            this.table = table;
            this.round = round;
        }

        public ArrayList<CompetitorStatus> getTable() {
            return table;
        }
        
        public int getRound() {
            return round;
        }
    }
    
}
