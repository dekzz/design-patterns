
package org.dt.Strategy;

import java.util.HashMap;
import java.util.Map;
import org.dt.datomala_zadaca_4.FileHandler;
import org.dt.datomala_zadaca_4.Log;
import java.util.Date;
import org.dt.MVC.Model.CacheModel;

public class NKPageRemove implements RemovalStrategy{

    @Override
    public void execute(HashMap<String, CacheModel> map_) {
        String mostUsedKey = "";
        CacheModel mostUsed = new CacheModel();
        for (Map.Entry pair : map_.entrySet()) {
            CacheModel temp = (CacheModel)pair.getValue();
            if(temp.getTimesUsed() >= mostUsed.getTimesUsed()) {
                mostUsedKey = (String)pair.getKey();
                mostUsed = temp;
            }
        }
        FileHandler.deleteFile(mostUsed.getStoragePath());
        map_.remove(mostUsedKey);
        Log.getInstance().writeLog("DEL | File '" + mostUsed.getStoragePath() + "' (" + mostUsedKey + ")" 
                                 + " removed from cache/storage after " + (new Date().getTime() - mostUsed.getTimeStored().getTime()) /1000
                                 + " sec of activity with " + mostUsed.getTimesUsed() + " times being used!");
    }
    
}
