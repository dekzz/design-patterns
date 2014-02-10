package org.dt.Chain;

import org.dt.MVC.View.BrowserView;
import org.dt.MVC.Model.WebsiteDataModel;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ImportsHandler extends TagHandler{
    
    WebsiteDataModel website;
    BrowserView view;

    public ImportsHandler(WebsiteDataModel website, BrowserView view) {
        this.website = website;
        this.view = view;
    }

    @Override
    public void handleRequest(Document doc, String tag) {
        if(tag.equals("link[href]")) {
            Elements imports = doc.select(tag);

            website.setOthers(imports);
            if(imports.isEmpty())
                view.printEmpty(this.getClass().getName(), doc, tag);
        } else
            if(successor != null)
                successor.handleRequest(doc, tag);
    }
    
}
