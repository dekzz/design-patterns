
package org.dt.Chain;

import org.jsoup.nodes.Document;

public abstract class TagHandler {
    
    TagHandler successor;
    
    public void setSuccessor(TagHandler successor) {
        this.successor = successor;
    }
    
    public abstract void handleRequest(Document doc, String tag);
    
}
