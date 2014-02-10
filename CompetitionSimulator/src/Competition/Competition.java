/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Competition;

import Competitor.DisqualifiedTeamState;
import Competitor.NormalTeamState;
import Competitor.Team;
import Competitor.WeakTeamState;
import Observatory.TheEye;
import datomala_zadaca_2.ConsoleTextColor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dex
 */
public class Competition extends Thread{ // memento caretaker; command receiver
    private boolean stopped = false;
    private boolean paused = false;
    private long interval;
    private int controlInterval;
    private int threshold;
    private int round;
    private ArrayList<Team> teams = new ArrayList<Team>();
    private ArrayList<RoundInfo> rounds = new ArrayList<RoundInfo>();
    private ArrayList<Object> tables = new ArrayList<>();
    private CompetitionTable tempTable;
    private CompetitionTable table;
    private TheEye theEye = new TheEye();

    public Competition(ArrayList<Team> teamList, int interval, int controlInterval, int threshold) {
        this.teams = new ArrayList<>(teamList);
        this.interval = Long.valueOf(interval);
        this.controlInterval = controlInterval;
        this.round = 0;
        this.threshold = threshold;
        this.table = new CompetitionTable(updateTable(), round);
        this.tempTable = new CompetitionTable(updateTable(), round);
        addMemento(tempTable.saveToMemento());
        addObservers();
    }
    
    @Override
    public void run() {
        
        while(true) {
            long start = System.currentTimeMillis();
            round++;
            System.out.println(ConsoleTextColor.ANSI_GREEN + "-= Round " + round + " =-" + ConsoleTextColor.ANSI_RESET);
            
            rounds.add(new RoundInfo(MatchMaker.results(teams)));
            
            tempTable.setTable(updateTable());
            tempTable.setRound(round);
            table.restoreFromMemento(getMemento(tables.size()-1));
            if(tempTable.changed(table.getTable()))
                addMemento(tempTable.saveToMemento());
            
            theEye.calculateEfficiency();

            if(round % controlInterval == 0) {
                for(Team team : teams) {
                    table.restoreFromMemento(getMemento(tables.size()-1));
                    for(CompetitorStatus item : table.getTable()) {
                        if(team.getName().equals(item.getTeamName())) {
                            int previousPosition = item.getPosition();
                            
                            if(team.getPosition() < previousPosition) {
                                team.getState().weaken(team);
                            } else if(team.getPosition() == previousPosition && team.getChanceCount() > 0) {
                                team.getState().weaken(team);
                            } else if(team.getPosition() > previousPosition) {
                                team.getState().strengthen(team);
                            }
                            if(team.getChanceCount() == threshold && !(team.getState() instanceof DisqualifiedTeamState)){
                                team.getState().obliterate(team);
                                theEye.removeObserver(team);
                            }
                        }
                    }
                }
            }
            
            if(countCompetitors() == 2 || stopped) {
                ArrayList<Team> winner = winners();
                System.out.println("\nCompetition has ended!\n");
                System.out.println("Congratz to the winners: " + winner.get(0).getId() + " " + winner.get(0).getName() + " (" + winner.get(0).getPoints() + ")\n"
                                 + "                         " + winner.get(1).getPosition() + " " + winner.get(1).getName() + " (" + winner.get(1).getPoints() + ")\n");
                break;
            }
            
            while(paused && !stopped) {
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                }
            }
            
            long stop = System.currentTimeMillis();
            long workTime = stop - start;
            if(workTime < interval) {
                long idleTime =  ((interval * 1000) - workTime);
                try {
                    sleep(idleTime);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Competition.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        super.run();
    }
    
    private ArrayList<CompetitorStatus> updateTable() {
        ArrayList<CompetitorStatus> table = new ArrayList<>();
        Collections.sort(teams);
        int position = 0;
        
        for(Team team : teams) {
            CompetitorStatus competitor = new CompetitorStatus(++position, team.getName(), team.getPoints());
            table.add(competitor);
        }
        
        for(int i = 0; i < table.size()-1; i++) {
            if(table.get(i).getPoints() == table.get(i+1).getPoints()) {
                table.get(i+1).setPosition(table.get(i).getPosition());
            }
            teams.get(i).setPosition(table.get(i).getPosition());
        }
        
        for(int i = 0; i < table.size(); i++) {
            teams.get(i).setPosition(table.get(i).getPosition());
        }
        
        return table;
    }

    public int countCompetitors() {
        int count = 0;
        for(Team team : teams) {
            if(team.getState() instanceof NormalTeamState || team.getState() instanceof WeakTeamState)
                count++;
        }
        return count;
    }
    
    private ArrayList<Team> winners() {
        ArrayList<Team> winners = new ArrayList<>();
        for(Team team : teams) {
            if(team.getState() instanceof NormalTeamState || team.getState() instanceof WeakTeamState) 
                winners.add(team);
        }
        Collections.sort(winners);
        return winners;
    }
    
    private void addObservers() {
        for(Team team : teams)
            theEye.addObserver(team);
    }
    
    public void addMemento(Object m) {
        tables.add(m);
    }
    
    public Object getMemento(int index) {
        return tables.get(index);
    }
    
    public void startCompetition() {
        this.start();
        paused = false;
        stopped = false;
    }
    
    public void pauseCompetition() {
        paused = true;
    }
    
    public void resumeCompetition() {
        paused = false;
    }
    
    public void stopCompetition() {
        stopped = true;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopRequest) {
        this.stopped = stopRequest;
    }

    public CompetitionTable getTable() {
        return table;
    }
    
    public CompetitionTable getTable(int index) {
        table.restoreFromMemento(getMemento(index));
        return table;
    }
    
     public CompetitionTable getTempTable() {
        return tempTable;
    }
    
    public ArrayList<RoundInfo> getRounds() {
        return rounds;
    }

    public ArrayList<CompetitionTable> getTables() {
        ArrayList<CompetitionTable> tab = new ArrayList<>();
        for(int i = 0; i < tables.size(); i++) {
            table.restoreFromMemento(getMemento(i));
            tab.add(table);
        }
        return tab;
    }
    
}
