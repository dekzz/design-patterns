package org.dt.chain;

import org.dt.MVC.View.BrowserView;
import org.dt.MVC.Model.WebsiteDataModel;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class MediaHandler extends TagHandler{
    
    WebsiteDataModel website;
    BrowserView view;

    public MediaHandler(WebsiteDataModel website, BrowserView view) {
        this.website = website;
        this.view = view;
    }

    @Override
    public void handleRequest(Document doc, String tag) {
        if(tag.equals("[src]")) {
            Elements media = doc.select(tag);

            website.setOthers(media);
            if(media.isEmpty())
                view.printEmpty(this.getClass().getName(), doc, tag);
        } else 
            if(successor != null)
                successor.handleRequest(doc, tag);
    }
    
}
