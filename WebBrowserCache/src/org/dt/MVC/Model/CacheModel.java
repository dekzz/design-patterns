
package org.dt.MVC.Model;

import java.io.Serializable;
import java.util.Date;

public class CacheModel implements Serializable{
    private String storagePath;
    private Date timeStored;
    private Date lastUsed;
    private int timesUsed;

    public CacheModel() {
        this.storagePath = "";
        this.timeStored = new Date();
        this.lastUsed = new Date();
        this.timesUsed = 0;
    }

    public CacheModel(String storagePath) {
        this.storagePath = storagePath;
        this.timeStored = new Date();
        this.lastUsed = new Date();
        this.timesUsed = 0;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public Date getTimeStored() {
        return timeStored;
    }

    public void setTimeStored(Date timeStored) {
        this.timeStored = timeStored;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }

    public int getTimesUsed() {
        return timesUsed;
    }

    public void setTimesUsed(int timesUsed) {
        this.timesUsed = timesUsed;
    }
    
}
