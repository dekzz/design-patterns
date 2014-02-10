
package org.dt.Strategy;

import java.util.HashMap;
import java.util.Map;
import org.dt.datomala_zadaca_4.FileHandler;
import org.dt.datomala_zadaca_4.Log;
import java.util.Date;
import org.dt.MVC.Model.CacheModel;

public class NSPageRemove implements RemovalStrategy{

    @Override
    public void execute(HashMap<String, CacheModel> map_) {
        String oldestKey = "";
        CacheModel oldest = new CacheModel();
        for (Map.Entry pair : map_.entrySet()) {
            CacheModel temp = (CacheModel)pair.getValue();
            if(temp.getTimeStored().before(oldest.getTimeStored())) {
                oldestKey = (String)pair.getKey();
                oldest = temp;
            }
        }
        FileHandler.deleteFile(oldest.getStoragePath());
        map_.remove(oldestKey);
        Log.getInstance().writeLog("DEL | File '" + oldest.getStoragePath() + "' (" + oldestKey + ")" 
                                 + " removed from cache/storage after " + (new Date().getTime() - oldest.getTimeStored().getTime()) /1000
                                 + " sec of activity with " + oldest.getTimesUsed() + " times being used!");
    }
    
}
