
package org.dt.MVC.Controller;

import org.dt.MVC.Model.WebsiteDataModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.dt.MVC.View.BrowserView;
import org.dt.chain.ImportsHandler;
import org.dt.chain.LinkHandler;
import org.dt.chain.MediaHandler;
import org.dt.chain.TagHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Controller {
    
    private ArrayList<Object> websites;
    private WebsiteDataModel websiteData;
    private BrowserView view;
    private String link;
    private String tag;
    private TimerTask refreshTask;
    private ScheduledExecutorService scheduler;
    private long startTime;
    private long stopTime;
    
    private String commandRegex = "^([A-Z] ?){1,1}([0-9]*){0,1}$"; 
    private String command = "";
    private String param;
    private Pattern pattern;
    private Matcher match;

    public Controller(WebsiteDataModel websiteData, BrowserView view) {
        this.websites = new ArrayList<>();
        this.websiteData = websiteData;
        this.view = view;
        this.link = "a[href]";
        this.tag = "[src]";
        this.refreshTask = new TimerTask() {
                                    @Override
                                    public void run() {
                                        reloadWebsite(false);
                                    }};
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.startTime = 0;
        this.stopTime = 0;
    }
    
    public void addModel(WebsiteDataModel data) {
        this.websiteData = data;
    }
    
    public void addView(BrowserView view) {
        this.view = view;
    }
    
    public void addMemento(Object m) {
        this.websites.add(m);
    }
    
    public Object getMemento(int index) {
        return websites.get(index);
    }
    
    public void updateTimeActive() {
        stopTime = System.currentTimeMillis();
        long duration = stopTime - startTime;
        websiteData.setTimeActive(websiteData.getTimeActive() + duration);
        if(wasActive(websiteData.getUrl()) != null) addMemento(websiteData.saveToMemento());
    }
    
    public void crawl(String url, long interval) {   
        ScheduledFuture refresh = scheduler.scheduleAtFixedRate(refreshTask, interval, interval, TimeUnit.SECONDS);
        loadWebsite(url);
        help();
        while(true) {
            System.out.print("> ");
            String cmd = System.console().readLine();
            pattern = Pattern.compile(commandRegex);
            match = pattern.matcher(cmd);
            if(match.matches()) {
                command = match.group(1).trim();
                param = match.group(2).trim();
            } else {
                command = "";
                param = "";
            }

            switch(command){
                case "B":
                    view.printNumberOfLinks(websiteData.getNumberOfLinks());
                    break;
                case "I":
                    view.printLinks(websiteData.getLinks());
                    break;
                case "J":
                    if(!param.isEmpty() && Integer.parseInt(param) > 0 && Integer.parseInt(param) <= websiteData.getNumberOfLinks()) {
                        updateTimeActive();
                        loadWebsite(websiteData.getLinks().get(Integer.parseInt(param)-1).attr("abs:href"));
                        refresh.cancel(true);
                        refresh = scheduler.scheduleAtFixedRate(refreshTask, interval, interval, TimeUnit.SECONDS);
                    }
                    else
                        System.out.println("! Incorrect parameter: there is no link with that ID!");
                    break;
                case "R":
                    reloadWebsite(true);
                    refresh.cancel(true);
                    refresh = scheduler.scheduleAtFixedRate(refreshTask, interval, interval, TimeUnit.SECONDS);
                    break;
                case "S":
                    statistics();
                    break;
                case "Q":
                    scheduler.shutdown();
                    return;
                case "T":
                    if(!param.isEmpty()) {
                        if(Integer.parseInt(param) == 1) tag = "[src]";
                        if(Integer.parseInt(param) == 2) tag = "link[href]";
                    }
                    break;
                case "N":
                    if(tag.equals("[src]")) view.printNumberOfMedia(websiteData.getNumberOfOthers());
                    if(tag.equals("link[href]")) view.printNumberOfImports(websiteData.getNumberOfOthers());
                    break;
                case "M":
                    if(tag.equals("[src]")) view.printMedia(websiteData.getOther());
                    if(tag.equals("link[href]")) view.printImports(websiteData.getOther());
                    break;
                case "H":
                    help();
                    break;
                case "":
                    break;
                default:
                    System.out.println("! Unknown command");
            }
        }
        
    }
    
    public void loadWebsite(String url) {
        startTime = System.currentTimeMillis();
        System.out.println("! Loading: \"" + url + "\" ...");
        WebsiteDataModel website = wasActive(url);
        if(website != null){
            websiteData = website;
        } else {
            try {
                Document doc = Jsoup.connect(url).get();
                website = new WebsiteDataModel(doc);
                
                TagHandler linkHandler = new LinkHandler(website, view);
                TagHandler mediaHandler = new MediaHandler(website, view);
                TagHandler importsHandler = new ImportsHandler(website, view);
                
                linkHandler.setSuccessor(mediaHandler);
                mediaHandler.setSuccessor(importsHandler);
                
                linkHandler.handleRequest(doc, link);
                linkHandler.handleRequest(doc, tag);
                
                websiteData = website;
                addMemento(websiteData.saveToMemento());
                System.out.println("! Loaded: \"" + url + "\"");
            } catch (IOException ex) {
                System.out.println("! Could not connect to: \"" + url + "\"");
            }
        }
    }
    
    public void reloadWebsite(boolean manual) {
        updateTimeActive();
        String url = websiteData.getUrl();
        if(wasActive(url) != null) {
            try {    
                System.out.println("! Reloading: \"" + url + "\" ...");
                Document doc = Jsoup.connect(url).get();
                Elements links = doc.select("a[href]");

                TagHandler linkHandler = new LinkHandler(websiteData, view);
                TagHandler mediaHandler = new MediaHandler(websiteData, view);
                TagHandler importsHandler = new ImportsHandler(websiteData, view);
                
                linkHandler.setSuccessor(mediaHandler);
                mediaHandler.setSuccessor(importsHandler);
                
                if(manual)
                    websiteData.setManualReloads(websiteData.getManualReloads()+1);
                else
                    websiteData.setAutoReloads(websiteData.getAutoReloads()+1);
                if(isDifferent(links, websiteData.getLinks())) {
                    System.out.println("! Content changed!");
                    websiteData.setContextChanged(websiteData.getContextChanged()+1);
                }
                linkHandler.handleRequest(doc, link);
                linkHandler.handleRequest(doc, tag);
                websites.add(websiteData.saveToMemento());
                System.out.println("! Reloaded: \"" + url + "\"");
            } catch (IOException ex) {
                System.out.println("! Error occurred while reloading: \"" + url + "\"");
            }
        }
    }
    
    private WebsiteDataModel wasActive(String url) {
        for(int i = 0; i < websites.size(); i++) {
            WebsiteDataModel website = new WebsiteDataModel();
            website.restoreFromMemento(getMemento(i));
            if(website.getUrl().equals(url)){
                websites.remove(i);
                return website;
            }
        }
        return null;
    }
    
    private boolean isDifferent(Elements links1, Elements links2) {
        if(links1.size() != links2.size()) return true;
        else {
            for(int i = 0; i < links1.size(); i++) {
                if(!links1.get(i).attr("abs:href").equals(links2.get(i).attr("abs:href"))) return true;
            }
        }
        return false;
    }
    
    public void statistics() {
        System.out.println("\n     -= STATISTICS =-");
        for(Object memento : websites) {
            WebsiteDataModel website = new WebsiteDataModel();
            website.restoreFromMemento(memento);
            view.printStatistics(website.getUrl(), 
                                 website.getNumberOfLinks(), 
                                 website.getNumberOfOthers(),
                                 website.getTimeActive(), 
                                 website.getManualReloads(),
                                 website.getAutoReloads(), 
                                 website.getContextChanged());
        }
    }
    
    public static void help() {
        System.out.println("\n|=================================================|\n"
                           + "| B    ->  number of links                        |\n" 
                           + "| I    ->  list of URLs                           |\n" 
                           + "| J n  ->  switch to / activate n-th website      |\n" 
                           + "| R    ->  reload website                         |\n" 
                           + "| S    ->  website statistics                     |\n"
                           + "| T n  ->  1 - media | 2 - imports                |\n"
                           + "| N    ->  number of secondary elements           |\n"
                           + "| M    ->  list of secondary elements             |\n"
                           + "| H    ->  help menu                              |\n"
                           + "| Q    ->  exit application                       |\n"
                           + "|=================================================|\n");
    }
    
}
