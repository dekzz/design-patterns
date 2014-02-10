
package org.dt.Strategy;

import java.util.HashMap;
import org.dt.MVC.Model.CacheModel;

public interface RemovalStrategy {
    void execute(HashMap<String, CacheModel> map_);
}
