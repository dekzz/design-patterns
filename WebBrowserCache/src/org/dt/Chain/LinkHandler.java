package org.dt.Chain;

import org.dt.MVC.View.BrowserView;
import org.dt.MVC.Model.WebsiteDataModel;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class LinkHandler extends TagHandler{
    
    WebsiteDataModel website;
    BrowserView view;

    public LinkHandler(WebsiteDataModel website, BrowserView view) {
        this.website = website;
        this.view = view;
    }

    @Override
    public void handleRequest(Document doc, String tag) {
        if(tag.equals("a[href]")) {
            Elements links = doc.select(tag);

            website.setLinks(links);
            if(links.isEmpty())
                view.printEmpty(this.getClass().getName(), doc, tag);
        } else {
            if(successor != null)
                successor.handleRequest(doc, tag);
        }
    }
    
}
