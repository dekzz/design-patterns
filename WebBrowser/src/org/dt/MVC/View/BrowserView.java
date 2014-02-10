
package org.dt.MVC.View;

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
        for(Element link : links) {
            System.out.println("| " + i + " <" + link.attr("abs:href") + "> (" + link.text() + ")");
            i++;
        }
    }

    public void printMedia(Elements media) {
        int i = 1;
        for(Element src : media) {
            System.out.println("| " + i + " " + src.tagName() + ": <" + src.attr("abs:src") + "> (" + src.attr("width") + "x" + src.attr("height") + ")");
            i++;
        }
    }
    
    public void printImports(Elements imports) {
        int i = 1;
        for(Element link : imports) {
            System.out.println("| " + i + " " + link.tagName() + " <" + link.attr("abs:href") + "> (" + link.attr("rel") + ")");
            i++;
        }
    }
    
    public void printEmpty(String handler, Document doc, String tag){
        System.out.println("! " + handler + ": Document " + doc.toString() + " does not contain: " + tag + "\n");
    }

    public void printStatistics(String url, int nrLinks, int nrOthers, double timeActive, int manualReloads, int autoReloads, int contextChanged) {
        System.out.println("\n| URL: " + url
                         + "\n| # links: " + nrLinks
                         + "\n| # others: " + nrOthers
                         + "\n| Active for: " + timeActive/1000 + " s"
                         + "\n| Manual reloads: " + manualReloads
                         + "\n| Auto reloads: " + autoReloads
                         + "\n| Content changed: " + contextChanged);
    }
}
