
package org.dt.Cache;

import org.dt.datomala_zadaca_4.FileHandler;
import org.dt.datomala_zadaca_4.Log;
import java.util.HashMap;
import org.dt.MVC.Model.CacheModel;
import org.dt.MVC.View.BrowserView;
import org.dt.Strategy.RemovalStrategy;

public class CacheWebsite implements Cache {
    
    String cachePath;
    HashMap<String, CacheModel> map_;
    RemovalStrategy removalStrategy;
    BrowserView view;
    int size;
    String unit;
    String strategy;

    public CacheWebsite(BrowserView view, String cachePath, int size, String unit, String strategy, RemovalStrategy removalStrategy, String clean) {
        this.view = view;
        if(cachePath.endsWith("/"))
            this.cachePath = cachePath;
        else
            this.cachePath = cachePath.concat("/");
        this.map_ = new HashMap<>();
        this.size = size;
        this.unit = unit;
        this.strategy = strategy;
        this.removalStrategy = removalStrategy;
        if(!clean.equals(""))
            FileHandler.clean(cachePath);
    }

    @Override
    public CacheModel acquire(String url) {
        return map_.get(url);
    }

    @Override
    public void release(String url, CacheModel cacheData) {
        if(noSpace()) {
          view.noSpace(strategy);
          removalStrategy.execute(map_);
        }
        map_.put(url, cacheData);
        Log.getInstance().writeLog("ADD | File '" + cacheData.getStoragePath() + "' (" + url + ") added to cache/storage on " + cacheData.getTimeStored());
    }
    
    private boolean noSpace() {
        if(unit.equals("") &&  map_.size() >= size) {
            return true;
        } else if(!unit.equals("") && FileHandler.size(cachePath)/1024 >= size) {
            return true;
        }
        return false;
    }

    public String getCachePath() {
        return cachePath;
    }

    public HashMap<String, CacheModel> getMap_() {
        return map_;
    }

    public void setMap_(HashMap<String, CacheModel> map_) {
        this.map_ = map_;
    }
    
}
