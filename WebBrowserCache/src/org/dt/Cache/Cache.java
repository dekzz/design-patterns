
package org.dt.Cache;

import org.dt.MVC.Model.CacheModel;

public interface Cache {
    public CacheModel acquire(String url);
    public void release (String url, CacheModel website);
}
