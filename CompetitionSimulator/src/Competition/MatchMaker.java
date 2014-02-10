/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Competition;

import Competitor.Team;
import Competitor.DisqualifiedTeamState;
import datomala_zadaca_2.ConsoleTextColor;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author dex
 */
public class MatchMaker {
    
    public static ArrayList<MatchResult> results(ArrayList<Team> teamList) {
        ArrayList<Team> teams = new ArrayList<>();
        ArrayList<MatchResult> matchResults = new ArrayList<>();
        int team1index, team2index, res;
        Random rand = new Random();
        
        for(Team team : teamList) {
            if(!(team.getState() instanceof DisqualifiedTeamState))
                teams.add(team);
        }
        
        while(teams.size() > 1) {
            team1index = rand.nextInt(teams.size());
            Team team1 = new Team(teams.get(team1index).getId(), teams.get(team1index).getName());
            teams.remove(team1index);
            
            team2index = rand.nextInt(teams.size());
            Team team2 = new Team(teams.get(team2index).getId(), teams.get(team2index).getName());
            teams.remove(team2index);

            MatchResult opponents = new MatchResult(team1, team2);
            res = rand.nextInt(3);
            opponents.setResult(res);
            matchResults.add(opponents);
            
            switch(res) {
                    case 0:
                        for(Team team : teamList) {
                            if(team.getId() == team1.getId()) {
                                team.setPoints(team.getPoints()+1);
                                team.setRoundsPlayed(team.getRoundsPlayed()+1);
                            }
                            else if(team.getId() == team2.getId()) {
                                team.setPoints(team.getPoints()+1);
                                team.setRoundsPlayed(team.getRoundsPlayed()+1);
                            }
                        }
                        break;
                    case 1:
                        for(Team team : teamList) {
                            if(team.getId() == team1.getId()) {
                                team.setPoints(team.getPoints()+3);
                                team.setRoundsPlayed(team.getRoundsPlayed()+1);
                            }
                        }
                        break;
                    case 2:
                        for(Team team : teamList) {
                            if(team.getId() == team2.getId()) {
                                team.setPoints(team.getPoints()+3);
                                team.setRoundsPlayed(team.getRoundsPlayed()+1);
                            }
                        }
                        break;
                }
            if(teams.size() == 1)
                System.out.println(ConsoleTextColor.ANSI_CYAN + "F" + ConsoleTextColor.ANSI_RESET + "Team '" + teams.get(0).getName() + "' was free this round!");
        }
        return matchResults;
    }

}
