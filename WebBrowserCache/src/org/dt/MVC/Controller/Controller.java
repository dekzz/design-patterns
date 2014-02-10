
package org.dt.MVC.Controller;

import java.io.File;
import org.dt.MVC.Model.WebsiteDataModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.dt.Cache.CacheWebsite;
import org.dt.datomala_zadaca_4.FileHandler;
import org.dt.MVC.View.BrowserView;
import org.dt.Chain.ImportsHandler;
import org.dt.Chain.LinkHandler;
import org.dt.Chain.MediaHandler;
import org.dt.Chain.TagHandler;
import org.dt.MVC.Model.CacheModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Controller {

    private CacheWebsite cachedWebsites;
    private CacheModel cacheData;
    private String cachePath;
    private String cacheFileName;
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

    public Controller(WebsiteDataModel websiteData, BrowserView view, CacheWebsite cachedWebsites) {
        this.cachedWebsites = cachedWebsites;
        this.cacheData = new CacheModel();
        this.cachePath = cachedWebsites.getCachePath();
        this.cacheFileName = "cache.dat";
        loadCache();
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
    
    private void loadCache() {
        Object cacheMap = FileHandler.loadFile(cachePath, cacheFileName);
        if(cacheMap != null) {
            HashMap<String, CacheModel> loaded = new HashMap<>();
            loaded.putAll((HashMap<String, CacheModel>)cacheMap);
            this.cachedWebsites.setMap_(loaded);
        }
    }
    
    public void addModel(WebsiteDataModel data) {
        this.websiteData = data;
    }
    
    public void addView(BrowserView view) {
        this.view = view;
    }
    
    public void updateTimeActive() {
        stopTime = System.currentTimeMillis();
        long duration = stopTime - startTime;
        websiteData.setTimeActive(websiteData.getTimeActive() + duration);
    }
    
    public void crawl(String url, long interval) {   
        ScheduledFuture refresh = scheduler.scheduleAtFixedRate(refreshTask, interval, interval, TimeUnit.SECONDS);
        loadWebsite(url);
        view.help();
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
                        view.invalidParam();
                    break;
                case "R":
                    reloadWebsite(true);
                    refresh.cancel(true);
                    refresh = scheduler.scheduleAtFixedRate(refreshTask, interval, interval, TimeUnit.SECONDS);
                    break;
                case "S":
                    statistics();
                    break;
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
                case "C":
                    cacheInfo();
                    break;
                case "X":
                    view.cacheClearing();
                    FileHandler.clean(cachePath);
                    cachedWebsites.setMap_(new HashMap<String, CacheModel>());                    
                    view.cacheCleared();
                    break;
                case "H":
                    view.help();
                    break;
                case "Q":
                    scheduler.shutdown();
                    FileHandler.saveFile(cachePath, cacheFileName, cachedWebsites.getMap_());
                    return;
                case "":
                    break;
                default:
                    view.unknownCommand();
            }
        }
    }
    
    public void loadWebsite(String url) {
        startTime = System.currentTimeMillis();
        view.loading(url);
        CacheModel cache = cachedWebsites.acquire(url);
        if(cache != null){
            cacheData = cache;
            cacheData.setLastUsed(new Date());
            cacheData.setTimesUsed(cacheData.getTimesUsed()+1);
            Document doc;
            try {
                doc = Jsoup.parse(new File(cacheData.getStoragePath()), "UTF-8", url);
                activateWebsite(doc, view);
                view.loaded(cacheData.getStoragePath());
            } catch (IOException ex) {
                view.loadingError(cacheData.getStoragePath());
            }
            
        } else {
            try {
                Document doc = Jsoup.connect(url).get();
                activateWebsite(doc, view);
                String path = cachePath;
                String fileName = "website" + websiteData.hashCode() + ".html";
                FileHandler.saveFile(path, fileName, doc.html());
                cacheData = new CacheModel(path.concat(fileName));
                cachedWebsites.release(websiteData.getUrl(), cacheData);
                view.loaded(url);
            } catch (IOException ex) {
                view.loadingError(url);
            }
        }
    }
    
    public void reloadWebsite(boolean manual) {
        updateTimeActive();
        String url = websiteData.getUrl();
        try {    
            view.reloading(url);
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("a[href]");

            TagHandler linkHandler = new LinkHandler(websiteData, view);
            TagHandler mediaHandler = new MediaHandler(websiteData, view);
            TagHandler importsHandler = new ImportsHandler(websiteData, view);

            linkHandler.setSuccessor(mediaHandler);
            mediaHandler.setSuccessor(importsHandler);

            linkHandler.handleRequest(doc, link);
            linkHandler.handleRequest(doc, tag);
            
            if(manual)
                websiteData.setManualReloads(websiteData.getManualReloads()+1);
            else
                websiteData.setAutoReloads(websiteData.getAutoReloads()+1);
            if(isDifferent(links, websiteData.getLinks())) {
                view.contentChanged();
                websiteData.setContextChanged(websiteData.getContextChanged()+1);
                String fileName = "website" + websiteData.hashCode() + ".html";
                FileHandler.saveFile(cachePath, fileName, doc.html());
            } 
            CacheModel cache = cachedWebsites.acquire(url);
            if(cache == null) {
                String fileName = "website" + websiteData.hashCode() + ".html";
                cacheData = new CacheModel(cachePath.concat(fileName));
                cachedWebsites.release(websiteData.getUrl(), cacheData);
                FileHandler.saveFile(cachePath, fileName, doc.html());
            }
            view.reloaded(url);
        } catch (IOException ex) {
            view.reloadingError(url);
        }
    }
    
    private void activateWebsite(Document doc, BrowserView view) {
        WebsiteDataModel website = new WebsiteDataModel(doc);
                
        TagHandler linkHandler = new LinkHandler(website, view);
        TagHandler mediaHandler = new MediaHandler(website, view);
        TagHandler importsHandler = new ImportsHandler(website, view);

        linkHandler.setSuccessor(mediaHandler);
        mediaHandler.setSuccessor(importsHandler);

        linkHandler.handleRequest(doc, link);
        linkHandler.handleRequest(doc, tag);
        
        websiteData = website;
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
    
    public void cacheInfo() {
        view.cacheHeader();
        for (Map.Entry pair : cachedWebsites.getMap_().entrySet()) {
            CacheModel value = (CacheModel)pair.getValue();
            view.cacheInfo(pair.getKey().toString(), value.getStoragePath(), value.getTimeStored(), value.getLastUsed(), value.getTimesUsed());
        }
            
    }
    
    public void statistics() {
        view.statistics();
        view.printStatistics(websiteData.getUrl(), 
                             cacheData.getStoragePath(),
                             websiteData.getNumberOfLinks(), 
                             websiteData.getNumberOfOthers(),
                             websiteData.getTimeActive(), 
                             websiteData.getManualReloads(),
                             websiteData.getAutoReloads(), 
                             websiteData.getContextChanged());

    }
    
}
