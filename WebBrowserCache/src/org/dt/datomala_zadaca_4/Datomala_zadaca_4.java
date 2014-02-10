
package org.dt.datomala_zadaca_4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.dt.Cache.CacheWebsite;
import org.dt.MVC.Controller.Controller;
import org.dt.MVC.Model.WebsiteDataModel;
import org.dt.MVC.View.BrowserView;
import org.dt.Strategy.NKPageRemove;
import org.dt.Strategy.NSPageRemove;
import org.dt.Strategy.RemovalStrategy;

public class Datomala_zadaca_4 {
    
    public static void main(String[] args) {
        String commandRegex = "([0-9]+)\\s([0-9]+)\\s(KB\\s)?(NS|NK)(\\sclean)?$";
        Pattern pattern;
        Matcher match;
        String url = "";
        String cachePath = "";
        long interval = 0;
        int size = 0;
        String unit = "";
        String strategy = "";
        String clean = "";
        
       if (args.length >= 5 || args.length <= 7) {
            pattern = Pattern.compile(commandRegex);
            StringBuilder params = new StringBuilder("");
            for(int i = 2; i < args.length; i++) {
                params.append(args[i]);
                params.append(" ");
            }
            match = pattern.matcher(params.toString().trim());
            if(match.matches()) {
                url = args[0];
                cachePath = args[1];
                interval = Long.parseLong(match.group(1).trim());
                size = Integer.parseInt(match.group(2).trim());
                if(match.group(3) != null)
                    unit = match.group(3).trim();
                strategy = match.group(4).trim();
                if(match.group(5) != null)
                    clean = match.group(5).trim();
                
            } else {
                throw new IllegalArgumentException("Invalid arguments!");
            }
            
            Log.setFileName(cachePath.concat("/log.txt"));
            
            WebsiteDataModel websiteData = new WebsiteDataModel();
            BrowserView view = new BrowserView();
            
            RemovalStrategy removalStrategy = null;
            switch (strategy) {
                case "NS":
                    removalStrategy = new NSPageRemove();
                    break;
                case "NK":
                    removalStrategy = new NKPageRemove();
                    break;
            }
            CacheWebsite cachedWebsites = new CacheWebsite(view, cachePath, size, unit, strategy, removalStrategy, clean);
            
            Controller controller = new Controller(websiteData, view, cachedWebsites);
            controller.crawl(url, interval);
            
        } else {
            throw new IllegalArgumentException("Invalid number of arguments!");
        }
    }
    
}