/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datomala_zadaca_2;

import Competitor.Team;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author dex
 */
public class FileParser {
    
    public static ArrayList<Team> parseTeamList(File teamList) throws IOException {
        ArrayList<Team> teams = new ArrayList<>();
        int id;
        String name;
        
        BufferedReader br = new BufferedReader(new FileReader(teamList));
        String line;
        while ((line = br.readLine()) != null) {
           id = Integer.parseInt(line.substring(0, 5).trim());
           name = line.substring(5).trim();
           Team team = new Team(id, name);
           teams.add(team);
        }
        br.close();
        return teams;
    }
}
