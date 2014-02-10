
package org.dt.MVC.View;

import java.util.Date;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class BrowserView {

    public void printNumberOfLinks(int n) {
        System.out.println("! Number of links: " + n);
    }
    
    public void printNumberOfMedia(int n) {
        System.out.println("! Number of media: " + n);
    }
    
    public void printNumberOfImports(int n) {
        System.out.println("! Number of imports: " + n);
    }
    
    public void printLinks(Elements links) {
        int i = 1;
        int urlSize = 0;
        int descSize = 0;
        int idSize = Integer.toString(links.size()).length();
        for(Element link : links) {
            int urlTemp = link.attr("abs:href").length();
            int descTemp = link.text().length();
            if(urlTemp >= urlSize)
                urlSize = urlTemp;
            if(descTemp >= descSize)
                descSize = descTemp;
        }
        header(idSize, urlSize, descSize);
        for(Element link : links) {
            String leftAlignFormat = "| %"+ idSize +"d | %-" + urlSize + "s | %-" + descSize + "s |%n";
            System.out.format(leftAlignFormat, i++, link.attr("abs:href"), link.text());
        }
        footer(idSize, urlSize, descSize);
        
    }

    public void printMedia(Elements media) {
        int i = 1;
        int elemSize = 0;
        int descSize = 0;
        int idSize = Integer.toString(media.size()).length();
        for(Element elem : media) {
            int urlTemp = elem.attr("abs:src").length();
            int descTemp = (elem.attr("width").concat("x").concat(elem.attr("height"))).length();
            if(urlTemp >= elemSize)
                elemSize = urlTemp;
            if(descTemp >= descSize)
                descSize = descTemp;
        }
        header(idSize, elemSize, descSize);
        for(Element src : media) {
            String leftAlignFormat = "| %"+ idSize +"d | %-" + elemSize + "s | %-" + descSize + "s |%n";
            System.out.format(leftAlignFormat, i++, src.attr("abs:src"), src.attr("width").concat("x").concat(src.attr("height")));
        }
        footer(idSize, elemSize, descSize);
    }
    
    public void printImports(Elements imports) {
        int i = 1;
        int elemSize = 0;
        int descSize = 0;
        int idSize = Integer.toString(imports.size()).length();
        for(Element elem : imports) {
            int urlTemp = elem.attr("abs:href").length();
            int descTemp = elem.attr("rel").length();
            if(urlTemp >= elemSize)
                elemSize = urlTemp;
            if(descTemp >= descSize)
                descSize = descTemp;
        }
        header(idSize, elemSize, descSize);
        for(Element link : imports) {
            
            String leftAlignFormat = "| %"+ idSize +"d | %-" + elemSize + "s | %-" + descSize + "s |%n";
            System.out.format(leftAlignFormat, i++, link.attr("abs:href"), link.attr("rel"));
        }
        footer(idSize, elemSize, descSize);
    }
    
    public void printEmpty(String handler, Document doc, String tag){
        System.out.println("! " + handler + ": Document " + doc.baseUri() + " does not contain: " + tag + " -");
    }

    public void printStatistics(String url, String storagePath, int nrLinks, int nrOthers, double timeActive, int manualReloads, int autoReloads, int contextChanged) {
        System.out.println("\n| URL: " + url
                         + "\n| Storage: " + storagePath
                         + "\n| # links: " + nrLinks
                         + "\n| # others: " + nrOthers
                         + "\n| Active for: " + timeActive/1000 + " s"
                         + "\n| Manual reloads: " + manualReloads
                         + "\n| Auto reloads: " + autoReloads
                         + "\n| Content changed: " + contextChanged);
    }
    
    public void cacheHeader() {
        System.out.println("\n     -= CACHE =-");
    }
    
    public void cacheInfo(String url, String storagePath, Date timeStored, Date lastUsed, int timesUsed) {
         System.out.println("\n| URL: " + url
                          + "\n| Storage: " + storagePath
                          + "\n| Stored: " + timeStored
                          + "\n| Last used: " + lastUsed
                          + "\n| Times used: " + timesUsed);
   }
    
    public void cacheClearing() {
        System.out.println("! Clearing cache ...");
    }
    
    public void cacheCleared() {
        System.out.println("! Cache cleared!  +");
    }
    
    public void noSpace(String strategy) {
        System.out.println("! Cache full, using '" + strategy + "' swamp strategy!");
    }
    
    public void loading(String url) {
        System.out.println("! Loading: \"" + url + "\" ...");
    }
    
    public void loaded(String url) {
        System.out.println("! Loaded: \"" + url + "\"  +");
    }
    
    public void loadingError(String url) {
        System.out.println("! Could not connect to: \"" + url + "\"  -");
    }
    
    public void reloading(String url) {
        System.out.println("! Reloading: \"" + url + "\" ...");
    }
    
    public void reloaded(String url) {
        System.out.println("! Reloaded: \"" + url + "\"  +");
    }
    
    public void reloadingError(String url) {
        System.out.println("! Error occurred while reloading: \"" + url + "\"  -");
    }
    
    public void contentChanged() {
        System.out.println("! Content changed!");
    }
    
    public void statistics(){
        System.out.println("\n     -= STATISTICS =-");
    }
    
    public void invalidParam(){
        System.out.println("! Incorrect parameter: there is no link with that ID!");
    }
    
    public void unknownCommand() {
        System.out.println("! Unknown command");
    }
    
    public void help() {
        System.out.println("\n|=================================================|\n"
                           + "| B    ->  number of links                        |\n" 
                           + "| I    ->  list of URLs                           |\n" 
                           + "| J n  ->  switch to n-th website                 |\n" 
                           + "| R    ->  reload website                         |\n" 
                           + "| S    ->  website statistics                     |\n"
                           + "| T n  ->  tag: 1 - media | 2 - imports           |\n"
                           + "| N    ->  number of secondary elements           |\n"
                           + "| M    ->  list of secondary elements             |\n"
                           + "| C    ->  cache info                             |\n"
                           + "| X    ->  clear cache                            |\n"
                           + "| H    ->  help menu                              |\n"
                           + "| Q    ->  exit application                       |\n"
                           + "|=================================================|\n");
    }
    
    private void header(int idSize, int elemSize, int descSize) {
        for(int x = 0; x < idSize + elemSize + descSize + 10; x++) {
            System.out.print("-");
        }
        System.out.format("%n");
    }
    
    private void footer(int idSize, int elemSize, int descSize) {
        for(int x = 0; x < idSize + elemSize + descSize + 10; x++) {
            System.out.print("-");
        }
        System.out.format("%n");
    }
}
