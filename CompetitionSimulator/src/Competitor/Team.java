/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Competitor;

import Observatory.Observer;
import java.text.DecimalFormat;

/**
 *
 * @author dex
 */
public class Team implements Observer, Comparable<Team> { //state 
    
    private int id;
    private String name;
    private int position;
    private int points;
    private double efficiency;
    private int roundsPlayed;
    private State state;
    private int chanceCount;

    public Team(int id, String name) {
        this.id = id;
        this.name = name;
        this.position = 0;
        this.points = 0;
        this.efficiency = 0;
        this.roundsPlayed = 0;
        this.chanceCount = 0;
        this.state = new NormalTeamState();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public int getPoints() {
        return points;
    }

    public State getState() {
        return state;
    }

    public int getChanceCount() {
        return chanceCount;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public double getEfficiency() {
        return efficiency;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    public void setRoundsPlayed(int roundsPlayed) {
        this.roundsPlayed = roundsPlayed;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setChanceCount(int chanceCount) {
        this.chanceCount = chanceCount;
    }

    @Override
    public int compareTo(Team team) {
        //descending
        return team.getPoints() - this.points;
        //ascending
        //return this.points - team.getPoints();
    }

    @Override
    public void updateEfficiency(double efficiency) {
        DecimalFormat df = new DecimalFormat("#.###");
        System.out.println(" Efficiency for team '" + name + "' changed from " + df.format(this.efficiency) + " to " + df.format(efficiency));
        this.efficiency = efficiency;
    }


    
}
