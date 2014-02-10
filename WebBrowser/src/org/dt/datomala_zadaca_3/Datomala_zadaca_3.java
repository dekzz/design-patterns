
package org.dt.datomala_zadaca_3;

import org.dt.MVC.View.BrowserView;
import org.dt.MVC.Controller.Controller;
import org.dt.MVC.Model.WebsiteDataModel;

public class Datomala_zadaca_3 {

    public static void main(String[] args) {

        if (args.length == 2) {
            long interval = Long.parseLong(args[1]);
            
            WebsiteDataModel website = new WebsiteDataModel();
            BrowserView view = new BrowserView();
            Controller controller = new Controller(website, view);
            controller.crawl(args[0], interval);
            
        } else {
            throw new IllegalArgumentException("Invalid number of arguments!");
        }

    }
      
}
