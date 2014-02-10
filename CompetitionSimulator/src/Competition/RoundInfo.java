/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Competition;

import java.util.ArrayList;

/**
 *
 * @author dex
 */
public class RoundInfo {
    
    public ArrayList<MatchResult> match;

    public RoundInfo(ArrayList<MatchResult> match) {
        this.match = new ArrayList<>(match);
    }

    public ArrayList<MatchResult> getMatch() {
        return match;
    }
    
}
