
package org.dt.MVC.Model;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class WebsiteDataModel{
    private Document info;
    private Elements links;
    private Elements others;
    private double timeActive;
    private int manualReloads;
    private int autoReloads;
    private int contextChanged;

    public WebsiteDataModel() {
    }

    public WebsiteDataModel(Document info) {
        this.info = info;
    }

    public WebsiteDataModel(Document info, Elements links, Elements others) {
        this.info = info;
        this.links = links;
        this.others = others;
        this.timeActive = 0;
        this.manualReloads = 0;
        this.autoReloads = 0;
        this.contextChanged = 0;
    }

    public String getUrl() {
        return info.baseUri();
    }
    
    public Document getInfo() {
        return info;
    }

    public Elements getLinks() {
        return links;
    }

    public Elements getOther() {
        return others;
    }
    
    public int getNumberOfLinks() {
        return links.size();
    }
 
    public int getNumberOfOthers(){
        return others.size();
    }

    public double getTimeActive() {
        return timeActive;
    }

    public int getManualReloads() {
        return manualReloads;
    }

    public int getAutoReloads() {
        return autoReloads;
    }

    public int getContextChanged() {
        return contextChanged;
    }

    public void setInfo(Document info) {
        this.info = info;
    }

    public void setLinks(Elements links) {
        this.links = links;
    }

    public void setOthers(Elements others) {
        this.others = others;
    }

    public void setTimeActive(double timeActive) {
        this.timeActive = timeActive;
    }

    public void setManualReloads(int manualReloads) {
        this.manualReloads = manualReloads;
    }

    public void setAutoReloads(int autoReloads) {
        this.autoReloads = autoReloads;
    }

    public void setContextChanged(int contextChanged) {
        this.contextChanged = contextChanged;
    }
    /*
    public Object saveToMemento() {
        return new Memento(info, links, others, timeActive, manualReloads, autoReloads, contextChanged);
    }
    
    public void restoreFromMemento(Object m) {
        if(m instanceof Memento) {
            Memento memento = (Memento) m;
            info = memento.getInfo();
            links = memento.getLinks();
            others = memento.getOthers();
            timeActive = memento.getTimeActive();
            manualReloads = memento.getManualReloads();
            autoReloads = memento.getAutoReloads();
            contextChanged = memento.getContextChanged();
        }
    }
    
    private static class Memento {
        
        private Document info;
        private Elements links;
        private Elements others;
        private double timeActive;
        private int manualReloads;
        private int autoReloads;
        private int contextChanged;

        public Memento(Document info, Elements links, Elements others, double timeActive, int manualReloads, int autoReloads, int contextChanged) {
            this.info = info;
            this.links = links;
            this.others = others; 
            this.timeActive = timeActive;
            this.manualReloads = manualReloads;
            this.autoReloads = autoReloads;
            this.contextChanged = contextChanged;
        }

        public Document getInfo() {
            return info;
        }

        public Elements getLinks() {
            return links;
        }

        public Elements getOthers() {
            return others;
        }

        public double getTimeActive() {
            return timeActive;
        }

        public int getManualReloads() {
            return manualReloads;
        }

        public int getAutoReloads() {
            return autoReloads;
        }

        public int getContextChanged() {
            return contextChanged;
        }
        
    }
    */
}
