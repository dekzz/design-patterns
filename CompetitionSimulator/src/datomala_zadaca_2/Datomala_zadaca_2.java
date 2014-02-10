/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package datomala_zadaca_2;

import Competition.Competition;
import Competition.CompetitionTable;
import Competition.CompetitorStatus;
import Competition.MatchResult;
import Competition.RoundInfo;
import Competitor.Team;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author dex
 */
public class Datomala_zadaca_2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        if(args.length == 4) {
            DecimalFormat df = new DecimalFormat("#.###");
            String commandRegex = "^([a-z]+ ?[a-zA-Z\\-]* ?){1,1}([0-9]* ?){0,1}$";  //([A-Za-z0-9]*){0,1}
            String cmd;
            String command;
            String param1;
            String param2;
            Pattern pattern;
            Matcher match;
            
            File teamListFile = new File(args[0]);
            Competition competition = null;
            ArrayList<Team> teamList = new ArrayList<>();
            
            if(teamListFile.isFile()) {
                try {
                    teamList = FileParser.parseTeamList(teamListFile);
                    competition = new Competition(teamList, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                    
                    command(competition);
                } catch (IOException ex) {
                    System.out.println("Parsing error on team list file: " + teamListFile.getAbsolutePath());
                } 
            } else {
                System.out.println("Team list must be in .txt file!");
                System.exit(0);
            }
            
            if(competition.isPaused() || competition.isStopped() || !competition.isAlive()) {
                help();
                while(true) {
                    System.out.print("> ");
                    cmd = System.console().readLine();
                    pattern = Pattern.compile(commandRegex);
                    match = pattern.matcher(cmd);
                    if(match.matches()) {
                        command = match.group(1).trim();
                        param1 = match.group(2).trim();
                        //param2 = match.group(3).trim();
                    } else {
                        command = "";
                        param1 = "";
                        //param2 = "";
                    }

                    switch(command){
                        case "competition":
                            command(competition);
                            break;
                        case "info":
                            System.out.println("----------------------------------------");
                            System.out.println(" Number of teams (total) : " + teamList.size());
                            System.out.println(" Number of teams (active) : " + competition.countCompetitors());
                            System.out.println(" Number of rounds : " + competition.getRounds().size());
                            System.out.println(" Number of tables : " + competition.getTables().size());
                            System.out.println("----------------------------------------");
                            break;
                        case "team list":
                            System.out.println("---------------------------------------------------------");
                            for(Team team : teamList) {
                                System.out.println(" " + team.getId() + " | " + team.getName() + "  (Position: " + team.getPosition() + " | Points: " + team.getPoints() + " | Efficiency " + df.format(team.getEfficiency()) + ")");
                            }
                            System.out.println("---------------------------------------------------------");
                            break;
                        case "team":
                            if(!param1.isEmpty() && Integer.parseInt(param1) <= teamList.size() && Integer.parseInt(param1) > 0) {
                                int id = Integer.parseInt(param1) - 1;
                                Team team = teamList.get(id);
                                System.out.println("------------------------------------------------------------------------");
                                System.out.println(" " + team.getId() + " | " + team.getName() + " | Position: " + team.getPosition() + " | Points: " + team.getPoints() + " | Rounds: " + team.getRoundsPlayed() + " | Efficiency: " + df.format(team.getEfficiency()));
                                System.out.println("------------------------------------------------------------------------");
                                for(RoundInfo round : competition.getRounds()) {
                                    for(MatchResult game : round.getMatch()) {
                                        if(game.getTeam1().getId() == team.getId() || game.getTeam2().getId() == team.getId())
                                            System.out.println(" " + game.getTeam1().getName() + " - " + game.getTeam2().getName() + " " + game.getResult());
                                    }
                                }
                                System.out.println("------------------------------------------------------------------------");
                            }
                            else
                                System.out.println("Incorrect parameters!");
                            break;
                        case "round":
                            if(!param1.isEmpty() && Integer.parseInt(param1) <= competition.getRounds().size() && Integer.parseInt(param1) > 0) {
                                int num = Integer.parseInt(param1) - 1;
                                RoundInfo round = competition.getRounds().get(num);
                                System.out.println("----------------------------------------");
                                for(MatchResult game : round.getMatch()) {
                                    System.out.println(" " + game.getTeam1().getName() + " - " + game.getTeam2().getName() + " " + game.getResult());
                                }
                                System.out.println("----------------------------------------");
                            }
                            else
                                System.out.println("Incorrect parameters!");
                            break;
                        case "table":
                            if(!param1.isEmpty() && Integer.parseInt(param1) < competition.getTables().size() && Integer.parseInt(param1) >= 0) {
                                int num = Integer.parseInt(param1);
                                CompetitionTable table = competition.getTable(num);
                                System.out.println("------------------------------");
                                for(CompetitorStatus status : table.getTable()) {
                                    System.out.println(" " + status.getPosition() + " | " + status.getTeamName() + " (" + status.getPoints() + ")");
                                }
                                System.out.println("------------------------------");
                            }
                            else
                                System.out.println("Incorrect parameters!");
                            break;
                        case "table round":
                            if(!param1.isEmpty() && Integer.parseInt(param1) < competition.getTables().size() && Integer.parseInt(param1) >= 0) {
                                int num = Integer.parseInt(param1);
                                CompetitionTable table = competition.getTable(num);
                                System.out.println("----------------------------------------");
                                for(CompetitorStatus status : table.getTable()) {
                                    System.out.println(" " + status.getPosition() + " | " + status.getTeamName() + " (" + status.getPoints() + ")");
                                }
                                System.out.println("rnd " + table.getRound());
                                if(table.getRound() > 0) {
                                    RoundInfo round = competition.getRounds().get(table.getRound()-1);
                                    System.out.println("----------------------------------------");
                                    for(MatchResult game : round.getMatch()) {
                                        System.out.println(" " + game.getTeam1().getName() + " - " + game.getTeam2().getName() + " " + game.getResult());
                                    }
                                }
                                System.out.println("----------------------------------------");
                            }
                            else
                                System.out.println("Incorrect parameters!");
                            break;
                        case "competition rounds":
                            for(RoundInfo round : competition.getRounds()) {
                                for(MatchResult game : round.getMatch()) {
                                    System.out.println(game.getTeam1().getName() + " - " + game.getTeam2().getName() + " " + game.getResult());
                                }
                                System.out.println("\n");
                            }
                            break;
                        case "competition tables":
                            for(CompetitionTable table : competition.getTables()) {
                                for(CompetitorStatus status : table.getTable()) {
                                    System.out.println(status.getPosition() + " " + status.getTeamName() + " " + status.getPoints());
                                }
                                System.out.println("\n");
                            }
                            break;
                        case "last":
                            System.out.println("------------------------------");
                            for(CompetitorStatus status : competition.getTempTable().getTable()) {
                                System.out.println(" " + status.getPosition() + " | " + status.getTeamName() + " (" + status.getPoints() + ")");
                            }
                            System.out.println("------------------------------");
                            break;
                        case "help":
                            help();
                            break;
                        case "exit":
                            return;
                        case "":
                            break;
                        default:
                            System.out.println(ConsoleTextColor.ANSI_RED + "!" + ConsoleTextColor.ANSI_RESET+ " Unknown command");
                    }
                }
           }
        } else {
            throw new IllegalArgumentException("Invalid number of arguments!");
        }
        
    }
    
    private static void command(Competition competition) {
        String cmd;
        Command command;
        Invoker invoker;
        System.out.println(ConsoleTextColor.ANSI_RED + "\n|=========================================|\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " Use 'start' to start competition!" + ConsoleTextColor.ANSI_RED + "       |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " Use 'pause' to pause competition!" + ConsoleTextColor.ANSI_RED + "       |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " Use 'resume' to resume competition!" + ConsoleTextColor.ANSI_RED + "     |\n" + ConsoleTextColor.ANSI_RESET 
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " Use 'stop' to stop competition!" + ConsoleTextColor.ANSI_RED + "         |\n" + ConsoleTextColor.ANSI_RESET 
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " Use 'close' to close menu!" + ConsoleTextColor.ANSI_RED + "              |\n" + ConsoleTextColor.ANSI_RESET 
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " Use 'exit' to exit application!" + ConsoleTextColor.ANSI_RED + "         |" + ConsoleTextColor.ANSI_RESET 
                         + ConsoleTextColor.ANSI_RED + "\n|=========================================|\n" + ConsoleTextColor.ANSI_RESET);

        while(true) {
            System.out.print("> ");
            cmd = System.console().readLine().trim();
            
            switch (cmd) {
                case "start":
                    if(!competition.isAlive() && competition.getState() != Thread.State.TERMINATED) {
                        command = new StartCommand(competition);
                        invoker = new Invoker();
                        invoker.setStartCommand(command);
                        invoker.executeStart();
                        System.out.println(ConsoleTextColor.ANSI_RED + "!" + ConsoleTextColor.ANSI_RESET+ " Competition started!");
                    } else
                        System.out.println("Competition was already started!");
                    break;
                case "pause":
                    if(competition.isAlive()) {
                        command = new PauseCommand(competition);
                        invoker = new Invoker();
                        invoker.setPauseCommand(command);
                        invoker.executePause();
                        System.out.println(ConsoleTextColor.ANSI_RED + "!" + ConsoleTextColor.ANSI_RESET+ " Competition paused!");
                        return;
                    } else
                        System.out.println(" Competition must be running!");
                    break;
                case "resume":
                    if(competition.isPaused()) {
                        command = new ResumeCommand(competition);
                        invoker = new Invoker();
                        invoker.setResumeCommand(command);
                        invoker.executeResume();
                        System.out.println(ConsoleTextColor.ANSI_RED + "!" + ConsoleTextColor.ANSI_RESET+ " Competition resumed!");
                    } else
                        System.out.println(" Competition is not paused!");
                    break;
                case "stop":
                    if(competition.isAlive()) {
                        command = new StopCommand(competition);
                        invoker = new Invoker();
                        invoker.setStopCommand(command);
                        invoker.executeStop();
                        System.out.println(ConsoleTextColor.ANSI_RED + "!" + ConsoleTextColor.ANSI_RESET+ " Competition stoped!");
                        do {
                        } while (competition.getState() != Thread.State.TERMINATED);
                        return;
                    } else
                        System.out.println(" Competition must be running!");
                    break;
                case "close":
                    if(!competition.isAlive() && competition.getState() == Thread.State.TERMINATED) {
                        return;
                    }
                    break;
                case "exit":
                    if(!competition.isAlive()) {
                        System.exit(0);
                    }
                    break;
                case "":
                    break;
                default:
                    System.out.println(ConsoleTextColor.ANSI_RED + "!" + ConsoleTextColor.ANSI_RESET+ " Unknown command");
            }
        }
    }
    
    private static void help() {
        System.out.println(ConsoleTextColor.ANSI_RED + "\n|==========================================================================================|\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_CYAN  + " List of commands:" + ConsoleTextColor.ANSI_RED + "                                                                        |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " help                  ->    display help menu" + ConsoleTextColor.ANSI_RED + "                                            |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " competition           ->    competition options" + ConsoleTextColor.ANSI_RED + "                                          |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " info                  ->    display competition info" + ConsoleTextColor.ANSI_RED + "                                     |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " team list             ->    print team list" + ConsoleTextColor.ANSI_RED + "                                              |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " team #param           ->    print all match results for team with id #param " + ConsoleTextColor.ANSI_RED + "             |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " round #param          ->    print match results for #param round" + ConsoleTextColor.ANSI_RED + "                         |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " table #param          ->    print points table for #param round" + ConsoleTextColor.ANSI_RED + "                          |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " table round #param    ->    print points table and matches for #param round" + ConsoleTextColor.ANSI_RED + "              |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " competition rounds    ->    print all match results" + ConsoleTextColor.ANSI_RED + "                                      |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " competition tables    ->    print all point tables for competition" + ConsoleTextColor.ANSI_RED + "                       |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " last                  ->    final table" + ConsoleTextColor.ANSI_RED + "                                                  |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|" + ConsoleTextColor.ANSI_RESET + " exit                  ->    exit application" + ConsoleTextColor.ANSI_RED + "                                             |\n" + ConsoleTextColor.ANSI_RESET
                         + ConsoleTextColor.ANSI_RED + "|==========================================================================================| \n" + ConsoleTextColor.ANSI_RESET);
    }
    
}
