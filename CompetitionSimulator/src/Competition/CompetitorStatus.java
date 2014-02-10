/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Competition;

/**
 *
 * @author dex
 */
public class CompetitorStatus {
    
    private int position;
    private String teamName;
    private int points;

    public CompetitorStatus(int position, String teamName, int points) {
        this.position = position;
        this.teamName = teamName;
        this.points = points;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    
}
